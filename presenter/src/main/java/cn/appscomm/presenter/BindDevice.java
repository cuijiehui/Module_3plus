package cn.appscomm.presenter;

import java.util.Calendar;

import cn.appscomm.bluetooth.BluetoothSend;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;
import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;

/**
 * 作者：hsh
 * 日期：2017/3/29
 * 说明：绑定设备逻辑类
 */

public enum BindDevice {
    INSTANCE;
    private String TAG = BindDevice.class.getSimpleName();

    private PVBluetoothCallback callback;
    private String[] macs;

    public void start(PVBluetoothCallback callback, String[] macs) {
        this.callback = callback;
        this.macs = macs;
        BluetoothSend.clearAllCommand(macs);

        int sex = PSP.INSTANCE.getGender();
        int age = Calendar.getInstance().get(Calendar.YEAR) - PSP.INSTANCE.getBirthdayYear();
        int height = (int) PSP.INSTANCE.getHeight();                                                 // 身高单位是0.1cm,则需要除以10换算为cm
        int weight = (int) PSP.INSTANCE.getWeight();
        int unit = PSP.INSTANCE.getUnit();

        PBluetooth.INSTANCE.getWatchID(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_BIND, macs);
        PBluetooth.INSTANCE.getDeviceVersion(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_BIND, macs);
        PBluetooth.INSTANCE.bindStart(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_BIND, macs);
        PBluetooth.INSTANCE.setDateTime(pvBluetoothCallback, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND), BluetoothSend.COMMAND_TYPE_BIND, macs);
        PBluetooth.INSTANCE.bindEnd(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_BIND, macs);
        PBluetooth.INSTANCE.setUnit(pvBluetoothCallback, unit, BluetoothSend.COMMAND_TYPE_BIND, macs);
        PBluetooth.INSTANCE.setUserInfo(pvBluetoothCallback, sex, age, height, weight, BluetoothSend.COMMAND_TYPE_BIND, macs);
    }

    public void onDestroy() {
        pvBluetoothCallback = null;
        this.callback = null;
    }

    private PVBluetoothCallback pvBluetoothCallback = new PVBluetoothCallback() {
        @Override
        public void onSuccess(Object[] objects, int len, int dataType, String mac, BluetoothCommandType bluetoothCommandType) {
            switch (bluetoothCommandType) {
                case GET_WATCH_ID:
                    LogUtil.w(TAG, "绑定:获取watchID成功");
                    PSP.INSTANCE.setWatchID(BluetoothVar.watchID);
                    break;
                case GET_DEVICE_VERSION:
                    LogUtil.w(TAG, "绑定:获取设备版本成功");
                    PSP.INSTANCE.setDeviceVersion(BluetoothVar.softVersion);
                    break;
                case BIND_START:
                    LogUtil.w(TAG, "绑定:绑定开始");
                    break;
                case SET_DATE_TIME:
                    LogUtil.w(TAG, "绑定:设置日期时间成功");
                    break;
                case BIND_END:
                    LogUtil.w(TAG, "绑定:绑定结束");
                    if (callback != null)
                        callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, BluetoothCommandType.BIND_SUCCESS);
                    break;
                case SET_UNIT:
                    LogUtil.w(TAG, "绑定:设置单位成功");
                    break;
                case SET_USER_INFO:
                    LogUtil.w(TAG, "绑定:设置个人信息成功");
                    break;
            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            switch (bluetoothCommandType) {
                case GET_WATCH_ID:
                case GET_DEVICE_VERSION:
                case BIND_START:
                case SET_DATE_TIME:
                case BIND_END:
                    LogUtil.i(TAG, "绑定失败...!!!");
                    SPManager.INSTANCE.setSPValue(SPKey.SP_WATCH_ID, "");
                    SPManager.INSTANCE.setSPValue(SPKey.SP_MAC, "");
                    SPManager.INSTANCE.setSPValue(SPKey.SP_DEVICE_VERSION, "");
                    SPManager.INSTANCE.setSPValue(SPKey.SP_DEVICE_NAME, "");
                    if (callback != null)
                        callback.onFail(mac, BluetoothCommandType.BIND_FAIL);
                    break;
            }
        }
    };
}
