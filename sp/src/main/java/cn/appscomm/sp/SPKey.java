package cn.appscomm.sp;

/**
 * Created by Administrator on 2016/7/27.
 */
public interface SPKey {

    String SHARE_PREFERENCE_FILE_NAME = "config";                                                   // sharePreference的文件名

    // 目标
    String SP_GOAL_STEP = "goal_step";                                                              // 步数目标
    String SP_GOAL_DISTANCE = "goal_distance";                                                      // 距离目标
    String SP_GOAL_SPORT_TIME = "goal_sport_time";                                                  // 运动时长目标
    String SP_GOAL_CALORIES = "goal_calories";                                                      // 卡路里目标
    String SP_GOAL_SLEEP = "goal_sleep";                                                            // 睡眠目标

    // 个人设置
    String SP_ACCOUNT_ID = "account_id";                                                            // account_id
    String SP_USER_INFO_ID = "user_info_id";                                                        // userInfoId
    String SP_USER_ID = "user_id";                                                                  // userId
    String SP_NICK_NAME = "nick_name";                                                              // 别名
    String SP_IMG_PATH = "img_path";                                                                // 头像服务器路径
    String SP_NAME = "name";                                                                        // 姓名
    String SP_EMAIL = "email";                                                                      // 邮件
    String SP_PASSWORD = "password";                                                                // 密码
    String SP_GENDER = "gender";                                                                    // 性别(0:男 1:女)
    String SP_BIRTHDAY_YEAR = "birthday_year";                                                      // 生日年
    String SP_BIRTHDAY_MONTH = "birthday_month";                                                    // 生日月
    String SP_BIRTHDAY_DAY = "birthday_day";                                                        // 生日日
    String SP_HEIGHT = "height";                                                                    // 身高(保存为公制 单位是0.1cm 例如170cm 保存为1700)
    String SP_WEIGHT = "weight";                                                                    // 体重(保存为公制 单位是0.1kg 例如69.8kg 保存为698)
    String SP_UNIT = "unit";                                                                        // 单位(0:英制 1:公制)
    String SP_COUNTRY = "country";                                                                  // 国家
    String SP_LANGUAGE = "language";                                                                // 语言

    // 设备界面
    String SP_WATCH_FACE_INDEX = "watch_face_index";                                                // 设备界面 索引
    String SP_DATE_FORMAT = "date_format";                                                          // 设备界面 日期格式
    String SP_TIME_FORMAT = "time_format";                                                          // 设备界面 时间格式
    String SP_BATTERY_SHOW = "battery_show";                                                        // 设备界面 电池格式
    String SP_LUNAR_FORMAT = "lunar_format";                                                        // 设备界面 日历格式
    String SP_SCREEN_FORMAT = "screen_format";                                                      // 设备界面 屏幕格式
    String SP_BACKGROUND_STYLE = "background_style";                                                // 设备界面 背景风格
    String SP_SPORT_DATA_SHOW = "sport_data_show";                                                  // 设备界面 运动数据格式
    String SP_USERNAME_FORMAT = "username_format";                                                  // 设备界面 用户名格式

    // 高级设置
    String SP_AUTO_SYNC_SWITCH = "auto_sync_switch";                                                // 自动同步开关
    String SP_PRESET_SLEEP_SWITCH = "preset_sleep_switch";                                          // 预设睡眠开关
    String SP_BED_TIME_HOUR = "bed_time_hour";                                                      // 睡眠时间 时
    String SP_BED_TIME_MIN = "bed_time_min";                                                        // 睡眠时间 分
    String SP_AWAKE_TIME_HOUR = "awake_time_hour";                                                  // 清醒时间 时
    String SP_AWAKE_TIME_MIN = "awake_time_min";                                                    // 清醒时间 分
    String SP_CALORIES_TYPE = "calories_type";                                                      // 卡路里类型
    String SP_RAISE_WAKE_SWITCH = "raise_wake_switch";                                              // 抬手亮屏开关

    // 高级设置 久坐提醒
    String SP_INACTIVITY_ALERT_SWITCH = "inactivity_alert_switch";                                  // 久坐提醒开关
    String SP_INACTIVITY_ALERT_INTERVAL = "inactivity_alert_interval";                              // 久坐提醒间隔
    String SP_INACTIVITY_ALERT_START_HOUR = "inactivity_alert_start_hour";                          // 久坐提醒开始 时
    String SP_INACTIVITY_ALERT_START_MIN = "inactivity_alert_start_min";                            // 久坐提醒开始 分
    String SP_INACTIVITY_ALERT_END_HOUR = "inactivity_alert_end_hour";                              // 久坐提醒结束 时
    String SP_INACTIVITY_ALERT_END_MIN = "inactivity_alert_end_min";                                // 久坐提醒结束 分
    String SP_INACTIVITY_ALERT_CYCLE = "inactivity_alert_cycle";                                    // 久坐提醒周期

