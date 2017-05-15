package cn.appscomm.ota;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

import cn.appscomm.ota.util.LogUtil;
import cn.appscomm.ota.util.OtaUtil;


/**
 * 作者：hsh
 * 日期：2017/3/20
 * 说明：OTA服务类
 * 1、主要工作是：
 * 2、连接：通过mac地址连接设备
 * 3、发送：发送数据到设备，包含大字节数组的处理
 * 4、接收：通过系统回调，接收设备发送过来的数据
 * 5、回调：通过EventBus把数据发送给蓝牙管理者去解析
 */
public class OtaService extends Service {

    private static final String TAG = OtaService.class.getSimpleName();

    private static final UUID UUID_CONFIG_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_SERVICE_DFU = UUID.fromString("00001530-1212-EFDE-1523-785FEABCD123");
    public static final UUID UUID_CHARACTERISTIC_1531 = UUID.fromString("00001531-1212-EFDE-1523-785FEABCD123");
    public static final UUID UUID_CHARACTERISTIC_1532 = UUID.fromString("00001532-1212-EFDE-1523-785FEABCD123");
    public static final UUID UUID_CHARACTERISTIC_1534 = UUID.fromString("00001534-1212-EFDE-1523-785FEABCD123");

    public static final int SEND_TYPE_1531 = 0;                                                     // 使用1531通道发送
    public static final int SEND_TYPE_1532 = 1;                                                     // 使用1532通道发送

    private final int SEND_DATA_MAX_LEN = 20;                                                       // 一包发送数据的最大长度
    private final int SEND_DATA_TIMEOUT = 10;                                                       // 发送数据超时时间10秒

    private int sendTimeOutCount = 0;                                                               // 发送超时计数(0表示不计数 其他值则进行计数)

    private BluetoothAdapter mBluetoothAdapter = null;                                              // 蓝牙适配器
    private BluetoothGatt mBluetoothGatt = null;

    public String mac = "";                                                                         // mac地址
    private Handler mHandler = new Handler();                                                       // 用于发送计时

