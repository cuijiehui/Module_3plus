package cn.appscomm.bluetooth.mode;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.UUID;

import cn.appscomm.bluetooth.BluetoothLeService;
import cn.appscomm.bluetooth.BluetoothMessage;
import cn.appscomm.bluetooth.BluetoothParse;
import cn.appscomm.bluetooth.BluetoothSend;
import cn.appscomm.bluetooth.protocol.Leaf;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;

/**
 * 作者：hsh
 * 日期：2017/5/10
 * 说明：
 */

public class Device {

    public String mac;
    public BluetoothGatt bluetoothGatt;
    public boolean isSupportHeartRate;
    public LinkedList<NotificationInfo> notifications = new LinkedList<>();
    public Handler mHandler = null;
    public boolean mBluetoothConnectFlag;                                                           // 是否已连接
    public boolean mBluetoothDiscoverFlag;                                                          // 是否已发现服务
    public int reconnectCount;                                                                      // 重连次数
    public boolean isSendDataFlag;                                                                  // 是否处于发送数据中
    public Leaf leaf;                                                                               // 当前发送的Leaf
    public BluetoothSend bluetoothSend = new BluetoothSend();                                       // 每个设备都有自己的BluetoothSend
    public BluetoothParse bluetoothParse = new BluetoothParse();                                    // 每个设备都有自己的BluetoothParse

    private UUID UUID_CHARACTERISTIC_8001 = UUID.fromString("00008001-0000-1000-8000-00805f9b34fb");
    private UUID UUID_CHARACTERISTIC_8002 = UUID.fromString("00008002-0000-1000-8000-00805f9b34fb");
    private UUID UUID_SERVICE_BASE = UUID.fromString("00006006-0000-1000-8000-00805f9b34fb");
    private UUID UUID_CHARACTERISTIC_8003 = UUID.fromString("00008003-0000-1000-8000-00805f9b34fb");
    private UUID UUID_CHARACTERISTIC_8004 = UUID.fromString("00008004-0000-1000-8000-00805f9b34fb");
    private UUID UUID_SERVICE_EXTEND = UUID.fromString("00007006-0000-1000-8000-00805f9b34fb");
    private UUID UUID_CHARACTERISTIC_HEART_RATE = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    private UUID UUID_SERVICE_HEART_RATE = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");


    public Device(String mac, BluetoothGatt bluetoothGatt, boolean isSupportHeartRate, Handler handler) {
        this.mac = mac;
        this.bluetoothGatt = bluetoothGatt;
        this.isSupportHeartRate = isSupportHeartRate;
        this.mHandler = handler;
    }

    public void addNotification() {
        notifications.clear();
        notifications.addLast(new NotificationInfo(UUID_SERVICE_BASE, UUID_CHARACTERISTIC_8002));
        notifications.addLast(new NotificationInfo(UUID_SERVICE_EXTEND, UUID_CHARACTERISTIC_8004));
        if (isSupportHeartRate)
            notifications.addLast(new NotificationInfo(UUID_SERVICE_HEART_RATE, UUID_CHARACTERISTIC_HEART_RATE));
    }

    public class NotificationInfo {
        public UUID service;
        public UUID characteristic;

        NotificationInfo(UUID service, UUID characteristic) {
            this.service = service;
            this.characteristic = characteristic;
        }
    }

