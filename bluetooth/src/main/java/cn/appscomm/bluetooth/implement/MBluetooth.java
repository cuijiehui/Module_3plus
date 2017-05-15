package cn.appscomm.bluetooth.implement;


import android.text.TextUtils;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothLeService;
import cn.appscomm.bluetooth.BluetoothManager;
import cn.appscomm.bluetooth.BluetoothSend;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.interfaces.PMBluetoothCall;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.protocol.AboutExtend.Music;
import cn.appscomm.bluetooth.protocol.AboutMsgPush.EmailPush;
import cn.appscomm.bluetooth.protocol.AboutMsgPush.MsgCountPush;
import cn.appscomm.bluetooth.protocol.AboutMsgPush.PhoneNamePush;
import cn.appscomm.bluetooth.protocol.AboutMsgPush.SchedulePush;
import cn.appscomm.bluetooth.protocol.AboutMsgPush.SmsPush;
import cn.appscomm.bluetooth.protocol.AboutMsgPush.SocialPush;
import cn.appscomm.bluetooth.protocol.AboutSetting.BatteryPower;
import cn.appscomm.bluetooth.protocol.AboutSetting.BrightScreenTime;
import cn.appscomm.bluetooth.protocol.AboutSetting.DateTime;
import cn.appscomm.bluetooth.protocol.AboutSetting.DeviceVersion;
import cn.appscomm.bluetooth.protocol.AboutSetting.Language;
import cn.appscomm.bluetooth.protocol.AboutSetting.RestoreFactory;
import cn.appscomm.bluetooth.protocol.AboutSetting.ScreenBrightnessSetting;
import cn.appscomm.bluetooth.protocol.AboutSetting.ShockMode;
import cn.appscomm.bluetooth.protocol.AboutSetting.ShockStrength;
import cn.appscomm.bluetooth.protocol.AboutSetting.TimeSurfaceSetting;
import cn.appscomm.bluetooth.protocol.AboutSetting.Unit;
import cn.appscomm.bluetooth.protocol.AboutSetting.UpgradeMode;
import cn.appscomm.bluetooth.protocol.AboutSetting.Volume;
import cn.appscomm.bluetooth.protocol.AboutSetting.WatchID;
import cn.appscomm.bluetooth.protocol.AboutSetting.WorkMode;
import cn.appscomm.bluetooth.protocol.AboutSport.AllDataTypeCount;
import cn.appscomm.bluetooth.protocol.AboutSport.CaloriesType;
import cn.appscomm.bluetooth.protocol.AboutSport.GetHeartRateDataEx;
import cn.appscomm.bluetooth.protocol.AboutSport.HeartRateAlarmThreshold;
import cn.appscomm.bluetooth.protocol.AboutSport.HeartRateFrequency;
import cn.appscomm.bluetooth.protocol.AboutSport.AutoSleep;
import cn.appscomm.bluetooth.protocol.AboutSport.DeleteHeartRateData;
import cn.appscomm.bluetooth.protocol.AboutSport.DeleteSleepData;
import cn.appscomm.bluetooth.protocol.AboutSport.DeleteSportData;
import cn.appscomm.bluetooth.protocol.AboutSport.DeviceDisplayData;
import cn.appscomm.bluetooth.protocol.AboutSport.GetHeartRateData;
import cn.appscomm.bluetooth.protocol.AboutSport.GetSleepData;
import cn.appscomm.bluetooth.protocol.AboutSport.GetSportData;
import cn.appscomm.bluetooth.protocol.AboutSport.Goal;
import cn.appscomm.bluetooth.protocol.AboutSport.HeartRateCount;
import cn.appscomm.bluetooth.protocol.AboutSport.InactivityAlert;
import cn.appscomm.bluetooth.protocol.AboutSport.SportSleepMode;
import cn.appscomm.bluetooth.protocol.AboutState.BindEnd;
import cn.appscomm.bluetooth.protocol.AboutState.BindStart;
import cn.appscomm.bluetooth.protocol.AboutState.RemindCount;
import cn.appscomm.bluetooth.protocol.AboutState.RemindSetting;
import cn.appscomm.bluetooth.protocol.AboutState.SwitchSetting;
import cn.appscomm.bluetooth.protocol.AboutUser.UsageHabits;
import cn.appscomm.bluetooth.protocol.AboutUser.UserInfo;
import cn.appscomm.bluetooth.protocol.AboutUser.UserName;