    private byte[] sendBytes = null;                                                                // 发送的字节数组
    private int sendBytesPacketCount = 0;                                                           // 发送的包数

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public OtaService getService() {
            return OtaService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    // 初始化各参数
    private void initData() {
        sendBytes = null;
        sendBytesPacketCount = 0;
        sendTimeOutCount = 0;
    }

    // 发送数据到设备超时处理
    Runnable checkSendTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            if (sendTimeOutCount > 0) {
                LogUtil.i("service_timeout", "距离上次发送1531数据，已经用时:" + (SEND_DATA_TIMEOUT - sendTimeOutCount) + "秒...!!!");
                if (--sendTimeOutCount <= 0) {
                    LogUtil.i("service_timeout", "---发送的数据已超时" + SEND_DATA_TIMEOUT + "秒");
                    sendTimeOutCount = 0;
                    disconnect();
                    EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_GATT_DISCONNECTED, null));
                    return;
                }
                mHandler.postDelayed(checkSendTimeoutRunnable, 1000);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, TAG + "服务创建...!!!");
        initData();
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (mBluetoothManager != null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, TAG + "服务已销毁...!!!");
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 连接设备
     *
     * @param mac 要连接的MAC地址
     * @return false：连接有误 true：连接中
     */
    public boolean connect(String mac) {
        try {
            this.mac = mac;
            BluetoothDevice bluetoothdevice;
            if (mBluetoothAdapter == null || (bluetoothdevice = mBluetoothAdapter.getRemoteDevice(mac)) == null) {
                return false;
            } else {
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.disconnect();
                    mBluetoothGatt.close();
                    SystemClock.sleep(500);
                    mBluetoothGatt = null;
                }
                mBluetoothGatt = bluetoothdevice.connectGatt(this, android.os.Build.VERSION.SDK_INT < 19, mGattCallback);
                LogUtil.w(TAG, "-------------连接设备(通过mac地址连接设备,mac : " + this.mac + "   绑定状态是 : " + bluetoothdevice.getBondState() + ")");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        initData();
        if (mBluetoothGatt != null) {
            try {
                mBluetoothGatt.close();
                mBluetoothGatt.disconnect();
                mBluetoothGatt = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送数据到设备
     *
     * @param bytes    要发送的数据
     * @param sendType 发送类型
     */
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
//                LogUtil.i("test_sendLargeBytes", "大字节数组发送第一包:" + OtaUtil.byteArrayToHexString(firstBytes) + " 共" + sendBytesPacketCount + "包数据!!!");
                sendBytesPacketCount--;                                                                 // 发了一包，总数减一
            }
            send(firstBytes == null ? bytes : firstBytes, sendType);
        }
    }

    // 继续发送数据(false：已经发送完毕 true：继续发送)
    private boolean continueSendBytes(int sendType) {
        if (sendBytesPacketCount != 0) {
            byte[] tmpBytes = sendBytesPacketCount == 1 ?
                    new byte[sendBytes.length % SEND_DATA_MAX_LEN == 0 ? SEND_DATA_MAX_LEN : sendBytes.length % SEND_DATA_MAX_LEN] :
                    new byte[SEND_DATA_MAX_LEN];
            int count = sendBytes.length / SEND_DATA_MAX_LEN + (sendBytes.length % SEND_DATA_MAX_LEN == 0 ? 0 : 1);
            int index = count - sendBytesPacketCount;
            System.arraycopy(sendBytes, SEND_DATA_MAX_LEN * index, tmpBytes, 0, tmpBytes.length);
//            LogUtil.i("test_sendLargeBytes", "还有" + (sendBytesPacketCount == 1 ? "最后一" : sendBytesPacketCount) + "包没有发!!!");
//            LogUtil.i("test_sendLargeBytes", "索引 : " + index + "   长度 : " + tmpBytes.length + "   总长度 : " + sendBytes.length);
//            LogUtil.i("test_sendLargeBytes", "包数据是：" + OtaUtil.byteArrayToHexString(tmpBytes));
            sendBytesPacketCount--;                                                                 // 每发一包，减一包
            send(tmpBytes, sendType);
            return true;
        }
        return false;
    }

    // 发送
    private void send(byte bytes[], int sendType) {
        if (bytes != null && mBluetoothGatt != null) {
            BluetoothGattCharacteristic characteristic;
            try {
                switch (sendType) {
                    case SEND_TYPE_1531:
                        mHandler.removeCallbacks(checkSendTimeoutRunnable);
                        sendTimeOutCount = SEND_DATA_TIMEOUT;
                        mHandler.postDelayed(checkSendTimeoutRunnable, 1000);
//                        LogUtil.v(TAG, ">>>>>>>>>>>>>>>>>>>>写数据到设备(1531) : " + OtaUtil.byteArrayToHexString(bytes));
                        characteristic = mBluetoothGatt.getService(UUID_SERVICE_DFU).getCharacteristic(UUID_CHARACTERISTIC_1531);
                        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                        break;
                    default:
//                        LogUtil.v(TAG, ">>>>>>>>>>>>>>>>>>>>写数据到设备(1532) : " + OtaUtil.byteArrayToHexString(bytes));
                        characteristic = mBluetoothGatt.getService(UUID_SERVICE_DFU).getCharacteristic(UUID_CHARACTERISTIC_1532);
                        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                        break;
                }
                characteristic.setValue(bytes);
                mBluetoothGatt.writeCharacteristic(characteristic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打开监听
     */
    public void openNotification() {
        if (mBluetoothGatt != null) {
            try {
                BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(UUID_SERVICE_DFU).getCharacteristic(UUID_CHARACTERISTIC_1531);
                mBluetoothGatt.setCharacteristicNotification(characteristic, true);

                BluetoothGattDescriptor bluetoothgattdescriptor = characteristic.getDescriptor(UUID_CONFIG_DESCRIPTOR);
                bluetoothgattdescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(bluetoothgattdescriptor);
            } catch (Exception e) {
                e.printStackTrace();
                disconnect();                                                                       // 打开监听失败，直接断开连接
            }
        }
    }

    // 蓝牙广播回调
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        // 连接：状态回调
        public void onConnectionStateChange(BluetoothGatt bluetoothgatt, int state, int newState) {

            // 断开连接
            if (newState == 0) {
                LogUtil.e(TAG, "xxxxxxxxxxxxx连接状态回调(state=" + state + " newState=" + newState + " 断开连接)");
                disconnect();
                EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_GATT_DISCONNECTED, null));
            }

            // 连接失败回调(此处断开一直重连)
            else if ((state == 133) && (newState == 2)) {
                LogUtil.e(TAG, "+++++++++++++连接状态回调(state=" + state + " newState=" + newState + " 未连接到设备,准备重新连接)");
                EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_GATT_DISCONNECTED, null));
            }

            // 已经连接
            else if ((newState == 2) && (state == 0)) {
                LogUtil.w(TAG, "==>>1、连接状态回调(state=" + state + " newState=" + newState + " (" + "已连接),准备发现服务...!!!)");
                EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_GATT_CONNECTED, null));
                mBluetoothGatt.discoverServices();
            }
        }

        // 连接：发现服务
        @Override
        public void onServicesDiscovered(BluetoothGatt bluetoothgatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                SystemClock.sleep(200);
                LogUtil.w(TAG, "==>>2、已发现服务(onServicesDiscovered),准备打开1531监听...!!!");
                openNotification();
            } else {
                LogUtil.e(TAG, "==>>onServicesDiscovered,有异常...!!!");
            }
        }

        // 连接：打开监听回调
        @Override
        public void onDescriptorWrite(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor bluetoothgattdescriptor, int i) {
            if (OtaService.UUID_CONFIG_DESCRIPTOR.equals(bluetoothgattdescriptor.getUuid())) {
                LogUtil.w(TAG, "==>>3、已打开1531监听(onDescriptorWrite),准备发送Discovered广播...!!!");
                bluetoothgatt.readDescriptor(bluetoothgattdescriptor);
            } else {
                LogUtil.i(TAG, "开启监听有问题,断开连接...");
                disconnect();
                EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_GATT_DISCONNECTED, null));
            }
        }

        // 连接：连接完成
        public void onDescriptorRead(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor bluetoothgattdescriptor, int i) {
            if (OtaService.UUID_CONFIG_DESCRIPTOR.equals(bluetoothgattdescriptor.getUuid())) {
                LogUtil.w(TAG, "==>>4、已经连接完毕(onDescriptorRead),发送Discovered广播...!!!");
                EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_GATT_DISCOVERED, null));
            } else {
                LogUtil.e(TAG, "==>>onDescriptorRead,有异常...!!!");
            }
        }

        // 发送：写回调
        public void onCharacteristicWrite(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {
            if (mBluetoothGatt == null) return;
            EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_DATA_WRITE_CALLBACK, null));

            if (UUID_CHARACTERISTIC_1532.equals(bluetoothgattcharacteristic.getUuid())) {
                continueSendBytes(SEND_TYPE_1532);
            }
        }

        // 接收：设备返回数据到手机
        public void onCharacteristicChanged(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic) {
            if (mBluetoothGatt == null) return;
            byte[] bytes = bluetoothgattcharacteristic.getValue();

            // 1531或1534通道
            if (UUID_CHARACTERISTIC_1531.equals(bluetoothgattcharacteristic.getUuid()) || UUID_CHARACTERISTIC_1534.equals(bluetoothgattcharacteristic.getUuid())) {
//                LogUtil.e(TAG, "<<<<<<<<<<获取到设备返回的数据(1531/1532) : " + OtaUtil.byteArrayToHexString(bytes));
//                LogUtil.i(TAG, " ");
                sendTimeOutCount = 0;
                EventBus.getDefault().post(new OtaMessage(OtaMessage.ACTION_DATA_AVAILABLE, bytes));
            }
        }

        // 读回调
        public void onCharacteristicRead(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {
            LogUtil.i(TAG, "==>>onCharacteristicRead(系统返回读回调)");
        }
    };
}