    // 发送数据到设备超时处理
    public int sendTimeOutCount = 0;                                                               // 发送超时计数(0表示不计数 其他值则进行计数)
    private Runnable checkSendTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            if (sendTimeOutCount > 0) {
                LogUtil.i("service_timeout", "距离上次发送数据，已经用时:" + (SEND_DATA_TIMEOUT - sendTimeOutCount) + "秒...!!!");
                if (--sendTimeOutCount <= 0) {
                    LogUtil.i("service_timeout", "---发送的数据已超时" + SEND_DATA_TIMEOUT + "秒");
                    sendTimeOutCount = 0;
                    disconnect(bluetoothGatt);
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED, bluetoothGatt, null));
                    return;
                }
                if (mHandler != null)
                    mHandler.postDelayed(checkSendTimeoutRunnable, 1000);
            }
        }
    };

    /**
     * 发送数据到设备
     *
     * @param bytes    要发送的数据
     * @param sendType 发送类型
     */
    private byte[] sendBytes = null;                                                                // 发送的字节数组
    private int sendBytesPacketCount = 0;                                                           // 发送的包数
    private final int SEND_DATA_MAX_LEN = 20;                                                       // 一包发送数据的最大长度
    private final int SEND_DATA_TIMEOUT = 10;                                                       // 发送数据超时时间10秒

    public void sendDataToDevice(byte[] bytes, int sendType) {
        sendBytes = null;
        sendBytesPacketCount = 0;
        if (bytes != null) {
            byte[] firstBytes = null;
            if (bytes.length > SEND_DATA_MAX_LEN) {
                firstBytes = new byte[SEND_DATA_MAX_LEN];
                sendBytes = bytes;
                sendBytesPacketCount = sendBytes.length / SEND_DATA_MAX_LEN + (sendBytes.length % SEND_DATA_MAX_LEN == 0 ? 0 : 1);
                System.arraycopy(sendBytes, 0, firstBytes, 0, SEND_DATA_MAX_LEN);
                LogUtil.i("test_sendLargeBytes", "大字节数组发送第一包:" + ParseUtil.byteArrayToHexString(firstBytes) + " 共" + sendBytesPacketCount + "包数据!!!");
                sendBytesPacketCount--;                                                                 // 发了一包，总数减一
            }
            send(firstBytes == null ? bytes : firstBytes, sendType);
        }
    }

    // 继续发送数据(false：已经发送完毕 true：继续发送)
    public boolean continueSendBytes(int sendType) {
        if (sendBytesPacketCount != 0) {
            byte[] tmpBytes = sendBytesPacketCount == 1 ?
                    new byte[sendBytes.length % SEND_DATA_MAX_LEN == 0 ? SEND_DATA_MAX_LEN : sendBytes.length % SEND_DATA_MAX_LEN] :
                    new byte[SEND_DATA_MAX_LEN];
            int count = sendBytes.length / SEND_DATA_MAX_LEN + (sendBytes.length % SEND_DATA_MAX_LEN == 0 ? 0 : 1);
            int index = count - sendBytesPacketCount;
            System.arraycopy(sendBytes, SEND_DATA_MAX_LEN * index, tmpBytes, 0, tmpBytes.length);
            LogUtil.i("test_sendLargeBytes", "还有" + (sendBytesPacketCount == 1 ? "最后一" : sendBytesPacketCount) + "包没有发!!!");
            LogUtil.i("test_sendLargeBytes", "索引 : " + index + "   长度 : " + tmpBytes.length + "   总长度 : " + sendBytes.length);
            LogUtil.i("test_sendLargeBytes", "包数据是：" + ParseUtil.byteArrayToHexString(tmpBytes));
            sendBytesPacketCount--;                                                                 // 每发一包，减一包
            send(tmpBytes, sendType);
            return true;
        }
        return false;
    }

    // 发送
    private void send(byte bytes[], int sendType) {
        if (bluetoothGatt == null) return;
        BluetoothGattCharacteristic characteristic;
        try {
            switch (sendType) {
                case BluetoothLeService.SEND_TYPE_8001:
                    if (mHandler != null) {
                        mHandler.removeCallbacks(checkSendTimeoutRunnable);
                        sendTimeOutCount = SEND_DATA_TIMEOUT;
                        mHandler.postDelayed(checkSendTimeoutRunnable, 1000);
                    }
                    LogUtil.v("test", ">>>>>>>>>>>>>>>>>>>>写数据到设备(8001) : " + ParseUtil.byteArrayToHexString(bytes));
                    characteristic = bluetoothGatt.getService(UUID_SERVICE_BASE).getCharacteristic(UUID_CHARACTERISTIC_8001);
                    characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                    break;
                default:
                    LogUtil.v("test", ">>>>>>>>>>>>>>>>>>>>写数据到设备(8003) : " + ParseUtil.byteArrayToHexString(bytes));
                    characteristic = bluetoothGatt.getService(UUID_SERVICE_EXTEND).getCharacteristic(UUID_CHARACTERISTIC_8003);
                    characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                    break;
            }
            characteristic.setValue(bytes);
            bluetoothGatt.writeCharacteristic(characteristic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 发送：发送03到设备
    public void send03ToDevice(UUID service, UUID characteristic) {
        LogUtil.i("test", "==>>命令已经发送到设备了，写03到设备结束...");
        if (bluetoothGatt != null) {
            BluetoothGattCharacteristic bluetoothgattcharacteristic = bluetoothGatt.getService(service).getCharacteristic(characteristic);
            bluetoothgattcharacteristic.setValue(new byte[]{0x03});
            bluetoothGatt.writeCharacteristic(bluetoothgattcharacteristic);
        }
    }

    public void initData() {
        sendBytes = null;
        sendBytesPacketCount = 0;
        sendTimeOutCount = 0;
    }

    public void disconnect(BluetoothGatt bluetoothGatt) {
        try {
            bluetoothGatt.close();
            bluetoothGatt.disconnect();
            bluetoothGatt = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
