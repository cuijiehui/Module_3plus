package cn.appscomm.presenter.implement;

import cn.appscomm.presenter.interfaces.PVSPCall;
import cn.appscomm.sp.implement.MSP;
import cn.appscomm.sp.interfaces.PMSPCall;

/**
 * Created by Administrator on 2017/3/6.
 */
public enum PSP implements PVSPCall {
    INSTANCE;

    private PMSPCall mCall = MSP.INSTANCE;

    @Override
    public Object getSPValue(String configKey, int dataType) {
        return mCall.getSPValue(configKey, dataType);
    }

    @Override
    public boolean setSPValue(String configKey, Object configValue) {
        return mCall.setSPValue(configKey, configValue);
    }

    @Override
    public boolean delSPValue(String configKey) {
        return mCall.delSPValue(configKey);
    }

    @Override
    public boolean delSPFile() {
        return mCall.delSPFile();
    }

    @Override
    public void setStepGoal(int stepGoal) {
        mCall.setStepGoal(stepGoal);
    }

    @Override
    public int getStepGoal() {
        return mCall.getStepGoal();
    }


    @Override
    public int getDistanceGoal() {
        return mCall.getDistanceGoal();
    }

    @Override
    public void setDistanceGoal(int distanceGoal) {
        mCall.setDistanceGoal(distanceGoal);
    }

    @Override
    public int getSportTimeGoal() {
        return mCall.getSportTimeGoal();
    }

    @Override
    public void setSportTimeGoal(int sportTimeGoal) {
        mCall.setSportTimeGoal(sportTimeGoal);
    }

    @Override
    public int getCaloriesGoal() {
        return mCall.getCaloriesGoal();
    }

    @Override
    public void setCaloriesGoal(int caloriesGoal) {
        mCall.setCaloriesGoal(caloriesGoal);
    }

    @Override
    public int getSleepGoal() {
        return mCall.getSleepGoal();
    }

    @Override
    public void setSleepGoal(int sleepGoal) {
        mCall.setSleepGoal(sleepGoal);
    }

    @Override
    public int getUserInfoId() {
        return mCall.getUserInfoId();
    }

    @Override
    public void setUserInfoId(int userInfoId) {
        mCall.setUserInfoId(userInfoId);
    }

    @Override
    public int getUserId() {
        return mCall.getUserId();
    }

    @Override
    public void setUserId(int userId) {
        mCall.setUserId(userId);
    }

    @Override
    public String getNickName() {
        return mCall.getNickName();
    }

    @Override
    public void setNickName(String nickName) {
        mCall.setNickName(nickName);
    }

    @Override
    public String getImagePath() {
        return mCall.getImagePath();
    }

    @Override
    public void setImagePath(String imagePath) {
        mCall.setImagePath(imagePath);
    }

    @Override
    public String getName() {
        return mCall.getName();
    }

    @Override
    public void setName(String name) {
        mCall.setName(name);
    }

    @Override
    public String getEmail() {
        return mCall.getEmail();
    }

    @Override
    public void setEmail(String email) {
        mCall.setEmail(email);
    }

    @Override
    public String getPassword() {
        return mCall.getPassword();
    }

    @Override
    public void setPassword(String password) {
        mCall.setPassword(password);
    }

    @Override
    public int getGender() {
        return mCall.getGender();
    }

    @Override
    public void setGender(int gender) {
        mCall.setGender(gender);
    }

    @Override
    public int getBirthdayYear() {
        return mCall.getBirthdayYear();
    }

    @Override
    public void setBirthdayYear(int birthdayYear) {
        mCall.setBirthdayYear(birthdayYear);
    }

    @Override
    public int getBirthdayMonth() {
        return mCall.getBirthdayMonth();
    }

    @Override
    public void setBirthdayMonth(int birthdayMonth) {
        mCall.setBirthdayMonth(birthdayMonth);
    }

    @Override
    public int getBirthdayDay() {
        return mCall.getBirthdayDay();
    }

