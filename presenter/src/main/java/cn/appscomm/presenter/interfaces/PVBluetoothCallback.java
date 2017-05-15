package cn.appscomm.presenter.interfaces;

/**
 * 作者：hsh
 * 日期：2017/3/6
 * 说明：蓝牙结果回调给v
 */
public interface PVBluetoothCallback {
    enum BluetoothCommandType {
        GET_WATCH_ID,                                                                               // watch_id
        GET_DEVICE_VERSION,                                                                         // 设备版本
        GET_DATE_TIME,                                                                              // 获取设备时间
        SET_DATE_TIME,                                                                              // 设置设备时间
        GET_TIME_SURFACE_SETTING,                                                                   // 获取设备时间界面设置
        SET_TIME_SURFACE_SETTING,                                                                   // 设置设备时间界面
        GET_SCREEN_BRIGHTNESS,                                                                      // 获取设备屏幕亮度
        SET_SCREEN_BRIGHTNESS,                                                                      // 设置设备屏幕亮度
        GET_BATTERY_POWER,                                                                          // 获取设备电量值
        GET_VOLUME,                                                                                 // 获取设备音量值
        SET_VOLUME,                                                                                 // 设置设备音量值
        GET_SHOCK_MODE,                                                                             // 获取设备震动模式
        SET_SHOCK_MODE,                                                                             // 设置设备震动模式
        GET_LANGUAGE,                                                                               // 获取设备语言
        SET_LANGUAGE,                                                                               // 设置设备语言
        GET_UNIT,                                                                                   // 获取设备单位
        SET_UNIT,                                                                                   // 设置设备单位
        RESTORE_FACTORY,                                                                            // 恢复出厂
        ENTER_UPDATE_MODE,                                                                          // 进入升级模式
        GET_SHOCK_STRENGTH,                                                                         // 获取设备震动强度
        SET_SHOCK_STRENGTH,                                                                         // 设置设备震动强度
        GET_WORK_MODE,                                                                              // 获取设备工作模式
        SET_WORK_MODE,                                                                              // 设置设备工作模式
        GET_BRIGHT_SCREEN_TIME,                                                                     // 获取设备亮屏时间
        SET_BRIGHT_SCREEN_TIME,                                                                     // 设置设备亮屏时间
        GET_USER_INFO,                                                                              // 获取个人信息
        SET_USER_INFO,                                                                              // 设置个人信息
        GET_USAGE_HABIT,                                                                            // 获取用户习惯
        SET_USAGE_HABIT,                                                                            // 设置用户习惯
        GET_USER_NAME,                                                                              // 获取用户名称
        SET_USER_NAME,                                                                              // 设置用户名称
        GET_GOAL_SETTING,                                                                           // 获取目标设置
        SET_STEP_GOAL,                                                                              // 设置步数目标
        SET_CALORIES_GOAL,                                                                          // 设置卡路里目标
        SET_DISTANCE_GOAL,                                                                          // 设置距离目标
        SET_SPORT_TIME_GOAL,                                                                        // 设置运动时长目标
        SET_SLEEP_GOAL,                                                                             // 设置睡眠目标
        GET_SPORT_SLEEP_MODE,                                                                       // 获取运动睡眠模式
        GET_ALL_DATA_TYPE_COUNT,                                                                    // 获取所有类型数据的条数
        DELETE_SPORT_DATA,                                                                          // 删除运动数据
        GET_SPORT_DATA,                                                                             // 获取运动数据
        DELETE_SLEEP_DATA,                                                                          // 删除睡眠数据
        GET_SLEEP_DATA,                                                                             // 获取睡眠数据
        GET_DEVICE_DISPLAY,                                                                         // 获取设备显示
        GET_AUTO_SLEEP,                                                                             // 获取自动睡眠
        SET_AUTO_SLEEP,                                                                             // 设置自动睡眠
        GET_HEART_RATE_DATA_COUNT,                                                                  // 获取心率总数
        DELETE_HEART_RATE_DATA,                                                                     // 删除心率数据
        GET_HEART_RATE,                                                                             // 获取心率数据
        GET_BLOOD_PRESSURE,                                                                         // 获取血压数据
        DELETE_BLOOD_PRESSURE,                                                                      // 删除血压数据
        GET_ULTRAVIOLET,                                                                            // 获取紫外线
        GET_HEART_RATE_FREQUENCY,                                                                   // 获取自动心率时间间隔
        SET_HEART_RATE_FREQUENCY,                                                                   // 设置自动心率时间间隔
        GET_HEART_RATE_ALARM_THRESHOLD,                                                             // 获取心率报警门限
        SET_HEART_RATE_ALARM_THRESHOLD,                                                             // 设置心率报警门限
        GET_INACTIVITY_ALERT,                                                                       // 获取静坐提醒
        SET_INACTIVITY_ALERT,                                                                       // 设置静坐提醒
        GET_CALORIES_TYPE,                                                                          // 获取卡路里类型
        SET_CALORIES_TYPE,                                                                          // 设置卡路里类型
        GET_HEART_RATE_EX,                                                                          // 获取心率数据(一条命令有2条心率数据)
        SEND_INCOME_CALL_NAME_OR_NUMBER,                                                            // 发送来电姓名或号码
        SEND_INCOME_CALL_COUNT,                                                                     // 发送来电数量
        SEND_OFF_HOOK,                                                                              // 发送挂机
        SEND_MISS_CALL_NAME_OR_NUMBER,                                                              // 发送未接姓名或号码
        SEND_MISS_CALL_DATETIME,                                                                    // 发送未接日期时间
        SEND_MISS_CALL_COUNT,                                                                       // 发送未接数量
        SEND_SMS_NAME_OR_NUMBER,                                                                    // 发送短信姓名或号码
        SEND_SMS_CONTENT,                                                                           // 发送短信内容
        SEND_SMS_DATETIME,                                                                          // 发送短信日期时间
        SEND_SMS_COUNT,                                                                             // 发送短信数量
        SEND_SOCIAL_TITLE,                                                                          // 发送社交标题
        SEND_SOCIAL_CONTENT,                                                                        // 发送社交内容
        SEND_SOCIAL_DATETIME,                                                                       // 发送社交日期时间
        SEND_SOCIAL_COUNT,                                                                          // 发送社交数量
        SEND_EMAIL_ADDRESS,                                                                         // 发送邮件地址
        SEND_EMAIL_CONTENT,                                                                         // 发送邮件内容
        SEND_EMAIL_DATETIME,                                                                        // 发送邮件日期时间
        SEND_EMAIL_COUNT,                                                                           // 发送邮件数量
        SEND_SCHEDULE_CONTENT,                                                                      // 发送日程内容
        SEND_SCHEDULE_DATETIME,                                                                     // 发送日程日期时间
        SEND_SCHEDULE_COUNT,                                                                        // 发送日程数量
        GET_SWITCH,                                                                                 // 获取开关
        SET_SWITCH,                                                                                 // 设置开关
        GET_REMINDER_COUNT,                                                                         // 获取提醒条数
        GET_REMINDER,                                                                               // 获取提醒
        NEW_REMINDER,                                                                               // 新增提醒
        CHANGE_REMINDER,                                                                            // 修改提醒
        DELETE_A_REMINDER,                                                                          // 删除一条提醒
        DELETE_ALL_REMINDER,                                                                        // 删除所有提醒
        GET_UID,                                                                                    // 获取UID
        SET_UID,                                                                                    // 设置UID
        CHECK_INIT,                                                                                 // 检查初始化
        BIND_START,                                                                                 // 绑定开始
        BIND_END,                                                                                   // 绑定结束

