package cn.appscomm.presenter.interfaces;

import java.util.Date;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.presenter.SyncBluetoothData;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface PVBluetoothCall {

    int SYNC_TYPE_GET_HEART_RATE = SyncBluetoothData.GET_HEART_RATE_COUNT;                          // 获取心率
    int SYNC_TYPE_GET_BLOOD_PRESSURE = SyncBluetoothData.GET_BLOOD_PRESSURE_COUNT;                  // 获取血压
    int SYNC_TYPE_GET_BATTERY = SyncBluetoothData.GET_BATTERY;                                      // 获取电池
    int SYNC_TYPE_GET_SPORT_SLEEP_MODE = SyncBluetoothData.GET_SPORT_SLEEP_MODE;                    // 获取运动睡眠模式
    int SYNC_TYPE_GET_ULTRAVIOLET = SyncBluetoothData.GET_ULTRAVIOLET;                              // 获取紫外线
    int SYNC_TYPE_GET_DEVICE_VERSION = SyncBluetoothData.GET_DEVICE_VERSION;                        // 获取设备版本
    int SYNC_TYPE_SET_LANGUAGE = SyncBluetoothData.SET_LANGUAGE;                                    // 设置语言
    int SYNC_TYPE_SET_UNIT = SyncBluetoothData.SET_UNIT;                                            // 设置单位

    int REMINDER_TYPE_EAT = BluetoothCommandConstant.REMIND_TYPE_EAT;                               // 吃饭
    int REMINDER_TYPE_MEDICINE = BluetoothCommandConstant.REMIND_TYPE_MEDICINE;                     // 吃药
    int REMINDER_TYPE_DRINK = BluetoothCommandConstant.REMIND_TYPE_DRINK;                           // 喝水
    int REMINDER_TYPE_SLEEP = BluetoothCommandConstant.REMIND_TYPE_SLEEP;                           // 睡觉
    int REMINDER_TYPE_AWAKE = BluetoothCommandConstant.REMIND_TYPE_AWAKE;                           // 清醒
    int REMINDER_TYPE_SPORT = BluetoothCommandConstant.REMIND_TYPE_SPORT;                           // 运动
    int REMINDER_TYPE_METTING = BluetoothCommandConstant.REMIND_TYPE_METTING;                       // 会议
    int REMINDER_TYPE_CUSTOM = BluetoothCommandConstant.REMIND_TYPE_CUSTOM;                         // 自定义

    int SWITCH_TYPE_ANTI = BluetoothCommandConstant.SWITCH_TYPE_ANTI;                               // 防丢开关
    int SWITCH_TYPE_AUTO_SYNC = BluetoothCommandConstant.SWITCH_TYPE_AUTO_SYNC;                     // 自动同步开关
    int SWITCH_TYPE_SLEEP = BluetoothCommandConstant.SWITCH_TYPE_SLEEP;                             // 睡眠开关
    int SWITCH_TYPE_AUTO_SLEEP = BluetoothCommandConstant.SWITCH_TYPE_AUTO_SLEEP;                   // 自动睡眠开关
    int SWITCH_TYPE_CALL = BluetoothCommandConstant.SWITCH_TYPE_CALL;                               // 来电开关
    int SWITCH_TYPE_MISS_CALL = BluetoothCommandConstant.SWITCH_TYPE_MISS_CALL;                     // 未接来电开关
    int SWITCH_TYPE_SMS = BluetoothCommandConstant.SWITCH_TYPE_SMS;                                 // 短信开关
    int SWITCH_TYPE_SOCIAL = BluetoothCommandConstant.SWITCH_TYPE_SOCIAL;                           // 社交开关
    int SWITCH_TYPE_EMAIL = BluetoothCommandConstant.SWITCH_TYPE_EMAIL;                             // 邮件开关
    int SWITCH_TYPE_CALENDAR = BluetoothCommandConstant.SWITCH_TYPE_CALENDAR;                       // 日程开关
    int SWITCH_TYPE_SEDENTARY = BluetoothCommandConstant.SWITCH_TYPE_SEDENTARY;                     // 久坐开关
    int SWITCH_TYPE_LOW_POWER = BluetoothCommandConstant.SWITCH_TYPE_LOW_POWER;                     // 低电量提醒开关
    int SWITCH_TYPE_SECOND_REMINDER = BluetoothCommandConstant.SWITCH_TYPE_SECOND_REMINDER;         // 二次提醒开关
    int SWITCH_TYPE_RING = BluetoothCommandConstant.SWITCH_TYPE_RING;                               // 铃声开关
    int SWITCH_TYPE_RAISE_WAKE = BluetoothCommandConstant.SWITCH_TYPE_RAISE_WAKE;                   // 抬手亮屏开关

    /**
     * 开启服务
     */
    void startService();

    /**
     * 重启服务
     *
     * @param mac                需要连接的MAC地址，如果为空，则重连以前的MAC地址
     * @param isSupportHeartRate 是否支持心率功能
     * @return true：需要连接的mac地址不为空，可以重启服务 false：mac地址为空，不能重启服务
     */
    boolean resetService(String mac, boolean isSupportHeartRate);

    /**
     * 结束服务
     *
     * @param mac 结束指定mac的服务,null为结束所有
     */
    void endService(String mac);

    /**
     * 蓝牙服务是否运行
     *
     * @return true:运行 false:停止
     */
    boolean isBluetoothLeServiceRunning();

    /**
     * 连接指定mac的设备
     *
     * @param mac                指定的mac
     * @param isSupportHeartRate 是否支持心率
     */
    void connect(String mac, boolean isSupportHeartRate);

    /**
     * 手机是否连接设备
     *
     * @param mac 指定mac的设备是否连接
     * @return true：已连接 false：未连接
     */
    boolean isConnect(String mac);

    /*--------------------------------------------------设置类--------------------------------------------------*/

    /**
     * 获取watchID
     *
     * @param callback 回调结果
     */
    void getWatchID(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取设备版本
     *
     * @param callback 回调结果
     */
    void getDeviceVersion(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取设备的日期时间
     *
     * @param callback 回调结果
     */
    void getDateTime(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置设备的日期时间
     *
     * @param callback 回调结果
     * @param year     年
     * @param month    月
     * @param day      日
     * @param hour     时
     * @param min      分
     * @param sec      秒
     */
    void setDateTime(PVBluetoothCallback callback, int year, int month, int day, int hour, int min, int sec, String[] macs);

    /**
     * 获取设备时间界面设置
     *
     * @param callback 回调结果
     */
    void getTimeSurfaceSetting(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置设备时间界面
     *
     * @param dateFormat      日期格式
     * @param timeFormat      时间格式
     * @param batteryFormat   电池格式
     * @param lunarFormat     日历格式
     * @param screenFormat    屏幕格式
     * @param backgroundStyle 背景格式
     * @param sportDataFormat 运动数据格式
     * @param usernameFormat  用户名格式
     */
    void setTimeSurfaceSetting(PVBluetoothCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, String[] macs);

    // TODO 设置/获取 一级界面

    /**
     * 获取屏幕亮度
     *
     * @param callback 回调结果
     */
    void getScreenBrightness(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置屏幕亮度
     *
     * @param callback        回调结果
     * @param brightnessValue 屏幕亮度
     */
    void setScreenBrightness(PVBluetoothCallback callback, int brightnessValue, String[] macs);

    /**
     * 获取电池值
     *
     * @param callback 回调结果
     */
    void getBatteryPower(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取音量值
     *
     * @param callback 回调结果
     */
    void getVolume(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置音量值
     *
     * @param callback 回调结果
     * @param volume   音量值
     */
    void setVolume(PVBluetoothCallback callback, int volume, String[] macs);

    /**
     * 获取震动模式
     *
     * @param callback 回调结果
     */
    void getShockMode(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置震动模式
     *
     * @param callback  回调结果
     * @param shockType 震动类型
     * @param shockMode 震动模式
     */
    void setShockMode(PVBluetoothCallback callback, int shockType, int shockMode, String[] macs);

    /**
     * 获取语言
     *
     * @param callback 回调结果
     */
    void getLanguage(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置语言
     *
     * @param callback 回调结果
     * @param language 语言
     */
    void setLanguage(PVBluetoothCallback callback, int language, String[] macs);

    /**
     * 获取单位
     *
     * @param callback 回调结果
     */
    void getUnit(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置单位
     *
     * @param callback 回调结果
     * @param unit     单位
     */
    void setUnit(PVBluetoothCallback callback, int unit, String[] macs);

    /**
     * 恢复出厂
     *
     * @param callback 回调结果
     */
    void restoreFactory(PVBluetoothCallback callback, String[] macs);

    /**
     * 进入升级模式
     *
     * @param callback 回调结果
     */
    void enterUpdateMode(PVBluetoothCallback callback, String[] macs);

    /**
     * 进入升级模式(需要填入飞思卡尔、触摸芯片、心率的地址)
     *
     * @param touchPanelPath 触摸芯片升级文件路径
     * @param heartRatePath  心率升级文件路径
     * @param freescalePath  飞思卡尔升级文件路径
     * @param callback       回调结果
     */
    void enterUpdateMode(String touchPanelPath, String heartRatePath, String freescalePath, PVBluetoothCallback callback, String[] macs);

    /**
     * 获取震动强度
     *
     * @param callback 回调结果
     */
    void getShockStrength(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置震动强度
     *
     * @param callback      回调结果
     * @param shockStrength 震动强度
     */
    void setShockStrength(PVBluetoothCallback callback, int shockStrength, String[] macs);

    // TODO 设置/获取 主界面和报警界面的背景颜色

    /**
     * 获取工作模式
     *
     * @param callback 回调结果
     */
    void getWorkMode(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置工作模式
     *
     * @param callback 回调结果
     * @param workMode 工作模式
     */
    void setWorkMode(PVBluetoothCallback callback, int workMode, String[] macs);

    /**
     * 获取亮屏时间
     *
     * @param callback 回调结果
     */
    void getBrightScreenTime(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置亮屏时间
     *
     * @param callback         回调结果
     * @param brightScreenTime 亮屏时间
     */
    void setBrightScreenTime(PVBluetoothCallback callback, int brightScreenTime, String[] macs);

    /*-----------------------------------------------个人信息类-------------------------------------------------*/

    /**
     * 获取个人信息
     *
     * @param callback 回调结果
     */
    void getUserInfo(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置个人信息
     *
     * @param callback 回调结果
     * @param sex      性别
     * @param age      年龄
     * @param height   身高
     * @param weight   体重
     */
    void setUserInfo(PVBluetoothCallback callback, int sex, int age, int height, int weight, String[] macs);

    /**
     * 获取用户习惯
     *
     * @param callback 回调结果
     */
    void getUsageHabits(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置用户习惯
     *
     * @param callback    回调结果
     * @param usageHabits 用户习惯
     */
    void setUsageHabits(PVBluetoothCallback callback, int usageHabits, String[] macs);

    /**
     * 获取用户名称
     *
     * @param callback 回调结果
     */
    void getUserName(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置用户名称
     *
     * @param callback 回调结果
     * @param userName 用户名称
     */
    void setUserName(PVBluetoothCallback callback, String userName, String[] macs);

    /*-----------------------------------------------运动相关类-------------------------------------------------*/

    /**
     * 获取目标设置
     *
     * @param callback 回调结果
     */
    void getGoal(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置步数目标
     *
     * @param callback 回调结果
     * @param stepGoal 步数目标值
     */
    void setStepGoal(PVBluetoothCallback callback, int stepGoal, String[] macs);

    /**
     * 设置卡路里目标
     *
     * @param callback     回调结果
     * @param caloriesGoal 卡路里目标值
     */
    void setCaloriesGoal(PVBluetoothCallback callback, int caloriesGoal, String[] macs);

    /**
     * 设置距离目标
     *
     * @param callback     回调结果
     * @param distanceGoal 距离目标值
     */
    void setDistanceGoal(PVBluetoothCallback callback, int distanceGoal, String[] macs);

    /**
     * 设置运动时长目标
     *
     * @param callback      回调结果
     * @param sportTimeGoal 运动时长
     */
    void setSportTimeGoal(PVBluetoothCallback callback, int sportTimeGoal, String[] macs);

    /**
     * 设置睡眠目标
     *
     * @param callback  回调结果
     * @param sleepGoal 睡眠目标值
     */
    void setSleepGoal(PVBluetoothCallback callback, int sleepGoal, String[] macs);

    /**
     * 获取运动睡眠模式
     *
     * @param callback 回调结果
     */
    void getSportSleepMode(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取所有类型的条数(运动、睡眠、心率、血压等)
     *
     * @param callback 回调结果
     */
    void getAllDataTypeCount(PVBluetoothCallback callback, String[] macs);

    /**
     * 删除运动数据
     *
     * @param callback 回调结果
     */
    void deleteSportData(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取运动数据
     *
     * @param callback       回调结果
     * @param sportDataCount 运动数据条数
     */
    void getSportData(PVBluetoothCallback callback, int sportDataCount, String[] macs);

    /**
     * 删除睡眠数据
     *
     * @param callback 回调结果
     */
    void deleteSleepData(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取睡眠数据
     *
     * @param callback       回调结果
     * @param sleepDataCount 睡眠数据条数
     */
    void getSleepData(PVBluetoothCallback callback, int sleepDataCount, String[] macs);

    /**
     * 获取设备显示
     *
     * @param callback 回调结果
     */
    void getDeviceDisplay(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取自动睡眠
     *
     * @param callback 回调结果
     */
    void getAutoSleep(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置自动睡眠
     *
     * @param callback  回调结果
     * @param enterHour 进入小时
     * @param enterMin  进入分钟
     * @param quitHour  退出小时
     * @param quitMin   退出分钟
     * @param cycle     周期
     */
    void setAutoSleep(PVBluetoothCallback callback, int enterHour, int enterMin, int quitHour, int quitMin, int cycle, String[] macs);

    /**
     * 获取心率总数
     *
     * @param callback 回调结果
     */
    void getHeartRateCount(PVBluetoothCallback callback, String[] macs);

    /**
     * 删除心率数据
     *
     * @param callback 回调结果
     */
    void deleteHeartRateData(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取心率数据
     *
     * @param callback           回调结果
     * @param heartRateDataCount 心率数据条数
     */
    void getHeartRateData(PVBluetoothCallback callback, int heartRateDataCount, String[] macs);

    /**
     * 获取自动心率
     *
     * @param callback 回调结果
     */
    void getAutoHeartRateFrequency(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置自动心率
     *
     * @param callback           回调结果
     * @param heartRateFrequency 心率间隔
     */
    void setAutoHeartRateFrequency(PVBluetoothCallback callback, int heartRateFrequency, String[] macs);

    /**
     * 获取心率报警门限
     *
     * @param callback 回调结果
     */
    void getHeartRateAlarmThreshold(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置心率报警门限
     *
     * @param callback             回调结果
     * @param heartRateAlarmSwitch 心率开关
     * @param heartRateMinValue    心率最小值报警
     * @param heartRateMaxValue    心率最大值报警
     */
    void setHeartRateAlarmThreshold(PVBluetoothCallback callback, int heartRateAlarmSwitch, int heartRateMinValue, int heartRateMaxValue, String[] macs);

    /**
     * 获取久坐提醒
     *
     * @param callback 回调结果
     */
    void getInactivityAlert(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置久坐提醒
     *
     * @param callback  回调结果
     * @param isOpen    是否开启
     * @param cycle     周期
     * @param interval  间隔
     * @param startHour 开始小时
     * @param startMin  开始分钟
     * @param endHour   结束小时
     * @param endMin    结束分钟
     */
    void setInactivityAlert(PVBluetoothCallback callback, int isOpen, int cycle, int interval, int startHour, int startMin, int endHour, int endMin, String[] macs);

    /**
     * 获取卡路里类型
     *
     * @param callback 回调结果
     */
    void getCaloriesType(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置卡路里类型
     *
     * @param callback 回调结果
     * @param enable   开关
     */
    void setCaloriesType(PVBluetoothCallback callback, boolean enable, String[] macs);

    /**
     * 获取心率数据(一条命令返回2条心率值)
     *
     * @param callback           回调结果
     * @param heartRateDataCount 心率数据条数
     */
    void getHeartRateDataEx(PVBluetoothCallback callback, int heartRateDataCount, String[] macs);

    // TODO 获取血压总数
    // TODO 删除血压命令
    // TODO 获取血压数据
    // TODO 血压芯片学习
    // TODO 设备计时运动总数
    // TODO 获取计时运动数据
    // TODO 删除计时运动数据
    // TODO 获取紫外线

    /*-------------------------------------------------通知类---------------------------------------------------*/

    /**
     * 发送来电姓名或号码
     *
     * @param callback     回调结果
     * @param nameOrNumber 姓名或号码
     */
    void sendIncomeCall(PVBluetoothCallback callback, String nameOrNumber, String[] macs);

    /**
     * 发送挂机
     *
     * @param callback 回调结果
     */
    void sendOffCall(PVBluetoothCallback callback, String[] macs);

    /**
     * 发送未接来电姓名或号码
     *
     * @param callback      回调结果
     * @param nameOrNumber  姓名或号码
     * @param dateTime      日期时间
     * @param missCallCount 未接数量
     */
    void sendMissCall(PVBluetoothCallback callback, String nameOrNumber, Date dateTime, int missCallCount, String[] macs);

    /**
     * 发送短消息
     *
     * @param callback 回调结果
     * @param name     姓名
     * @param content  内容
     * @param dateTime 日期时间
     * @param smsCount 短信条数
     */
    void sendSMS(PVBluetoothCallback callback, String name, String content, Date dateTime, int smsCount, String[] macs);

    /**
     * 发送社交
     *
     * @param callback    回调结果
     * @param title       标题
     * @param content     内容
     * @param dateTime    日期时间
     * @param socialType  社交类型
     * @param socialCount 社交条数
     */
    void sendSocial(PVBluetoothCallback callback, String title, String content, Date dateTime, int socialType, int socialCount, String[] macs);

    /**
     * 发送邮件
     *
     * @param callback   回调结果
     * @param email      邮件
     * @param content    内容
     * @param dateTime   日期时间
     * @param emailCount 邮件条数
     */
    void sendEmail(PVBluetoothCallback callback, String email, String content, Date dateTime, int emailCount, String[] macs);

    /**
     * 发送日程
     *
     * @param callback      回调结果
     * @param content       内容
     * @param dateTime      日期时间
     * @param scheduleCount 日程条数
     */
    void sendSchedule(PVBluetoothCallback callback, String content, Date dateTime, int scheduleCount, String[] macs);

     /*-----------------------------------------------状态设置类-------------------------------------------------*/

    /**
     * 获取开关设置
     *
     * @param callback 回调结果
     */
    void getSwitchSetting(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置开关
     *
     * @param callback   回调结果
     * @param switchType 开关类型
     * @param enable     开关
     */
    void setSwitchSetting(PVBluetoothCallback callback, int switchType, boolean enable, String[] macs);

    /**
     * 获取提醒条数
     *
     * @param callback 回调结果
     */
    void getReminderCount(PVBluetoothCallback callback, String[] macs);

    /**
     * 获取提醒
     *
     * @param callback      回调结果
     * @param reminderCount 提醒条数
     */
    void getReminder(PVBluetoothCallback callback, int reminderCount, String[] macs);

    /**
     * 新增一条提醒
     *
     * @param callback   回答结果
     * @param reminderBT 需要新增的提醒(index可以设为0)
     */
    void addReminder(PVBluetoothCallback callback, ReminderBT reminderBT, String[] macs);

    /**
     * 删除一条提醒
     *
     * @param callback   回调结果
     * @param reminderBT 若不为空：需要删除的提醒 若为null：删除所有提醒
     */
    void deleteReminder(PVBluetoothCallback callback, ReminderBT reminderBT, String[] macs);

    /**
     * 修改提醒
     *
     * @param callback    回调结果
     * @param oldReminder 旧提醒
     * @param newReminder 新提醒
     */
    void changeReminder(PVBluetoothCallback callback, ReminderBT oldReminder, ReminderBT newReminder, String[] macs);

    /**
     * 获取UID
     *
     * @param callback 回调结果
     */
    void getUID(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置UID
     *
     * @param callback 回调结果
     * @param UID      UID
     */
    void setUID(PVBluetoothCallback callback, int UID, String[] macs);

    /**
     * 检查初始化
     *
     * @param callback 回调结果
     */
    void checkInit(PVBluetoothCallback callback, String[] macs);

    /**
     * 绑定开始
     *
     * @param callback 回调结果
     */
    void bindStart(PVBluetoothCallback callback, String[] macs);

    /**
     * 绑定结束
     *
     * @param callback 回调结果
     */
    void bindEnd(PVBluetoothCallback callback, String[] macs);

    /*-------------------------------------------------支付类---------------------------------------------------*/

    // TODO 支付：卡号
    // TODO 支付：读取余额
    // TODO 支付：交易记录
    // TODO 支付：透传

    /*-------------------------------------------------扩展类---------------------------------------------------*/

    /**
     * 发送歌曲名
     *
     * @param callback   回调结果
     * @param musicState 音乐状态(true:播放 false:暂停)
     * @param songName   歌曲名
     */
    void sendSongName(PVBluetoothCallback callback, boolean musicState, String songName, String[] macs);

    /**
     * 无音乐播放
     *
     * @param callback 回调结果
     */
    void sendStop(PVBluetoothCallback callback, String[] macs);

    /**
     * 设置音量
     *
     * @param callback 回调结果
     * @param volume   音量值
     */
    void sendVolume(PVBluetoothCallback callback, int volume, String[] macs);

    // TODO 远程拍照
    // TODO 寻找手机
    // TODO 短信快捷回复
    // TODO 联系人推送

    /*---------------------------------------------同步/绑定处理类-----------------------------------------------*/

    /**
     * 同步蓝牙数据
     *
     * @param callback 回调结果
     * @param syncType 同步类型
     */
    void syncBluetoothData(PVBluetoothCallback callback, int syncType, String[] macs);

    /**
     * 绑定设备
     *
     * @param callback 回调结果
     */
    void bindDevice(PVBluetoothCallback callback, String[] macs);
}