/**
 * 作者：hsh
 * 日期：2017/3/20
 * 说明：蓝牙M模块实现类
 */
public enum MBluetooth implements PMBluetoothCall {
    INSTANCE {

    };

    /*------------------------------------------------------------------服务相关-------------------------------------------------------------------*/
    @Override
    public void startService() {
        BluetoothManager.INSTANCE.startService();
    }

    @Override
    public boolean resetService(String mac, boolean isSupportHeartRate) {
        return BluetoothManager.INSTANCE.restartService(mac, isSupportHeartRate);
    }

    @Override
    public void endService(String mac) {
        BluetoothManager.INSTANCE.endService(mac);
    }

    @Override
    public boolean isBluetoothLeServiceRunning() {
        return BluetoothManager.INSTANCE.isBluetoothLeServiceRunning();
    }

    @Override
    public void connect(String mac, boolean isSupportHeartRate) {
        BluetoothManager.INSTANCE.connect(mac, isSupportHeartRate);
    }

    @Override
    public boolean isConnect(String mac) {
        return BluetoothManager.INSTANCE.isConnect(mac);
    }

    /*------------------------------------------------------------------设备属性-------------------------------------------------------------------*/
    public void getWatchID(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new WatchID(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getDeviceVersion(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new DeviceVersion(callback, 1, 1), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getDateTime(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new DateTime(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setDateTime(IBluetoothResultCallback callback, int year, int month, int day, int hour, int min, int sec, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new DateTime(callback, 7, year, month, day, hour, min, sec), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getTimeSurfaceSetting(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new TimeSurfaceSetting(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setTimeSurfaceSetting(IBluetoothResultCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new TimeSurfaceSetting(callback, 8, (byte) dateFormat, (byte) timeFormat, (byte) batteryFormat, (byte) lunarFormat, (byte) screenFormat, (byte) backgroundStyle, (byte) sportDataFormat, (byte) usernameFormat), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getScreenBrightness(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new ScreenBrightnessSetting(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setScreenBrightness(IBluetoothResultCallback callback, int brightnessValue, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new ScreenBrightnessSetting(callback, 1, (byte) brightnessValue), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getBatteryPower(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BatteryPower(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getVolume(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Volume(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setVolume(IBluetoothResultCallback callback, int volume, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Volume(callback, 1, (byte) volume), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getShockMode(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new ShockMode(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setShockMode(IBluetoothResultCallback callback, int shockType, int shockMode, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new ShockMode(callback, 2, (byte) shockType, (byte) shockMode), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getLanguage(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Language(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setLanguage(IBluetoothResultCallback callback, int language, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Language(callback, 1, (byte) language), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getUnit(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Unit(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setUnit(IBluetoothResultCallback callback, int unit, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Unit(callback, 1, (byte) unit), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void restoreFactory(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new RestoreFactory(callback, 1, (byte) 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void enterUpdateMode(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UpgradeMode(callback, 1, (byte) 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void enterUpdateMode(IBluetoothResultCallback callback, byte[] freescaleAddress, byte freescaleSectorCount, byte[] touchAddress, byte touchSectorCount, byte[] heartRateAddress, byte heartRateSectorCount, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UpgradeMode(callback, 16, (byte) 1, freescaleAddress, freescaleSectorCount, touchAddress, touchSectorCount, heartRateAddress, heartRateSectorCount), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getShockStrength(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new ShockStrength(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setShockStrength(IBluetoothResultCallback callback, int shockStrength, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new ShockStrength(callback, 1, (byte) shockStrength), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getWorkMode(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new WorkMode(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setWorkMode(IBluetoothResultCallback callback, int workMode, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new WorkMode(callback, 1, (byte) workMode), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getBrightScreenTime(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BrightScreenTime(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setBrightScreenTime(IBluetoothResultCallback callback, int brightScreenTime, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BrightScreenTime(callback, 1, (byte) brightScreenTime), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getUserInfo(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UserInfo(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setUserInfo(IBluetoothResultCallback callback, int sex, int age, int height, int weight, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UserInfo(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getUsageHabits(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UsageHabits(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setUsageHabits(IBluetoothResultCallback callback, int usageHabits, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UsageHabits(callback, 1, (byte) usageHabits), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getUserName(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UserName(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setUserName(IBluetoothResultCallback callback, String userName, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new UserName(callback, 16, userName), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getGoal(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Goal(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setGoal(IBluetoothResultCallback callback, int goalType, int goalValue, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Goal(callback, 4, (byte) goalType, goalValue, (byte) 0x01), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getSportSleepMode(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new SportSleepMode(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getAllDataTypeCount(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new AllDataTypeCount(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void deleteSportData(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new DeleteSportData(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getSportData(IBluetoothResultCallback callback, int sportDataCount, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new GetSportData(callback, 2, 0, sportDataCount), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void deleteSleepData(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new DeleteSleepData(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getSleepData(IBluetoothResultCallback callBack, int sleepDataCount, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new GetSleepData(callBack, 1, 0, sleepDataCount), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getDeviceDisplay(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new DeviceDisplayData(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getAutoSleep(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new AutoSleep(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setAutoSleep(IBluetoothResultCallback callback, int enterHour, int enterMin, int quitHour, int quitMin, int cycle, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new AutoSleep(callback, 5, (byte) enterHour, (byte) enterMin, (byte) quitHour, (byte) quitMin, (byte) cycle), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getHeartRateCount(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new HeartRateCount(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void deleteHeartRateData(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new DeleteHeartRateData(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getHeartRateData(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new GetHeartRateData(callback, 1, 0, heartRateDataCount), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getAutoHeartRateFrequency(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new HeartRateFrequency(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setAutoHeartRateFrequency(IBluetoothResultCallback callback, int heartRateFrequency, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new HeartRateFrequency(callback, 1, (byte) heartRateFrequency), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getHeartRateAlarmThreshold(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new HeartRateAlarmThreshold(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setHeartRateAlarmThreshold(IBluetoothResultCallback callback, int heartRateAlarmSwitch, int heartRateMinValue, int heartRateMaxValue, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new HeartRateAlarmThreshold(callback, 3, (byte) heartRateMaxValue, (byte) heartRateMinValue, heartRateAlarmSwitch == 1), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getInactivityAlert(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new InactivityAlert(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setInactivityAlert(IBluetoothResultCallback callback, int isOpen, int cycle, int interval, int startHour, int startMin, int endHour, int endMin, int commandType, String[] macs) {
        if (macs == null) return;
        cycle = isOpen == 1 ? cycle + (1 << 7) : cycle;
        BluetoothSend.addLeaf(new InactivityAlert(callback, 8, cycle, interval, startHour, startMin, endHour, endMin, 100), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getCaloriesType(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new CaloriesType(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setCaloriesType(IBluetoothResultCallback callback, boolean enable, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new CaloriesType(callback, 1, enable ? (byte) 0x01 : (byte) 0x00), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getHeartRateDataEx(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new GetHeartRateDataEx(callback, 1, 0, heartRateDataCount), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void sendPhoneName(IBluetoothResultCallback callback, int len, int callType, byte[] bNameOrNumber, int commandType, String[] macs) {
        if (macs == null) return;
        if (macs == null) return;
        BluetoothSend.addLeaf(new PhoneNamePush(callback, len, (byte) callType, bNameOrNumber), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void sendMessageCount(IBluetoothResultCallback callback, int msgType, int msgCount, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new MsgCountPush(callback, 2, (byte) msgType, (byte) msgCount), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void sendSMSAndNotify(IBluetoothResultCallback callback, int pushLen, int pushType, byte[] pushData, int smsNotifyType, int commandType, String[] macs) {
        if (macs == null) return;
        switch (smsNotifyType) {
            case PMBluetoothCall.SMS_NOTIFY_TYPE_SMS:
                BluetoothSend.addLeaf(new SmsPush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
            case PMBluetoothCall.SMS_NOTIFY_TYPE_SOCIAL:
                BluetoothSend.addLeaf(new SocialPush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
            case PMBluetoothCall.SMS_NOTIFY_TYPE_EMAIL:
                BluetoothSend.addLeaf(new EmailPush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
            case PMBluetoothCall.SMS_NOTIFY_TYPE_SCHEDULE:
                BluetoothSend.addLeaf(new SchedulePush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
        }
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getSwitchSetting(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new SwitchSetting(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setSwitchSetting(IBluetoothResultCallback callback, int switchType, boolean enable, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new SwitchSetting(callback, 3, (byte) 0x01, (byte) switchType, enable ? (byte) 0x01 : (byte) 0x00), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getReminderCount(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new RemindCount(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getReminder(IBluetoothResultCallback callback, int reminderCount, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new RemindSetting(callback, 1, 0, reminderCount), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setReminder(IBluetoothResultCallback callback, int type, ReminderBT reminderData, ReminderBT reminderData1, int commandType, String[] macs) {
        if (macs == null) return;
        int len = 6;
        byte[] remindContent = null;
        ReminderBT newReminderData = type == 3 ? new ReminderBT() : reminderData1;
        ReminderBT oldReminderData = new ReminderBT();
        oldReminderData.type = 0;
        oldReminderData.hour = 0;
        oldReminderData.min = 0;
        oldReminderData.cycle = 0;
        oldReminderData.enable = false;

        if (type == 1) {                                                                            // 修改
            len = 11;
            newReminderData = reminderData;
            oldReminderData = reminderData1;
        }
        if (!TextUtils.isEmpty(newReminderData.content)) {
            len += (newReminderData.content.getBytes().length > 24 ? 24 : newReminderData.content.getBytes().length);
            remindContent = new byte[len - type == 1 ? 11 : 6];
            System.arraycopy(newReminderData.content.getBytes(), 0, remindContent, 0, remindContent.length);
        }

        BluetoothSend.addLeaf(new RemindSetting(callback, len, (byte) type,
                (byte) newReminderData.type, (byte) newReminderData.hour, (byte) newReminderData.min, (byte) newReminderData.cycle, newReminderData.enable ? (byte) 0x01 : (byte) 0x00,
                remindContent,
                (byte) oldReminderData.type, (byte) oldReminderData.hour, (byte) oldReminderData.min, (byte) oldReminderData.cycle, oldReminderData.enable ? (byte) 0x01 : (byte) 0x00), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void getUID(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BindStart(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void setUID(IBluetoothResultCallback callback, int UID, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BindStart(callback, 5, BluetoothCommandConstant.BIND_START_SET_UID, UID), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void checkInit(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BindEnd(callback, 1, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void bindStart(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BindStart(callback, 1, BluetoothCommandConstant.BIND_START_NO_UID, 0), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void bindEnd(IBluetoothResultCallback callback, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new BindEnd(callback, 1, (byte) 0x01), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void sendSongName(byte musicState, byte[] songName, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Music(songName.length + 1, musicState, songName), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void sendStop(int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Music(1, BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_STOP, null), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }

    @Override
    public void sendVolume(int volume, int commandType, String[] macs) {
        if (macs == null) return;
        BluetoothSend.addLeaf(new Music(2, BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_VOLUME, new byte[]{(byte) volume}), commandType, macs);
        for (String mac : macs) BluetoothManager.INSTANCE.send(mac);
    }
}