    @Override
    public void setBirthdayDay(int birthdayDay) {
        mCall.setBirthdayDay(birthdayDay);
    }

    @Override
    public float getHeight() {
        return mCall.getHeight();
    }

    @Override
    public void setHeight(float height) {
        mCall.setHeight(height);
    }

    @Override
    public float getWeight() {
        return mCall.getWeight();
    }

    @Override
    public void setWeight(float weight) {
        mCall.setWeight(weight);
    }

    @Override
    public int getUnit() {
        return mCall.getUnit();
    }

    @Override
    public void setUnit(int unit) {
        mCall.setUnit(unit);
    }

    @Override
    public String getCountry() {
        return mCall.getCountry();
    }

    @Override
    public void setCountry(String country) {
        mCall.setCountry(country);
    }

    @Override
    public int getLanguage() {
        return mCall.getLanguage();
    }

    @Override
    public void setLanguage(int language) {
        mCall.setLanguage(language);
    }

    @Override
    public int getWatchFaceIndex() {
        return mCall.getWatchFaceIndex();
    }

    @Override
    public void setWatchFaceIndex(int watchFaceIndex) {
        mCall.setWatchFaceIndex(watchFaceIndex);
    }

    @Override
    public int getDateFormat() {
        return mCall.getDateFormat();
    }

    @Override
    public void setDateFormat(int dateFormat) {
        mCall.setDateFormat(dateFormat);
    }

    @Override
    public int getTimeFormat() {
        return mCall.getTimeFormat();
    }

    @Override
    public void setTimeFormat(int timeFormat) {
        mCall.setTimeFormat(timeFormat);
    }

    @Override
    public int getBatteryShow() {
        return mCall.getBatteryShow();
    }

    @Override
    public void setBatteryShow(int batteryShow) {
        mCall.setBatteryShow(batteryShow);
    }

    @Override
    public int getLunarFormat() {
        return mCall.getLunarFormat();
    }

    @Override
    public void setLunarFormat(int lunarFormat) {
        mCall.setLunarFormat(lunarFormat);
    }

    @Override
    public int getScreenFormat() {
        return mCall.getScreenFormat();
    }

    @Override
    public void setScreenFormat(int screenFormat) {
        mCall.setScreenFormat(screenFormat);
    }

    @Override
    public int getBackgroundStyle() {
        return mCall.getBackgroundStyle();
    }

    @Override
    public void setBackgroundStyle(int backgroundStyle) {
        mCall.setBackgroundStyle(backgroundStyle);
    }

    @Override
    public int getSportDataShow() {
        return mCall.getSportDataShow();
    }

    @Override
    public void setSportDataShow(int sportDataShow) {
        mCall.setSportDataShow(sportDataShow);
    }

    @Override
    public int getUsernameFormat() {
        return mCall.getUsernameFormat();
    }

    @Override
    public void setUsernameFormat(int usernameFormat) {
        mCall.setUsernameFormat(usernameFormat);
    }

    @Override
    public boolean getAutoSyncSwitch() {
        return mCall.getAutoSyncSwitch();
    }

    @Override
    public void setAutoSyncSwitch(boolean autoSyncSwitch) {
        mCall.setAutoSyncSwitch(autoSyncSwitch);
    }

    @Override
    public boolean getPresetSleepSwitch() {
        return mCall.getPresetSleepSwitch();
    }

    @Override
    public void setPresetSleepSwitch(boolean presetSleepSwitch) {
        mCall.setPresetSleepSwitch(presetSleepSwitch);
    }

    @Override
    public int getBedtimeHour() {
        return mCall.getBedtimeHour();
    }

    @Override
    public void setBedtimeHour(int bedtimeHour) {
        mCall.setBedtimeHour(bedtimeHour);
    }

    @Override
    public int getBedTimeMin() {
        return mCall.getBedTimeMin();
    }

    @Override
    public void setBedTimeMin(int bedtimeMin) {
        mCall.setBedTimeMin(bedtimeMin);
    }

