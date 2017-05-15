package cn.appscomm.bluetooth.protocol;

import java.util.LinkedList;

import cn.appscomm.bluetooth.mode.HeartRateBT;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.mode.SleepBT;
import cn.appscomm.bluetooth.mode.SportBT;

/**
 * Created by Administrator on 2016/1/28.
 */
public class BluetoothVar {
    public static String watchID;                                                                   // watchID

    public static String deviceType;                                                                // 设备类型
    public static String softVersion;                                                               // 软件版本
    public static String hardwareVersion;                                                           // 硬件版本
    public static String commProtocol;                                                              // 通信协议
    public static String functionVersion;                                                           // 功能版本
    public static String otherVersion;                                                              // 其他

    public static int sportCount;                                                                   // 运动条数
    public static int sleepCount;                                                                   // 睡眠条数
    public static int heartRateCount;                                                               // 心率条数
    public static int moodCount;                                                                    // 情绪和疲劳度条数
    public static int bloodPressureCount;                                                           // 血压条数

    public static int batteryPower;                                                                 // 电量
    public static int sportSleepMode;                                                               // 运动/睡眠模式
    public static String dateTime;                                                                  // 日期时间

    public static LinkedList<SportBT> sportBTDataList;                                              // 存储运动数据
    public static LinkedList<SleepBT> sleepBTDataList;                                              // 存储睡眠数据
    public static LinkedList<HeartRateBT> heartRateBTDataList;                                      // 存储心率数据
    public static LinkedList<Integer> indexResendCommand;                                           // 需要单独获取的索引号集合

    public static int stepGoalsValue;                                                               // 步数目标值 单位是步
    public static int stepGoalsFlag;                                                                // 步数目标标志
    public static int calorieGoalsValue;                                                            // 卡路里目标 单位是千卡
    public static int calorieGoalsFlag;                                                             // 卡路里目标标志
    public static int distanceGoalsValue;                                                           // 距离目标 单位是千米
    public static int distanceGoalsFlag;                                                            // 距离目标标志
    public static int sportTimeGoalsValue;                                                          // 运动时长目标 单位是分钟
    public static int sportTimeGoalsFlag;                                                           // 运动时长目标标志
    public static int sleepGoalsValue;                                                              // 睡眠时间目标 单位是小时
    public static int sleepGoalsFlag;                                                               // 睡眠时间目标标志

    public static int dateFormat;                                                                   // 日期格式
    public static int timeFormat;                                                                   // 时间格式
    public static int batteryShow;                                                                  // 电池显示
    public static int lunarFormat;                                                                  // 农历格式
    public static int screenFormat;                                                                 // 屏幕格式
    public static int backgroundStyle;                                                              // 背景风格
    public static int sportDataShow;                                                                // 运动数据显示
    public static int usernameFormat;                                                               // 用户名格式

    public static int screenBrightness;                                                             // 屏幕亮度

    public static int volume;                                                                       // 音量
    public static int shockStrength;                                                                // 震动强度
    public static int brightScreenTime;                                                             // 亮屏时间
    public static int[] mainBackgroundColor;                                                        // 主界面背景颜色
    public static int[] alarmBackgroundColor;                                                       // 报警界面背景颜色
    public static int workMode;                                                                     // 工作模式

    public static int language;                                                                     // 语言

    public static int unit;                                                                         // 单位

    public static int sex;                                                                          // 性别
    public static int age;                                                                          // 年龄
    public static int height;                                                                       // 身高
    public static float weight;                                                                     // 体重

    public static int usageHabits;                                                                  // 用户习惯
    public static String userName;                                                                  // 用户名

    public static int deviceDisplayStep;                                                            // 设备端显示的步数
    public static int deviceDisplayCalorie;                                                         // 设备端显示的卡路里
    public static int deviceDisplayDistance;                                                        // 设备端显示的距离
    public static int deviceDisplaySleep;                                                           // 设备端显示的睡眠
    public static int deviceDisplaySportTime;                                                       // 设备端显示的运动时长
    public static int deviceDisplayHeartRate;                                                       // 设备端显示的心率值
    public static int deviceDisplayMood;                                                            // 设备端显示的情绪疲劳度值

