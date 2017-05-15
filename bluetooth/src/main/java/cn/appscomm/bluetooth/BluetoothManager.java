package cn.appscomm.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import cn.appscomm.bluetooth.implement.RemoteControlSend;
import cn.appscomm.bluetooth.mode.Device;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;

/**
 * 作者：hsh
 * 日期：2017/3/17
 * 说明：蓝牙管理类
 * 1、蓝牙服务的开启和关闭
 * 2、蓝牙数据的统一发送(数据由BluetoothSend整理好)
 * 3、蓝牙数据的接收(使用总线来接收,需要解析就给BluetoothParse类来解析)
 */
public enum BluetoothManager {
    INSTANCE;
    private static final String TAG = BluetoothManager.class.getSimpleName();
    private Context context = BluetoothAppContext.INSTANCE.getContext();
    private final int MAX_RECONNECT_COUNT = 10;                                                     // 最大重连次数10，若超过则清空缓存

    private BluetoothLeService mBluetoothLeService;                                                 // 蓝牙服务对象

    // 初始化数据(把各变量初始化一遍)
    private void initData(Device device) {
        try {
            if (mBluetoothLeService != null) {
                if (device == null)
                    mBluetoothLeService.disconnectAll();
                else
                    mBluetoothLeService.disconnect(device.bluetoothGatt);
                mBluetoothLeService = null;
            }
            if (device == null) {
                for (Map.Entry<String, Device> entry : BluetoothLeService.deviceMap.entrySet()) {
                    if (entry.getValue() != null) {
                        entry.getValue().bluetoothSend.clear();
                    }
                }
            } else {
                device.bluetoothSend.clear();
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
     */
    public void startService() {
        setEventBus(true);
        Intent gattServiceIntent = new Intent(context, BluetoothLeService.class);
        context.startService(gattServiceIntent);
    }

    /**
     * 重启服务
     *
     * @param mac 要连接的设备的MAC地址
     * @return 需要连接的mac地址不为空，可以重启服务
     */
    public boolean restartService(String mac, boolean isSupportHeartRate) {
        LogUtil.i(TAG, "重启BluetoothLeService服务...");
        if (TextUtils.isEmpty(mac)) {
            LogUtil.i(TAG, "传入的mac为空，无法重启服务");
            return false;
        }
        Device device = BluetoothLeService.deviceMap.get(mac);
        if (device != null) {
            endService(device.mac);
            connect(device.mac, isSupportHeartRate);
            return true;
        }
        return false;
    }

    /**
     * 结束服务
     */
    public void endService(String mac) {
        Device device = BluetoothLeService.deviceMap.get(mac);
        if (device == null)
            setEventBus(false);
        LogUtil.i(TAG, "BluetoothLeService服务关闭");
        initData(device);
    }

    /**
     * 手机是否连接设备
     *
     * @return true：已连接 false：未连接
     */
    public boolean isConnect(String mac) {
        Device device = BluetoothLeService.deviceMap.get(mac);
        return device != null && device.mBluetoothDiscoverFlag;
    }

    // 蓝牙服务是否开启
    public boolean isBluetoothLeServiceRunning() {
        return mBluetoothLeService != null;
    }

    // 连接设备
    public void connect(String mac, boolean isSupportHeartRate) {
        if (TextUtils.isEmpty(mac)) {
            LogUtil.i(TAG, "mac为空，不能连接，关闭服务");
            endService(mac);
            return;
        }
        if (mBluetoothLeService == null) {
            LogUtil.i(TAG, "mBluetoothLeService为null，但mac(" + mac + ")不为空，重启服务");
            startService();
            return;
        }
        if (!mBluetoothLeService.connect(mac, isSupportHeartRate)) {
            LogUtil.i(TAG, "连接设备失败，重启服务");
            restartService(mac, isSupportHeartRate);
        }
    }

    /**
     * 发送数据
     */
    public void send(String mac) {
        if (TextUtils.isEmpty(mac)) return;
        LogUtil.i(TAG, "--- mac : " + mac);
        Device device = BluetoothLeService.deviceMap.get(mac);
        if (device != null) {
            if (!device.mBluetoothDiscoverFlag) {
                LogUtil.e(TAG, "发送失败：已连接设备，但没有发现服务");
                return;
            }
            if (device.isSendDataFlag) {
                LogUtil.e(TAG, "发送失败：有数据在发送中...");
                return;
            }
            device.leaf = device.bluetoothSend.getSendCommand();
            if (device.leaf == null) {
                LogUtil.e(TAG, "发送失败：没有需要发送的数据");
                return;
            }
            byte[] sendData = device.leaf.getSendData();
            if (sendData != null && sendData.length > 0) {
                LogUtil.w(TAG, "发送：" + ParseUtil.byteArrayToHexString(sendData));
                device.isSendDataFlag = true;
                device.sendDataToDevice(sendData, device.leaf.getBluetoothSendType());
            } else {
                LogUtil.e(TAG, "发送失败：Leaf整理为要发送的byte[]时有误");
            }
        }
    }

    // 继续发送(内部使用)
    private void continueSend(Device device) {
        device.isSendDataFlag = false;
        send(device.mac);
    }

    // 蓝牙断开，断开次数大于10次，清空缓存
    private void doReconnect(Device device) {
        LogUtil.i(TAG, "蓝牙断连次数:" + (device.reconnectCount + 1));
        if (++device.reconnectCount > MAX_RECONNECT_COUNT) {
            device.bluetoothSend.clear();
            device.reconnectCount = 0;
        }
        connect(device.mac, device.isSupportHeartRate);
    }

    // EventBus 事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBluetoothMessageHandle(BluetoothMessage event) {
        if (event != null && !TextUtils.isEmpty(event.msgType)) {
            switch (event.msgType) {

                case BluetoothMessage.START_SERVICE_SUCCESS:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 开启服务成功(" + event.msgType + ")--------------------------");
                    mBluetoothLeService = event.bluetoothLeService;
                    break;

                case BluetoothMessage.ACTION_GATT_CONNECTED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 已连接(" + event.msgType + ")----------------------------------");
                    if (mBluetoothLeService != null) {
                        Device device = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (device != null) {
                            device.mBluetoothConnectFlag = true;
                            device.mBluetoothDiscoverFlag = false;
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_DISCONNECTED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 断开连接(" + event.msgType + ")-----------------------------");
                    if (mBluetoothLeService != null) {
                        Device device = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (device != null) {
                            device.mBluetoothConnectFlag = false;
                            device.mBluetoothDiscoverFlag = false;
                            doReconnect(device);
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_TIMEOUT:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 超时(" + event.msgType + ")----------------------------------");
                    if (mBluetoothLeService != null) {
                        Device device = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (device != null) {
                            doCallBack(BluetoothCommandConstant.RESULT_CODE_FAIL, device);
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_DISCOVERED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 发现服务(" + event.msgType + ")----------------------");
                    if (mBluetoothLeService != null) {
                        Device device = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        LogUtil.i(TAG, "device : " + (device != null));
                        if (device != null) {
                            device.reconnectCount = 0;                                              // 重连次数清0
                            if (device.mBluetoothConnectFlag) {                                     // mBluetoothConnectFlag = true 说明有连接
                                device.mBluetoothDiscoverFlag = true;                               // 只有mmBluetoothConnectFlag为true，mBluetoothDiscoverFlag才可以为true
                                device.isSendDataFlag = false;                                      // 连接并发现服务后，释放发送标志，以便可以继续发送数据
                                send(device.mac);                                                   // 发送数据
                            } else {
                                device.mBluetoothDiscoverFlag = false;                              // 还没有CONNECTED状态，却有DISCOVERED状态，有问题
                                restartService(device.mac, device.isSupportHeartRate);              // 重启服务
                            }
                        }
                    }
                    for (Map.Entry<String, Device> entry : BluetoothLeService.deviceMap.entrySet()) {
                        LogUtil.i(TAG, "mac(" + entry.getKey() + ") device(" + entry.getValue() + ")");
                    }

                    break;

                case BluetoothMessage.ACTION_DATA_8002_AVAILABLE:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 接收到数据(" + event.msgType + ")------------------------------");
                    Device device = mBluetoothLeService.getDevice(event.bluetoothGatt);
                    if (device != null) {
                        device.mBluetoothConnectFlag = true;
                        device.mBluetoothDiscoverFlag = true;
                        LogUtil.i(TAG, "leaf : " + (device.leaf != null));
                        if (device.leaf != null) {
                            BluetoothParse bluetoothParse = device.bluetoothParse;
                            byte[] bytes = bluetoothParse.proReceiveData(event.msgContent);
                            if (bytes == null) {
                                LogUtil.i(TAG, "整理：接收到的数据有异常了，无法整理...");
                                // TODO
                                return;
                            }
                            if (bytes.length == 1) {
                                switch (bytes[0]) {
                                    case BluetoothCommandConstant.RESULT_CODE_PROTOCOL_ERROR:           // 回调失败
                                        doCallBack(BluetoothCommandConstant.RESULT_CODE_ERROR, device);
                                        break;
                                    case BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE:         // 继续接收
                                        return;
                                    case BluetoothCommandConstant.RESULT_CODE_EXCEPTION:                // 异常
                                        // TODO
                                        break;
                                }
                            } else {
                                LogUtil.i(TAG, "整条命令：" + ParseUtil.byteArrayToHexString(bytes));
                                int ret = bluetoothParse.parseBluetoothData(bytes, device.leaf);
                                if (ret == BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE) {     // 继续接收命令
                                    LogUtil.i(TAG, "数据还没有接收完，继续接收数据...");
                                    return;
                                }
                                if (ret == BluetoothCommandConstant.RESULT_CODE_RE_SEND) {              // 命令需要重新发送
                                    continueSend(device);
                                    return;
                                }
                                doCallBack(ret, device);
                            }
                        }
                        continueSend(device);
                    }
                    break;

                case BluetoothMessage.ACTION_DATA_8003_SEND_CALLBACK:
                    device = mBluetoothLeService.getDevice(event.bluetoothGatt);
                    if (device != null) {
                        if (device.leaf != null && !device.leaf.isActiveCommand()) {                                  // 被动命令，设备不会发送数据，这里可以继续发送数据了
                            device.bluetoothSend.removeFirst();
                            continueSend(device);
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_DATA_8004_AVAILABLE:                                   // 远程控制命令(音乐、拍照、寻找手机)
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 远程控制(" + event.msgType + ")------------------------------");
                    if (event.msgContent != null && event.msgContent.length > 0) {
                        RemoteControlSend.INSTANCE.parse(event.msgContent);
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_REAL_TIME_HEART_RATE:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 实时心率(" + event.msgType + ")----------------------");
                    byte[] bHeartRateValue = event.msgContent;
                    BluetoothVar.realTimeHeartRateValue = bHeartRateValue[1] & 0xFF;
                    LogUtil.v(TAG, "心率值:" + BluetoothVar.realTimeHeartRateValue);
                    break;

            }
        }
    }

    // 回调结果给调用者(0:成功 1:失败 2:协议解析错误 3:数据还没有接收完 -1:没有进行解析命令 -2:大字节数组接收错误)，最后释放发送标志，并接续发送数据
    private void doCallBack(int result, Device device) {
        if (device.bluetoothSend.getSendDataSize() > 0) {
            device.bluetoothSend.removeFirst();
            IBluetoothResultCallback iBluetoothResultCallback = device.leaf.getIBluetoothResultCallback();
            if (iBluetoothResultCallback != null) {
                switch (result) {
                    case BluetoothCommandConstant.RESULT_CODE_SUCCESS:                              // 0、成功
                        LogUtil.i(TAG, "成功,准备回调!!!");
                        iBluetoothResultCallback.onSuccess(device.leaf, device.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_FAIL:                                 // 1、失败
                        LogUtil.i(TAG, "失败,准备回调!!!");
                        iBluetoothResultCallback.onFailed(device.leaf, device.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_PROTOCOL_ERROR:                       // 2、协议解析错误
                        LogUtil.i(TAG, "协议解析错误,准备回调!!!");
                        iBluetoothResultCallback.onFailed(device.leaf, device.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_ERROR:                                // -1、错误
                        LogUtil.i(TAG, "错误,重发并准备回调!!!");
                        iBluetoothResultCallback.onFailed(device.leaf, device.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_LARGE_BYTES_ERROR:                     // -2、大字节数组接收错误
                        LogUtil.i(TAG, "大字节数组接收错误,准备回调!!!");
                        iBluetoothResultCallback.onFailed(device.leaf, device.mac);
                        break;
                }
            } else {
                LogUtil.i(TAG, "iBluetoothResultCallback 为null,不需要回调");
            }
        }
    }
}
