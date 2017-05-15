package cn.appscomm.presenter.implement;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothSend;
import cn.appscomm.bluetooth.implement.MBluetooth;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.interfaces.PMBluetoothCall;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;
import cn.appscomm.ota.util.OtaUtil;
import cn.appscomm.presenter.BindDevice;
import cn.appscomm.presenter.SyncBluetoothData;
import cn.appscomm.presenter.interfaces.PVBluetoothCall;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;
import cn.appscomm.presenter.interfaces.PVSPCall;

/**
 * 作者：hsh
 * 日期：2017/3/6
 * 说明：蓝牙P
 * 1、主要是实现PVBluetoothCall接口的所有方法
 * 2、还有一些逻辑功能的处理
 */
public enum PBluetooth implements PVBluetoothCall {
    INSTANCE {
    };

    private static final String TAG = PBluetooth.class.getSimpleName();
    private PMBluetoothCall mCall = MBluetooth.INSTANCE;                                            // 用户调用M层的方法
    private PVSPCall pvspCall = PSP.INSTANCE;
    private SimpleDateFormat smsDataTimeSDF = new SimpleDateFormat("yyyyMMdd'T'HHmmss");            // SMS的时间格式

    /*-----------------------------------------------蓝牙服务相关-----------------------------------------------*/
    @Override
    public void startService() {
        mCall.startService();
    }

    @Override
    public boolean resetService(String mac, boolean isSupportHeartRate) {
        return mCall.resetService(mac, isSupportHeartRate);
    }

    @Override
    public void endService(String mac) {
        mCall.endService(mac);
    }

    @Override
    public boolean isBluetoothLeServiceRunning() {
        return mCall.isBluetoothLeServiceRunning();
    }

    @Override
    public void connect(String mac, boolean isSupportHeartRate) {
        mCall.connect(mac, isSupportHeartRate);
    }

    @Override
    public boolean isConnect(String mac) {
        return mCall.isConnect(mac);
    }