    @Override
    public int getAwakeTimeHour() {
        return mCall.getAwakeTimeHour();
    }

    @Override
    public void setAwakeTimeHour(int awakeTimeHour) {
        mCall.setAwakeTimeHour(awakeTimeHour);
    }

    @Override
    public int getAwakeTimeMin() {
        return mCall.getAwakeTimeMin();
    }

    @Override
    public void setAwakeTimeMin(int awakeTimeMin) {
        mCall.setAwakeTimeMin(awakeTimeMin);
    }

    @Override
    public int getCaloriesType() {
        return mCall.getCaloriesType();
    }

    @Override
    public void setCaloriesType(int caloriesType) {
        mCall.setCaloriesType(caloriesType);
    }

    @Override
    public boolean getRaiseWakeSwitch() {
        return mCall.getRaiseWakeSwitch();
    }

    @Override
    public void setRaiseWakeSwitch(boolean raiseWakeSwitch) {
        mCall.setRaiseWakeSwitch(raiseWakeSwitch);
    }

    @Override
    public boolean getInactivityAlertSwitch() {
        return mCall.getInactivityAlertSwitch();
    }

    @Override
    public void setInactivityAlertSwitch(boolean inactivityAlertSwitch) {
        mCall.setInactivityAlertSwitch(inactivityAlertSwitch);
    }

    @Override
    public int getInactivityAlertInterval() {
        return mCall.getInactivityAlertInterval();
    }

    @Override
    public void setInactivityAlertInterval(int inactivityAlertInterval) {
        mCall.setInactivityAlertInterval(inactivityAlertInterval);
    }

    @Override
    public int getInactivityAlertStartHour() {
        return mCall.getInactivityAlertStartHour();
    }

    @Override
    public void setInactivityAlertStartHour(int inactivityAlertStartHour) {
        mCall.setInactivityAlertStartHour(inactivityAlertStartHour);
    }

    @Override
    public int getInactivityAlertStartMin() {
        return mCall.getInactivityAlertStartHour();
    }

    @Override
    public void setInactivityAlertStartMin(int inactivityAlertStartMin) {
        mCall.setInactivityAlertStartMin(inactivityAlertStartMin);
    }

    @Override
    public int getInactivityAlertEndHour() {
        return mCall.getInactivityAlertEndHour();
    }

    @Override
    public void setInactivityAlertEndHour(int inactivityAlertEndHour) {
        mCall.setInactivityAlertEndHour(inactivityAlertEndHour);
    }

    @Override
    public int getInactivityAlertEndMin() {
        return mCall.getInactivityAlertEndMin();
    }

    @Override
    public void setInactivityAlertEndMin(int inactivityAlertEndMin) {
        mCall.setInactivityAlertEndMin(inactivityAlertEndMin);
    }

    @Override
    public int getInactivityAlertCycle() {
        return mCall.getInactivityAlertCycle();
    }

    @Override
    public void setInactivityAlertCycle(int inactivityAlertCycle) {
        mCall.setInactivityAlertCycle(inactivityAlertCycle);
    }

    @Override
    public boolean getCallSwitch() {
        return mCall.getCallSwitch();
    }

    @Override
    public void setCallSwitch(boolean callSwitch) {
        mCall.setCallSwitch(callSwitch);
    }

    @Override
    public boolean getMissCallSwitch() {
        return mCall.getMiscallSwitch();
    }

    @Override
    public void setMissCallSwitch(boolean miscallSwitch) {
        mCall.setMiscallSwitch(miscallSwitch);
    }

    @Override
    public boolean getSMSSwitch() {
        return mCall.getSMSSwitch();
    }

    @Override
    public void setSMSSwitch(boolean smsSwitch) {
        mCall.setSMSSwitch(smsSwitch);
    }

    @Override
    public boolean getEmailSwitch() {
        return mCall.getEmailSwitch();
    }

    @Override
    public void setEmailSwitch(boolean emailSwitch) {
        mCall.setEmailSwitch(emailSwitch);
    }

