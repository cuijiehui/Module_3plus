package cn.appscomm.sp;

/**
 * Created by Administrator on 2016/8/6.
 */
public interface SPDefaultValue {
    int DEFAULT_GOAL_STEP = 7000;                                                                   // 默认步数700
    int DEFAULT_GOAL_DISTANCE = 5;                                                                  // 默认距离5
    int DEFAULT_GOAL_SPORT_TIME = 60;                                                               // 默认运动时长60
    int DEFAULT_GOAL_CALORIES = 350;                                                                // 默认卡路里350
    int DEFAULT_GOAL_SLEEP = 8;                                                                     // 默认睡眠8

    int DEFAULT_USER_INFO_ID = -1;                                                                  // 默认userInfoId为-1
    int DEFAULT_USER_ID = -1;                                                                       // 默认userId为-1
    String DEFAULT_NICK_NAME = "";                                                                  // 默认nickName为空
    String DEFAULT_NAME = "user";                                                                   // 默认姓名为user
    int DEFAULT_GENDER = 0;                                                                         // 默认性别为男性
    int DEFAULT_UNIT = 0;                                                                           // 默认单位为0
    int DEFAULT_WEIGHT = 600;                                                                       // 默认体重为60kg(单位是0.1kg 不需要英制的,若单位为英制,则公制转英制)
    int DEFAULT_HEIGHT = 1700;                                                                      // 默认身高为170cm(单位是0.1cm 不需要英制的,若单位为英制,则公制转英制)
    String DEFAULT_COUNTRY = "CH";                                                                  // 默认国家瑞士

    boolean DEFAULT_AUTO_SYNC = false;                                                              // 默认自动同步关闭
    boolean DEFAULT_PRESET_SLEEP_SWITCH = false;                                                    // 默认预设睡眠开关 关
    int DEFAULT_BEDTIME_HOUR = 23;                                                                  // 默认睡眠时间 23时
    int DEFAULT_BEDTIME_MIN = 0;                                                                    // 默认睡眠时间 0分
    int DEFAULT_AWAKE_TIME_HOUR = 7;                                                                // 默认清醒时间 7时
    int DEFAULT_AWAKE_TIME_MIN = 0;                                                                 // 默认清醒时间 0分
    boolean DEFAULT_CALORIES_TYPE = false;                                                          // 卡路里类型(false:active true:active+restingCalories)
    boolean DEFAULT_RAISE_WAKE = false;                                                             // 抬手亮屏

    boolean DEFAULT_PUSH_CALL = false;                                                              // 默认推送电话关闭
    boolean DEFAULT_PUSH_MISCALL = false;                                                           // 默认推送未接来电关闭
    boolean DEFAULT_PUSH_SMS = false;                                                               // 默认推送短信关闭
    boolean DEFAULT_PUSH_EMAIL = false;                                                             // 默认推送邮件关闭
    boolean DEFAULT_PUSH_SOCIAL = false;                                                            // 默认推送社交关闭
    boolean DEFAULT_PUSH_CALENDAR = false;                                                          // 默认推送日历关闭
    boolean DEFAULT_PUSH_ANTI = false;                                                              // 默认推送防丢关闭

    int DEFAULT_CALL_SHOCK = 7;                                                                     // 默认来电长震动
    int DEFAULT_MISCALL_SHOCK = 4;                                                                  // 默认未接来电短震动2次
    int DEFAULT_SMS_SHOCK = 4;                                                                      // 默认短信短震动2次
    int DEFAULT_EMAIL_SHOCK = 4;                                                                    // 默认邮件短震动2次
    int DEFAULT_SOCIAL_SHOCK = 0;                                                                   // 默认社交不震动
    int DEFAULT_CALENDAR_SHOCK = 4;                                                                 // 默认日历短震动2次

    boolean DEFAULT_HEART_RATE_AUTO_TRACK = false;                                                  // 自动心率
    int DEFAULT_HEART_RATE_FREQUENCY = 5;                                                           // 心率频次
    boolean DEFAULT_HEART_RATE_RANGE_ALERT = false;                                                 // 心率报警
    int DEFAULT_HEART_RATE_MIN = 50;                                                                // 心率最小值40
    int DEFAULT_HEART_RATE_MAX = 180;                                                               // 心率最大值180

    int DEFAULT_WATCH_FACE_INDEX = 0;                                                               // 设备界面 索引格式
    int DEFAULT_WATCH_FACE_DATE_FORMAT = 0;                                                         // 设备界面 日期格式
    int DEFAULT_WATCH_FACE_TIME_FORMAT = 1;                                                         // 设备界面 时间格式
    int DEFAULT_WATCH_FACE_BATTERY_SHOW = 0;                                                        // 设备界面 电池格式
    int DEFAULT_WATCH_FACE_LUNAR_FORMAT = 0;                                                        // 设备界面 农历格式
    int DEFAULT_WATCH_FACE_SCREEN_FORMAT = 1;                                                       // 设备界面 屏幕格式
    int DEFAULT_WATCH_FACE_BACKGROUND_STYLE = 0;                                                    // 设备界面 背景风格
    int DEFAULT_WATCH_FACE_SPORT_DATA_SHOW = 0;                                                     // 设备界面 运动数据格式
    int DEFAULT_WATCH_FACE_USERNAME_FORMAT = 0;                                                     // 设备界面 用户名格式

    boolean DEFAULT_INACTIVITY_ALERT_SWITCH = false;                                                // 久坐提醒开关
    int DEFAULT_INACTIVITY_ALERT_INTERVAL = 15;                                                     // 久坐提醒间隔
    int DEFAULT_INACTIVITY_ALERT_START_HOUR = 0;                                                    // 久坐提醒开始时
    int DEFAULT_INACTIVITY_ALERT_START_MIN = 0;                                                     // 久坐提醒开始分
    int DEFAULT_INACTIVITY_ALERT_END_HOUR = 23;                                                     // 久坐提醒结束时
    int DEFAULT_INACTIVITY_ALERT_END_MIN = 0;                                                       // 久坐提醒结束分
    int DEFAULT_INACTIVITY_ALERT_CYCLE = 0;                                                         // 久坐提醒周期

    boolean DEFAULT_AUTO_LOGIN = false;                                                             // 默认不自动登录
    boolean DEFAULT_THIRD_PARTY_LOGIN = false;                                                      // 默认不是第三方登录
    boolean DEFAULT_HEART_RATE_FUNCTION = false;                                                    // 默认不支持心率功能
}
