package cn.appscomm.presenter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.appscomm.bluetooth.BluetoothSend;
import cn.appscomm.bluetooth.mode.HeartRateBT;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.mode.SleepBT;
import cn.appscomm.bluetooth.mode.SportBT;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SportCacheDB;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;
import cn.appscomm.presenter.interfaces.PVDBCall;
import cn.appscomm.presenter.interfaces.PVSPCall;
import cn.appscomm.presenter.util.ModeConvertUtil;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：hsh
 * 日期：2017/3/29
 * 说明：同步蓝牙数据的逻辑处理
 */

public enum SyncBluetoothData {
    INSTANCE;

    private String TAG = SyncBluetoothData.class.getSimpleName();

    public static int GET_HEART_RATE_COUNT = 1;                                                     // 获取心率数据条数
    public static int GET_BLOOD_PRESSURE_COUNT = 1 << 1;                                            // 获取血压数据条数
    public static int GET_BATTERY = 1 << 2;                                                         // 获取电量
    public static int GET_SPORT_SLEEP_MODE = 1 << 3;                                                // 获取运动睡眠模式
    public static int GET_ULTRAVIOLET = 1 << 4;                                                     // 获取紫外线
    public static int GET_DEVICE_VERSION = 1 << 5;                                                  // 获取设备版本
    public static int SET_LANGUAGE = 1 << 6;                                                        // 设置语言
    public static int SET_UNIT = 1 << 7;                                                            // 设置单位

    private final int DELETE_SPORT_SUCCESS = 1;                                                     // 删除运动成功
    private final int DELETE_SLEEP_SUCCESS = 1 << 1;                                                // 删除睡眠成功
    private final int DELETE_HEART_RATE_SUCCESS = 1 << 2;                                           // 删除心率成功
    private final int DELETE_BLOOD_PRESSURE_SUCCESS = 1 << 3;                                       // 删除血压成功

    private PVDBCall pvdbCall = PDB.INSTANCE;
    private PVSPCall pvspCall = PSP.INSTANCE;
    private PVBluetoothCallback callback;
    private int syncType;

    Map<String, DeviceSynParam> deviceSynParamMap = new HashMap<>();

    class DeviceSynParam {
        int deleteFlag;
        boolean isSyncFail;
        List<Integer> saveSportIdList = new LinkedList<>();                                         // 保存运动数据的id
        List<Integer> saveSleepIdList = new LinkedList<>();                                         // 保存睡眠数据的id
        List<Integer> saveHeartRateIdList = new LinkedList<>();                                     // 保存心率数据的id

        DeviceSynParam(int deleteFlag, boolean isSyncFail) {
            this.deleteFlag = deleteFlag;
            this.isSyncFail = isSyncFail;
        }
    }

    class MacBluetoothData {
        String mac;
        LinkedList<SportBT> sportBTLinkedList;
        LinkedList<SleepBT> sleepBTLinkedList;
        LinkedList<HeartRateBT> heartRateBTLinkedList;

        MacBluetoothData(String mac, LinkedList<SportBT> sportBTLinkedList, LinkedList<SleepBT> sleepBTLinkedList, LinkedList<HeartRateBT> heartRateBTLinkedList) {
            this.mac = mac;
            this.sportBTLinkedList = sportBTLinkedList;
            this.sleepBTLinkedList = sleepBTLinkedList;
            this.heartRateBTLinkedList = heartRateBTLinkedList;
        }
    }


    public void start(PVBluetoothCallback callback, int syncType, String[] macs) {
        if (macs == null || macs.length == 0) return;
        this.callback = callback;
        this.syncType = syncType;
        deviceSynParamMap.clear();
        for (String mac : macs) {
            deviceSynParamMap.put(mac, new DeviceSynParam(0, false));
        }
        PBluetooth.INSTANCE.getAllDataTypeCount(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_SYNC, macs);
    }

    public void onDestroy() {
        pvBluetoothCallback = null;
        this.callback = null;
    }

