package cn.appscomm.presenter.interfaces;

import cn.appscomm.sp.SPManager;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface PVSPCall {

    int DATA_STRING = SPManager.DATA_STRING;                                                        // string
    int DATA_INT = SPManager.DATA_INT;                                                              // int
    int DATA_LONG = SPManager.DATA_LONG;                                                            // long
    int DATA_BOOLEAN = SPManager.DATA_BOOLEAN;                                                      // boolean
    int DATA_FLOAT = SPManager.DATA_FLOAT;                                                          // float

    /**
     * 获取sp的值
     *
     * @param configKey 键
     * @param dataType  数据类型
     * @return 值
     */
    Object getSPValue(String configKey, int dataType);

    /**
     * 设置sp的值
     *
     * @return true：成功 false：失败
     */
    boolean setSPValue(String configKey, Object configValue);

    /**
     * 删除sp的值
     *
     * @param configKey 键
     * @return true：成功 false：失败
     */
    boolean delSPValue(String configKey);

    /**
     * 删除配置信息表
     *
     * @return true：成功 false：失败
     */
    boolean delSPFile();


    /**
     * 获取步数目标
     *
     * @return 步数目标
     */
    int getStepGoal();

    /**
     * 设置步数目标
     *
     * @param stepGoal 步数目标
     */
    void setStepGoal(int stepGoal);

    /**
     * 获取距离目标
     *
     * @return 距离目标
     */
    int getDistanceGoal();

    /**
     * 设置距离目标
     *
     * @param distanceGoal 距离目标
     */
    void setDistanceGoal(int distanceGoal);

    /**
     * 获取运动时长目标
     *
     * @return 运动时长目标
     */
    int getSportTimeGoal();

    /**
     * 设置运动时长目标
     *
     * @param sportTimeGoal 运动时长目标
     */
    void setSportTimeGoal(int sportTimeGoal);

    /**
     * 获取卡路里目标
     *
     * @return 卡路里目标
     */
    int getCaloriesGoal();

    /**
     * 设置运动时长目标
     *
     * @param caloriesGoal 运动时长目标
     */
    void setCaloriesGoal(int caloriesGoal);

    /**
     * 获取睡眠目标
     *
     * @return 睡眠目标
     */
    int getSleepGoal();

    /**
     * 设置睡眠目标
     *
     * @param sleepGoal 睡眠目标
     */
    void setSleepGoal(int sleepGoal);

    /**
     * 获取UserInfoId
     *
     * @return UserInfoId
     */
    int getUserInfoId();

    /**
     * 设置UserInfoId
     *
     * @param userInfoId UserInfoId
     */
    void setUserInfoId(int userInfoId);

    /**
     * 获取UserId
     *
     * @return UserId
     */
    int getUserId();

    /**
     * 设置UserId
     *
     * @param userId UserId
     */
    void setUserId(int userId);

    /**
     * 获取别名
     *
     * @return 别名
     */
    String getNickName();

    /**
     * 设置别名
     *
     * @param nickName 别名
     */
    void setNickName(String nickName);

    /**
     * 获取头像地址
     *
     * @return 头像地址
     */
    String getImagePath();

    /**
     * 设置头像地址
     *
     * @param imagePath 头像地址
     */
    void setImagePath(String imagePath);

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    String getName();

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    void setName(String name);

    /**
     * 获取邮箱
     *
     * @return 邮箱
     */
    String getEmail();

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    void setEmail(String email);

    /**
     * 获取密码
     *
     * @return 密码
     */
    String getPassword();

    /**
     * 设置密码
     *
     * @param password 密码
     */
    void setPassword(String password);

    /**
     * 获取性别
     *
     * @return 性别
     */
    int getGender();

    /**
     * 获取性别
     *
     * @param gender 性别
     */
    void setGender(int gender);

    /**
     * 获取生日年
     *
     * @return 生日年
     */
    int getBirthdayYear();

    /**
     * 设置生日年
     *
     * @param birthdayYear 生日年
     */
    void setBirthdayYear(int birthdayYear);

    /**
     * 获取生日月
     *
     * @return 生日月
     */
    int getBirthdayMonth();

    /**
     * 设置生日月
     */
    void setBirthdayMonth(int birthdayMonth);

    /**
     * 获取生日日
     *
     * @return 生日日
     */
    int getBirthdayDay();

    /**
     * 设置生日日
     *
     * @param birthdayDay 生日日
     */
    void setBirthdayDay(int birthdayDay);

    /**
     * 获取身高
     *
     * @return 身高
     */
    float getHeight();

    /**
     * 设置身高
     *
     * @param height 身高
     */
    void setHeight(float height);

    /**
     * 获取体重
     *
     * @return 体重
     */
    float getWeight();

    /**
     * 设置体重
     *
     * @param weight 身高
     */
    void setWeight(float weight);

    /**
     * 获取单位
     *
     * @return 单位
     */
    int getUnit();

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    void setUnit(int unit);

    /**
     * 获取国家
     *
     * @return 国家
     */
    String getCountry();

    /**
     * 设置国家
     *
     * @param country 国家
     */
    void setCountry(String country);

    /**
     * 获取语言
     *
     * @return 语言
     */
    int getLanguage();

    /**
     * 设置语言
     *
     * @param language 语言
     */
    void setLanguage(int language);

    /**
     * 获取时间界面索引
     *
     * @return 时间界面索引
     */
    int getWatchFaceIndex();

    /**
     * 设置时间界面索引
     *
     * @param watchFaceIndex 时间界面索引
     */
    void setWatchFaceIndex(int watchFaceIndex);

    /**
     * 获取日期格式
     *
     * @return 日期格式
     */
    int getDateFormat();

    /**
     * 设置日期格式
     *
     * @param dateFormat 日期格式
     */
    void setDateFormat(int dateFormat);

    /**
     * 获取时间格式
     *
     * @return 时间格式
     */
    int getTimeFormat();

    /**
     * 设置时间格式
     *
     * @param timeFormat 时间格式
     */
    void setTimeFormat(int timeFormat);

    /**
     * 获取电池显示
     *
     * @return 电池显示
     */
    int getBatteryShow();

    /**
     * 设置电池显示
     *
     * @param batteryShow 电池显示
     */
    void setBatteryShow(int batteryShow);

    /**
     * 获取农历显示
     *
     * @return 农历显示
     */
    int getLunarFormat();

    /**
     * 设置农历显示
     *
     * @param lunarFormat 农历显示
     */
    void setLunarFormat(int lunarFormat);

    /**
     * 获取屏幕格式
     *
     * @return 屏幕格式
     */
    int getScreenFormat();

    /**
     * 设置屏幕格式
     *
     * @param screenFormat 屏幕格式
     */
    void setScreenFormat(int screenFormat);

    /**
     * 获取背景风格
     *
     * @return 背景风格
     */
    int getBackgroundStyle();

    /**
     * 设置背景风格
     *
     * @param backgroundStyle 背景风格
     */
    void setBackgroundStyle(int backgroundStyle);

    /**
     * 获取运动数据显示
     *
     * @return 运动数据显示
     */
    int getSportDataShow();

    /**
     * 设置运动数据显示
     *
     * @param sportDataShow 运动数据显示
     */
    void setSportDataShow(int sportDataShow);

    /**
     * 获取用户名显示
     *
     * @return 用户名显示
     */
    int getUsernameFormat();

    /**
     * 设置用户名显示
     *
     * @param usernameFormat 用户名显示
     */
    void setUsernameFormat(int usernameFormat);

    /**
     * 获取自动同步开关
     *
     * @return 自动同步开关
     */
    boolean getAutoSyncSwitch();

    /**
     * 设置自动同步开关
     *
     * @param autoSyncSwitch 自动同步开关
     */
    void setAutoSyncSwitch(boolean autoSyncSwitch);

    /**
     * 获取预设睡眠开关
     *
     * @return 预设睡眠开关
     */
    boolean getPresetSleepSwitch();

    /**
     * 设置预设睡眠开关
     *
     * @param presetSleepSwitch 预设睡眠开关
     */
    void setPresetSleepSwitch(boolean presetSleepSwitch);

    /**
     * 获取睡眠时
     *
     * @return 睡眠时
     */
    int getBedtimeHour();

    /**
     * 设置睡眠时
     *
     * @param bedtimeHour 睡眠时
     */
    void setBedtimeHour(int bedtimeHour);

    /**
     * 获取睡眠分
     *
     * @return 睡眠分
     */
    int getBedTimeMin();

    /**
     * 设置睡眠分
     *
     * @param bedtimeMin 睡眠分
     */
    void setBedTimeMin(int bedtimeMin);

    /**
     * 获取清醒时
     *
     * @return 清醒时
     */
    int getAwakeTimeHour();

    /**
     * 设置清醒时
     *
     * @param awakeTimeHour 清醒时
     */
    void setAwakeTimeHour(int awakeTimeHour);

    /**
     * 获取清醒分
     *
     * @return 清醒分
     */
    int getAwakeTimeMin();

    /**
     * 设置清醒分
     *
     * @param awakeTimeMin 清醒分
     */
    void setAwakeTimeMin(int awakeTimeMin);

    /**
     * 获取卡路里类型
     *
     * @return 卡路里类型
     */
    int getCaloriesType();

    /**
     * 设置卡路里类型
     *
     * @param caloriesType 卡路里类型
     */
    void setCaloriesType(int caloriesType);

    /**
     * 获取抬手亮屏开关
     *
     * @return 抬手亮屏开关
     */
    boolean getRaiseWakeSwitch();

    /**
     * 设置抬手亮屏开关
     *
     * @param raiseWakeSwitch 抬手亮屏开关
     */
    void setRaiseWakeSwitch(boolean raiseWakeSwitch);

    /**
     * 获取久坐提醒开关
     *
     * @return 久坐提醒开关
     */
    boolean getInactivityAlertSwitch();

    /**
     * 设置久坐提醒开关
     *
     * @param inactivityAlertSwitch 久坐提醒开关
     */
    void setInactivityAlertSwitch(boolean inactivityAlertSwitch);

    /**
     * 获取久坐提醒间隔
     *
     * @return 久坐提醒间隔
     */
    int getInactivityAlertInterval();

    /**
     * 设置久坐提醒间隔
     *
     * @param inactivityAlertInterval 久坐提醒间隔
     */
    void setInactivityAlertInterval(int inactivityAlertInterval);

    /**
     * 获取久坐提醒开始时
     *
     * @return 久坐提醒开始时
     */
    int getInactivityAlertStartHour();

    /**
     * 设置久坐提醒开始时
     *
     * @param inactivityAlertStartHour 久坐提醒开始时
     */
    void setInactivityAlertStartHour(int inactivityAlertStartHour);

    /**
     * 获取久坐提醒开始分
     *
     * @return 久坐提醒开始分
     */
    int getInactivityAlertStartMin();

    /**
     * 设置久坐提醒开始分
     *
     * @param inactivityAlertStartMin 久坐提醒开始分
     */
    void setInactivityAlertStartMin(int inactivityAlertStartMin);

    /**
     * 获取久坐提醒结束时
     *
     * @return 久坐提醒结束时
     */
    int getInactivityAlertEndHour();

    /**
     * 设置久坐提醒结束时
     *
     * @param inactivityAlertEndHour 久坐提醒结束时
     */
    void setInactivityAlertEndHour(int inactivityAlertEndHour);

    /**
     * 获取久坐提醒结束分
     *
     * @return 久坐提醒结束分
     */
    int getInactivityAlertEndMin();

    /**
     * 设置久坐提醒结束分
     *
     * @param inactivityAlertEndMin 久坐提醒结束分
     */
    void setInactivityAlertEndMin(int inactivityAlertEndMin);

    /**
     * 获取久坐提醒周期
     *
     * @return 久坐提醒周期
     */
    int getInactivityAlertCycle();

    /**
     * 设置久坐提醒周期
     *
     * @param inactivityAlertCycle 久坐提醒周期
     */
    void setInactivityAlertCycle(int inactivityAlertCycle);

    /**
     * 获取来电开关
     *
     * @return 来电开关
     */
    boolean getCallSwitch();

    /**
     * 设置来电开关
     *
     * @param callSwitch 来电开关
     */
    void setCallSwitch(boolean callSwitch);

    /**
     * 获取未接来电开关
     *
     * @return 未接来电开关
     */
    boolean getMissCallSwitch();

    /**
     * 设置未接来电开关
     *
     * @param miscallSwitch 未接来电开关
     */
    void setMissCallSwitch(boolean miscallSwitch);

    /**
     * 获取短信开关
     *
     * @return 短信开关
     */
    boolean getSMSSwitch();

    /**
     * 设置短信开关
     *
     * @param smsSwitch 短信开关
     */
    void setSMSSwitch(boolean smsSwitch);

    /**
     * 获取邮箱开关
     *
     * @return 邮箱开关
     */
    boolean getEmailSwitch();

    /**
     * 设置邮箱开关
     *
     * @param emailSwitch 邮箱开关
     */
    void setEmailSwitch(boolean emailSwitch);

    /**
     * 获取社交开关
     *
     * @return 社交开关
     */
    boolean getSocialSwitch();

    /**
     * 设置社交开关
     *
     * @param socialSwitch 社交开关
     */
    void setSocialSwitch(boolean socialSwitch);

    /**
     * 获取日历开关
     *
     * @return 日历开关
     */
    boolean getCalendarSwitch();

    /**
     * 设置日历开关
     *
     * @param calendarSwitch 日历开关
     */
    void setCalendarSwitch(boolean calendarSwitch);

    /**
     * 获取防丢开关
     *
     * @return 防丢开关
     */
    boolean getAntiSwitch();

    /**
     * 设置防丢开关
     *
     * @param antiSwitch 防丢开关
     */
    void setAntiSwitch(boolean antiSwitch);

    /**
     * 获取来电震动
     *
     * @return 来电震动
     */
    int getCallShock();

    /**
     * 设置来电震动
     *
     * @param callShock 来电震动
     */
    void setCallShock(int callShock);

    /**
     * 获取未接来电震动
     *
     * @return 未接来电震动
     */
    int getMiscallShock();

    /**
     * 设置未接来电震动
     *
     * @param miscallShock 未接来电震动
     */
    void setMiscallShock(int miscallShock);

    /**
     * 获取短信震动
     *
     * @return 短信震动
     */
    int getSMSShock();

    /**
     * 设置短信震动
     *
     * @param smsShock 短信震动
     */
    void setSMSShock(int smsShock);

    /**
     * 获取邮箱震动
     *
     * @return 邮箱震动
     */
    int getEmailShock();

    /**
     * 设置邮箱震动
     *
     * @param emailShock 邮箱震动
     */
    void setEmailShock(int emailShock);

    /**
     * 获取社交震动
     *
     * @return 社交震动
     */
    int getSocialShock();

    /**
     * 设置社交震动
     *
     * @param socialShock 社交震动
     */
    void setSocialShock(int socialShock);

    /**
     * 获取日历震动
     *
     * @return 日历震动
     */
    int getCalendarShock();

    /**
     * 设置日历震动
     *
     * @param calendarShock 日历震动
     */
    void setCalendarShock(int calendarShock);

    /**
     * 获取自动心率开关
     *
     * @return 自动心率开关
     */
    boolean getHeartRateAutoTrackSwitch();

    /**
     * 设置自动心率开关
     *
     * @param heartRateAutoTrackSwitch 自动心率开关
     */
    void setHeartRateAutoTrackSwitch(boolean heartRateAutoTrackSwitch);

    /**
     * 获取心率频次
     *
     * @return 心率频次
     */
    int getHeartRateFrequency();

    /**
     * 设置心率频次
     *
     * @param heartRateFrequency 心率频次
     */
    void setHeartRateFrequency(int heartRateFrequency);

    /**
     * 获取心率报警开关
     *
     * @return 心率报警开关
     */
    boolean getHeartRateRangeAlertSwitch();

    /**
     * 设置心率报警开关
     *
     * @param heartRateRangeAlertSwitch 心率报警开关
     */
    void setHeartRateRangeAlertSwitch(boolean heartRateRangeAlertSwitch);

    /**
     * 获取心率报警最小值
     *
     * @return 心率报警最小值
     */
    int getHeartRateMin();

    /**
     * 设置心率报警最小值
     *
     * @param heartRateMin 心率报警最小值
     */
    void setHeartRateMin(int heartRateMin);

    /**
     * 获取心率报警最大值
     *
     * @return 心率报警最大值
     */
    int getHeartRateMax();

    /**
     * 设置心率报警最大值
     *
     * @param heartRateMax 心率报警最大值
     */
    void setHeartRateMax(int heartRateMax);

    /**
     * 获取watchID
     *
     * @return watchID
     */
    String getWatchID();

    /**
     * 设置watchID
     *
     * @param watchID watchID
     */
    void setWatchID(String watchID);

    /**
     * 获取MAC
     *
     * @return MAC
     */
    String getMAC();

    /**
     * 设置MAC
     *
     * @param MAC MAC
     */
    void setMAC(String MAC);

    /**
     * 获取设备名
     *
     * @return 设备名
     */
    String getDeviceName();

    /**
     * 设置设备名
     *
     * @param deviceName 设备名
     */
    void setDeviceName(String deviceName);

    /**
     * 获取设备类型
     *
     * @return 设备类型
     */
    String getDeviceType();

    /**
     * 设置设备类型
     *
     * @param deviceType 设备类型
     */
    void setDeviceType(String deviceType);

    /**
     * 获取设备版本
     *
     * @return 设备版本
     */
    String getDeviceVersion();

    /**
     * 设置设备版本
     *
     * @param deviceVersion 设备版本
     */
    void setDeviceVersion(String deviceVersion);

    /**
     * 获取产品代号
     *
     * @return 产品代号
     */
    String getProductCode();

    /**
     * 设置产品代号
     *
     * @param productCode 产品代号
     */
    void setProductCode(String productCode);

    /**
     * 获取步数目标达成
     *
     * @return 步数目标达成
     */
    String getGoalAchievementStep();

    /**
     * 设置步数目标达成
     *
     * @param goalAchievementStep 步数目标达成
     */
    void setGoalAchievementStep(String goalAchievementStep);

    /**
     * 获取距离目标达成
     *
     * @return 距离目标达成
     */
    String getGoalAchievementDistance();

    /**
     * 设置距离目标达成
     *
     * @param goalAchievementDistance 距离目标达成
     */
    void setGoalAchievementDistance(String goalAchievementDistance);

    /**
     * 获取卡路里目标达成
     *
     * @return 卡路里目标达成
     */
    String getGoalAchievementCalories();

    /**
     * 设置卡路里目标达成
     *
     * @param goalAchievementCalories 卡路里目标达成
     */
    void setGoalAchievementCalories(String goalAchievementCalories);

    /**
     * 获取睡眠目标达成
     *
     * @return 睡眠目标达成
     */
    String getGoalAchievementSleep();

    /**
     * 设置睡眠目标达成
     *
     * @param goalAchievementSleep 睡眠目标达成
     */
    void setGoalAchievementSleep(String goalAchievementSleep);

    /**
     * 获取运动睡眠模式
     *
     * @return 运动睡眠模式
     */
    int getSportSleepMode();

    /**
     * 设置运动睡眠模式
     *
     * @param sportSleepMode 运动睡眠模式
     */
    void setSportSleepMode(int sportSleepMode);

    /**
     * 获取紫外线
     *
     * @return 紫外线值
     */
    int getUltraviolet();

    /**
     * 设置紫外线
     *
     * @param ultraviolet 紫外线值
     */
    void setUltraviolet(int ultraviolet);

    /**
     * 获取最后一次同步时间
     *
     * @return 最后一次同步时间
     */
    String getLastSyncTime();

    /**
     * 设置最后一次同步时间
     *
     * @param lastSyncTime 最后一次同步时间
     */
    void setLastSyncTime(String lastSyncTime);

    /**
     * 获取Token
     *
     * @return Token
     */
    String getToken();

    /**
     * 设置Token
     *
     * @param token Token
     */
    void setToken(String token);

    /**
     * 获取AccountID
     *
     * @return AccountID
     */
    String getAccountID();

    /**
     * 设置AccountID
     *
     * @param accountID AccountID
     */
    void setAccountID(String accountID);

    /**
     * 获取自动登录
     *
     * @return 自动登录
     */
    boolean getAutoLogin();

    /**
     * 设置自动登录
     *
     * @param autoLogin 自动登录
     */
    void setAutoLogin(boolean autoLogin);

    /**
     * 获取第三方登录
     *
     * @return 第三方登录
     */
    boolean getThirdPartLogin();

    /**
     * 设置第三方登录
     *
     * @param thirdPartLogin 第三方登录
     */
    void setThirdPartLogin(boolean thirdPartLogin);

    /**
     * 获取心率功能
     *
     * @return true:已打开 false:关闭
     */
    boolean getHeartRateFunction();

    /**
     * 设置心率功能
     *
     * @param isOpen true:已打开 false:关闭
     */
    void setHeartRateFunction(boolean isOpen);

    /**
     * 获取服务器固件的版本
     *
     * @return 服务器固件的版本
     */
    String getNetFirmwareVersion();

    /**
     * 设置服务器固件的版本
     *
     * @param netFirmwareVersion 服务器固件的版本
     */
    void setNetFirmwareVersion(String netFirmwareVersion);

    /**
     * 获取服务器固件的url
     *
     * @return 服务器固件的url
     */
    String getNetFirmwareUrl();

    /**
     * 设置服务器固件的url
     *
     * @param netFirmwareUrl 服务器固件的url
     */
    void setNetFirmwareUrl(String netFirmwareUrl);


}
