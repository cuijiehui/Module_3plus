package cn.appscomm.bluetooth;

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

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import cn.appscomm.bluetooth.mode.Device;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;

/**
 * 作者：hsh
 * 日期：2017/3/20
 * 说明：蓝牙服务类
 * 1、主要工作是：
 * 2、连接：通过mac地址连接设备，并打开各种特征通道
 * 3、发送：发送数据到设备，包含大字节数组的处理
 * 4、接收：通过系统回调，接收设备发送过来的数据
 * 5、回调：通过EventBus把数据发送给蓝牙管理者去解析
 */
public class BluetoothLeService extends Service {

    private static final String TAG = BluetoothLeService.class.getSimpleName();

    private static final UUID UUID_CONFIG_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private static final UUID UUID_CHARACTERISTIC_8001 = UUID.fromString("00008001-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHARACTERISTIC_8002 = UUID.fromString("00008002-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_SERVICE_BASE = UUID.fromString("00006006-0000-1000-8000-00805f9b34fb");

    public static final UUID UUID_CHARACTERISTIC_8003 = UUID.fromString("00008003-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_CHARACTERISTIC_8004 = UUID.fromString("00008004-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_SERVICE_EXTEND = UUID.fromString("00007006-0000-1000-8000-00805f9b34fb");

    public static final UUID UUID_CHARACTERISTIC_HEART_RATE = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_SERVICE_HEART_RATE = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");

    public static final int SEND_TYPE_8001 = 0;                                                     // 使用8001通道发送
    public static final int SEND_TYPE_8003 = 1;                                                     // 使用8003通道发送

    public static boolean isSend03 = true;                                                          // true:需要发送03 false:不需要发送03
    public static boolean isSupportHeartRate = false;                                               // 是否支持心率功能

    private BluetoothAdapter mBluetoothAdapter = null;                                              // 蓝牙适配器
    public static Map<String, Device> deviceMap = new HashMap<>();

    private Handler mHandler = new Handler();                                                       // 用于发送计时

    private byte[] sendBytes = null;                                                                // 发送的字节数组
    private int sendBytesPacketCount = 0;                                                           // 发送的包数

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.START_SERVICE_SUCCESS, this));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, TAG + "服务创建...!!!");
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
    public boolean connect(String mac, boolean isSupportHeartRate) {
        try {
            BluetoothDevice bluetoothdevice;
            if (mBluetoothAdapter == null || (bluetoothdevice = mBluetoothAdapter.getRemoteDevice(mac)) == null) {
                return false;
            } else {
                Device device = deviceMap.get(mac);
                LogUtil.i(TAG, "要连接的mac(" + mac + ") device(" + (device != null) + ")");
                if (device != null) {
                    if (device.bluetoothGatt != null) {
                        device.bluetoothGatt.disconnect();
                        device.bluetoothGatt.close();
                        SystemClock.sleep(500);
                        device.bluetoothGatt = null;
                    }
                    device.bluetoothGatt = bluetoothdevice.connectGatt(this, android.os.Build.VERSION.SDK_INT < 19, mGattCallback);
                } else {
                    BluetoothGatt mBluetoothGatt = bluetoothdevice.connectGatt(this, android.os.Build.VERSION.SDK_INT < 19, mGattCallback);
                    deviceMap.put(mac, new Device(mac, mBluetoothGatt, isSupportHeartRate, mHandler));
                }
                LogUtil.w(TAG, "-------------连接设备(通过mac地址连接设备,mac : " + mac + "   绑定状态是 : " + bluetoothdevice.getBondState() + ")");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 断开连接
     */
    public void disconnectAll() {
        if (deviceMap != null) {
            for (Map.Entry<String, Device> entry : deviceMap.entrySet()) {
                Device device = entry.getValue();
                if (device.bluetoothGatt != null) {
                    device.initData();
                    disconnect(device.bluetoothGatt);
                }
            }
        }
    }

    public void disconnect(BluetoothGatt bluetoothGatt) {
        Device device = getDevice(bluetoothGatt);
        if (device != null) {
            device.initData();
            device.disconnect(bluetoothGatt);
        }
    }

    /**
     * 打开监听
     */
    public void openNotification(BluetoothGatt bluetoothGatt, LinkedList<Device.NotificationInfo> notifications) {
        try {
            BluetoothGattCharacteristic characteristic = bluetoothGatt.getService(notifications.getFirst().service).getCharacteristic(notifications.getFirst().characteristic);
            bluetoothGatt.setCharacteristicNotification(characteristic, true);

            BluetoothGattDescriptor bluetoothgattdescriptor = characteristic.getDescriptor(UUID_CONFIG_DESCRIPTOR);
            bluetoothgattdescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            bluetoothGatt.writeDescriptor(bluetoothgattdescriptor);
        } catch (Exception e) {
            e.printStackTrace();
            disconnect(bluetoothGatt);                                                                           // 打开监听失败，直接断开连接
        }
    }

    public Device getDevice(BluetoothGatt bluetoothGatt) {
        if (deviceMap == null || deviceMap.size() == 0) return null;
        for (Map.Entry<String, Device> entry : deviceMap.entrySet()) {
            Device device = entry.getValue();
            if (device != null && device.bluetoothGatt == bluetoothGatt) {
                return device;
            }
        }
        return null;
    }

    // 蓝牙广播回调
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        // 连接：状态回调
        public void onConnectionStateChange(BluetoothGatt bluetoothgatt, int state, int newState) {

            Device device = getDevice(bluetoothgatt);
            if (device != null) {

                // 断开连接
                if (newState == 0) {
                    LogUtil.e(TAG, "xxxxxxxxxxxxx连接状态回调(state=" + state + " newState=" + newState + " 断开连接)");
                    disconnect(bluetoothgatt);
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED, bluetoothgatt, null));
                }

                // 连接失败回调(此处断开一直重连)
                else if ((state == 133) && (newState == 2)) {
                    LogUtil.e(TAG, "+++++++++++++连接状态回调(state=" + state + " newState=" + newState + " 未连接到设备,准备重新连接)");
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED, bluetoothgatt, null));
                }

                // 已经连接
                else if ((newState == 2) && (state == 0)) {
                    LogUtil.w(TAG, "==>>1、连接状态回调(state=" + state + " newState=" + newState + " (" + "已连接),准备发现服务...!!!)");
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_CONNECTED, bluetoothgatt, null));
                    device.bluetoothGatt.discoverServices();
                }
            }
        }

        // 连接：发现服务
        @Override
        public void onServicesDiscovered(BluetoothGatt bluetoothgatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                SystemClock.sleep(200);
                LogUtil.w(TAG, "==>>2、已发现服务(onServicesDiscovered),准备打开8002监听...!!!");
                Device device = getDevice(bluetoothgatt);
                if (device != null) {
                    device.addNotification();
                    openNotification(device.bluetoothGatt, device.notifications);
                }
            } else {
                LogUtil.e(TAG, "==>>onServicesDiscovered,有异常...!!!");
            }
        }

        // 连接：打开监听回调
        @Override
        public void onDescriptorWrite(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor bluetoothgattdescriptor, int i) {
            if (BluetoothLeService.UUID_CONFIG_DESCRIPTOR.equals(bluetoothgattdescriptor.getUuid())) {
                Device device = getDevice(bluetoothgatt);
                if (device != null) {
                    if (device.notifications != null && device.notifications.size() > 0) {
                        Device.NotificationInfo info = device.notifications.removeFirst();
                        if (info.service.equals(UUID_SERVICE_BASE)) {
                            LogUtil.w(TAG, "==>>3.1、" + device.mac + "已打开8001监听(onDescriptorWrite),准备打开其他功能监听...!!!");
                        } else if (info.service.equals(UUID_SERVICE_EXTEND)) {
                            LogUtil.w(TAG, "==>>3.2、" + device.mac + "已打开其他功能监听(onDescriptorWrite)");
                        } else if (info.service.equals(UUID_SERVICE_HEART_RATE)) {
                            LogUtil.w(TAG, "==>>3.3、" + device.mac + "已打开心率监听(onDescriptorWrite),准备发送Discovered广播...!!!");
                        }
                        if (device.notifications.size() > 0) {
                            openNotification(device.bluetoothGatt, device.notifications);
                        } else {
                            device.bluetoothGatt.readDescriptor(bluetoothgattdescriptor);
                        }
                    } else {
                        LogUtil.i(TAG, "开启监听有问题,断开连接...");
//                        disconnect();
//                        EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED));
                    }
                }
            } else {
                LogUtil.e(TAG, "==>>onDescriptorWrite,有异常...!!!");
            }
        }

        // 连接：连接完成
        public void onDescriptorRead(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor bluetoothgattdescriptor, int i) {
            if (BluetoothLeService.UUID_CONFIG_DESCRIPTOR.equals(bluetoothgattdescriptor.getUuid())) {
                LogUtil.w(TAG, "==>>4、已经连接完毕(onDescriptorRead),发送Discovered广播...!!!");
                EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCOVERED, bluetoothgatt, null));
            } else {
                LogUtil.e(TAG, "==>>onDescriptorRead,有异常...!!!");
            }
        }

        // 发送：写回调
        public void onCharacteristicWrite(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {
            // 8001通道写回调
            if (UUID_CHARACTERISTIC_8001.equals(bluetoothgattcharacteristic.getUuid())) {
                LogUtil.i(TAG, "==>>onCharacteristicWrite(系统返回8001通道写回调)");
                Device device = getDevice(bluetoothgatt);
                if (device != null) {
                    if (!device.continueSendBytes(SEND_TYPE_8001) && isSend03) {
                        device.send03ToDevice(UUID_SERVICE_BASE, UUID_CHARACTERISTIC_8002);
                    }
                }
            }

            // 8003通道写回调
            else if (UUID_CHARACTERISTIC_8003.equals(bluetoothgattcharacteristic.getUuid())) {
                Device device = getDevice(bluetoothgatt);
                if (device != null) {
                    if (!device.continueSendBytes(SEND_TYPE_8003)) {
                        LogUtil.i(TAG, "==>>onCharacteristicWrite(系统返回8003通道写回调)");
                        EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_DATA_8003_SEND_CALLBACK, bluetoothgatt, null));
                    }
                }
            }
        }

        // 接收：设备返回数据到手机
        public void onCharacteristicChanged(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic) {
            byte[] bytes = bluetoothgattcharacteristic.getValue();
            Device device = getDevice(bluetoothgatt);
            if (device != null) {
                // 8002通道
                if (UUID_CHARACTERISTIC_8002.equals(bluetoothgattcharacteristic.getUuid())) {
                    LogUtil.e(TAG, "<<<<<<<<<<获取到设备返回的数据(8002) : " + ParseUtil.byteArrayToHexString(bytes));
                    LogUtil.i(TAG, " ");
                    device.sendTimeOutCount = 0;
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_DATA_8002_AVAILABLE, bluetoothgatt, bytes));
                }

                // 8004通道
                else if (UUID_CHARACTERISTIC_8004.equals(bluetoothgattcharacteristic.getUuid())) {
                    LogUtil.e(TAG, "<<<<<<<<<<获取到设备返回的数据(8004) : " + ParseUtil.byteArrayToHexString(bytes));
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_DATA_8004_AVAILABLE, bluetoothgatt, bytes));
                }

                // 心率通道
                else if (UUID_CHARACTERISTIC_HEART_RATE.equals(bluetoothgattcharacteristic.getUuid())) {
                    LogUtil.e(TAG, "<<<<<<<<<<获取到设备返回的数据(心率) : " + ParseUtil.byteArrayToHexString(bytes));
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_REAL_TIME_HEART_RATE, bluetoothgatt, bytes));
                }
            }
        }

        // 读回调
        public void onCharacteristicRead(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {
            LogUtil.i(TAG, "==>>onCharacteristicRead(系统返回读回调)");
        }
    };
}