    /*-------------------------------------------------V层调用-------------------------------------------------*/
    @Override
    public void getWatchID(final PVBluetoothCallback callback, String[] macs) {
        getWatchID(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getDeviceVersion(final PVBluetoothCallback callback, String[] macs) {
        getDeviceVersion(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getDateTime(final PVBluetoothCallback callback, String[] macs) {
        getDateTime(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setDateTime(final PVBluetoothCallback callback, int year, int month, int day, int hour, int min, int sec, String[] macs) {
        setDateTime(callback, year, month, day, hour, min, sec, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getTimeSurfaceSetting(PVBluetoothCallback callback, String[] macs) {
        getTimeSurfaceSetting(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setTimeSurfaceSetting(PVBluetoothCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, String[] macs) {
        setTimeSurfaceSetting(callback, dateFormat, timeFormat, batteryFormat, lunarFormat, screenFormat, backgroundStyle, sportDataFormat, usernameFormat, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getScreenBrightness(PVBluetoothCallback callback, String[] macs) {
        getScreenBrightness(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setScreenBrightness(PVBluetoothCallback callback, int brightnessValue, String[] macs) {
        setScreenBrightness(callback, brightnessValue, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getBatteryPower(PVBluetoothCallback callback, String[] macs) {
        getBatteryPower(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getVolume(PVBluetoothCallback callback, String[] macs) {
        getVolume(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setVolume(PVBluetoothCallback callback, int volume, String[] macs) {
        setVolume(callback, volume, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getShockMode(PVBluetoothCallback callback, String[] macs) {
        getShockMode(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setShockMode(PVBluetoothCallback callback, int shockType, int shockMode, String[] macs) {
        setShockMode(callback, shockType, shockMode, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getLanguage(PVBluetoothCallback callback, String[] macs) {
        getLanguage(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setLanguage(PVBluetoothCallback callback, int language, String[] macs) {
        setLanguage(callback, language, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUnit(PVBluetoothCallback callback, String[] macs) {
        getUnit(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUnit(PVBluetoothCallback callback, int unit, String[] macs) {
        setUnit(callback, unit, BluetoothSend.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void restoreFactory(PVBluetoothCallback callback, String[] macs) {
        restoreFactory(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void enterUpdateMode(PVBluetoothCallback callback, String[] macs) {
        enterUpdateMode(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void enterUpdateMode(String touchPanelPath, String heartRatePath, String freescalePath, PVBluetoothCallback callback, String[] macs) {
        enterUpdateMode(touchPanelPath, heartRatePath, freescalePath, callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getShockStrength(PVBluetoothCallback callback, String[] macs) {
        getShockStrength(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setShockStrength(PVBluetoothCallback callback, int shockStrength, String[] macs) {
        setShockStrength(callback, shockStrength, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getWorkMode(PVBluetoothCallback callback, String[] macs) {
        getWorkMode(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setWorkMode(PVBluetoothCallback callback, int workMode, String[] macs) {
        setWorkMode(callback, workMode, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getBrightScreenTime(PVBluetoothCallback callback, String[] macs) {
        getBrightScreenTime(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setBrightScreenTime(PVBluetoothCallback callback, int brightScreenTime, String[] macs) {
        setBrightScreenTime(callback, brightScreenTime, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUserInfo(PVBluetoothCallback callback, String[] macs) {
        getUserInfo(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUserInfo(PVBluetoothCallback callback, int sex, int age, int height, int weight, String[] macs) {
        setUserInfo(callback, sex, age, height, weight, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUsageHabits(PVBluetoothCallback callback, String[] macs) {
        getUsageHabits(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUsageHabits(PVBluetoothCallback callback, int usageHabits, String[] macs) {
        setUsageHabits(callback, usageHabits, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUserName(PVBluetoothCallback callback, String[] macs) {
        getUserName(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUserName(PVBluetoothCallback callback, String userName, String[] macs) {
        setUserName(callback, userName, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getGoal(PVBluetoothCallback callback, String[] macs) {
        getGoal(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setStepGoal(PVBluetoothCallback callback, int stepGoal, String[] macs) {
        setStepGoal(callback, stepGoal, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setCaloriesGoal(PVBluetoothCallback callback, int caloriesGoal, String[] macs) {
        setCaloriesGoal(callback, caloriesGoal, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setDistanceGoal(PVBluetoothCallback callback, int sportTimeGoal, String[] macs) {
        setDistanceGoal(callback, sportTimeGoal, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setSportTimeGoal(PVBluetoothCallback callback, int sportTimeGoal, String[] macs) {
        setSportTimeGoal(callback, sportTimeGoal, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setSleepGoal(PVBluetoothCallback callback, int sleepGoal, String[] macs) {
        setSleepGoal(callback, sleepGoal, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getSportSleepMode(PVBluetoothCallback callback, String[] macs) {
        getSportSleepMode(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getAllDataTypeCount(PVBluetoothCallback callback, String[] macs) {
        getAllDataTypeCount(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteSportData(PVBluetoothCallback callback, String[] macs) {
        deleteSportData(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getSportData(PVBluetoothCallback callback, int sportDataCount, String[] macs) {
        getSportData(callback, sportDataCount, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteSleepData(PVBluetoothCallback callback, String[] macs) {
        deleteSleepData(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getSleepData(PVBluetoothCallback callback, int sleepDataCount, String[] macs) {
        getSleepData(callback, sleepDataCount, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getDeviceDisplay(PVBluetoothCallback callback, String[] macs) {
        getDeviceDisplay(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getAutoSleep(PVBluetoothCallback callback, String[] macs) {
        getAutoSleep(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setAutoSleep(PVBluetoothCallback callback, int enterHour, int enterMin, int quitHour, int quitMin, int cycle, String[] macs) {
        setAutoSleep(callback, enterHour, enterMin, quitHour, quitMin, cycle, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateCount(PVBluetoothCallback callback, String[] macs) {
        getHeartRateCount(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteHeartRateData(PVBluetoothCallback callback, String[] macs) {
        deleteHeartRateData(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateData(PVBluetoothCallback callback, int heartRateCount, String[] macs) {
        getHeartRateData(callback, heartRateCount, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getAutoHeartRateFrequency(PVBluetoothCallback callback, String[] macs) {
        getAutoHeartRateFrequency(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setAutoHeartRateFrequency(PVBluetoothCallback callback, int heartRateFrequency, String[] macs) {
        setAutoHeartRateFrequency(callback, heartRateFrequency, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateAlarmThreshold(PVBluetoothCallback callback, String[] macs) {
        getHeartRateAlarmThreshold(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setHeartRateAlarmThreshold(PVBluetoothCallback callback, int heartRateAlarmSwitch, int heartRateMinValue, int heartRateMaxValue, String[] macs) {
        setHeartRateAlarmThreshold(callback, heartRateAlarmSwitch, heartRateMinValue, heartRateMaxValue, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getInactivityAlert(PVBluetoothCallback callback, String[] macs) {
        getInactivityAlert(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setInactivityAlert(PVBluetoothCallback callback, int isOpen, int cycle, int interval, int startHour, int startMin, int endHour, int endMin, String[] macs) {
        setInactivityAlert(callback, isOpen, cycle, interval, startHour, startMin, endHour, endMin, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getCaloriesType(PVBluetoothCallback callback, String[] macs) {
        getCaloriesType(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setCaloriesType(PVBluetoothCallback callback, boolean enable, String[] macs) {
        setCaloriesType(callback, enable, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateDataEx(PVBluetoothCallback callback, int heartRateDataCount, String[] macs) {
        getHeartRateDataEx(callback, heartRateDataCount, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void sendIncomeCall(PVBluetoothCallback callback, String nameOrNumber, String[] macs) {
        int commandType = BluetoothSend.COMMAND_TYPE_CALL;
        sendIncomeMissCall(callback, nameOrNumber, BluetoothCommandConstant.PHONE_NAME_PUSH_TYPE_INCOME_CALL, PVBluetoothCallback.BluetoothCommandType.SEND_INCOME_CALL_NAME_OR_NUMBER, commandType, macs);
        sendMessageCount(callback, 1, PVBluetoothCallback.BluetoothCommandType.SEND_INCOME_CALL_COUNT, BluetoothCommandConstant.MSG_PUSH_TYPE_INCOME_CALL, BluetoothSend.COMMAND_TYPE_CALL, macs);
    }

    @Override
    public void sendOffCall(PVBluetoothCallback callback, String[] macs) {
        sendMessageCount(callback, 1, PVBluetoothCallback.BluetoothCommandType.SEND_OFF_HOOK, BluetoothCommandConstant.MSG_PUSH_TYPE_OFF_CALL, BluetoothSend.COMMAND_TYPE_CALL, macs);
    }

    @Override
    public void sendMissCall(PVBluetoothCallback callback, String nameOrNumber, Date dateTime, int missCallCount, String[] macs) {
        String sDateTime = smsDataTimeSDF.format(dateTime);
        int commandType = BluetoothSend.COMMAND_TYPE_CALL;
        sendIncomeMissCall(callback, nameOrNumber, BluetoothCommandConstant.PHONE_NAME_PUSH_TYPE_MISS_CALL, PVBluetoothCallback.BluetoothCommandType.SEND_MISS_CALL_NAME_OR_NUMBER, commandType, macs);
        sendIncomeMissCall(callback, sDateTime, BluetoothCommandConstant.PHONE_NAME_PUSH_TYPE_MISS_CALL_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_MISS_CALL_DATETIME, commandType, macs);
        sendMessageCount(callback, missCallCount, PVBluetoothCallback.BluetoothCommandType.SEND_MISS_CALL_COUNT, BluetoothCommandConstant.MSG_PUSH_TYPE_MISS_CALL, BluetoothSend.COMMAND_TYPE_CALL, macs);
    }

    @Override
    public void sendSMS(PVBluetoothCallback callback, String name, String content, Date dateTime, int smsCount, String[] macs) {
        String sDateTime = smsDataTimeSDF.format(dateTime);
        int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_SMS;
        int commandType = BluetoothSend.COMMAND_TYPE_NOTIFY;
        sendSMSAndNotify(callback, name, BluetoothCommandConstant.SMS_PUSH_TYPE_NAME_OR_NUMBER, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_NAME_OR_NUMBER, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, content, BluetoothCommandConstant.SMS_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_CONTENT, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, sDateTime, BluetoothCommandConstant.SMS_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_DATETIME, smsNotifyType, commandType, macs);
        sendMessageCount(callback, smsCount, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_COUNT, BluetoothCommandConstant.MSG_PUSH_TYPE_SMS, commandType, macs);
    }

    @Override
    public void sendSocial(PVBluetoothCallback callback, String title, String content, Date dateTime, int socialType, int socialCount, String[] macs) {
        String sDateTime = smsDataTimeSDF.format(dateTime);
        int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_SOCIAL;
        int commandType = BluetoothSend.COMMAND_TYPE_NOTIFY;
        sendSMSAndNotify(callback, title, BluetoothCommandConstant.SOCIAL_PUSH_TYPE_TITLE, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_TITLE, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, content, BluetoothCommandConstant.SOCIAL_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_CONTENT, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, sDateTime, BluetoothCommandConstant.SOCIAL_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_DATETIME, smsNotifyType, commandType, macs);
        sendMessageCount(callback, socialCount, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_COUNT, socialType, BluetoothSend.COMMAND_TYPE_NOTIFY, macs);
    }

    @Override
    public void sendEmail(PVBluetoothCallback callback, String email, String content, Date dateTime, int emailCount, String[] macs) {
        if (!TextUtils.isEmpty(email)) {
            String sDateTime = smsDataTimeSDF.format(dateTime);
            int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_EMAIL;
            int commandType = BluetoothSend.COMMAND_TYPE_NOTIFY;
            sendSMSAndNotify(callback, email, BluetoothCommandConstant.EMAIL_PUSH_TYPE_ADDRESS, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_ADDRESS, smsNotifyType, commandType, macs);
            sendSMSAndNotify(callback, content, BluetoothCommandConstant.EMAIL_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_CONTENT, smsNotifyType, commandType, macs);
            sendSMSAndNotify(callback, sDateTime, BluetoothCommandConstant.EMAIL_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_DATETIME, smsNotifyType, commandType, macs);
        }
        sendMessageCount(callback, emailCount, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_COUNT, BluetoothCommandConstant.MSG_PUSH_TYPE_EMAIL, BluetoothSend.COMMAND_TYPE_NOTIFY, macs);
    }

    @Override
    public void sendSchedule(PVBluetoothCallback callback, String content, Date dateTime, int scheduleCount, String[] macs) {
        if (!TextUtils.isEmpty(content)) {
            String sDateTime = smsDataTimeSDF.format(dateTime);
            int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_EMAIL;
            int commandType = BluetoothSend.COMMAND_TYPE_NOTIFY;
            sendSMSAndNotify(callback, content, BluetoothCommandConstant.SCHEDULE_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_SCHEDULE_CONTENT, smsNotifyType, commandType, macs);
            sendSMSAndNotify(callback, sDateTime, BluetoothCommandConstant.SCHEDULE_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_SCHEDULE_DATETIME, smsNotifyType, commandType, macs);
        }
        sendMessageCount(callback, scheduleCount, PVBluetoothCallback.BluetoothCommandType.SEND_SCHEDULE_COUNT, BluetoothCommandConstant.MSG_PUSH_TYPE_CALENDAR, BluetoothSend.COMMAND_TYPE_NOTIFY, macs);
    }

    @Override
    public void getSwitchSetting(PVBluetoothCallback callback, String[] macs) {
        getSwitchSetting(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setSwitchSetting(PVBluetoothCallback callback, int switchType, boolean enable, String[] macs) {
        setSwitchSetting(callback, switchType, enable, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getReminderCount(PVBluetoothCallback callback, String[] macs) {
        getReminderCount(callback, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getReminder(PVBluetoothCallback callback, int reminderCount, String[] macs) {
        getReminder(callback, reminderCount, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void addReminder(PVBluetoothCallback callback, ReminderBT reminderBT, String[] macs) {
        setReminder(callback, 0, null, reminderBT, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteReminder(PVBluetoothCallback callback, ReminderBT reminderBT, String[] macs) {
        int type = reminderBT == null ? 3 : 2;
        setReminder(callback, type, null, reminderBT, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void changeReminder(PVBluetoothCallback callback, ReminderBT oldReminder, ReminderBT newReminder, String[] macs) {
        setReminder(callback, 1, oldReminder, newReminder, BluetoothSend.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUID(PVBluetoothCallback callback, String[] macs) {
        getUID(callback, BluetoothSend.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void setUID(PVBluetoothCallback callback, int UID, String[] macs) {
        setUID(callback, UID, BluetoothSend.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void checkInit(PVBluetoothCallback callback, String[] macs) {
        checkInit(callback, BluetoothSend.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void bindStart(PVBluetoothCallback callback, String[] macs) {
        bindStart(callback, BluetoothSend.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void bindEnd(PVBluetoothCallback callback, String[] macs) {
        bindEnd(callback, BluetoothSend.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void sendSongName(PVBluetoothCallback callback, boolean musicState, String songName, String[] macs) {
        if (TextUtils.isEmpty(songName)) return;
        sendSongName(callback, musicState, songName, BluetoothSend.COMMAND_TYPE_REMOTE_CONTROL, macs);
    }

    @Override
    public void sendStop(PVBluetoothCallback callback, String[] macs) {
        sendStop(callback, BluetoothSend.COMMAND_TYPE_REMOTE_CONTROL, macs);
    }

    @Override
    public void sendVolume(PVBluetoothCallback callback, int volume, String[] macs) {
        sendVolume(callback, volume, BluetoothSend.COMMAND_TYPE_REMOTE_CONTROL, macs);
    }

    @Override
    public void syncBluetoothData(PVBluetoothCallback callback, int syncType, String[] macs) {
        SyncBluetoothData.INSTANCE.start(callback, syncType, macs);
    }

    @Override
    public void bindDevice(PVBluetoothCallback callback, String[] macs) {
        BindDevice.INSTANCE.start(callback, macs);
    }

    /*-------------------------------------------P层内部调用及逻辑实现-------------------------------------------*/
    public void getWatchID(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getWatchID(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.watchID}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, PVBluetoothCallback.BluetoothCommandType.GET_WATCH_ID);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_WATCH_ID);
            }
        }, commandType, macs);
    }

    public void getDeviceVersion(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getDeviceVersion(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.softVersion}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, PVBluetoothCallback.BluetoothCommandType.GET_DEVICE_VERSION);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_DEVICE_VERSION);
            }
        }, commandType, macs);
    }

    public void getDateTime(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getDateTime(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.dateTime}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, PVBluetoothCallback.BluetoothCommandType.GET_DATE_TIME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_DATE_TIME);
            }
        }, commandType, macs);
    }

    public void setDateTime(final PVBluetoothCallback callback, int year, int month, int day, int hour, int min, int sec, int commandType, String[] macs) {
        mCall.setDateTime(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_DATE_TIME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_DATE_TIME);
            }
        }, year, month, day, hour, min, sec, commandType, macs);
    }

    public void getTimeSurfaceSetting(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getTimeSurfaceSetting(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.dateFormat, BluetoothVar.timeFormat, BluetoothVar.batteryShow, BluetoothVar.lunarFormat, BluetoothVar.screenFormat, BluetoothVar.backgroundStyle, BluetoothVar.sportDataShow, BluetoothVar.usernameFormat}, 8, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_TIME_SURFACE_SETTING);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_TIME_SURFACE_SETTING);
            }
        }, commandType, macs);
    }

    public void setTimeSurfaceSetting(final PVBluetoothCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, int commandType, String[] macs) {
        mCall.setTimeSurfaceSetting(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_TIME_SURFACE_SETTING);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_TIME_SURFACE_SETTING);
            }
        }, dateFormat, timeFormat, batteryFormat, lunarFormat, screenFormat, backgroundStyle, sportDataFormat, usernameFormat, commandType, macs);
    }

    public void getScreenBrightness(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getScreenBrightness(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.screenBrightness}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_SCREEN_BRIGHTNESS);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_SCREEN_BRIGHTNESS);
            }
        }, commandType, macs);
    }

    public void setScreenBrightness(final PVBluetoothCallback callback, int brightnessValue, int commandType, String[] macs) {
        mCall.setScreenBrightness(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_SCREEN_BRIGHTNESS);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_SCREEN_BRIGHTNESS);
            }
        }, brightnessValue, commandType, macs);
    }

    public void getBatteryPower(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getBatteryPower(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.batteryPower}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_BATTERY_POWER);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_BATTERY_POWER);
            }
        }, commandType, macs);
    }

    public void getVolume(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getVolume(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.volume}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_VOLUME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_VOLUME);
            }
        }, commandType, macs);
    }

    public void setVolume(final PVBluetoothCallback callback, int volume, int commandType, String[] macs) {
        mCall.setVolume(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_VOLUME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_VOLUME);
            }
        }, volume, commandType, macs);
    }

    public void getShockMode(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getShockMode(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.antiShock, BluetoothVar.clockShock, BluetoothVar.callShock, BluetoothVar.missCallShock, BluetoothVar.smsShock, BluetoothVar.socialShock, BluetoothVar.emailShock, BluetoothVar.calendarShock, BluetoothVar.sedentaryShock, BluetoothVar.lowPowerShock}, 10, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_SHOCK_MODE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_SHOCK_MODE);
            }
        }, commandType, macs);
    }

    public void setShockMode(final PVBluetoothCallback callback, int shockType, int shockMode, int commandType, String[] macs) {
        mCall.setShockMode(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_SHOCK_MODE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_SHOCK_MODE);
            }
        }, shockType, shockMode, commandType, macs);
    }

    public void getLanguage(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getLanguage(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.language}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_LANGUAGE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_LANGUAGE);
            }
        }, commandType, macs);
    }

    public void setLanguage(final PVBluetoothCallback callback, int language, int commandType, String[] macs) {
        mCall.setLanguage(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_LANGUAGE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_LANGUAGE);
            }
        }, language, commandType, macs);
    }

    public void getUnit(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getUnit(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setUnit(BluetoothVar.unit);
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.unit}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_UNIT);

            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_UNIT);
            }
        }, commandType, macs);
    }

    public void setUnit(final PVBluetoothCallback callback, final int unit, int commandType, String[] macs) {
        mCall.setUnit(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setUnit(unit);
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_UNIT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_UNIT);
            }
        }, unit, commandType, macs);
    }

    public void restoreFactory(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.restoreFactory(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.RESTORE_FACTORY);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.RESTORE_FACTORY);
            }
        }, commandType, macs);
    }

    public void enterUpdateMode(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.enterUpdateMode(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.ENTER_UPDATE_MODE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.ENTER_UPDATE_MODE);
            }
        }, commandType, macs);
    }

    public void enterUpdateMode(String touchPanelPath, String heartRatePath, String freescalePath, final PVBluetoothCallback callback, int commandType, String[] macs) {
        byte[] bTouchAddressSectorCount = OtaUtil.getAddressSectorCount(touchPanelPath);
        byte[] bHeartRateAddressSectorCount = OtaUtil.getAddressSectorCount(heartRatePath);
        byte[] bFreescaleAddressSectorCount = OtaUtil.getAddressSectorCount(freescalePath);
        byte[] bTouchAddress = new byte[4];
        byte[] bHeartRateAddress = new byte[4];
        byte[] bFreescaleAddress = new byte[4];
        System.arraycopy(bTouchAddressSectorCount, 0, bTouchAddress, 0, 4);
        System.arraycopy(bHeartRateAddressSectorCount, 0, bHeartRateAddress, 0, 4);
        System.arraycopy(bFreescaleAddressSectorCount, 0, bFreescaleAddress, 0, 4);

        mCall.enterUpdateMode(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.ENTER_UPDATE_MODE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.ENTER_UPDATE_MODE);
            }
        }, bFreescaleAddress, bFreescaleAddressSectorCount[4], bTouchAddress, bTouchAddressSectorCount[4], bHeartRateAddress, bHeartRateAddressSectorCount[4], commandType, macs);
    }

    public void getShockStrength(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getShockStrength(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.shockStrength}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_SHOCK_STRENGTH);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_SHOCK_STRENGTH);
            }
        }, commandType, macs);
    }

    public void setShockStrength(final PVBluetoothCallback callback, int shockStrength, int commandType, String[] macs) {
        mCall.setShockStrength(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_SHOCK_STRENGTH);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_SHOCK_STRENGTH);
            }
        }, shockStrength, commandType, macs);
    }

    public void getWorkMode(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getWorkMode(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.workMode}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_WORK_MODE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_WORK_MODE);
            }
        }, commandType, macs);
    }

    public void setWorkMode(final PVBluetoothCallback callback, int workMode, int commandType, String[] macs) {
        mCall.setWorkMode(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_WORK_MODE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_WORK_MODE);
            }
        }, workMode, commandType, macs);
    }

    public void getBrightScreenTime(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getBrightScreenTime(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.brightScreenTime}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_BRIGHT_SCREEN_TIME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_BRIGHT_SCREEN_TIME);
            }
        }, commandType, macs);
    }

    public void setBrightScreenTime(final PVBluetoothCallback callback, int brightScreenTime, int commandType, String[] macs) {
        mCall.setBrightScreenTime(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_BRIGHT_SCREEN_TIME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_BRIGHT_SCREEN_TIME);
            }
        }, brightScreenTime, commandType, macs);
    }

    public void getUserInfo(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getUserInfo(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.sex, BluetoothVar.age, BluetoothVar.height, BluetoothVar.weight}, 4, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_USER_INFO);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_USER_INFO);
            }
        }, commandType, macs);
    }

    public void setUserInfo(final PVBluetoothCallback callback, int sex, int age, int height, int weight, int commandType, String[] macs) {
        mCall.setUserInfo(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_USER_INFO);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_USER_INFO);
            }
        }, sex, age, height, weight, commandType, macs);
    }

    public void getUsageHabits(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getUsageHabits(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.usageHabits}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_USAGE_HABIT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_USAGE_HABIT);
            }
        }, commandType, macs);
    }

    public void setUsageHabits(final PVBluetoothCallback callback, int usageHabits, int commandType, String[] macs) {
        mCall.setUsageHabits(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_USAGE_HABIT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_USAGE_HABIT);
            }
        }, usageHabits, commandType, macs);
    }

    public void getUserName(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getUserName(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.userName}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, PVBluetoothCallback.BluetoothCommandType.GET_USER_NAME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_USER_NAME);
            }
        }, commandType, macs);
    }

    public void setUserName(final PVBluetoothCallback callback, String userName, int commandType, String[] macs) {
        mCall.setUserName(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_USER_NAME);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_USER_NAME);
            }
        }, userName, commandType, macs);
    }

    public void getGoal(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getGoal(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.stepGoalsValue, BluetoothVar.calorieGoalsValue, BluetoothVar.distanceGoalsValue, BluetoothVar.sportTimeGoalsValue, BluetoothVar.sleepGoalsValue}, 5, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_GOAL_SETTING);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_GOAL_SETTING);
            }
        }, commandType, macs);
    }

    public void setStepGoal(final PVBluetoothCallback callback, int stepGoal, int commandType, String[] macs) {
        stepGoal /= 100;
        mCall.setGoal(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_STEP_GOAL);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_STEP_GOAL);
            }
        }, BluetoothCommandConstant.GOAL_TYPE_STEP, stepGoal, commandType, macs);
    }

    public void setCaloriesGoal(final PVBluetoothCallback callback, int caloriesGoal, int commandType, String[] macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_CALORIES_GOAL);

            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_CALORIES_GOAL);
            }
        }, BluetoothCommandConstant.GOAL_TYPE_CALORIE, caloriesGoal, commandType, macs);
    }

    public void setDistanceGoal(final PVBluetoothCallback callback, int distanceGoal, int commandType, String[] macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_DISTANCE_GOAL);

            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_DISTANCE_GOAL);
            }
        }, BluetoothCommandConstant.GOAL_TYPE_DISTANCE, distanceGoal, commandType, macs);
    }

    public void setSportTimeGoal(final PVBluetoothCallback callback, int sportTimeGoal, int commandType, String[] macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_SPORT_TIME_GOAL);

            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_SPORT_TIME_GOAL);
            }
        }, BluetoothCommandConstant.GOAL_TYPE_SPORT_TIME, sportTimeGoal, commandType, macs);
    }

    public void setSleepGoal(final PVBluetoothCallback callback, int sleepGoal, int commandType, String[] macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_SLEEP_GOAL);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_SLEEP_GOAL);
            }
        }, BluetoothCommandConstant.GOAL_TYPE_SLEEP, sleepGoal, commandType, macs);
    }

    public void getSportSleepMode(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getSportSleepMode(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.sportSleepMode}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_SPORT_SLEEP_MODE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_SPORT_SLEEP_MODE);
            }
        }, commandType, macs);
    }

    public void getAllDataTypeCount(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getAllDataTypeCount(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.sportCount, BluetoothVar.sleepCount, BluetoothVar.heartRateCount, BluetoothVar.moodCount, BluetoothVar.bloodPressureCount}, 5, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_ALL_DATA_TYPE_COUNT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_ALL_DATA_TYPE_COUNT);
            }
        }, commandType, macs);
    }

    public void deleteSportData(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.deleteSportData(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.DELETE_SPORT_DATA);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.DELETE_SPORT_DATA);
            }
        }, commandType, macs);
    }

    public void getSportData(final PVBluetoothCallback callback, int sportDataCount, int commandType, String[] macs) {
        mCall.getSportData(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.sportBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_SPORT, mac, PVBluetoothCallback.BluetoothCommandType.GET_SPORT_DATA);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_SPORT_DATA);
            }
        }, sportDataCount, commandType, macs);
    }

    public void deleteSleepData(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.deleteSleepData(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.DELETE_SLEEP_DATA);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.DELETE_SLEEP_DATA);
            }
        }, commandType, macs);
    }

    public void getSleepData(final PVBluetoothCallback callback, int sleepDataCount, int commandType, String[] macs) {
        mCall.getSleepData(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.sleepBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_SLEEP, mac, PVBluetoothCallback.BluetoothCommandType.GET_SLEEP_DATA);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_SLEEP_DATA);
            }
        }, sleepDataCount, commandType, macs);
    }

    public void getDeviceDisplay(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getDeviceDisplay(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.deviceDisplayStep, BluetoothVar.deviceDisplayCalorie, BluetoothVar.deviceDisplayDistance, BluetoothVar.deviceDisplaySportTime, BluetoothVar.deviceDisplaySleep, BluetoothVar.deviceDisplayHeartRate, BluetoothVar.deviceDisplayMood}, 7, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_DEVICE_DISPLAY);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_DEVICE_DISPLAY);
            }
        }, commandType, macs);
    }

    public void getAutoSleep(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getAutoSleep(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setBedtimeHour(BluetoothVar.bedTimeHour);
                pvspCall.setBedTimeMin(BluetoothVar.bedTimeMin);
                pvspCall.setAwakeTimeHour(BluetoothVar.awakeTimeHour);
                pvspCall.setAwakeTimeMin(BluetoothVar.awakeTimeMin);
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.bedTimeHour, BluetoothVar.bedTimeMin, BluetoothVar.awakeTimeHour, BluetoothVar.awakeTimeMin, BluetoothVar.remindSleepCycle}, 5, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_AUTO_SLEEP);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_AUTO_SLEEP);
            }
        }, commandType, macs);
    }

    public void setAutoSleep(final PVBluetoothCallback callback, final int bedTimeHour, final int bedTimeMin, final int awakeTimeHour, final int awakeTimeMin, int cycle, int commandType, String[] macs) {
        mCall.setAutoSleep(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setBedtimeHour(bedTimeHour);
                pvspCall.setBedTimeMin(bedTimeMin);
                pvspCall.setAwakeTimeHour(awakeTimeHour);
                pvspCall.setAwakeTimeMin(awakeTimeMin);
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_AUTO_SLEEP);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_AUTO_SLEEP);
            }
        }, bedTimeHour, bedTimeMin, awakeTimeHour, awakeTimeMin, cycle, commandType, macs);
    }

    public void getHeartRateCount(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getHeartRateCount(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.heartRateCount}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_DATA_COUNT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_DATA_COUNT);
            }
        }, commandType, macs);
    }

    public void deleteHeartRateData(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.deleteHeartRateData(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.DELETE_HEART_RATE_DATA);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.DELETE_HEART_RATE_DATA);
            }
        }, commandType, macs);
    }

    public void getHeartRateData(final PVBluetoothCallback callback, int heartRateCount, int commandType, String[] macs) {
        mCall.getHeartRateData(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.heartRateBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_HEART_RATE, mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE);
            }
        }, heartRateCount, commandType, macs);
    }

    public void getAutoHeartRateFrequency(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getAutoHeartRateFrequency(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setHeartRateFrequency(BluetoothVar.heartRateFrequency);
                pvspCall.setHeartRateAutoTrackSwitch(BluetoothVar.heartRateAutoTrackSwitch);
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.heartRateFrequency}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_FREQUENCY);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_FREQUENCY);
            }
        }, commandType, macs);
    }

    public void setAutoHeartRateFrequency(final PVBluetoothCallback callback, final int heartRateFrequency, int commandType, String[] macs) {
        mCall.setAutoHeartRateFrequency(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setHeartRateFrequency(heartRateFrequency);
                pvspCall.setHeartRateAutoTrackSwitch(heartRateFrequency > 0);
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_HEART_RATE_FREQUENCY);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_HEART_RATE_FREQUENCY);
            }
        }, heartRateFrequency, commandType, macs);
    }

    public void getHeartRateAlarmThreshold(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getHeartRateAlarmThreshold(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setHeartRateRangeAlertSwitch(BluetoothVar.heartRateAlarmSwitch);
                pvspCall.setHeartRateMin(BluetoothVar.heartRateMinValue);
                pvspCall.setHeartRateMax(BluetoothVar.heartRateMaxValue);
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.heartRateAlarmSwitch ? 1 : 0, BluetoothVar.heartRateMinValue, BluetoothVar.heartRateMaxValue}, 3, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_ALARM_THRESHOLD);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_ALARM_THRESHOLD);
            }
        }, commandType, macs);
    }

    public void setHeartRateAlarmThreshold(final PVBluetoothCallback callback, final int heartRateAlarmSwitch, final int heartRateMinValue, final int heartRateMaxValue, int commandType, String[] macs) {
        mCall.setHeartRateAlarmThreshold(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setHeartRateRangeAlertSwitch(heartRateAlarmSwitch > 0);
                pvspCall.setHeartRateMin(heartRateMinValue);
                pvspCall.setHeartRateMax(heartRateMaxValue);
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_HEART_RATE_ALARM_THRESHOLD);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_HEART_RATE_ALARM_THRESHOLD);
            }
        }, heartRateAlarmSwitch, heartRateMinValue, heartRateMaxValue, commandType, macs);
    }

    public void getInactivityAlert(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getInactivityAlert(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setInactivityAlertSwitch(BluetoothVar.inactivityAlertSwitch);
                pvspCall.setInactivityAlertCycle(BluetoothVar.inactivityAlertCycle);
                pvspCall.setInactivityAlertInterval(BluetoothVar.inactivityAlertInterval);
                pvspCall.setInactivityAlertStartHour(BluetoothVar.inactivityAlertStartHour);
                pvspCall.setInactivityAlertStartMin(BluetoothVar.inactivityAlertStartMin);
                pvspCall.setInactivityAlertEndHour(BluetoothVar.inactivityAlertEndHour);
                pvspCall.setInactivityAlertEndMin(BluetoothVar.inactivityAlertEndMin);
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.inactivityAlertSwitch ? 1 : 0, BluetoothVar.inactivityAlertCycle, BluetoothVar.inactivityAlertInterval, BluetoothVar.inactivityAlertStartHour, BluetoothVar.inactivityAlertStartMin, BluetoothVar.inactivityAlertEndHour, BluetoothVar.inactivityAlertEndMin}, 7, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_INACTIVITY_ALERT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_INACTIVITY_ALERT);
            }
        }, commandType, macs);
    }

    public void setInactivityAlert(final PVBluetoothCallback callback, final int isOpen, final int cycle, final int interval, final int startHour, final int startMin, final int endHour, final int endMin, int commandType, String[] macs) {
        mCall.setInactivityAlert(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setInactivityAlertSwitch(isOpen > 0);
                pvspCall.setInactivityAlertCycle(cycle);
                pvspCall.setInactivityAlertInterval(interval);
                pvspCall.setInactivityAlertStartHour(startHour);
                pvspCall.setInactivityAlertStartMin(startMin);
                pvspCall.setInactivityAlertEndHour(endHour);
                pvspCall.setInactivityAlertEndMin(endMin);
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_INACTIVITY_ALERT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_INACTIVITY_ALERT);
            }
        }, isOpen, cycle, interval, startHour, startMin, endHour, endMin, commandType, macs);
    }

    public void getCaloriesType(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getCaloriesType(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setCaloriesType(BluetoothVar.caloriesType);
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.caloriesType}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_CALORIES_TYPE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_CALORIES_TYPE);
            }
        }, commandType, macs);
    }

    public void setCaloriesType(final PVBluetoothCallback callback, final boolean enable, int commandType, String[] macs) {
        mCall.setCaloriesType(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setCaloriesType(enable ? 1 : 0);
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_CALORIES_TYPE);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_CALORIES_TYPE);
            }
        }, enable, commandType, macs);
    }

    public void getHeartRateDataEx(final PVBluetoothCallback callback, int heartRateDataCount, int commandType, String[] macs) {
        mCall.getHeartRateDataEx(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.heartRateBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_HEART_RATE, mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_EX);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_EX);
            }
        }, heartRateDataCount, commandType, macs);
    }

    public void sendMessageCount(final PVBluetoothCallback callback, int msgCount, final PVBluetoothCallback.BluetoothCommandType bluetoothCommandType, int msgType, int commandType, String[] macs) {
        mCall.sendMessageCount(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, bluetoothCommandType);
            }
        }, msgType, msgCount, commandType, macs);
    }

    public void sendIncomeMissCall(final PVBluetoothCallback callback, String nameOrNumber, int pushType, final PVBluetoothCallback.BluetoothCommandType bluetoothCommandType, int commandType, String[] macs) {
        int len = (nameOrNumber.getBytes().length > BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN ? BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN : nameOrNumber.getBytes().length) + 1;
        byte[] bSendData = new byte[len - 1];
        System.arraycopy(nameOrNumber.getBytes(), 0, bSendData, 0, len - 1);
        mCall.sendPhoneName(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, bluetoothCommandType);
            }
        }, len, pushType, bSendData, commandType, macs);
    }

    public void sendSMSAndNotify(final PVBluetoothCallback callback, String pushData, int pushType, final PVBluetoothCallback.BluetoothCommandType bluetoothCommandType, int smsNotifyType, int commandType, String[] macs) {
        int len = (pushData.getBytes().length > BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN ? BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN : pushData.getBytes().length) + 1;
        byte[] bSendData = new byte[len - 1];
        System.arraycopy(pushData.getBytes(), 0, bSendData, 0, len - 1);
        switch (bluetoothCommandType) {
            case SEND_SMS_DATETIME:
            case SEND_SOCIAL_DATETIME:
            case SEND_EMAIL_DATETIME:
            case SEND_SCHEDULE_DATETIME:
                try {
                    len = 16;
                    bSendData = pushData.getBytes("US-ASCII");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        mCall.sendSMSAndNotify(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, bluetoothCommandType);
            }
        }, len, pushType, bSendData, smsNotifyType, commandType, macs);
    }

    public void getSwitchSetting(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getSwitchSetting(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                pvspCall.setAntiSwitch(BluetoothVar.antiLostSwitch);
//                pvspCall.setAutoSyncSwitch(BluetoothVar.autoSyncSwitch);
                pvspCall.setCallSwitch(BluetoothVar.incomeCallSwitch);
                pvspCall.setMissCallSwitch(BluetoothVar.missCallSwitch);
                pvspCall.setSMSSwitch(BluetoothVar.smsSwitch);
                pvspCall.setSocialSwitch(BluetoothVar.socialSwitch);
                pvspCall.setEmailSwitch(BluetoothVar.emailSwitch);
                pvspCall.setCalendarSwitch(BluetoothVar.calendarSwitch);
                pvspCall.setRaiseWakeSwitch(BluetoothVar.raiseWakeSwitch);
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.antiLostSwitch ? 1 : 0, BluetoothVar.autoSyncSwitch ? 1 : 0, BluetoothVar.sleepSwitch ? 1 : 0, BluetoothVar.sleepStateSwitch ? 1 : 0, BluetoothVar.incomeCallSwitch ? 1 : 0, BluetoothVar.missCallSwitch ? 1 : 0, BluetoothVar.smsSwitch ? 1 : 0, BluetoothVar.socialSwitch ? 1 : 0, BluetoothVar.emailSwitch ? 1 : 0, BluetoothVar.calendarSwitch ? 1 : 0, BluetoothVar.sedentarySwitch ? 1 : 0, BluetoothVar.lowPowerSwitch ? 1 : 0, BluetoothVar.secondRemindSwitch ? 1 : 0, BluetoothVar.raiseWakeSwitch ? 1 : 0}, 14, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, PVBluetoothCallback.BluetoothCommandType.GET_SWITCH);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_SWITCH);
            }
        }, commandType, macs);
    }

    public void setSwitchSetting(final PVBluetoothCallback callback, final int switchType, final boolean enable, int commandType, String[] macs) {
        mCall.setSwitchSetting(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                switch (switchType) {
                    case PVBluetoothCall.SWITCH_TYPE_ANTI:
                        pvspCall.setAntiSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_AUTO_SYNC:
                        pvspCall.setAutoSyncSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_CALL:
                        pvspCall.setCallSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_MISS_CALL:
                        pvspCall.setMissCallSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_SMS:
                        pvspCall.setSMSSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_SOCIAL:
                        pvspCall.setSocialSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_EMAIL:
                        pvspCall.setEmailSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_CALENDAR:
                        pvspCall.setCalendarSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_RAISE_WAKE:
                        pvspCall.setRaiseWakeSwitch(enable);
                        break;

                }
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_SWITCH);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_SWITCH);
            }
        }, switchType, enable, commandType, macs);
    }

    public void getReminderCount(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getReminderCount(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.remindCount}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_REMINDER_COUNT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_REMINDER_COUNT);
            }
        }, commandType, macs);
    }

    public void getReminder(final PVBluetoothCallback callback, int reminderCount, int commandType, String[] macs) {
        mCall.getReminder(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.reminderBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_REMINDER, mac, PVBluetoothCallback.BluetoothCommandType.GET_REMINDER);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_REMINDER);
            }
        }, reminderCount, commandType, macs);
    }

    public void setReminder(final PVBluetoothCallback callback, int type, ReminderBT oldReminderData, ReminderBT newReminderData, int commandType, String[] macs) {
        PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.NEW_REMINDER;
        switch (type) {
            case 0:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.NEW_REMINDER;
                break;
            case 1:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.CHANGE_REMINDER;
                break;
            case 2:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.DELETE_A_REMINDER;
                break;
            case 3:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.DELETE_ALL_REMINDER;
                break;
        }
        final PVBluetoothCallback.BluetoothCommandType finalBluetoothCommandType = bluetoothCommandType;
        mCall.setReminder(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, finalBluetoothCommandType);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, finalBluetoothCommandType);
            }
        }, type, oldReminderData, newReminderData, commandType, macs);
    }

    public void getUID(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.getUID(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.UID}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.GET_UID);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.GET_UID);
            }
        }, commandType, macs);
    }

    public void setUID(final PVBluetoothCallback callback, int UID, int commandType, String[] macs) {
        mCall.setUID(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.SET_UID);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.SET_UID);
            }
        }, UID, commandType, macs);
    }

    public void checkInit(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.checkInit(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{BluetoothVar.initFlag}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.CHECK_INIT);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.CHECK_INIT);
            }
        }, commandType, macs);
    }

    public void bindStart(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.bindStart(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.BIND_START);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.BIND_START);
            }
        }, commandType, macs);
    }

    public void bindEnd(final PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.bindEnd(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(Leaf obj, String mac) {
                if (callback != null)
                    callback.onSuccess(new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, PVBluetoothCallback.BluetoothCommandType.BIND_END);
            }

            @Override
            public void onFailed(Leaf obj, String mac) {
                if (callback != null)
                    callback.onFail(mac, PVBluetoothCallback.BluetoothCommandType.BIND_END);
            }
        }, commandType, macs);
    }

    public void sendSongName(PVBluetoothCallback callback, boolean musicState, String songName, int commandType, String[] macs) {
        byte bMusicState = musicState ? BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_PLAY : BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_PAUSE;
        byte[] bOldSongName = songName.getBytes();
        int songLen = bOldSongName.length > BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN ? BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN : bOldSongName.length;
        byte[] bNewSongName = new byte[songLen];
        System.arraycopy(bOldSongName, 0, bNewSongName, 0, songLen);
        mCall.sendSongName(bMusicState, bNewSongName, commandType, macs);
    }

    public void sendStop(PVBluetoothCallback callback, int commandType, String[] macs) {
        mCall.sendStop(commandType, macs);
    }

    public void sendVolume(PVBluetoothCallback callback, int volume, int commandType, String[] macs) {
        mCall.sendVolume(volume, commandType, macs);
    }
}
