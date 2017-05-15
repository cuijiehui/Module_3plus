package cn.appscomm.ota;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.appscomm.ota.interfaces.IUpdateProgressCallBack;
import cn.appscomm.ota.util.LogUtil;
import cn.appscomm.ota.util.OtaAppContext;

/**
 * 作者：hsh
 * 日期：2017/5/2
 * 说明：OTA升级管理类
 */

public enum OtaManager {
    INSTANCE;

    private static final String TAG = OtaManager.class.getSimpleName();

    private String mac;                                                                             // 需要连接的mac地址
    private boolean mBluetoothConnectFlag;                                                          // 是否已连接
    private static boolean mStartServiceFlag = false;                                               // 是否之前已绑定服务

    private OtaService mOtaService;                                                                 // 蓝牙服务对象
    private IUpdateProgressCallBack iUpdateProgressCallBack;                                        // 升级进度、结果的回调

    // 初始化数据(把各变量初始化一遍)
    private void initData() {
        try {
            mac = "";
            mBluetoothConnectFlag = false;
            if (mOtaService != null) {
                mOtaService.disconnect();
                mOtaService = null;
            }
            if (mStartServiceFlag) {
                OtaAppContext.INSTANCE.getContext().unbindService(mServiceConnection);              // 如果没有绑定服务却解绑服务会抛异常
                mStartServiceFlag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开或关闭EventBus
    private void setEventBus(boolean isOpen) {
        if ((isOpen && EventBus.getDefault().isRegistered(this)) || (!isOpen && !EventBus.getDefault().isRegistered(this)))
            return;
        if (isOpen) {
            EventBus.getDefault().register(this);
        } else {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 开启服务
     *
     * @param mac 要连接的设备的MAC地址
     */
    public void update(String mac, List<String> pathList, IUpdateProgressCallBack iUpdateProgressCallBack) {
        this.mac = mac;
        this.iUpdateProgressCallBack = iUpdateProgressCallBack;
        if (TextUtils.isEmpty(this.mac) || !OtaCommand.INSTANCE.create(pathList)) {
            LogUtil.i(TAG, "mac为空 或 生成升级命令失败");
            sendResult(false);
            return;
        }
        LogUtil.i(TAG, "上次OtaManager开启状态 : " + mStartServiceFlag);
        setEventBus(true);
        if (!mStartServiceFlag) {
            mStartServiceFlag = true;
            Intent gattServiceIntent = new Intent(OtaAppContext.INSTANCE.getContext(), OtaService.class);
            OtaAppContext.INSTANCE.getContext().bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);   // 绑定BluetoothLeServiceEx服务
        }
    }

    // 连接设备
    private void connect() {
        if (TextUtils.isEmpty(mac)) {
            LogUtil.i(TAG, "mac为空，不能连接，关闭服务");
            endService();
            return;
        }
        if (mOtaService == null) {
            LogUtil.i(TAG, "mBluetoothLeService为null，但mac(" + mac + ")不为空，重启服务");
            return;
        }
        if (!mOtaService.connect(mac)) {
            LogUtil.i(TAG, "连接设备失败，重启服务");
        }
    }

    /**
     * 结束服务
     */
    public void endService() {
        setEventBus(false);
        LogUtil.i(TAG, "BluetoothLeService服务关闭");
        initData();
    }

    // 开始升级
    private void startUpdate() {
        OtaCommand.INSTANCE.start(mOtaService, iUpdateProgressCallBack);
    }

    // 发送结果
    private void sendResult(boolean result) {
        if (iUpdateProgressCallBack != null) {
            iUpdateProgressCallBack.updateResult(result);
        }
        endService();
    }

    // 处理解析的结果
    private void proResult(int result) {
        switch (result) {
            case OtaCommand.UPDATE_FAIL:
                sendResult(false);
                break;
            case OtaCommand.UPDATE_SUCCESS:
                sendResult(true);
                break;
            case OtaCommand.UPDATE_ING:
                break;
        }
    }

    // 绑定BluetoothLeService服务的回调
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            LogUtil.i(TAG, "OtaService服务开启成功,准备连接 mac : " + mac);
            mOtaService = ((OtaService.LocalBinder) service).getService();
            connect();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.e(TAG, "OtaService服务开启失败");
            mOtaService = null;
        }
    };

    // EventBus 事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBluetoothMessageHandle(OtaMessage event) {
        if (event != null && !TextUtils.isEmpty(event.msgType)) {
            switch (event.msgType) {

                case OtaMessage.ACTION_GATT_CONNECTED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 已连接(" + event.msgType + ")----------------------------------");
                    mBluetoothConnectFlag = true;
                    break;

                case OtaMessage.ACTION_GATT_DISCONNECTED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 断开连接(" + event.msgType + ")-----------------------------");
                    mBluetoothConnectFlag = false;
                    sendResult(false);
                    break;

                case OtaMessage.ACTION_GATT_DISCOVERED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 发现服务(" + event.msgType + ")----------------------");
                    if (mBluetoothConnectFlag) {
                        startUpdate();
                    }
                    break;

                case OtaMessage.ACTION_DATA_AVAILABLE:
//                    LogUtil.v(TAG, "----------------------蓝牙消息 : 接收到数据(" + event.msgType + ")-----------------------------");
                    proResult(OtaCommand.INSTANCE.parse(event.msgContent));
                    break;

                case OtaMessage.ACTION_DATA_WRITE_CALLBACK:
//                    LogUtil.i(TAG, "----------------------蓝牙消息 : 写回调(" + event.msgType + ")-----------------------------");
                    proResult(OtaCommand.INSTANCE.parse(null));
                    break;
            }
        }
    }
}