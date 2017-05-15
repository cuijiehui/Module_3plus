package cn.appscomm.bluetooth.interfaces;

import cn.appscomm.bluetooth.mode.ReminderBT;

/**
 * 作者：hsh
 * 日期：2017/3/06
 * 说明：蓝牙PM间的接口定义
 */
public interface PMBluetoothCall {
    int SMS_NOTIFY_TYPE_SMS = 0;
    int SMS_NOTIFY_TYPE_SOCIAL = 1;
    int SMS_NOTIFY_TYPE_EMAIL = 2;
    int SMS_NOTIFY_TYPE_SCHEDULE = 3;

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
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getWatchID(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取设备版本
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getDeviceVersion(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取设备的日期时间
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getDateTime(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置设备的日期时间
     *
     * @param callback    回调结果
     * @param year        年
     * @param month       月
     * @param day         日
     * @param hour        时
     * @param min         分
     * @param sec         秒
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setDateTime(IBluetoothResultCallback callback, int year, int month, int day, int hour, int min, int sec, int commandType, String[] macs);

    /**
     * 获取设备时间界面设置
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getTimeSurfaceSetting(IBluetoothResultCallback callback, int commandType, String[] macs);

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
     * @param commandType     发送类型
     * @param macs            要下发命令的所有设备
     */
    void setTimeSurfaceSetting(IBluetoothResultCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, int commandType, String[] macs);

    // TODO 一级界面获取设置未做

    /**
     * 获取屏幕亮度
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getScreenBrightness(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置屏幕亮度
     *
     * @param callback        回调结果
     * @param brightnessValue 屏幕亮度
     * @param commandType     发送类型
     * @param macs            要下发命令的所有设备
     */
    void setScreenBrightness(IBluetoothResultCallback callback, int brightnessValue, int commandType, String[] macs);

    /**
     * 获取电池值
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getBatteryPower(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取音量值
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getVolume(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置音量值
     *
     * @param callback    回调结果
     * @param volume      音量值
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setVolume(IBluetoothResultCallback callback, int volume, int commandType, String[] macs);

    /**
     * 获取震动模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getShockMode(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置震动模式
     *
     * @param callback    回调结果
     * @param shockType   震动类型
     * @param shockMode   震动模式
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setShockMode(IBluetoothResultCallback callback, int shockType, int shockMode, int commandType, String[] macs);

    /**
     * 获取语言
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getLanguage(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置语言
     *
     * @param callback    回调结果
     * @param language    语言
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setLanguage(IBluetoothResultCallback callback, int language, int commandType, String[] macs);

    /**
     * 获取单位
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUnit(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置单位
     *
     * @param callback    回调结果
     * @param unit        单位
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUnit(IBluetoothResultCallback callback, int unit, int commandType, String[] macs);

    /**
     * 恢复出厂
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void restoreFactory(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 进入升级模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void enterUpdateMode(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 进入升级模式(需要填入飞思卡尔、触摸芯片、心率的地址)
     *
     * @param callback             回调结果
     * @param freescaleAddress     飞思卡尔地址
     * @param freescaleSectorCount 飞思卡尔扇区个数
     * @param touchAddress         触摸芯片地址
     * @param touchSectorCount     触摸芯片扇区个数
     * @param heartRateAddress     心率地址
     * @param heartRateSectorCount 心率扇区个数
     * @param callback             回调结果
     * @param commandType          发送类型
     * @param macs                 要下发命令的所有设备
     */
    void enterUpdateMode(IBluetoothResultCallback callback, byte[] freescaleAddress, byte freescaleSectorCount, byte[] touchAddress, byte touchSectorCount, byte[] heartRateAddress, byte heartRateSectorCount, int commandType, String[] macs);

    /**
     * 获取震动强度
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getShockStrength(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置震动强度
     *
     * @param callback      回调结果
     * @param shockStrength 震动强度
     * @param commandType   发送类型
     * @param macs          要下发命令的所有设备
     */
    void setShockStrength(IBluetoothResultCallback callback, int shockStrength, int commandType, String[] macs);

    // TODO 设置/获取 主界面和报警界面的背景颜色

    /**
     * 获取工作模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getWorkMode(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置工作模式
     *
     * @param callback    回调结果
     * @param workMode    工作模式
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setWorkMode(IBluetoothResultCallback callback, int workMode, int commandType, String[] macs);

    /**
     * 获取亮屏时间
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getBrightScreenTime(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置亮屏时间
     *
     * @param callback         回调结果
     * @param brightScreenTime 亮屏时间
     * @param commandType      发送类型
     * @param macs             要下发命令的所有设备
     */
    void setBrightScreenTime(IBluetoothResultCallback callback, int brightScreenTime, int commandType, String[] macs);

    /*-----------------------------------------------个人信息类-------------------------------------------------*/

    /**
     * 获取个人信息
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUserInfo(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置个人信息
     *
     * @param callback    回调结果
     * @param sex         性别
     * @param age         年龄
     * @param height      身高
     * @param weight      体重
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUserInfo(IBluetoothResultCallback callback, int sex, int age, int height, int weight, int commandType, String[] macs);

    /**
     * 获取用户习惯
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUsageHabits(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置用户习惯
     *
     * @param callback    回调结果
     * @param usageHabits 用户习惯
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUsageHabits(IBluetoothResultCallback callback, int usageHabits, int commandType, String[] macs);

    /**
     * 获取用户名称
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUserName(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置用户名称
     *
     * @param callback    回调结果
     * @param userName    用户名称
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUserName(IBluetoothResultCallback callback, String userName, int commandType, String[] macs);

    /*-----------------------------------------------运动相关类-------------------------------------------------*/

    /**
     * 获取目标
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getGoal(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置目标
     *
     * @param callback    回调结果
     * @param goalType    目标类型
     * @param goalValue   目标值
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setGoal(IBluetoothResultCallback callback, int goalType, int goalValue, int commandType, String[] macs);

    /**
     * 获取运动睡眠模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getSportSleepMode(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取所有类型的条数(运动、睡眠、心率、血压等)
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getAllDataTypeCount(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 删除运动数据
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void deleteSportData(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取运动数据
     *
     * @param callback       回调结果
     * @param sportDataCount 运动数据条数
     * @param commandType    发送类型
     * @param macs           要下发命令的所有设备
     */
    void getSportData(IBluetoothResultCallback callback, int sportDataCount, int commandType, String[] macs);

    /**
     * 删除睡眠数据
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void deleteSleepData(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取睡眠数据
     *
     * @param callBack       回调结果
     * @param sleepDataCount 睡眠数据条数
     * @param commandType    发送类型
     * @param macs           要下发命令的所有设备
     */
    void getSleepData(IBluetoothResultCallback callBack, int sleepDataCount, int commandType, String[] macs);

    /**
     * 获取设备显示
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getDeviceDisplay(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取自动睡眠
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getAutoSleep(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置自动睡眠
     *
     * @param callback    回调结果
     * @param enterHour   进入小时
     * @param enterMin    进入分钟
     * @param quitHour    退出小时
     * @param quitMin     退出分钟
     * @param cycle       周期
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setAutoSleep(IBluetoothResultCallback callback, int enterHour, int enterMin, int quitHour, int quitMin, int cycle, int commandType, String[] macs);

    /**
     * 获取心率总数
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getHeartRateCount(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 删除心率数据
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void deleteHeartRateData(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取心率数据
     *
     * @param callback           回调结果
     * @param heartRateDataCount 心率数据条数
     * @param commandType        发送类型
     * @param macs               要下发命令的所有设备
     */
    void getHeartRateData(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String[] macs);

    /**
     * 获取自动心率
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getAutoHeartRateFrequency(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置自动心率
     *
     * @param callback           回调结果
     * @param heartRateFrequency 心率间隔
     * @param commandType        发送类型
     * @param macs               要下发命令的所有设备
     */
    void setAutoHeartRateFrequency(IBluetoothResultCallback callback, int heartRateFrequency, int commandType, String[] macs);

    /**
     * 获取心率报警门限
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getHeartRateAlarmThreshold(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置心率报警门限
     *
     * @param callback             回调结果
     * @param heartRateAlarmSwitch 心率开关
     * @param heartRateMinValue    心率最小值报警
     * @param heartRateMaxValue    心率最大值报警
     * @param commandType          发送类型
     * @param macs                 要下发命令的所有设备
     */
    void setHeartRateAlarmThreshold(IBluetoothResultCallback callback, int heartRateAlarmSwitch, int heartRateMinValue, int heartRateMaxValue, int commandType, String[] macs);

    /**
     * 获取久坐提醒
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getInactivityAlert(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置久坐提醒
     *
     * @param callback    回调结果
     * @param isOpen      是否开启
     * @param cycle       周期
     * @param interval    间隔
     * @param startHour   开始小时
     * @param startMin    开始分钟
     * @param endHour     结束小时
     * @param endMin      结束分钟
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setInactivityAlert(IBluetoothResultCallback callback, int isOpen, int cycle, int interval, int startHour, int startMin, int endHour, int endMin, int commandType, String[] macs);

    /**
     * 获取卡路里类型
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getCaloriesType(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置卡路里类型
     *
     * @param callback    回调结果
     * @param enable      开关
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setCaloriesType(IBluetoothResultCallback callback, boolean enable, int commandType, String[] macs);

    /**
     * 获取心率数据(一条命令返回2条心率值)
     *
     * @param callback           回调结果
     * @param heartRateDataCount 心率数据条数
     * @param commandType        发送类型
     * @param macs               要下发命令的所有设备
     */
    void getHeartRateDataEx(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String[] macs);

    // TODO 获取血压总数
    // TODO 删除血压命令
    // TODO 获取血压数据
    // TODO 血压芯片学习
    // TODO 设备计时运动总数
    // TODO 获取计时运动数据
    // TODO 删除计时运动数据

    /*-------------------------------------------------通知类---------------------------------------------------*/

    /**
     * 发送来电姓名或号码
     *
     * @param callback      回调结果
     * @param len           姓名或号码字节数组的长度
     * @param callType      呼叫类型(未接或来电)
     * @param bNameOrNumber 姓名或号码
     * @param commandType   发送类型
     * @param macs          要下发命令的所有设备
     */
    void sendPhoneName(IBluetoothResultCallback callback, int len, int callType, byte[] bNameOrNumber, int commandType, String[] macs);

    /**
     * 发送消息条数
     *
     * @param callback    回调结果
     * @param msgType     消息类型
     * @param msgCount    消息条数
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void sendMessageCount(IBluetoothResultCallback callback, int msgType, int msgCount, int commandType, String[] macs);

    /**
     * 发送短信和通知
     *
     * @param callback      回调结果
     * @param pushLen       推送长度
     * @param pushType      推送类型
     * @param pushData      推送数据
     * @param smsNotifyType 短信通知类型(短信、社交、邮件、日程)
     * @param commandType   发送类型
     * @param macs          要下发命令的所有设备
     */
    void sendSMSAndNotify(IBluetoothResultCallback callback, int pushLen, int pushType, byte[] pushData, int smsNotifyType, int commandType, String[] macs);

    /*-----------------------------------------------状态设置类-------------------------------------------------*/

    /**
     * 获取开关设置
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getSwitchSetting(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置开关
     *
     * @param callback    回调结果
     * @param switchType  开关类型
     * @param enable      开关
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setSwitchSetting(IBluetoothResultCallback callback, int switchType, boolean enable, int commandType, String[] macs);

    /**
     * 获取提醒条数
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getReminderCount(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 获取提醒
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getReminder(IBluetoothResultCallback callback, int reminderCount, int commandType, String[] macs);

    /**
     * 设置提醒
     *
     * @param callback    回调结果
     * @param type        提醒操作(0x00:新增 0x01:修改 0x02:删除 0x03:全部删除)
     * @param oldReminder 旧提醒
     * @param newReminder 新提醒
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setReminder(IBluetoothResultCallback callback, int type, ReminderBT oldReminder, ReminderBT newReminder, int commandType, String[] macs);

    /**
     * 获取UID
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUID(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 设置UID
     *
     * @param callback    回调结果
     * @param UID         UID
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUID(IBluetoothResultCallback callback, int UID, int commandType, String[] macs);

    /**
     * 检查初始化
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void checkInit(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 绑定开始
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void bindStart(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 绑定结束
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void bindEnd(IBluetoothResultCallback callback, int commandType, String[] macs);

    /**
     * 发送歌曲名
     *
     * @param musicState  音乐状态(true:播放 false:暂停)
     * @param songName    歌曲名
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void sendSongName(byte musicState, byte[] songName, int commandType, String[] macs);

    /**
     * 无音乐播放
     *
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void sendStop(int commandType, String[] macs);

    /**
     * 设置音量
     *
     * @param volume      音量值
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void sendVolume(int volume, int commandType, String[] macs);
}
