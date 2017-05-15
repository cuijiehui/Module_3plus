package cn.appscomm.presenter.message.manager;


import android.text.TextUtils;

import java.util.Date;

import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.presenter.interfaces.PVBluetoothCall;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;
import cn.appscomm.presenter.interfaces.PVSPCall;
import cn.appscomm.presenter.mode.CallSMSInfo;
import cn.appscomm.presenter.mode.NotificationInfo;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/20
 * 说明：
 */

public enum MessageManager {
    INSTANCE;

    private static final String TAG = MessageManager.class.getSimpleName();
    private PVSPCall pvSPCall = PSP.INSTANCE;
    private PVBluetoothCall pvBluetoothCall = PBluetooth.INSTANCE;

    /**
     * 发送来电
     *
     * @param callSMSInfo 来电信息
     */
    public void sendIncomeCall(CallSMSInfo callSMSInfo) {
        LogUtil.i(TAG, "发送：来电");
        String nameOrNumber = TextUtils.isEmpty(callSMSInfo.name) ? callSMSInfo.phoneNo : callSMSInfo.name;
        pvBluetoothCall.sendIncomeCall(pvBluetoothCallback, nameOrNumber, null);
    }


    /**
     * 发送未接来电
     *
     * @param callSMSInfo 未接信息
     */
    public void sendMissCall(CallSMSInfo callSMSInfo) {
        LogUtil.i(TAG, "发送：未接来电");
        String nameOrNumber = TextUtils.isEmpty(callSMSInfo.name) ? callSMSInfo.phoneNo : callSMSInfo.name;
        pvBluetoothCall.sendMissCall(pvBluetoothCallback, nameOrNumber, new Date(), callSMSInfo.callSMSCount, null);
    }

    /**
     * 发送挂断
     */
    public void sendOffCall() {
        LogUtil.i(TAG, "发送：来电挂断");
        pvBluetoothCall.sendOffCall(pvBluetoothCallback, null);
    }

    /**
     * 发送短信
     *
     * @param callSMSInfo 短信信息
     */
    public void sendSMS(CallSMSInfo callSMSInfo) {
        LogUtil.i(TAG, "发送：短信");
        String nameOrNumber = TextUtils.isEmpty(callSMSInfo.name) ? callSMSInfo.phoneNo : callSMSInfo.name;
        pvBluetoothCall.sendSMS(pvBluetoothCallback, nameOrNumber, callSMSInfo.content, new Date(callSMSInfo.receiveTime), callSMSInfo.callSMSCount, null);
    }

    /**
     * 发送社交
     *
     * @param notificationInfo 社交信息
     */
    public void sendSocial(NotificationInfo notificationInfo) {
        LogUtil.i(TAG, "发送：社交");
        pvBluetoothCall.sendSocial(pvBluetoothCallback, notificationInfo.title, notificationInfo.content, new Date(notificationInfo.timeStamp), notificationInfo.notificationType, notificationInfo.notificationCount, null);
    }

    /**
     * 发送邮件
     *
     * @param notificationInfo 邮件信息
     */
    public void sendEmail(NotificationInfo notificationInfo) {
        LogUtil.i(TAG, "发送：邮件");
        pvBluetoothCall.sendEmail(pvBluetoothCallback, notificationInfo.title, notificationInfo.content, new Date(notificationInfo.timeStamp), notificationInfo.notificationCount, null);
    }

    /**
     * 发送日历
     *
     * @param calendarCount 日历条数
     */
    public void sendCalendar(int calendarCount) {
        LogUtil.i(TAG, "发送：日历");
        pvBluetoothCall.sendSchedule(pvBluetoothCallback, "", new Date(), calendarCount, null);
    }

    /**
     * 检查电话和短信开关
     *
     * @return 0:incomeCall 1:missCall 2:SMS
     */
    public boolean[] checkCallSMSSwitch() {
        return new boolean[]{pvSPCall.getCallSwitch(), pvSPCall.getMissCallSwitch(), pvSPCall.getSMSSwitch()};
    }

    /**
     * 检查通知开关
     *
     * @return 0:social 1:email 2:calendar
     */
    public boolean[] checkNotificationSwitch() {
        return new boolean[]{pvSPCall.getSocialSwitch(), pvSPCall.getEmailSwitch(), pvSPCall.getCalendarSwitch()};
    }


    private PVBluetoothCallback pvBluetoothCallback = new PVBluetoothCallback() {
        @Override
        public void onSuccess(Object[] objects, int len, int dataType, String mac, BluetoothCommandType bluetoothCommandType) {
            printfResult(true, bluetoothCommandType);
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            printfResult(false, bluetoothCommandType);
        }
    };

    private void printfResult(boolean isSuccess, PVBluetoothCallback.BluetoothCommandType bluetoothCommandType) {
        String result = isSuccess ? "成功" : "失败";
        switch (bluetoothCommandType) {
            case SEND_INCOME_CALL_NAME_OR_NUMBER:
                LogUtil.w(TAG, "==>来电 1、姓名或号码" + result);
                break;
            case SEND_INCOME_CALL_COUNT:
                LogUtil.w(TAG, "==>来电 2、数量" + result);
                break;
            case SEND_MISS_CALL_NAME_OR_NUMBER:
                LogUtil.w(TAG, "==>未接来电 1、姓名或号码" + result);
                break;
            case SEND_MISS_CALL_COUNT:
                LogUtil.w(TAG, "==>未接来电 2、数量" + result);
                break;
            case SEND_SMS_NAME_OR_NUMBER:
                LogUtil.w(TAG, "==>短信 1、姓名或号码" + result);
                break;
            case SEND_SMS_CONTENT:
                LogUtil.w(TAG, "==>短信 2、内容" + result);
                break;
            case SEND_SMS_DATETIME:
                LogUtil.w(TAG, "==>短信 3、时间" + result);
                break;
            case SEND_SMS_COUNT:
                LogUtil.w(TAG, "==>短信 4、数量" + result);
                break;
            case SEND_SOCIAL_TITLE:
                LogUtil.w(TAG, "==>社交 1、标题" + result);
                break;
            case SEND_SOCIAL_CONTENT:
                LogUtil.w(TAG, "==>社交 2、内容" + result);
                break;
            case SEND_SOCIAL_DATETIME:
                LogUtil.w(TAG, "==>社交 3、日期时间" + result);
                break;
            case SEND_SOCIAL_COUNT:
                LogUtil.w(TAG, "==>社交 4、条数" + result);
                break;
            case SEND_EMAIL_ADDRESS:
                LogUtil.w(TAG, "==>邮件 1、地址" + result);
                break;
            case SEND_EMAIL_CONTENT:
                LogUtil.w(TAG, "==>邮件 2、内容" + result);
                break;
            case SEND_EMAIL_DATETIME:
                LogUtil.w(TAG, "==>邮件 3、日期时间" + result);
                break;
            case SEND_EMAIL_COUNT:
                LogUtil.w(TAG, "==>邮件 4、数量" + result);
                break;
            case SEND_SCHEDULE_CONTENT:
                LogUtil.w(TAG, "==>日历 1、内容" + result);
                break;
            case SEND_SCHEDULE_DATETIME:
                LogUtil.w(TAG, "==>日历 2、日期时间" + result);
                break;
            case SEND_SCHEDULE_COUNT:
                LogUtil.w(TAG, "==>日历 3、数量" + result);
                break;
        }
    }
}