    @Override
    public boolean getSocialSwitch() {
        return mCall.getSocialSwitch();
    }

    @Override
    public void setSocialSwitch(boolean socialSwitch) {
        mCall.setSocialSwitch(socialSwitch);
    }

    @Override
    public boolean getCalendarSwitch() {
        return mCall.getCalendarSwitch();
    }

    @Override
    public void setCalendarSwitch(boolean calendarSwitch) {
        mCall.setCalendarSwitch(calendarSwitch);
    }

    @Override
    public boolean getAntiSwitch() {
        return mCall.getAntiSwitch();
    }

    @Override
    public void setAntiSwitch(boolean antiSwitch) {
        mCall.setAntiSwitch(antiSwitch);
    }

    @Override
    public int getCallShock() {
        return mCall.getCallShock();
    }

    @Override
    public void setCallShock(int callShock) {
        mCall.setCallShock(callShock);
    }

    @Override
    public int getMiscallShock() {
        return mCall.getMiscallShock();
    }

    @Override
    public void setMiscallShock(int miscallShock) {
        mCall.setMiscallShock(miscallShock);
    }

    @Override
    public int getSMSShock() {
        return mCall.getSMSShock();
    }

    @Override
    public void setSMSShock(int smsShock) {
        mCall.setSMSShock(smsShock);
    }

    @Override
    public int getEmailShock() {
        return mCall.getEmailShock();
    }

    @Override
    public void setEmailShock(int emailShock) {
        mCall.setEmailShock(emailShock);
    }

    @Override
    public int getSocialShock() {
        return mCall.getSocialShock();
    }

    @Override
    public void setSocialShock(int socialShock) {
        mCall.setSocialShock(socialShock);
    }

    @Override
    public int getCalendarShock() {
        return mCall.getCalendarShock();
    }

    @Override
    public void setCalendarShock(int calendarShock) {
        mCall.setCalendarShock(calendarShock);
    }

    @Override
    public boolean getHeartRateAutoTrackSwitch() {
        return mCall.getHeartRateAutoTrackSwitch();
    }

    @Override
    public void setHeartRateAutoTrackSwitch(boolean heartRateAutoTrackSwitch) {
        mCall.setHeartRateAutoTrackSwitch(heartRateAutoTrackSwitch);
    }

    @Override
    public int getHeartRateFrequency() {
        return mCall.getHeartRateFrequency();
    }

    @Override
    public void setHeartRateFrequency(int heartRateFrequency) {
        mCall.setHeartRateFrequency(heartRateFrequency);
    }

    @Override
    public boolean getHeartRateRangeAlertSwitch() {
        return mCall.getHeartRateRangeAlertSwitch();
    }

    @Override
    public void setHeartRateRangeAlertSwitch(boolean heartRateRangeAlertSwitch) {
        mCall.setHeartRateRangeAlertSwitch(heartRateRangeAlertSwitch);
    }

    @Override
    public int getHeartRateMin() {
        return mCall.getHeartRateMin();
    }

    @Override
    public void setHeartRateMin(int heartRateMin) {
        mCall.setHeartRateMin(heartRateMin);
    }

    @Override
    public int getHeartRateMax() {
        return mCall.getHeartRateMax();
    }

    @Override
    public void setHeartRateMax(int heartRateMax) {
        mCall.setHeartRateMax(heartRateMax);
    }

    @Override
    public String getWatchID() {
        return mCall.getWatchID();
    }

    @Override
    public void setWatchID(String watchID) {
        mCall.setWatchID(watchID);
    }

    @Override
    public String getMAC() {
        return mCall.getMAC();
    }

    @Override
    public void setMAC(String MAC) {
        mCall.setMAC(MAC);
    }

    @Override
    public String getDeviceName() {
        return mCall.getDeviceName();
    }

    @Override
    public void setDeviceName(String deviceName) {
        mCall.setDeviceName(deviceName);
    }

