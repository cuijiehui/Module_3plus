package cn.appscomm.server.constant;

/**
 * Created by Administrator on 2017/2/24.
 * <pre>
 * 注:
 *    a、get 相当于 query   : 查询
 *    b、set 相当于 upload  : 上传
 * </pre>
 */
public interface Urls {

    boolean RELEASE = true;                                                                         // true(正式服务器)   false(测试服务器)
    String HOST = RELEASE ? "http://new.fashioncomm.com/" : "http://new.fashioncomm.com/";
    String HOST1 = RELEASE ? "https://api.mykronoz.com/" : "http://new.fashioncomm.com/";

    int LANG_CHINESE = 200;                                                                         // 中文
    int LANG_ENGLISH = 201;                                                                         // 英文
    int LANG_FRENCH = 202;                                                                          // 法文
    int LANG_GERMAN = 203;                                                                          // 德文
    int LANG_ITALIAN = 204;                                                                         // 意大利文
    int LANG_JAPANESE = 205;                                                                        // 日文
    int LANG_KOREAN = 206;                                                                          // 韩文
    int LANG_SPANISH = 207;                                                                         // 西班牙文
    int DEFAULT_LANGUAGE = LANG_CHINESE;                                                            // 默认语言

    String DEFAULT_PRODUCT_CODE = "WT10A";
    String DEFAULT_DEVICE_TYPE = "LF02A";
    String DEFAULT_CLIENT_TYPE = "android";
    String DEFAULT_CUSTOMER_CODE = "appscomm";

    String TOKEN_RETRIEVE = "security/tokenRetrieve";                                               // 刷新token
    String GET_SERVER_URL = "platform/queryServerUrl";                                              // 获取服务器url
    String GET_UPGRADE = "upgradeversion/queryUpgrade";                                             // 获取更新

    String ACCOUNT_UPLOAD_ICON = "account/uploadIcon";                                              // 上传头像
    String ACCOUNT_REGISTER = "account/register";                                                   // 注册
    String ACCOUNT_LOGIN = "account/login";                                                         // 登录
    String ACCOUNT_QUERY = "account/query";                                                         // 获取用户信息
    String ACCOUNT_EDIT = "account/edit";                                                           // 编辑
    String ACCOUNT_FORGET_PASSWORD = "account/forgetPassword";                                      // 忘记密码
    String ACCOUNT_MODIFY_PASSWORD = "account/modifyPassword";                                      // 修改密码
    String ACCOUNT_GETVERICODE = "account/getVeriCode";                                             // 获取验证码

    String ACCOUNT_SAVE_UNIT = "account/saveUnit";                                                  // 保存单位到服务器
    String ACCOUNT_CHG_USER_WEIGHT = "account/addUserWeight";                                       // 修改用户体重
    String ACCOUNT_GET_USER_WEIGHT = "account/queryUserWeight";                                     // 获取用户体重
    String ACCOUNT_CONTACTS_EDIT = "contacts/Edit";                                                 // 编辑联系人
    String ACCOUNT_GET_CONTACTS = "contacts/queryList";                                             // 获取所有联系人
    String ACCOUNT_DELETE_CONTACTS = "contacts/Delete";                                             // 删除联系人

    String DEVICE_DEVICE_PAIR = "device/pair";                                                      // 绑定
    String DEVICE_DEVICE_UNPAIR = "device/unpair";                                                  // 解绑
    String DEVICE_set_device_version = "device/uploadDeviceVersion";                                // 设置设备固件版本
    String DEVICE_GET_PAIR_DEVICE = "device/queryBindDevice";                                       // 获取绑定的设备
    String DEVICE_SET_DEVICE_CONFIG = "device/setDeviceConfig";                                     // 设置设备的配置
    String DEVICE_get_device_config = "device/queryDeviceConfig";                                   // 获取设备的配置
    String DEVICE_SET_DEVICE_REMINDER = "device/setDeviceRemind";                                   // 设置设备提醒
    String DEVICE_DELETE_DEVICE_REMINDER = "device/deleteDeviceRemind";                             // 删除设备提醒
    String DEVICE_GET_DEVICE_REMINDER = "device/queryDeviceRemind";                                 // 获取设备提醒
    String DEVICE_GET_FIRMWARE_VERSION = "device/queryProductVersion";                              // 获取固件版本

