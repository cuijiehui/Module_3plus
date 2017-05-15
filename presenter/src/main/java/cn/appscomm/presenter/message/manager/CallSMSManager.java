package cn.appscomm.presenter.message.manager;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import cn.appscomm.presenter.PresenterAppContext;
import cn.appscomm.presenter.mode.CallSMSInfo;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/20
 * 说明：电话和短信管理
 */

public class CallSMSManager extends Service {

    private static final String TAG = CallSMSManager.class.getSimpleName();
    private boolean callSwitch = false;
    private boolean missCallSwitch = false;
    private boolean smsSwitch = false;

    private String gLastCallNo = "", gLastName = "";                                                // 最后来电号码 最后来电姓名

    private final ContentObserver mSmsMmsObserver = new SmsMmsContentObserver();                    // 短信数据库监听
    private static int unReadSMSNum = 0;                                                            // 未接短息
    private static int missCallNum = 0;                                                             // 未接来电数量
    private MyCallListener customPhoneListener;                                                     // 来电动作监听
    private TelephonyManager tm;
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;                                // 设置初始状态为电话空闲
    private long lastCallStateTimeStamp;                                                            // 最后一次电话状态改变的时间戳

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "电话/短信 推送服务启动......");

        getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, mSmsMmsObserver); // 注册监听短信
        unReadSMSNum = getUnreadSMSCount(PresenterAppContext.INSTANCE.getContext());
        missCallNum = getMissCallCount(PresenterAppContext.INSTANCE.getContext());

        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        customPhoneListener = new MyCallListener();
        tm.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);                       // 监听电话
    }

    private void checkCallSMSSwitch() {
        boolean[] callSMSSwitch = MessageManager.INSTANCE.checkCallSMSSwitch();
        if (callSMSSwitch != null && callSMSSwitch.length == 3) {
            callSwitch = callSMSSwitch[0];
            missCallSwitch = callSMSSwitch[1];
            smsSwitch = callSMSSwitch[2];
        }
        LogUtil.i("NotificationReceiveService", "来电开关:" + callSwitch + " 未接来电开关:" + missCallSwitch + " 短信开关:" + smsSwitch);
    }

    /**
     * 来电监听
     */
    private class MyCallListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {

            LogUtil.w(TAG, "电话状态发生改变...");
            if (Math.abs(System.currentTimeMillis() - lastCallStateTimeStamp) < 200) {
                LogUtil.i(TAG, "两次电话状态变化太快，跳过...");
                return;
            }

            checkCallSMSSwitch();
            lastCallStateTimeStamp = System.currentTimeMillis();

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:                                           // 来电
                    String name = getContactNameByNumber(incomingNumber);                           // 获取来电姓名
                    if ((null == incomingNumber) || (incomingNumber.length() <= 2)) {               // 检查来电号码
                        incomingNumber = "00000000000";
                    }
                    gLastCallNo = incomingNumber;                                                   // 先赋值(后面的未接来电可能要用到)
                    gLastName = TextUtils.isEmpty(name) ? "" : name;                                // 先赋值(后面的未接来电可能要用到)
                    if (!callSwitch) return;
                    LogUtil.i(TAG, "来电.............姓名:" + name + "   号码:" + incomingNumber);
                    MessageManager.INSTANCE.sendIncomeCall(new CallSMSInfo(gLastName, gLastCallNo, 1));
                    break;

                case TelephonyManager.CALL_STATE_IDLE:                                              // 空闲
                case TelephonyManager.CALL_STATE_OFFHOOK:                                           // 挂机
                    LogUtil.i(TAG, "--->挂断 发送来电挂断");
                    MessageManager.INSTANCE.sendOffCall();
                    break;
            }

            // 未接来电&挂断 (发送未接前先发送挂断)
            if (lastState == TelephonyManager.CALL_STATE_RINGING && state == TelephonyManager.CALL_STATE_IDLE && missCallSwitch) {
                incomingNumber = TextUtils.isEmpty(incomingNumber) ? gLastCallNo : incomingNumber;  // 某些手机来电incomingNumber为空，这时需要用来电的号码
                String name = getContactNameByNumber(incomingNumber);                               // 获取未接姓名
                if ((null == incomingNumber) || (incomingNumber.length() <= 2)) {                   // 检查未接号码
                    incomingNumber = "00000000000";
                }
                int mNum = getMissCallCount(getApplicationContext());
                LogUtil.i(TAG, "数据库未接来电数量:" + mNum + " 本地未读来电数量:" + missCallNum);
                if (mNum != missCallNum && mNum > 0) {
                    LogUtil.i(TAG, "--->未接 发送未接来电");
                    MessageManager.INSTANCE.sendMissCall(new CallSMSInfo(name, incomingNumber, mNum));
                } else {
                    LogUtil.i(TAG, "xxx未接 自己挂断,不发送数量");
                }
                missCallNum = mNum;
                LogUtil.i(TAG, "更新本地未读来电数量:" + missCallNum);
            }

            lastState = state;
        }
    }

    /**
     * 短信监听
     */
    private class SmsMmsContentObserver extends ContentObserver {

        SmsMmsContentObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {
            int mNum = getUnreadSMSCount(getApplicationContext());
            LogUtil.i(TAG, "短信来了... mNum : " + mNum + " unReadSMSNum : " + unReadSMSNum);
            CallSMSInfo callSMSInfo = getLastSMS();
            if (callSMSInfo != null) {
                MessageManager.INSTANCE.sendSMS(callSMSInfo);
            } else {
                LogUtil.i(TAG, "短信为空...");
            }
            super.onChange(selfChange);
        }
    }

    // 获取未读短信数量
    private int getUnreadSMSCount(Context context) {
        Cursor cur = null;
        int smsCount = 0;
        try {
            cur = context.getContentResolver().query(Uri.parse("content://sms"), null, "type = 1 and read = 0", null, null);
            if (null != cur) {
                smsCount = cur.getCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
        return smsCount;
    }

    // 获取未接电话数量
    private int getMissCallCount(Context context) {
        Cursor cur = null;
        int missCallCount = 0;
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, "type = 3 and new = 1", null, null);
                if (null != cur) {
                    missCallCount = cur.getCount();
                }
            }
            return missCallCount;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
        return missCallCount;
    }

    // 获取指定号码联系人姓名
    private String getContactNameByNumber(String phoneNumber) {
        try {
            Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phoneNumber);

            Cursor cursor = getContentResolver().query(uri, new String[]{"display_name"}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(0);
                    LogUtil.i(TAG, name);
                    if (name != null) {
                        cursor.close();
                        return name;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // 获取手机上最后一条短信
    public synchronized CallSMSInfo getLastSMS() {
        try {
            CallSMSInfo callSMSInfo = new CallSMSInfo();
            String[] projection = new String[]{"address", "person", "body", "date"};
            String where = " type = 1 and read = 0 and  date >  " + (System.currentTimeMillis() - 30 * 1000);
            Cursor cur = getContentResolver().query(Uri.parse("content://sms/inbox"), projection, where, null, "date desc");
            if (null == cur)
                return null;
//            LogUtil.i(TAG, "move To first : " + (cur.moveToFirst() == true) + " cur : " + (cur != null));
            if (cur.moveToFirst()) {
                String number = cur.getString(cur.getColumnIndex("address"));
                String name = "1";
                try {
                    name = getContactNameByNumber(number);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String body = cur.getString(cur.getColumnIndex("body"));
                callSMSInfo.name = name;
                callSMSInfo.phoneNo = number;
                callSMSInfo.content = body;
                callSMSInfo.receiveTime = cur.getLong((cur.getColumnIndex("date")));
                return callSMSInfo;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
