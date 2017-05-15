package cn.appscomm.bluetooth;

import android.bluetooth.BluetoothGatt;

import java.util.Arrays;

/**
 * 作者：hsh
 * 日期：2017/3/17
 * 说明：蓝牙间的总线消息定义
 */
public class BluetoothMessage {
    private static final String MSG_HEAD = "cn.appscomm.bluetooth.";

    public static final String START_SERVICE_SUCCESS = MSG_HEAD + "START_SERVICE_SUCCESS";
    public static final String ACTION_DATA_8002_AVAILABLE = MSG_HEAD + "ACTION_DATA_8002_AVAILABLE";
    public static final String ACTION_DATA_8004_AVAILABLE = MSG_HEAD + "ACTION_DATA_8004_AVAILABLE";
    public static final String ACTION_DATA_8003_SEND_CALLBACK = MSG_HEAD + "ACTION_DATA_8003_SEND_CALLBACK";

    public static final String ACTION_GATT_CONNECTED = MSG_HEAD + "ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = MSG_HEAD + "ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_TIMEOUT = MSG_HEAD + "ACTION_GATT_TIMEOUT";
    public static final String ACTION_GATT_DISCOVERED = MSG_HEAD + "ACTION_GATT_DISCOVERED";
    public static final String ACTION_GATT_REAL_TIME_HEART_RATE = MSG_HEAD + "ACTION_GATT_REAL_TIME_HEART_RATE";

    public String msgType;
    public byte[] msgContent;
    public BluetoothGatt bluetoothGatt;
    public BluetoothLeService bluetoothLeService;

    public BluetoothMessage(String msgType, BluetoothGatt bluetoothGatt, byte[] msgContent) {
        this.msgType = msgType;
        this.bluetoothGatt = bluetoothGatt;
        this.msgContent = msgContent;
    }

    public BluetoothMessage(String msgType, BluetoothLeService bluetoothLeService) {
        this.msgType = msgType;
        this.bluetoothLeService = bluetoothLeService;
    }

    @Override
    public String toString() {
        return "蓝牙总线消息{" +
                "类型=" + msgType +
                ", 内容=" + Arrays.toString(msgContent) +
                '}';
    }
}