    // 通知
    String SP_CALL_SWITCH = "call_switch";                                                          // 电话推送
    String SP_MISCALL_SWITCH = "miscall_switch";                                                    // 未接来电推送
    String SP_SMS_SWITCH = "sms_switch";                                                            // 短信推送
    String SP_EMAIL_SWITCH = "email_switch";                                                        // 邮件推送
    String SP_SOCIAL_SWITCH = "social_switch";                                                      // 社交推送
    String SP_CALENDAR_SWITCH = "calendar_switch";                                                  // 日历推送
    String SP_ANTI_SWITCH = "anti_switch";                                                          // 防丢失推送

    // 通知震动
    String SP_CALL_SHOCK = "call_shock";                                                            // 电话震动
    String SP_MISS_CALL_SHOCK = "miscall_shock";                                                    // 未接来电震动
    String SP_SMS_SHOCK = "sms_shock";                                                              // 短信震动
    String SP_EMAIL_SHOCK = "email_shock";                                                          // 邮件震动
    String SP_SOCIAL_SHOCK = "social_shock";                                                        // 社交震动
    String SP_CALENDAR_SHOCK = "calendar_shock";                                                    // 日历震动

    // 心率
    String SP_HEART_RATE_AUTO_TRACK_SWITCH = "heart_rate_auto_track_switch";                        // 自动心率
    String SP_HEART_RATE_FREQUENCY = "heart_rate_frequency";                                        // 心率频次
    String SP_HEART_RATE_RANGE_ALERT_SWITCH = "heart_rate_range_alert_switch";                      // 心率报警
    String SP_HEART_RATE_MIN = "heart_rate_min";                                                    // 心率最小值
    String SP_HEART_RATE_MAX = "heart_rate_max";                                                    // 心率最大值

    // 绑定
    String SP_WATCH_ID = "watch_ID";                                                                // watchID
    String SP_MAC = "mac";                                                                          // mac地址
    String SP_DEVICE_NAME = "device_name";                                                          // 设备名称
    String SP_DEVICE_TYPE = "device_type";                                                          // 设备类型
    String SP_DEVICE_VERSION = "device_version";                                                    // 设备版本
    String SP_PRODUCT_CODE = "product_code";                                                        // 产品代号

    // 目标达成标志
    String SP_GOAL_ACHIEVEMENT_STEP = "goal_achievement_step";                                      // 步数目标达成
    String SP_GOAL_ACHIEVEMENT_DISTANCE = "goal_achievement_distance";                              // 距离目标达成
    String SP_GOAL_ACHIEVEMENT_CALORIES = "goal_achievement_calories";                              // 卡路里目标达成
    String SP_GOAL_ACHIEVEMENT_SLEEP = "goal_achievement_sleep";                                    // 睡眠目标达成

    String SP_SPORT_SLEEP_MODE = "sport_sleep_mode";                                                // 运动睡眠模式
    String SP_ULTRAVIOLET = "ultraviolet";                                                          // 紫外线
    String SP_LAST_SYNC_TIME = "last_sync_time";                                                    // 上一次同步时间
    String SP_SPORT_DOWNLOAD_DATE = "sport_download_date";                                          // 下载运动数据的时间段
    String SP_SLEEP_DOWNLOAD_DATE = "sleep_download_date";                                          // 下载睡眠数据的时间段
    String SP_HEART_RATE_DOWNLOAD_DATE = "heart_rate_download_date";                                // 下载心率数据的时间段

    String SP_TOKEN = "token";                                                                      // 普通token
    String SP_IS_NEED_SUBMIT_BIND = "is_need_submit_bind";                                          // 是否需要提交绑定
    String SP_IS_NEED_SUBMIT_UNBIND = "is_need_submit_unbind";                                      // 是否需要提交解绑

    String SP_AUTO_LOGIN = "auto_login";                                                            // 是否自动登录
    String SP_THIRD_PARTY_LOGIN = "third_party_login";                                              // 是否第三方登录
    String SP_HEART_RATE_FUNCTION = "heart_rate_function";                                          // 是否支持心率功能
    String SP_NET_FIRMWARE_VERSION = "net_firmware_version";                                        // 服务器上固件的版本
    String SP_NET_FIRMWARE_URL = "net_firmware_url";                                                // 服务器上固件的url
}