    String SPORT_GET_DATA = "sport/querySport";                                                     // 获取运动数据
    String SPORT_UPLOAD_DATA = "sport/uploadSport";                                                 // 上传运动数据
    String SPORT_GET_COUNT = "sport/countSport";                                                    // 获取运动数据总数

    String PRESSURE_GET_PRESSURE = "pressure/queryPressure";                                        // 获取压力
    String PRESSURE_SET_PREESURE = "pressure/uploadPressure";                                       // 设置压力

    String HEART_RATE_GET_DATA = "heartrate/queryHeartRate";                                        // 获取心率数据
    String HEART_RATE_UPLOAD_DATA = "heartrate/uploadHeartRate";                                    // 上传心率数据
    String HEART_RATE_GET_CONFIG = "heartrate/queryHeartRateConfig";                                // 获取心率配置
    String HEART_RATE_SET_CONFIG = "heartrate/uploadHeartRateConfig";                               // 设置心率配置

    String SLEEP_GET_DATA = "sleep/querySleep";                                                     // 获取睡眠数据
    String SLEEP_UPLOAD_DATA = "sleep/uploadSleep";                                                 // 设置睡眠数据
    String SLEEP_GET_COUNT = "sleep/countSleep";                                                    // 获取睡眠数据总数

    String LOCATE_GET_CURRENT_LOCATE = "locate/current";                                            // 获取现在定位
    String LOCATE_GET_HISTORY_LOCATE = "locate/history";                                            // 获取历史定位

    String ACCOUNT_GET_HISTORY_ALARM = "alarm/history";                                             // 获取历史报警
    String ACCOUNT_SET_STUDY_CONFIG = "/study/config";                                              // 设置学习配置
    String ACCOUNT_GET_HISTORY_STUDY_CONFIG = "study/history";                                      // 获取历史学习配置

    String ACCOUNT_GET_MOOD_DATA = "moodFatigue/queryMoodFatigue";                                  // 获取心情数据
    String ACCOUNT_SET_MOOD_DATA = "moodFatigue/uploadMoodFatigue";                                 // 设置心情数据

    String SPORT_GET_LASTTIME_SPORT = "sport/queryUploadSportTime";                                 // 获取最后设置运动数据时间
    String SLEEP_GET_LASTTIME_SLEEP = "sleep/querySleepTime";                                       // 获取最后设置睡眠数据时间
    String HEARTRATE_GET_LASTTIME_HEART_RATE = "heartrate/queryUploadHeartTime";                    // 获取最后设置心率数据时间
    String ACCOUNT_GET_LASTTIME_MOOD = "moodFatigue/queryUploadMoodTime";                           // 获取最后设置心情数据时间

    String LEADERBOARD_CREATE_DD = "leaderBoard/createDD";                                          // 创建朋友圈ID
    String LEADERBOARD_EDIT_ICON = "leaderBoard/editIcon";                                          // 更新头像地址
    String LEADERBOARD_GET_JOIN = "leaderBoard/queryJoin";                                          // 获取个人排行榜
    String LEADERBOARD_JOIN_FRIEND = "leaderBoard/joinFriend";                                      // 添加朋友
    String LEADERBOARD_HANDLER_FRIEND = "leaderBoard/handlerFriend";                                // 处理排行榜的结果
    String LEADERBOARD_GET_PENDING_FRIEND = "leaderBoard/getPendingFriend";                         // 获取排行榜的朋友
    String LEADERBOARD_GET_MAX_STEP_OF_FRIEND = "leaderBoard/getMaxStepOfFriend";                   // 获取朋友的最大步数
    String LEADERBOARD_GET = "leaderBoard/queryLeaderBoard";                                        // 获取朋友圈排行榜
    String LEADERBOARD_GET_WORD = "leaderBoard/queryLeaderBoardWord";                               // 获取世界排行榜
    String LEADERBOARD_GET_HIS = "leaderBoard/queryLeaderBoardHis";                                 // 获取排行榜历史
    String LEADERBOARD_DELETE = "leaderBoard/delete";                                               // 排行榜删除
    String LEADERBOARD_SET = "leaderBoard/uploadLeaderBoard";                                       // 设置数据到排行榜
}