    @Override
    public String getDeviceType() {
        return mCall.getDeviceType();
    }

    @Override
    public void setDeviceType(String deviceType) {
        mCall.setDeviceType(deviceType);
    }

    @Override
    public String getDeviceVersion() {
        return mCall.getDeviceVersion();
    }

    @Override
    public String getProductCode() {
        return mCall.getProductCode();
    }

    @Override
    public void setProductCode(String productCode) {
        mCall.setProductCode(productCode);
    }

    @Override
    public void setDeviceVersion(String deviceVersion) {
        mCall.setDeviceVersion(deviceVersion);
    }

    @Override
    public String getGoalAchievementStep() {
        return mCall.getGoalAchievementStep();
    }

    @Override
    public void setGoalAchievementStep(String goalAchievementStep) {
        mCall.setGoalAchievementStep(goalAchievementStep);
    }

    @Override
    public String getGoalAchievementDistance() {
        return mCall.getGoalAchievementDistance();
    }

    @Override
    public void setGoalAchievementDistance(String goalAchievementDistance) {
        mCall.setGoalAchievementDistance(goalAchievementDistance);
    }

    @Override
    public String getGoalAchievementCalories() {
        return mCall.getGoalAchievementCalories();
    }

    @Override
    public void setGoalAchievementCalories(String goalAchievementCalories) {
        mCall.setGoalAchievementCalories(goalAchievementCalories);
    }

    @Override
    public String getGoalAchievementSleep() {
        return mCall.getGoalAchievementSleep();
    }

    @Override
    public void setGoalAchievementSleep(String goalAchievementSleep) {
        mCall.setGoalAchievementSleep(goalAchievementSleep);
    }

    @Override
    public int getSportSleepMode() {
        return mCall.getSportSleepMode();
    }

    @Override
    public void setSportSleepMode(int sportSleepMode) {
        mCall.setSportSleepMode(sportSleepMode);
    }

    @Override
    public int getUltraviolet() {
        return mCall.getUltraviolet();
    }

    @Override
    public void setUltraviolet(int ultraviolet) {
        mCall.setUltraviolet(ultraviolet);
    }

    @Override
    public String getLastSyncTime() {
        return mCall.getLastSyncTime();
    }

    @Override
    public void setLastSyncTime(String lastSyncTime) {
        mCall.setLastSyncTime(lastSyncTime);
    }

    @Override
    public String getToken() {
        return mCall.getToken();
    }

    @Override
    public void setToken(String token) {
        mCall.setToken(token);
    }

    @Override
    public String getAccountID() {
        return mCall.getAccountID();
    }

    @Override
    public void setAccountID(String accountID) {
        mCall.setAccountID(accountID);
    }

    @Override
    public boolean getAutoLogin() {
        return mCall.getAutoLogin();
    }

    @Override
    public void setAutoLogin(boolean autoLogin) {
        mCall.setAutoLogin(autoLogin);
    }

    @Override
    public boolean getThirdPartLogin() {
        return mCall.getThirdPartLogin();
    }

    @Override
    public void setThirdPartLogin(boolean thirdPartLogin) {
        mCall.setThirdPartLogin(thirdPartLogin);
    }

    @Override
    public boolean getHeartRateFunction() {
        return mCall.getHeartRateFunction();
    }

    @Override
    public void setHeartRateFunction(boolean isOpen) {
        mCall.setHeartRateFunction(isOpen);
    }

    @Override
    public String getNetFirmwareVersion() {
        return mCall.getNetFirmwareVersion();
    }

    @Override
    public void setNetFirmwareVersion(String netFirmwareVersion) {
        mCall.setNetFirmwareVersion(netFirmwareVersion);
    }

    @Override
    public String getNetFirmwareUrl() {
        return mCall.getNetFirmwareUrl();
    }

    @Override
    public void setNetFirmwareUrl(String netFirmwareUrl) {
        mCall.setNetFirmwareUrl(netFirmwareUrl);
    }
}
