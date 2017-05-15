package cn.appscomm.mymodule;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.appscomm.bluetooth.BluetoothLeService;
import cn.appscomm.bluetooth.BluetoothManager;
import cn.appscomm.bluetooth.mode.Device;
import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.ota.OtaMessage;
import cn.appscomm.ota.interfaces.IUpdateProgressCallBack;
import cn.appscomm.presenter.interfaces.IRemoteControl;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;
import cn.appscomm.presenter.interfaces.PVServerCallback;
import cn.appscomm.presenter.util.LogUtil;

/**
 * Created by Administrator on 2017/3/7.
 */

public class LoginUI extends BaseUI {

    private String TAG = LoginUI.class.getSimpleName();
    private Context context;
    private Map<String, String> macMap = new HashMap<>();

    public LoginUI(Context context) {
        this.context = context;
    }

    public void start() {
        macMap.put("ZeFit3#00035", null);
        macMap.put("ZeFit3 HR#16657", null);
        macMap.put("ZeFit3#00055", null);
        macMap.put("ZeFit3#20046", null);
        macMap.put("VIBE #00045", null);
        macMap.put("VIBE #00093", null);
        macMap.put("VIBE #00031", null);
        macMap.put("VIBE #00014", null);
//        pvServerCall.login("6@qq.com", "666666", pvServerCallback);
//        pvSpCall.setStepGoal(6000);
//        pvDbCall.addSport(new SportDB(100, 100, 100, 100, "2017-03-21"));
//        pvSpCall.setEmailSwitch(true);
//        pvSpCall.setSocialSwitch(true);
//        pvSpCall.setCalendarSwitch(true);
//        pvSpCall.setCallSwitch(true);
//        pvSpCall.setMissCallSwitch(true);
//        pvSpCall.setSMSSwitch(true);
//        pvDbCall.getSport("2017-03-21");

        // F6:2C:66:9D:BB:17 ZeFit3#00035
        // E1:CD:AE:F0:30:0F ZeFit3 HR#16657

        pvBluetoothCall.startService();
//        pvSpCall.setHeartRateFunction(false);
//        pvSpCall.setDeviceName("ZeFit3#00035");


//        pvSpCall.setHeartRateFunction(true);
//        pvSpCall.setDeviceName("VIBE #00045");

//        pvSpCall.setHeartRateFunction(false);
//        pvSpCall.setDeviceName("ZeCircle2#00065");

//        pvSpCall.setHeartRateFunction(false);
//        pvSpCall.setDeviceName("ZeWatch4#00005");

//        pvSpCall.setDeviceName("HR #00058");
//        pvSpCall.setMAC("F6:2C:66:9D:BB:17");
//        pvBluetoothCall.startService();
//        pvBluetoothCall.getWatchID(pvBluetoothCallback);
//        pvSpCall.setWatchID("FCL42A17032511000045");
//        pvSpCall.setDeviceType("E02A");
//        LogUtil.i(TAG, pvDbCall.getSport("2017-03-21").toString());
//        LogUtil.i(TAG, pvSpCall.getDeviceName());
//        LogUtil.i(TAG, pvSpCall.getMAC());
//        LogUtil.i(TAG, pvSpCall.getMAC());
//        pvBluetoothCall.sendSMS(pvBluetoothCallback, "张三", "哈哈", new Date(), 1);
//        pvBluetoothCall.sendSocial(pvBluetoothCallback, "李四", "嘿嘿", new Date(), BluetoothCommandConstant.MSG_PUSH_TYPE_QQ, 2);
//        pvBluetoothCall.sendIncomeCall(pvBluetoothCallback, "王五");
//        SystemClock.sleep(10000);
//        pvBluetoothCall.sendOffCall(pvBluetoothCallback);
//        pvBluetoothCall.sendMissCall(pvBluetoothCallback,8,"赵六");
//        pvBluetoothCall.bindDevice(pvBluetoothCallback);
//        pvBluetoothCall.syncBluetoothData(pvBluetoothCallback, 0);
//        pvBluetoothCall.setStepGoal(pvBluetoothCallback, 1000);
//        pvBluetoothCall.setCaloriesGoal(pvBluetoothCallback, 50);
//        pvBluetoothCall.setDistanceGoal(pvBluetoothCallback, 6);
//        pvBluetoothCall.setSleepGoal(pvBluetoothCallback, 8);
//        pvBluetoothCall.setSportTimeGoal(pvBluetoothCallback, 80);
//        pvBluetoothCall.getGoal(pvBluetoothCallback);
//        pvBluetoothCall.syncBluetoothData(pvBluetoothCallback, 0);
//        List<SportCacheDB> sportCacheDBs = pvDbCall.getSportCacheList();
//        for (SportCacheDB sportCacheDB : sportCacheDBs) {
//            LogUtil.i(TAG, "---sportCacheDB : " + sportCacheDB.toString());
//        }
//        ReminderBT reminderBT1 = new ReminderBT(0, PVBluetoothCall.REMINDER_TYPE_EAT, 14, 50, (byte) 0xFF, true, null);
//        pvBluetoothCall.addReminder(pvBluetoothCallback,reminderBT1);
//        pvBluetoothCall.getReminder(pvBluetoothCallback, 1);
//        pvBluetoothCall.deleteReminder(pvBluetoothCallback,null);

//        pvServerCall.register("maxiao11@163.com", "123456", PVServerCall.ACCOUNT_TYPE_EMAIL, pvServerCallback);
//        pvServerCall.login("6@qq.com", "666666", pvServerCallback);
//        String[] test = new String[]{"1", "2", "3"};
//        LogUtil.i("test", Arrays.toString(test).replace(" ", "").replace("[", "").replace("]", ""));
//        LogUtil.i("test","accountId : " + pvSpCall.getAccountID());
//        pvServerCall.getWeekSportData(Calendar.getInstance(), true, pvServerCallback);
//        pvServerCall.getDaySportData(Calendar.getInstance(), pvServerCallback);
//        pvServerCall.getMonthSportData(Calendar.getInstance(), pvServerCallback);

        // 上传睡眠
        /*List<SleepDB> sleepDBList = new LinkedList<>();
        SleepDB sleepDB = new SleepDB();
        sleepDB.setTotal(59);
        sleepDB.setDeep(29);
        sleepDB.setLight(20);
        sleepDB.setAwake(10);
        sleepDB.setSleep(50);
        sleepDB.setFlag(-1);
        sleepDB.setAwakeTime(1);
        sleepDB.setDate("2017-04-14");
        sleepDB.setDetail(addOneHourDetail("2017-04-14", "06"));
        sleepDB.toString();
        sleepDBList.add(sleepDB);
        pvDbCall.addSleepList(sleepDBList);
        pvServerCall.uploadSleepData(pvServerCallback);*/
//        pvServerCall.getSleepData("2017-04-14 00:00:00", "2017-04-14 23:59:59", pvServerCallback);

        /*LinkedList<HeartRateBT> heartRateBTList = new LinkedList<>();
        heartRateBTList.add(new HeartRateBT(0, 67, 1492121328));
        heartRateBTList.add(new HeartRateBT(0, 67, 1492121388));
        heartRateBTList.add(new HeartRateBT(0, 78, 1492121628));
        heartRateBTList.add(new HeartRateBT(0, 78, 1492121748));
        Map<String, String> submitMap = ModeConvertUtil.heartRateBTListToMap(heartRateBTList);
        for (Map.Entry<String, String> entry : submitMap.entrySet()) {
            LogUtil.i("test", "key : " + entry.getKey() + "value : " + entry.getValue());
        }
        pvDbCall.addHeartRateSubmitList(submitMap);*/
//        pvServerCall.uploadHeartRateData(pvServerCallback);
//        pvServerCall.getHeartRateData("2017-04-14 00:00:00", "2017-04-14 23:59:59", pvServerCallback);

//        List<HeartRateDB> heartRateDBList = pvDbCall.getHeartRateList("2017-04-20");
//        LogUtil.i("test", "---" + (heartRateDBList != null) + " size : " + heartRateDBList.size());
//

//        List<HeartRateDB> heartRateDBList = new LinkedList<>();
//        HeartRateDB heartRateDB = new HeartRateDB();
//        heartRateDB.setAvg(93);
//        heartRateDB.setDetail("0&77,3&96,6&77,9&91,12&95,15&106,18&105,21&106,24&104,27&106,30&103,33&106,36&105,39&105,42&104,45&77,48&94,51&86,54&85,57&78,60&118,63&79,66&103,69&87,72&94,75&73,78&87,81&79,84&95,87&93,90&104,93&115,96&96,99&106,102&81,105&96,108&117,111&72,114&85,117&90,120&79,123&73,126&115,129&87,132&98,");
//        heartRateDB.setDate("2017-04-20");
//        heartRateDB.setFlag(1);
//        heartRateDBList.add(heartRateDB);
//        pvDbCall.updateHeartRateDetailList(heartRateDBList);

//        String date = "2017-04-20";
//        List<HeartRateDB> heartRateDBList = pvDbCall.getHeartRateList(date);
//        LogUtil.i("test","---heartList : " + Arrays.toString(heartRateDBList.toArray()));
//        List<HeartRateBT> heartRateBTList = ModeConvertUtil.heartRateDBToHeartRateBTList(heartRateDBList);
//        LogUtil.i("test","heartList : " + Arrays.toString(heartRateBTList.toArray()));
//        activityDetailHeartRateDataChart.setData(heartRateBTList, calendar);

//        List<SleepDB> sleepDBList = pvDbCall.getSleepList("2017-04-21");
//        for (SleepDB sleepDB : sleepDBList) {
//            LogUtil.i("test", sleepDB.toString());
//        }
//
//        pvBluetoothCall.getSwitchSetting(pvBluetoothCallback);
//        pvBluetoothCall.setSwitchSetting(pvBluetoothCallback, PVBluetoothCall.SWITCH_TYPE_CALL, true);
//        pvBluetoothCall.setSwitchSetting(pvBluetoothCallback, PVBluetoothCall.SWITCH_TYPE_MISS_CALL, true);
//        pvBluetoothCall.setSwitchSetting(pvBluetoothCallback, PVBluetoothCall.SWITCH_TYPE_SMS, true);
//        pvBluetoothCall.setSwitchSetting(pvBluetoothCallback, PVBluetoothCall.SWITCH_TYPE_EMAIL, true);
//        pvBluetoothCall.setSwitchSetting(pvBluetoothCallback, PVBluetoothCall.SWITCH_TYPE_SOCIAL, true);
//        pvBluetoothCall.setSwitchSetting(pvBluetoothCallback, PVBluetoothCall.SWITCH_TYPE_ANTI, true);
//
//        pvBluetoothCall.getAutoSleep(pvBluetoothCallback);
//        pvBluetoothCall.setAutoSleep(pvBluetoothCallback, 23, 0, 7, 0, 0);
//
//        pvBluetoothCall.getUnit(pvBluetoothCallback);
//        pvBluetoothCall.setUnit(pvBluetoothCallback, 1);
//
//        pvBluetoothCall.getInactivityAlert(pvBluetoothCallback);
//        pvBluetoothCall.setInactivityAlert(pvBluetoothCallback, 1, 0, 60, 23, 0, 7, 0);

//        SparseArray<float[]> weekSport = pvDbCall.getWeekSport(Calendar.getInstance(), true);
//        if (weekSport != null && weekSport.size() > 0) {
//            LogUtil.i(TAG, "---step : " + Arrays.toString(weekSport.get(0)));
//            LogUtil.i(TAG, "---calories : " + Arrays.toString(weekSport.get(1)));
//            LogUtil.i(TAG, "---distance : " + Arrays.toString(weekSport.get(2)));
//            LogUtil.i(TAG, "---sporttime : " + Arrays.toString(weekSport.get(3)));
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.WEEK_OF_MONTH, -1);
//        weekSport = pvDbCall.getWeekSport(calendar, true);
//        if (weekSport != null && weekSport.size() > 0) {
//            LogUtil.i(TAG, "---step1 : " + Arrays.toString(weekSport.get(0)));
//            LogUtil.i(TAG, "---calories1 : " + Arrays.toString(weekSport.get(1)));
//            LogUtil.i(TAG, "---distance1 : " + Arrays.toString(weekSport.get(2)));
//            LogUtil.i(TAG, "---sporttime1 : " + Arrays.toString(weekSport.get(3)));
//        }

//        List<SleepDB> sleepDBList = new LinkedList<>();
//        SleepDB sleepDB = new SleepDB();
//        sleepDB.setDate("2017-05-01");
//        sleepDB.setTotal(100);
//        sleepDB.setDetail("");
//        sleepDBList.add(sleepDB);
//        sleepDB = new SleepDB();
//        sleepDB.setDate("2017-05-02");
//        sleepDB.setTotal(200);
//        sleepDB.setDetail("");
//        sleepDBList.add(sleepDB);
//        sleepDB = new SleepDB();
//        sleepDB.setDate("2017-05-03");
//        sleepDB.setTotal(300);
//        sleepDB.setDetail("");
//        sleepDBList.add(sleepDB);
//        sleepDB = new SleepDB();
//        sleepDB.setDate("2017-05-04");
//        sleepDB.setTotal(400);
//        sleepDB.setDetail("");
//        sleepDBList.add(sleepDB);
//        pvDbCall.addSleepList(sleepDBList);

//        int[] weekSleep = pvDbCall.getWeekSleep(Calendar.getInstance(), false);
//        int[] monthSleep = pvDbCall.getMonthSleep(Calendar.getInstance());
//        LogUtil.i(TAG, "weekSleep : " + Arrays.toString(weekSleep) + " monthSleep : " + Arrays.toString(monthSleep));
//        pvOtherCall.registerRemoteTakePhoto(new IRemoteControl() {
//            @Override
//            public void startTakePhoto() {
//                LogUtil.i(TAG, "---------拍照");
//            }
//
//            @Override
//            public void endTakePhoto() {
//
//            }
//        });
    }

    private List<HeartRateDB> test() {
        List<HeartRateDB> heartRateDBList = new LinkedList<>();
        HeartRateDB heartRateDB = new HeartRateDB();
        heartRateDB.setAvg(93);
        heartRateDB.setDetail("0&77,3&96,6&77,9&91,12&95,15&106,18&105,21&106,24&104,27&106,30&103,33&106,36&105,39&105,42&104,45&77,48&94,51&86,54&85,57&78,60&118,63&79,66&103,69&87,72&94,75&73,78&87,81&79,84&95,87&93,90&104,93&115,96&96,99&106,102&81,105&96,108&117,111&72,114&85,117&90,120&79,123&73,126&115,129&87,132&98,");
        heartRateDB.setDate("2017-04-20");
        heartRateDB.setFlag(1);
        heartRateDBList.add(heartRateDB);
        return heartRateDBList;
//        pvdbCall.updateHeartRateDetailList(heartRateDBList);
    }

    private String addOneHourDetail(String date, String startHour) {
        // String detail = "2017-04-14 06:06:00&BEGIN,2017-04-14 06:16:00&AWAKE,2017-04-14 06:36:00&LIGHT&2017-04-14 07:06:00&DEEP,2017-04-14 07:06:00&END";
        String detail = date + " " + startHour + ":00:00&BEGIN,";
        detail += date + " " + startHour + ":16:00&AWAKE,";
        detail += date + " " + startHour + ":36:00&LIGHT,";
        detail += date + " " + startHour + ":59:00&DEEP,";
        detail += date + " " + startHour + ":59:00&END";
        return detail;
    }

    private boolean checkIsFinish() {
        boolean flag = true;
        for (Map.Entry<String, String> entry : macMap.entrySet()) {
            if (TextUtils.isEmpty(entry.getValue())) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private String[] getMacs() {
        if (macMap.size() == 0) return null;
        String[] macs = new String[macMap.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : macMap.entrySet()) {
            macs[i++] = entry.getValue();
        }
        return macs;
    }

    private void printf() {
        for (Map.Entry<String, String> entry : macMap.entrySet()) {
            LogUtil.i("test", "deviceName : " + entry.getKey() + " mac : " + entry.getValue());
        }
    }

    public void getUserInfo() {
        pvBluetoothScanCall.startScan(new IBluetoothScanResultCallBack() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi) {
                String deviceName = device.getName();
                if (!TextUtils.isEmpty(deviceName)) {
                    LogUtil.i(TAG, "扫描到的设备:" + deviceName);
                    if (deviceName.equals("ZeFit3#00035")
                            || deviceName.equals("ZeFit3 HR#16657")
                            || deviceName.equals("ZeFit3#00055")
                            || deviceName.equals("VIBE #00045")
                            || deviceName.equals("VIBE #00093")
                            || deviceName.equals("VIBE #00031")
                            || deviceName.equals("VIBE #00014")
                            || deviceName.equals("ZeFit3#20046")) {
                        String mac = macMap.get(deviceName);
                        if (TextUtils.isEmpty(mac)) {
                            macMap.put(deviceName, device.getAddress());

                        }
                        pvBluetoothCall.connect(device.getAddress(), false);
                        pvBluetoothScanCall.stopScan();
//                        if(checkIsFinish()) {
//
//                        }
                    }
                }
            }
        });
//        pvBluetoothCall.connect("F8:0A:74:AC:9B:1B", false);
//        pvBluetoothCall.syncBluetoothData(pvBluetoothCallback, 0);
//        pvSpCall.setHeartRateFunction(false);
//        pvSpCall.setDeviceName("ZeFit3 HR#16657");
//        pvBluetoothCall.startService();
//        pvSpCall.setProductCode("E02A");
//        pvServerCall.getFirmwareVersion(pvServerCallback);
//        pvServerCall.downloadFirmware("https://new.fashioncomm.com/download/E02A_0.5.zip", Environment.getExternalStoragePublicDirectory("E02A_0.5.zip"), pvServerCallback);
//        pvBluetoothCall.sendMissCall(pvBluetoothCallback, "13725405644", new Date(), 5);
//        pvBluetoothCall.endService();
//        pvBluetoothCall.bindDevice(pvBluetoothCallback);
/*        pvSpCall.setEmail("1084082600@qq.com");
        pvSpCall.setMAC("CC:E0:3E:B2:45:9D");
        pvSpCall.setWatchID("FCL42A17032511000045");
        pvSpCall.setDeviceType("VIBE");
//        List<SportCacheDB> sportCacheDBList = new LinkedList<>();
//        sportCacheDBList.add(new SportCacheDB(100, 200, 300, 60, 1491619000001L));
//        sportCacheDBList.add(new SportCacheDB(100, 300, 300, 60, 1491609000002L));
//        pvDbCall.addSportCacheList(sportCacheDBList);
//        pvServerCall.uploadSportData();
        pvServerCall.getSportData("2017-04-08 00:00:00", "2017-04-08 11:00:00", pvServerCallback);
//        pvServerCall.accountQuery(pvServerCallback);*/
    }

    public void getSportDB() {
//        SportDB sportDB = pvDbCall.getSport("2017-04-08");
//        LogUtil.i(TAG, "sportDB : " + sportDB.toString());
        SparseArray<float[]> weekSportData = pvDbCall.getWeekSport(Calendar.getInstance(), true);
        for (int i = 0; i < weekSportData.size(); i++) {
            LogUtil.i(TAG, "---" + Arrays.toString(weekSportData.get(i)));
        }
        SparseArray<float[]> monthSportData = pvDbCall.getMonthSport(Calendar.getInstance());
        for (int i = 0; i < monthSportData.size(); i++) {
            LogUtil.i(TAG, "---" + Arrays.toString(monthSportData.get(i)));
        }
    }

    public void end() {
        pvBluetoothCall.endService(null);
//        pvBluetoothCall.connect("E1:CD:AE:F0:30:0F", false);
    }

    public void restart() {

//        pvBluetoothScanCall.startScan(null);
//        pvBluetoothCall.getDateTime(pvBluetoothCallback, new String[]{"F6:2C:66:9D:BB:17", "E1:CD:AE:F0:30:0F"});
//        pvBluetoothCall.syncBluetoothData(pvBluetoothCallback, 0, new String[]{"F6:2C:66:9D:BB:17", "E1:CD:AE:F0:30:0F"});
//        pvBluetoothCall.bindDevice(pvBluetoothCallback, new String[]{"F6:2C:66:9D:BB:17", "E1:CD:AE:F0:30:0F", "CF:40:77:55:9E:45"});
        pvBluetoothCall.bindDevice(pvBluetoothCallback, getMacs());
//        pvBluetoothCall.restartService("");
    }

    public void getWatchID() {
        pvBluetoothCall.syncBluetoothData(pvBluetoothCallback, 0, getMacs());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
//        pvBluetoothCall.getWatchID(pvBluetoothCallback);
//        pvBluetoothCall.getDeviceVersion(pvBluetoothCallback);
//        pvBluetoothCall.getDateTime(pvBluetoothCallback);
//        pvBluetoothCall.setDateTime(pvBluetoothCallback, year, month, day, hour, min, sec);
    }

    @Override
    public void onBluetoothSuccess(PVBluetoothCallback.BluetoothCommandType bluetoothCommandType) {
        switch (bluetoothCommandType) {
            case GET_WATCH_ID:
                LogUtil.i(TAG, "watchID(" + bluetoothDataString + ")");
                break;
            case GET_DEVICE_VERSION:
                LogUtil.i(TAG, "DeviceVersion(" + bluetoothDataString + ")");
                break;
            case GET_DATE_TIME:
                LogUtil.i(TAG, "设备时间(" + bluetoothDataString + ")");
                break;
            case SET_DATE_TIME:
                LogUtil.i(TAG, "设置时间成功!!!");
                break;
            case BIND_SUCCESS:
                LogUtil.i(TAG, "绑定成功");
                break;
            case BIND_FAIL:
                LogUtil.i(TAG, "绑定失败");
                break;
            case SYNC_SUCCESS:
                LogUtil.i(TAG, "同步成功");
                /*List<SportDB> sportDBList = pvDbCall.getSportByCache("2017-04-01", "2017-04-09");
                if (sportDBList != null) {
                    for (SportDB sportDB : sportDBList) {
                        LogUtil.i(TAG, "sportDB : " + sportDB.toString());
                    }
                }*/
                pvServerCall.uploadSportData(pvServerCallback);
//                pvServerCall.uploadSleepData(pvServerCallback);
                break;
        }
    }

    @Override
    public void onBluetoothFail(PVBluetoothCallback.BluetoothCommandType bluetoothCommandType) {
        super.onBluetoothFail(bluetoothCommandType);
    }

    @Override
    public void onServerSuccess(PVServerCallback.RequestType requestType) {
        super.onServerSuccess(requestType);
        LogUtil.i(TAG, "网络请求成功");
        switch (requestType) {
            case LOGIN:
                LogUtil.i(TAG, "登录成功");
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "userinfoid : " + pvSpCall.getUserInfoId() + " userid : " + pvSpCall.getUserId(), Toast.LENGTH_SHORT).show();
//                pvSpCall.setUserId(13279);
                pvServerCall.getPairDevice("", pvServerCallback);
                break;
            case REGISTER:
                LogUtil.i(TAG, "注册成功");
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                break;
            case UPLOAD_SPORT_DATA:
                LogUtil.i(TAG, "上传运动数据成功");
                pvServerCall.getSportData("2017-04-08 00:00:00", "2017-04-08 23:00:00", pvServerCallback);
                break;
            case GET_SPORT_DATA:
                LogUtil.i(TAG, "下载运动数据成功");
                getSportDB();
                break;
            case UPLOAD_SLEEP_DATA:
                LogUtil.i(TAG, "上传睡眠成功");
                pvServerCall.getSleepData("2017-04-14 00:00:00", "2017-04-14 23:59:59", pvServerCallback);
                break;
            case DOWNLOAD_FIRMWARE:
                LogUtil.i(TAG, "下载固件成功");
                if (Environment.getExternalStoragePublicDirectory("E02A_0.5.zip").exists()) {
                    LogUtil.i(TAG, "文件存在");
                }
                break;
        }
    }

    @Override
    public void onServerFail(PVServerCallback.RequestType requestType) {
        super.onServerFail(requestType);
        LogUtil.i(TAG, "网络访问失败...");
        switch (requestType) {
            case LOGIN:
                LogUtil.i(TAG, "登录失败");
                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void play() {
    }

    public void pause() {
    }

    public void pre() {
    }

    public void next() {
    }

    public void update() {
        String dfuName = "53B00065";

        String nordicPath = Environment.getExternalStoragePublicDirectory("application_3.1.zip").getAbsolutePath();
        String freescalePath = Environment.getExternalStoragePublicDirectory("kl17_3.1.bin").getAbsolutePath();
        String languagePath = Environment.getExternalStoragePublicDirectory("Language_tc_0.1.bin").getAbsolutePath();
        String picturePath = Environment.getExternalStoragePublicDirectory("Picture1.2.bin").getAbsolutePath();
        String touchPath = Environment.getExternalStoragePublicDirectory("TouchPanel_1.6.bin").getAbsolutePath();

        dfuName = "40#00005";
        nordicPath = Environment.getExternalStoragePublicDirectory("application2.2.zip").getAbsolutePath();
        freescalePath = Environment.getExternalStoragePublicDirectory("kl17_2.2.bin").getAbsolutePath();
        touchPath = Environment.getExternalStoragePublicDirectory("TouchPanel_1.0.bin").getAbsolutePath();

//        nordicPath = "";
//        freescalePath = "";
        languagePath = "";
        picturePath = "";
//        touchPath = "";

        pvOtaCall.update(dfuName, touchPath, "", freescalePath, picturePath, languagePath, nordicPath, new IUpdateProgressCallBack() {
            @Override
            public void curUpdateProgress(int curPackage) {
                LogUtil.i(TAG, "升级进度 ： " + curPackage);
            }

            @Override
            public void curUpdateMax(int totalPackage) {
                LogUtil.i(TAG, "升级最大值 ： " + totalPackage);
            }

            @Override
            public void updateResult(boolean result) {
                LogUtil.i(TAG, "升级结果 ： " + result);
            }
        }, new String[]{});
    }
}