    public static boolean antiLostSwitch;                                                           // 防丢开关
    public static boolean autoSyncSwitch;                                                           // 自动同步开关
    public static boolean sleepSwitch;                                                              // 睡眠开关
    public static boolean sleepStateSwitch;                                                         // 自动睡眠监测开关
    public static boolean incomeCallSwitch;                                                         // 来电提醒开关
    public static boolean missCallSwitch;                                                           // 未接来电提醒开关
    public static boolean smsSwitch;                                                                // 短信提醒开关
    public static boolean socialSwitch;                                                             // 社交提醒开关
    public static boolean emailSwitch;                                                              // 邮件提醒开关
    public static boolean calendarSwitch;                                                           // 日历开关
    public static boolean sedentarySwitch;                                                          // 久坐提醒开关
    public static boolean lowPowerSwitch;                                                           // 超低功耗功能开关
    public static boolean secondRemindSwitch;                                                       // 二次提醒开关
    public static boolean raiseWakeSwitch;                                                          // 抬手亮屏开关

    public static int bedTimeHour;                                                                  // 进入睡眠时
    public static int bedTimeMin;                                                                   // 进入睡眠分
    public static int awakeTimeHour;                                                                // 退出睡眠时
    public static int awakeTimeMin;                                                                 // 退出睡眠分
    public static int remindSleepCycle;                                                             // 提醒睡眠周期

    public static int UID;                                                                          // 绑定开始UID
    public static boolean initFlag;                                                                 // 绑定结束(初始化标志)

    public static String cardNumber;                                                                // 卡号
    public static String money;                                                                     // 余额
    public static StringBuffer record;                                                              // 记录
    public static int recordCount;                                                                  // 记录条数
    public static boolean haveRecord;                                                               // 是否有记录

    public static int remindCount;                                                                  // 提醒条数
    public static LinkedList<ReminderBT> reminderBTDataList;                                        // 所有提醒数据

    public static int[] arrItems;                                                                   // 界面显示

    public static int[] shockItems;                                                                 // 震动
    public static int antiShock;                                                                    // 防丢失震动
    public static int clockShock;                                                                   // 闹钟震动
    public static int callShock;                                                                    // 来电震动
    public static int missCallShock;                                                                // 未接来电震动
    public static int smsShock;                                                                     // 短信震动
    public static int socialShock;                                                                  // 社交震动
    public static int emailShock;                                                                   // 邮件震动
    public static int calendarShock;                                                                // 日历震动
    public static int sedentaryShock;                                                               // 久坐震动
    public static int lowPowerShock;                                                                // 低点震动

    public static boolean heartRateAutoTrackSwitch;                                                 // 是否启动自动心率(自动心率大于0则打开，为0则关闭)
    public static int heartRateFrequency;                                                           // 自动心率频次
    public static int heartRateMaxValue;                                                            // 最大心率值
    public static int heartRateMinValue;                                                            // 最小心率值
    public static boolean heartRateAlarmSwitch;                                                     // 是否心率报警

    public static boolean inactivityAlertSwitch;                                                    // 久坐提醒 开关
    public static int inactivityAlertInterval;                                                      // 久坐提醒 间隔
    public static int inactivityAlertStartHour;                                                     // 久坐提醒 开始时
    public static int inactivityAlertStartMin;                                                      // 久坐提醒 开始分
    public static int inactivityAlertEndHour;                                                       // 久坐提醒 结束时
    public static int inactivityAlertEndMin;                                                        // 久坐提醒 结束分
    public static int inactivityAlertCycle;                                                         // 久坐提醒 周期

    public static int caloriesType;                                                                 // 卡路里类型
    public static int ultraviolet;                                                                  // 紫外线

    public static int realTimeHeartRateValue;                                                       // 实时心率值
}