        SYNC_SUCCESS,                                                                               // 同步成功
        SYNC_FAIL,                                                                                  // 同步失败
        BIND_SUCCESS,                                                                               // 绑定成功
        BIND_FAIL,                                                                                  // 绑定失败
    }

    int DATA_TYPE_INT = 0;                                                                          // int类型
    int DATA_TYPE_FLOAT = 1;                                                                        // float类型
    int DATA_TYPE_BOOLEAN = 2;                                                                      // boolean类型
    int DATA_TYPE_STRING = 3;                                                                       // String类型
    int DATA_TYPE_INT_ARRAY = 4;                                                                    // int[]类型
    int DATA_TYPE_LIST_SPORT = 5;                                                                   // List<SportBT>类型
    int DATA_TYPE_LIST_SLEEP = 6;                                                                   // List<SleepBT>类型
    int DATA_TYPE_LIST_HEART_RATE = 7;                                                              // List<HeartRateBT>类型
    int DATA_TYPE_LIST_REMINDER = 8;                                                                // List<ReminderBT>类型
    int DATA_TYPE_LIST_INT = 9;                                                                     // List<Integer>类型

    int RESULT_SUCCESS = 100;                                                                       // 蓝牙结果：成功

    /**
     * 蓝牙数据成功回调
     *
     * @param objects              数据集合
     * @param len                  数据长度
     * @param dataType             数据类型
     * @param mac                  回调指定的mac
     * @param bluetoothCommandType 蓝牙命令类型
     */
    void onSuccess(Object[] objects, int len, int dataType, String mac, BluetoothCommandType bluetoothCommandType);

    /**
     * 蓝牙数据失败回调
     *
     * @param mac                  回调指定的mac
     * @param bluetoothCommandType 蓝牙命令类型
     */
    void onFail(String mac, BluetoothCommandType bluetoothCommandType);
}