    private void saveSport(String mac) {
        Flowable.just(new MacBluetoothData(mac, BluetoothVar.sportBTDataList, null, null)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(saveSportConsumer);
    }

    private void saveSleep(String mac) {
        Flowable.just(new MacBluetoothData(mac, null, BluetoothVar.sleepBTDataList, null)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(saveSleepConsumer);
    }

    private void saveHeartRate(String mac) {
        Flowable.just(new MacBluetoothData(mac, null, null, BluetoothVar.heartRateBTDataList)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(saveHeartRateConsumer);
    }

    private Consumer<MacBluetoothData> saveSportConsumer = new Consumer<MacBluetoothData>() {
        @Override
        public void accept(MacBluetoothData macBluetoothData) throws Exception {
            deviceSynParamMap.get(macBluetoothData.mac).saveSportIdList.clear();
            List<SportCacheDB> sportCacheDBs = ModeConvertUtil.sportBTToSportCacheDBList(macBluetoothData.sportBTLinkedList);
            pvdbCall.addSportCacheList(sportCacheDBs);
            for (SportCacheDB sportCacheDB : sportCacheDBs) {
                deviceSynParamMap.get(macBluetoothData.mac).saveSportIdList.add(sportCacheDB.getId());
            }
            PBluetooth.INSTANCE.deleteSportData(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{macBluetoothData.mac});
        }
    };

    private Consumer<MacBluetoothData> saveSleepConsumer = new Consumer<MacBluetoothData>() {
        @Override
        public void accept(MacBluetoothData macBluetoothData) throws Exception {
            deviceSynParamMap.get(macBluetoothData.mac).saveSleepIdList.clear();
            List<SleepDB> sleepDBList = ModeConvertUtil.sleepBTToSleepDBList(macBluetoothData.sleepBTLinkedList);
            pvdbCall.addSleepList(sleepDBList);
            for (SleepDB sleepDB : sleepDBList) {
                deviceSynParamMap.get(macBluetoothData.mac).saveSleepIdList.add(sleepDB.getId());
            }
            PBluetooth.INSTANCE.deleteSleepData(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{macBluetoothData.mac});
        }
    };

    private Consumer<MacBluetoothData> saveHeartRateConsumer = new Consumer<MacBluetoothData>() {
        @Override
        public void accept(MacBluetoothData macBluetoothData) throws Exception {
            deviceSynParamMap.get(macBluetoothData.mac).saveHeartRateIdList.clear();
            Map<String, String> dateSubmitMap = ModeConvertUtil.heartRateBTListToMap(macBluetoothData.heartRateBTLinkedList);
            pvdbCall.addHeartRateSubmitList(dateSubmitMap);
            PBluetooth.INSTANCE.deleteHeartRateData(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{macBluetoothData.mac});
        }
    };

    private void checkIsSyncFinish(String mac) {
        if (deviceSynParamMap.get(mac).deleteFlag == 0) {
            if (!deviceSynParamMap.get(mac).isSyncFail) {
                sendSyncSuccess(mac);
            } else {
                sendSyncFail(mac);
            }
        }
    }

    private void sendSyncSuccess(String mac) {
        LogUtil.i(TAG, "同步完毕(" + mac + "):结果为成功");
        if (callback != null)
            callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SYNC_SUCCESS);
    }

    private void sendSyncFail(String mac) {
        LogUtil.i(TAG, "同步完毕(" + mac + "):结果为失败");
        if (callback != null)
            callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SYNC_FAIL);
    }

    private PVBluetoothCallback pvBluetoothCallback = new PVBluetoothCallback() {
        @Override
        public void onSuccess(Object[] objects, int len, int dataType, String mac, BluetoothCommandType bluetoothCommandType) {
            switch (bluetoothCommandType) {
                case GET_ALL_DATA_TYPE_COUNT:                                                       // 获取各类型数据的总数
                    LogUtil.w(TAG, "同步(" + mac + "):获取总数完成 运动(" + BluetoothVar.sportCount + ") 睡眠(" + BluetoothVar.sleepCount + ") 心率(" + BluetoothVar.heartRateCount + ") 血压(" + BluetoothVar.bloodPressureCount + ")");
                    if (BluetoothVar.sportCount == 0 && BluetoothVar.sleepCount == 0 && BluetoothVar.heartRateCount == 0 && BluetoothVar.bloodPressureCount == 0) {
                        sendSyncSuccess(mac);
                    }
                    if ((syncType & GET_BATTERY) > 0) {
                        PBluetooth.INSTANCE.getBatteryPower(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    if ((syncType & GET_SPORT_SLEEP_MODE) > 0) {
                        PBluetooth.INSTANCE.getSportSleepMode(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    if ((syncType & GET_ULTRAVIOLET) > 0) {
                        // TODO 获取紫外线
                    }
                    if (BluetoothVar.sportCount > 0) {
                        deviceSynParamMap.get(mac).deleteFlag |= DELETE_SPORT_SUCCESS;
                        PBluetooth.INSTANCE.getSportData(pvBluetoothCallback, BluetoothVar.sportCount, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    if (BluetoothVar.sleepCount > 0) {
                        deviceSynParamMap.get(mac).deleteFlag |= DELETE_SLEEP_SUCCESS;
                        PBluetooth.INSTANCE.getSleepData(pvBluetoothCallback, BluetoothVar.sleepCount, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    if (BluetoothVar.heartRateCount > 0) {
                        deviceSynParamMap.get(mac).deleteFlag |= DELETE_HEART_RATE_SUCCESS;
                        PBluetooth.INSTANCE.getHeartRateDataEx(pvBluetoothCallback, BluetoothVar.heartRateCount, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    if (BluetoothVar.bloodPressureCount > 0) {
                        // TODO 获取血压
                        deviceSynParamMap.get(mac).deleteFlag |= DELETE_BLOOD_PRESSURE_SUCCESS;
                    }
                    if ((syncType & GET_DEVICE_VERSION) > 0) {
                        PBluetooth.INSTANCE.getDeviceVersion(pvBluetoothCallback, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    PBluetooth.INSTANCE.setDateTime(pvBluetoothCallback, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND), BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    if ((syncType & SET_LANGUAGE) > 0) {
                        int language = pvspCall.getLanguage();
                        PBluetooth.INSTANCE.setLanguage(callback, language, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    if ((syncType & SET_UNIT) > 0) {
                        int unit = pvspCall.getUnit();
                        PBluetooth.INSTANCE.setUnit(callback, unit, BluetoothSend.COMMAND_TYPE_SYNC, new String[]{mac});
                    }
                    break;
                case GET_BATTERY_POWER:
                    LogUtil.w(TAG, "同步(" + mac + "):获取电量成功");
                    pvspCall.setBatteryShow(BluetoothVar.batteryShow);
                    break;
                case GET_SPORT_SLEEP_MODE:
                    LogUtil.w(TAG, "同步(" + mac + "):获取运动睡眠模式成功");
                    pvspCall.setSportSleepMode(BluetoothVar.sportSleepMode);
                    break;
                case GET_ULTRAVIOLET:
                    LogUtil.w(TAG, "同步(" + mac + "):获取紫外线成功");
                    pvspCall.setUltraviolet(BluetoothVar.ultraviolet);
                    break;
                case GET_DEVICE_VERSION:
                    LogUtil.w(TAG, "同步(" + mac + "):获取设备版本成功");
                    pvspCall.setDeviceVersion(BluetoothVar.softVersion);
                    break;
                case SET_DATE_TIME:
                    LogUtil.w(TAG, "同步(" + mac + "):设置时间成功");
                    break;
                case SET_LANGUAGE:
                    LogUtil.w(TAG, "同步(" + mac + "):设置语言成功");
                    break;
                case SET_UNIT:
                    LogUtil.w(TAG, "同步(" + mac + "):设置单位成功");
                    break;
                case GET_SPORT_DATA:
                    LogUtil.w(TAG, "同步(" + mac + "):获取运动数据成功");
                    saveSport(mac);
                    break;
                case GET_SLEEP_DATA:
                    LogUtil.w(TAG, "同步(" + mac + "):获取睡眠数据成功");
                    saveSleep(mac);
                    break;
                case GET_HEART_RATE:
                    LogUtil.w(TAG, "同步(" + mac + "):获取心率数据成功");
                    saveHeartRate(mac);
                    break;
                case GET_BLOOD_PRESSURE:
                    LogUtil.w(TAG, "同步(" + mac + "):获取血压数据成功");
                    break;
                case DELETE_SPORT_DATA:
                    LogUtil.w(TAG, "同步(" + mac + "):删除运动数据成功");
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_SPORT_SUCCESS;
                    checkIsSyncFinish(mac);
                    break;
                case DELETE_SLEEP_DATA:
                    LogUtil.w(TAG, "同步(" + mac + "):删除睡眠数据成功");
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_SLEEP_SUCCESS;
                    checkIsSyncFinish(mac);
                    break;
                case DELETE_HEART_RATE_DATA:
                    LogUtil.w(TAG, "同步(" + mac + "):删除心率数据成功");
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_HEART_RATE_SUCCESS;
                    checkIsSyncFinish(mac);
                    break;
                case DELETE_BLOOD_PRESSURE:
                    LogUtil.w(TAG, "同步(" + mac + "):删除血压数据成功");
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_BLOOD_PRESSURE_SUCCESS;
                    checkIsSyncFinish(mac);
                    break;

            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            switch (bluetoothCommandType) {
                case DELETE_SPORT_DATA:
                    pvdbCall.delSports(deviceSynParamMap.get(mac).saveSportIdList);
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_SPORT_SUCCESS;
                    deviceSynParamMap.get(mac).isSyncFail = true;
                    checkIsSyncFinish(mac);
                    break;
                case DELETE_SLEEP_DATA:
                    pvdbCall.delSleeps(deviceSynParamMap.get(mac).saveSleepIdList);
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_SLEEP_SUCCESS;
                    deviceSynParamMap.get(mac).isSyncFail = true;
                    checkIsSyncFinish(mac);
                    break;
                case DELETE_HEART_RATE_DATA:
                    // TODO 删除已保存到数据库的心率数据
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_HEART_RATE_SUCCESS;
                    deviceSynParamMap.get(mac).isSyncFail = true;
                    checkIsSyncFinish(mac);
                    break;
                case DELETE_BLOOD_PRESSURE:
                    // TODO 删除已保存到数据库的血压数据
                    deviceSynParamMap.get(mac).deleteFlag -= DELETE_BLOOD_PRESSURE_SUCCESS;
                    deviceSynParamMap.get(mac).isSyncFail = true;
                    checkIsSyncFinish(mac);
                    break;
            }
        }
    };
}
