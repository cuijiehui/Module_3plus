package cn.appscomm.sp.implement;

import android.text.TextUtils;

import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;
import cn.appscomm.sp.interfaces.PMSPCall;

/**
 * Created by Administrator on 2017/3/6.
 */

public enum MSP implements PMSPCall {
    INSTANCE;

    @Override
    public Object getSPValue(String configKey, int dataType) {
        return SPManager.INSTANCE.getSPValue(configKey, dataType);
    }

    @Override
    public boolean setSPValue(String configKey, Object configValue) {
        return SPManager.INSTANCE.setSPValue(configKey, configValue);
    }

    @Override
    public boolean delSPFile() {
        return SPManager.INSTANCE.delSPFile();
    }

    @Override
    public boolean delSPValue(String configKey) {
        return SPManager.INSTANCE.delSPValue(configKey);
    }


    @Override
    public int getStepGoal() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_STEP, SPManager.DATA_INT);
    }

    @Override
    public void setStepGoal(int stepGoal) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_STEP, stepGoal);
    }

    @Override
    public int getDistanceGoal() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_DISTANCE, SPManager.DATA_INT);
    }

    @Override
    public void setDistanceGoal(int distanceGoal) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_DISTANCE, distanceGoal);
    }

    @Override
    public int getSportTimeGoal() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_SPORT_TIME, SPManager.DATA_INT);
    }

    @Override
    public void setSportTimeGoal(int sportTimeGoal) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_SPORT_TIME, sportTimeGoal);
    }

    @Override
    public int getCaloriesGoal() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_CALORIES, SPManager.DATA_INT);
    }

    @Override
    public void setCaloriesGoal(int caloriesGoal) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_CALORIES, caloriesGoal);
    }

    @Override
    public int getSleepGoal() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_SLEEP, SPManager.DATA_INT);
    }

    @Override
    public void setSleepGoal(int sleepGoal) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_SLEEP, sleepGoal);
    }

    @Override
    public int getUserInfoId() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_INFO_ID, SPManager.DATA_INT);
    }

    @Override
    public void setUserInfoId(int userInfoId) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_USER_INFO_ID, userInfoId);
    }

    @Override
    public int getUserId() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_ID, SPManager.DATA_INT);
    }

    @Override
    public void setUserId(int userId) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_USER_ID, userId);
    }

    @Override
    public String getNickName() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_NICK_NAME, SPManager.DATA_STRING);
    }

    @Override
    public void setNickName(String nickName) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_NICK_NAME, nickName);
    }

    @Override
    public String getImagePath() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_IMG_PATH, SPManager.DATA_STRING);
    }

    @Override
    public void setImagePath(String imagePath) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_IMG_PATH, imagePath);
    }

    @Override
    public String getName() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_NAME, SPManager.DATA_STRING);
    }

    @Override
    public void setName(String name) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_NAME, name);
    }

    @Override
    public String getEmail() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_EMAIL, SPManager.DATA_STRING);
    }

    @Override
    public void setEmail(String email) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_EMAIL, email);
    }

    @Override
    public String getPassword() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_PASSWORD, SPManager.DATA_STRING);
    }

    @Override
    public void setPassword(String password) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_PASSWORD, password);
    }

    @Override
    public int getGender() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_GENDER, SPManager.DATA_INT);
    }

    @Override
    public void setGender(int gender) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GENDER, gender);
    }

    @Override
    public int getBirthdayYear() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_BIRTHDAY_YEAR, SPManager.DATA_INT);
    }

    @Override
    public void setBirthdayYear(int birthdayYear) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_BIRTHDAY_YEAR, birthdayYear);
    }

    @Override
    public int getBirthdayMonth() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_BIRTHDAY_MONTH, SPManager.DATA_INT);
    }

    @Override
    public void setBirthdayMonth(int birthdayMonth) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_BIRTHDAY_MONTH, birthdayMonth);
    }

    @Override
    public int getBirthdayDay() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_BIRTHDAY_DAY, SPManager.DATA_INT);
    }

    @Override
    public void setBirthdayDay(int birthdayDay) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_BIRTHDAY_DAY, birthdayDay);
    }

    @Override
    public float getHeight() {
        int height = (int) SPManager.INSTANCE.getSPValue(SPKey.SP_HEIGHT, SPManager.DATA_INT);
        return height / 10f;
    }

    @Override
    public void setHeight(float height) {
        int iHeight = (int) (height * 10);
        SPManager.INSTANCE.setSPValue(SPKey.SP_HEIGHT, iHeight);
    }

    @Override
    public float getWeight() {
        int weight = (int) SPManager.INSTANCE.getSPValue(SPKey.SP_WEIGHT, SPManager.DATA_INT);
        return weight / 10f;
    }

    @Override
    public void setWeight(float weight) {
        int iWeight = (int) (weight * 10);
        SPManager.INSTANCE.setSPValue(SPKey.SP_WEIGHT, iWeight);
    }

    @Override
    public int getUnit() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_UNIT, SPManager.DATA_INT);
    }

    @Override
    public void setUnit(int unit) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_UNIT, unit);
    }

    @Override
    public String getCountry() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_COUNTRY, SPManager.DATA_STRING);
    }

    @Override
    public void setCountry(String country) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_COUNTRY, country);
    }

    @Override
    public int getLanguage() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_LANGUAGE, SPManager.DATA_INT);
    }

    @Override
    public void setLanguage(int language) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_LANGUAGE, language);
    }

    @Override
    public int getWatchFaceIndex() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_WATCH_FACE_INDEX, SPManager.DATA_INT);
    }

    @Override
    public void setWatchFaceIndex(int watchFaceIndex) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_WATCH_FACE_INDEX, watchFaceIndex);
    }

    @Override
    public int getDateFormat() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_DATE_FORMAT, SPManager.DATA_INT);
    }

    @Override
    public void setDateFormat(int dateFormat) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_DATE_FORMAT, dateFormat);
    }

    @Override
    public int getTimeFormat() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_TIME_FORMAT, SPManager.DATA_INT);
    }

    @Override
    public void setTimeFormat(int timeFormat) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_TIME_FORMAT, timeFormat);
    }

    @Override
    public int getBatteryShow() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_BATTERY_SHOW, SPManager.DATA_INT);
    }

    @Override
    public void setBatteryShow(int batteryShow) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_BATTERY_SHOW, batteryShow);
    }

    @Override
    public int getLunarFormat() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_LUNAR_FORMAT, SPManager.DATA_INT);
    }

    @Override
    public void setLunarFormat(int lunarFormat) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_LUNAR_FORMAT, lunarFormat);
    }

    @Override
    public int getScreenFormat() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_SCREEN_FORMAT, SPManager.DATA_INT);
    }

    @Override
    public void setScreenFormat(int screenFormat) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_SCREEN_FORMAT, screenFormat);
    }

    @Override
    public int getBackgroundStyle() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_BACKGROUND_STYLE, SPManager.DATA_INT);
    }

    @Override
    public void setBackgroundStyle(int backgroundStyle) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_BACKGROUND_STYLE, backgroundStyle);
    }

    @Override
    public int getSportDataShow() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_SPORT_DATA_SHOW, SPManager.DATA_INT);
    }

    @Override
    public void setSportDataShow(int sportDataShow) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_SPORT_DATA_SHOW, sportDataShow);
    }

    @Override
    public int getUsernameFormat() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_USERNAME_FORMAT, SPManager.DATA_INT);
    }

    @Override
    public void setUsernameFormat(int usernameFormat) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_USERNAME_FORMAT, usernameFormat);
    }

    @Override
    public boolean getAutoSyncSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_AUTO_SYNC_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setAutoSyncSwitch(boolean autoSyncSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_AUTO_SYNC_SWITCH, autoSyncSwitch);
    }

    @Override
    public boolean getPresetSleepSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_PRESET_SLEEP_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setPresetSleepSwitch(boolean presetSleepSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_PRESET_SLEEP_SWITCH, presetSleepSwitch);
    }

    @Override
    public int getBedtimeHour() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_BED_TIME_HOUR, SPManager.DATA_INT);
    }

    @Override
    public void setBedtimeHour(int bedtimeHour) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_BED_TIME_HOUR, bedtimeHour);
    }

    @Override
    public int getBedTimeMin() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_BED_TIME_MIN, SPManager.DATA_INT);
    }

    @Override
    public void setBedTimeMin(int bedtimeMin) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_BED_TIME_MIN, bedtimeMin);
    }

    @Override
    public int getAwakeTimeHour() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_AWAKE_TIME_HOUR, SPManager.DATA_INT);
    }

    @Override
    public void setAwakeTimeHour(int awakeTimeHour) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_AWAKE_TIME_HOUR, awakeTimeHour);
    }

    @Override
    public int getAwakeTimeMin() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_AWAKE_TIME_MIN, SPManager.DATA_INT);
    }

    @Override
    public void setAwakeTimeMin(int awakeTimeMin) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_AWAKE_TIME_MIN, awakeTimeMin);
    }

    @Override
    public int getCaloriesType() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_CALORIES_TYPE, SPManager.DATA_INT);
    }

    @Override
    public void setCaloriesType(int caloriesType) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_CALORIES_TYPE, caloriesType);
    }

    @Override
    public boolean getRaiseWakeSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_RAISE_WAKE_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setRaiseWakeSwitch(boolean raiseWakeSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_RAISE_WAKE_SWITCH, raiseWakeSwitch);
    }

    @Override
    public boolean getInactivityAlertSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_INACTIVITY_ALERT_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setInactivityAlertSwitch(boolean inactivityAlertSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_INACTIVITY_ALERT_SWITCH, inactivityAlertSwitch);
    }

    @Override
    public int getInactivityAlertInterval() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_INACTIVITY_ALERT_INTERVAL, SPManager.DATA_INT);
    }

    @Override
    public void setInactivityAlertInterval(int inactivityAlertInterval) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_INACTIVITY_ALERT_INTERVAL, inactivityAlertInterval);
    }

    @Override
    public int getInactivityAlertStartHour() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_INACTIVITY_ALERT_START_HOUR, SPManager.DATA_INT);
    }

    @Override
    public void setInactivityAlertStartHour(int inactivityAlertStartHour) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_INACTIVITY_ALERT_START_HOUR, inactivityAlertStartHour);
    }

    @Override
    public int getInactivityAlertStartMin() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_INACTIVITY_ALERT_START_MIN, SPManager.DATA_INT);
    }

    @Override
    public void setInactivityAlertStartMin(int inactivityAlertStartMin) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_INACTIVITY_ALERT_START_MIN, inactivityAlertStartMin);
    }

    @Override
    public int getInactivityAlertEndHour() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_INACTIVITY_ALERT_END_HOUR, SPManager.DATA_INT);
    }

    @Override
    public void setInactivityAlertEndHour(int inactivityAlertEndHour) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_INACTIVITY_ALERT_END_HOUR, inactivityAlertEndHour);
    }

    @Override
    public int getInactivityAlertEndMin() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_INACTIVITY_ALERT_END_MIN, SPManager.DATA_INT);
    }

    @Override
    public void setInactivityAlertEndMin(int inactivityAlertEndMin) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_INACTIVITY_ALERT_END_MIN, inactivityAlertEndMin);
    }

    @Override
    public int getInactivityAlertCycle() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_INACTIVITY_ALERT_CYCLE, SPManager.DATA_INT);
    }

    @Override
    public void setInactivityAlertCycle(int inactivityAlertCycle) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_INACTIVITY_ALERT_CYCLE, inactivityAlertCycle);
    }

    @Override
    public boolean getCallSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_CALL_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setCallSwitch(boolean callSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_CALL_SWITCH, callSwitch);
    }

    @Override
    public boolean getMiscallSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_MISCALL_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setMiscallSwitch(boolean miscallSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_MISCALL_SWITCH, miscallSwitch);
    }

    @Override
    public boolean getSMSSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_SMS_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setSMSSwitch(boolean smsSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_SMS_SWITCH, smsSwitch);
    }

    @Override
    public boolean getEmailSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_EMAIL_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setEmailSwitch(boolean emailSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_EMAIL_SWITCH, emailSwitch);
    }

    @Override
    public boolean getSocialSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_SOCIAL_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setSocialSwitch(boolean socialSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_SOCIAL_SWITCH, socialSwitch);
    }

    @Override
    public boolean getCalendarSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_CALENDAR_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setCalendarSwitch(boolean calendarSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_CALENDAR_SWITCH, calendarSwitch);
    }

    @Override
    public boolean getAntiSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_ANTI_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setAntiSwitch(boolean antiSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_ANTI_SWITCH, antiSwitch);
    }

    @Override
    public int getCallShock() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_CALL_SHOCK, SPManager.DATA_INT);
    }

    @Override
    public void setCallShock(int callShock) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_CALL_SHOCK, callShock);
    }

    @Override
    public int getMiscallShock() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_MISS_CALL_SHOCK, SPManager.DATA_INT);
    }

    @Override
    public void setMiscallShock(int miscallShock) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_MISS_CALL_SHOCK, miscallShock);
    }

    @Override
    public int getSMSShock() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_SMS_SHOCK, SPManager.DATA_INT);
    }

    @Override
    public void setSMSShock(int smsShock) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_SMS_SHOCK, smsShock);
    }

    @Override
    public int getEmailShock() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_EMAIL_SHOCK, SPManager.DATA_INT);
    }

    @Override
    public void setEmailShock(int emailShock) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_EMAIL_SHOCK, emailShock);
    }

    @Override
    public int getSocialShock() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_SOCIAL_SHOCK, SPManager.DATA_INT);
    }

    @Override
    public void setSocialShock(int socialShock) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_SOCIAL_SHOCK, socialShock);
    }

    @Override
    public int getCalendarShock() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_CALENDAR_SHOCK, SPManager.DATA_INT);
    }

    @Override
    public void setCalendarShock(int calendarShock) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_CALENDAR_SHOCK, calendarShock);
    }

    @Override
    public boolean getHeartRateAutoTrackSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_HEART_RATE_AUTO_TRACK_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setHeartRateAutoTrackSwitch(boolean heartRateAutoTrackSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_HEART_RATE_AUTO_TRACK_SWITCH, heartRateAutoTrackSwitch);
    }

    @Override
    public int getHeartRateFrequency() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_HEART_RATE_FREQUENCY, SPManager.DATA_INT);
    }

    @Override
    public void setHeartRateFrequency(int heartRateFrequency) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_HEART_RATE_FREQUENCY, heartRateFrequency);
    }

    @Override
    public boolean getHeartRateRangeAlertSwitch() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_HEART_RATE_RANGE_ALERT_SWITCH, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setHeartRateRangeAlertSwitch(boolean heartRateRangeAlertSwitch) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_HEART_RATE_RANGE_ALERT_SWITCH, heartRateRangeAlertSwitch);
    }

    @Override
    public int getHeartRateMin() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_HEART_RATE_MIN, SPManager.DATA_INT);
    }

    @Override
    public void setHeartRateMin(int heartRateMin) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_HEART_RATE_MIN, heartRateMin);
    }

    @Override
    public int getHeartRateMax() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_HEART_RATE_MAX, SPManager.DATA_INT);
    }

    @Override
    public void setHeartRateMax(int heartRateMax) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_HEART_RATE_MAX, heartRateMax);
    }

    @Override
    public String getWatchID() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_WATCH_ID, SPManager.DATA_STRING);
    }

    @Override
    public void setWatchID(String watchID) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_WATCH_ID, watchID);
    }

    @Override
    public String getMAC() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_MAC, SPManager.DATA_STRING);
    }

    @Override
    public void setMAC(String MAC) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_MAC, MAC);
    }

    @Override
    public String getDeviceName() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_DEVICE_NAME, SPManager.DATA_STRING);
    }

    @Override
    public void setDeviceName(String deviceName) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_DEVICE_NAME, deviceName);
    }

    @Override
    public String getDeviceType() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_DEVICE_TYPE, SPManager.DATA_STRING);
    }

    @Override
    public void setDeviceType(String deviceType) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_DEVICE_TYPE, deviceType);
    }

    @Override
    public String getDeviceVersion() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_DEVICE_VERSION, SPManager.DATA_STRING);
    }

    @Override
    public void setDeviceVersion(String deviceVersion) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_DEVICE_VERSION, deviceVersion);
    }

    @Override
    public String getProductCode() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_PRODUCT_CODE, SPManager.DATA_STRING);
    }

    @Override
    public void setProductCode(String productCode) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_PRODUCT_CODE, productCode);
    }

    @Override
    public String getGoalAchievementStep() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_ACHIEVEMENT_STEP, SPManager.DATA_STRING);
    }

    @Override
    public void setGoalAchievementStep(String goalAchievementStep) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_ACHIEVEMENT_STEP, goalAchievementStep);
    }

    @Override
    public String getGoalAchievementDistance() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_ACHIEVEMENT_DISTANCE, SPManager.DATA_STRING);
    }

    @Override
    public void setGoalAchievementDistance(String goalAchievementDistance) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_ACHIEVEMENT_DISTANCE, goalAchievementDistance);
    }

    @Override
    public String getGoalAchievementCalories() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_ACHIEVEMENT_CALORIES, SPManager.DATA_STRING);
    }

    @Override
    public void setGoalAchievementCalories(String goalAchievementCalories) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_ACHIEVEMENT_CALORIES, goalAchievementCalories);
    }

    @Override
    public String getGoalAchievementSleep() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_GOAL_ACHIEVEMENT_SLEEP, SPManager.DATA_STRING);
    }

    @Override
    public void setGoalAchievementSleep(String goalAchievementSleep) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_GOAL_ACHIEVEMENT_SLEEP, goalAchievementSleep);
    }

    @Override
    public int getSportSleepMode() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_SPORT_SLEEP_MODE, SPManager.DATA_INT);
    }

    @Override
    public void setSportSleepMode(int sportSleepMode) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_SPORT_SLEEP_MODE, sportSleepMode);
    }

    @Override
    public int getUltraviolet() {
        return (int) SPManager.INSTANCE.getSPValue(SPKey.SP_ULTRAVIOLET, SPManager.DATA_INT);
    }

    @Override
    public void setUltraviolet(int ultraviolet) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_ULTRAVIOLET, ultraviolet);
    }

    @Override
    public String getLastSyncTime() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_LAST_SYNC_TIME, SPManager.DATA_STRING);
    }

    @Override
    public void setLastSyncTime(String lastSyncTime) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_LAST_SYNC_TIME, lastSyncTime);
    }

    @Override
    public String getToken() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_TOKEN, SPManager.DATA_STRING);
    }

    @Override
    public void setToken(String token) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_TOKEN, token);
    }

    @Override
    public String getAccountID() {
        return (String) SPManager.INSTANCE.getSPValue(SPKey.SP_ACCOUNT_ID, SPManager.DATA_STRING);
    }

    @Override
    public void setAccountID(String accountID) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_ACCOUNT_ID, accountID);
    }

    @Override
    public boolean getAutoLogin() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_AUTO_LOGIN, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setAutoLogin(boolean autoLogin) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_AUTO_LOGIN, autoLogin);
    }

    @Override
    public boolean getThirdPartLogin() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_THIRD_PARTY_LOGIN, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setThirdPartLogin(boolean thirdPartLogin) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_THIRD_PARTY_LOGIN, thirdPartLogin);
    }

    @Override
    public boolean getHeartRateFunction() {
        return (boolean) SPManager.INSTANCE.getSPValue(SPKey.SP_HEART_RATE_FUNCTION, SPManager.DATA_BOOLEAN);
    }

    @Override
    public void setHeartRateFunction(boolean isOpen) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_HEART_RATE_FUNCTION, isOpen);
    }

    @Override
    public String getNetFirmwareVersion() {
        String netFirmwareVersion = (String) SPManager.INSTANCE.getSPValue(SPKey.SP_NET_FIRMWARE_VERSION, SPManager.DATA_STRING);
        return TextUtils.isEmpty(netFirmwareVersion) ? "" : netFirmwareVersion;
    }

    @Override
    public void setNetFirmwareVersion(String netFirmwareVersion) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_NET_FIRMWARE_VERSION, netFirmwareVersion);
    }

    @Override
    public String getNetFirmwareUrl() {
        String netFirmwareUrl = (String) SPManager.INSTANCE.getSPValue(SPKey.SP_NET_FIRMWARE_URL, SPManager.DATA_STRING);
        return TextUtils.isEmpty(netFirmwareUrl) ? "" : netFirmwareUrl;
    }

    @Override
    public void setNetFirmwareUrl(String netFirmwareUrl) {
        SPManager.INSTANCE.setSPValue(SPKey.SP_NET_FIRMWARE_URL, netFirmwareUrl);
    }

}
