package cn.appscomm.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Calendar;

public enum SPManager {
    INSTANCE;

    public static final int DATA_STRING = 1;                                                        // string
    public static final int DATA_INT = 2;                                                           // int
    public static final int DATA_LONG = 3;                                                          // long
    public static final int DATA_BOOLEAN = 4;                                                       // boolean
    public static final int DATA_FLOAT = 5;                                                         // float

    /**
     * 写配置文件
     *
     * @param configKey   键
     * @param configValue 值
     * @return
     */
    public boolean setSPValue(String configKey, Object configValue) {
        if (SpAppContext.INSTANCE.getContext() == null || configValue == null) {
            return false;
        }
        SharedPreferences preferences = SpAppContext.INSTANCE.getContext().getSharedPreferences(SPKey.SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        if (configValue.getClass() == String.class) {                                               // String类型
            String str = (String) configValue;
            editor.putString(configKey, str);
        } else if (configValue.getClass() == Integer.class) {                                       // Integer类型
            Integer i = (Integer) configValue;
            editor.putInt(configKey, i);
        } else if (configValue.getClass() == Boolean.class) {                                       // Boolean类型
            Boolean b = (Boolean) configValue;
            editor.putBoolean(configKey, b);
        } else if (configValue.getClass() == Long.class) {                                          // Long类型
            Long l = (Long) configValue;
            editor.putLong(configKey, l);
        } else if (configValue.getClass() == Float.class) {                                         // Float类型
            Float f = (Float) configValue;
            editor.putFloat(configKey, f);
        } else {
            return false;
        }
        editor.commit();
        return true;
    }

    /**
     * 通过键删除相应的配置
     *
     * @param configKey 键
     * @return true : 成功 ; false : 失败
     */
    public boolean delSPValue(String configKey) {
        SharedPreferences preferences = SpAppContext.INSTANCE.getContext().getSharedPreferences(SPKey.SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.remove(configKey);
        return editor.commit();
    }

    /**
     * 删除配置文件
     *
     * @return true : 成功 ; false : 失败
     */
    public boolean delSPFile() {
        SharedPreferences preferences = SpAppContext.INSTANCE.getContext().getSharedPreferences(SPKey.SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        return editor.commit();
    }

    /**
     * 获取配置文件中的配置
     *
     * @param configKey 键
     * @param dataType  值的类型
     * @return 值(返回null, 意味着配置表没有该key)
     */
    public Object getSPValue(String configKey, int dataType) {
        if (SpAppContext.INSTANCE.getContext() != null) {
            SharedPreferences sp = SpAppContext.INSTANCE.getContext().getSharedPreferences(SPKey.SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
            if (sp != null && sp.contains(configKey)) {
                switch (dataType) {
                    case DATA_STRING:
                        return sp.getString(configKey, "");
                    case DATA_INT:
                        return sp.getInt(configKey, -1);
                    case DATA_LONG:
                        return sp.getLong(configKey, 0L);
                    case DATA_BOOLEAN:
                        return sp.getBoolean(configKey, false);
                    case DATA_FLOAT:
                        return sp.getFloat(configKey, 0.0f);
                    default:
                        return null;
                }
            }
        }
        return null;
    }

    /**
     * 设置配置表的默认值
     */
    public void initConfigValue() {
        setDefaultValue(SPKey.SP_GOAL_STEP, DATA_INT, SPDefaultValue.DEFAULT_GOAL_STEP);                                        // 步数目标
        setDefaultValue(SPKey.SP_GOAL_DISTANCE, DATA_INT, SPDefaultValue.DEFAULT_GOAL_DISTANCE);                                // 距离目标
        setDefaultValue(SPKey.SP_GOAL_SPORT_TIME, DATA_INT, SPDefaultValue.DEFAULT_GOAL_SPORT_TIME);                            // 运动时长目标
        setDefaultValue(SPKey.SP_GOAL_CALORIES, DATA_INT, SPDefaultValue.DEFAULT_GOAL_CALORIES);                                // 卡路里目标
        setDefaultValue(SPKey.SP_GOAL_SLEEP, DATA_INT, SPDefaultValue.DEFAULT_GOAL_SLEEP);                                      // 睡眠目标


        setDefaultValue(SPKey.SP_USER_INFO_ID, DATA_INT, SPDefaultValue.DEFAULT_USER_INFO_ID);                                  // userInfoId
        setDefaultValue(SPKey.SP_USER_ID, DATA_INT, SPDefaultValue.DEFAULT_USER_ID);                                            // userId
        setDefaultValue(SPKey.SP_NICK_NAME, DATA_STRING, SPDefaultValue.DEFAULT_NICK_NAME);                                     // nickName
        setDefaultValue(SPKey.SP_NAME, DATA_STRING, SPDefaultValue.DEFAULT_NAME);                                               // 姓名
        setDefaultValue(SPKey.SP_BIRTHDAY_YEAR, DATA_INT, Calendar.getInstance().get(Calendar.YEAR));                           // 年(注册当天对应的年)
        setDefaultValue(SPKey.SP_BIRTHDAY_MONTH, DATA_INT, Calendar.getInstance().get(Calendar.MONTH) + 1);                     // 月(注册当天对应的月)
        setDefaultValue(SPKey.SP_BIRTHDAY_DAY, DATA_INT, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));                    // 日(注册当天对应的日)
        setDefaultValue(SPKey.SP_GENDER, DATA_INT, SPDefaultValue.DEFAULT_GENDER);                                              // 性别
        setDefaultValue(SPKey.SP_UNIT, DATA_INT, SPDefaultValue.DEFAULT_UNIT);                                                  // 单位
        setDefaultValue(SPKey.SP_WEIGHT, DATA_INT, SPDefaultValue.DEFAULT_WEIGHT);                                              // 体重
        setDefaultValue(SPKey.SP_HEIGHT, DATA_INT, SPDefaultValue.DEFAULT_HEIGHT);                                              // 身高
        setDefaultValue(SPKey.SP_COUNTRY, DATA_STRING, SPDefaultValue.DEFAULT_COUNTRY);                                         // 国家

        setDefaultValue(SPKey.SP_AUTO_SYNC_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_AUTO_SYNC);                             // 自动同步
        setDefaultValue(SPKey.SP_PRESET_SLEEP_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PRESET_SLEEP_SWITCH);                // 预设睡眠开关
        setDefaultValue(SPKey.SP_BED_TIME_HOUR, DATA_INT, SPDefaultValue.DEFAULT_BEDTIME_HOUR);                                 // 睡眠时间时
        setDefaultValue(SPKey.SP_BED_TIME_MIN, DATA_INT, SPDefaultValue.DEFAULT_BEDTIME_MIN);                                   // 睡眠时间分
        setDefaultValue(SPKey.SP_AWAKE_TIME_HOUR, DATA_INT, SPDefaultValue.DEFAULT_AWAKE_TIME_HOUR);                            // 清醒时间时
        setDefaultValue(SPKey.SP_AWAKE_TIME_MIN, DATA_INT, SPDefaultValue.DEFAULT_AWAKE_TIME_MIN);                              // 清醒时间分
        setDefaultValue(SPKey.SP_CALORIES_TYPE, DATA_BOOLEAN, SPDefaultValue.DEFAULT_CALORIES_TYPE);                            // 卡路里类型
        setDefaultValue(SPKey.SP_RAISE_WAKE_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_RAISE_WAKE);                           // 抬手亮屏

        setDefaultValue(SPKey.SP_CALL_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PUSH_CALL);                                  // 来电
        setDefaultValue(SPKey.SP_MISCALL_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PUSH_MISCALL);                            // 未接来电
        setDefaultValue(SPKey.SP_SMS_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PUSH_SMS);                                    // 短信
        setDefaultValue(SPKey.SP_EMAIL_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PUSH_EMAIL);                                // 邮件
        setDefaultValue(SPKey.SP_SOCIAL_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PUSH_SOCIAL);                              // 社交
        setDefaultValue(SPKey.SP_CALENDAR_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PUSH_CALENDAR);                          // 日历
        setDefaultValue(SPKey.SP_ANTI_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_PUSH_ANTI);                                  // 防丢

        setDefaultValue(SPKey.SP_CALL_SHOCK, DATA_INT, SPDefaultValue.DEFAULT_CALL_SHOCK);                                      // 来电震动
        setDefaultValue(SPKey.SP_MISS_CALL_SHOCK, DATA_INT, SPDefaultValue.DEFAULT_MISCALL_SHOCK);                              // 未接来电震动
        setDefaultValue(SPKey.SP_SMS_SHOCK, DATA_INT, SPDefaultValue.DEFAULT_SMS_SHOCK);                                        // 短信震动
        setDefaultValue(SPKey.SP_EMAIL_SHOCK, DATA_INT, SPDefaultValue.DEFAULT_EMAIL_SHOCK);                                    // 邮件震动
        setDefaultValue(SPKey.SP_SOCIAL_SHOCK, DATA_INT, SPDefaultValue.DEFAULT_SOCIAL_SHOCK);                                  // 社交震动
        setDefaultValue(SPKey.SP_CALENDAR_SHOCK, DATA_INT, SPDefaultValue.DEFAULT_CALENDAR_SHOCK);                              // 日历震动

        setDefaultValue(SPKey.SP_HEART_RATE_AUTO_TRACK_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_HEART_RATE_AUTO_TRACK);     // 自动心率
        setDefaultValue(SPKey.SP_HEART_RATE_FREQUENCY, DATA_INT, SPDefaultValue.DEFAULT_HEART_RATE_FREQUENCY);                  // 心率频次
        setDefaultValue(SPKey.SP_HEART_RATE_RANGE_ALERT_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_HEART_RATE_RANGE_ALERT);   // 心率报警
        setDefaultValue(SPKey.SP_HEART_RATE_MIN, DATA_INT, SPDefaultValue.DEFAULT_HEART_RATE_MIN);                              // 心率最小值
        setDefaultValue(SPKey.SP_HEART_RATE_MAX, DATA_INT, SPDefaultValue.DEFAULT_HEART_RATE_MAX);                              // 心率最大值

        setDefaultValue(SPKey.SP_WATCH_FACE_INDEX, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_INDEX);                          // 设备界面 索引
        setDefaultValue(SPKey.SP_DATE_FORMAT, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_DATE_FORMAT);                         // 设备界面 日期格式
        setDefaultValue(SPKey.SP_TIME_FORMAT, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_TIME_FORMAT);                         // 设备界面 时间格式
        setDefaultValue(SPKey.SP_BATTERY_SHOW, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_BATTERY_SHOW);                       // 设备界面 电池格式
        setDefaultValue(SPKey.SP_LUNAR_FORMAT, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_LUNAR_FORMAT);                       // 设备界面 农历格式
        setDefaultValue(SPKey.SP_SCREEN_FORMAT, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_SCREEN_FORMAT);                     // 设备界面 屏幕格式
        setDefaultValue(SPKey.SP_BACKGROUND_STYLE, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_BACKGROUND_STYLE);               // 设备界面 背景风格
        setDefaultValue(SPKey.SP_SPORT_DATA_SHOW, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_SPORT_DATA_SHOW);                 // 设备界面 运动数据格式
        setDefaultValue(SPKey.SP_USERNAME_FORMAT, DATA_INT, SPDefaultValue.DEFAULT_WATCH_FACE_USERNAME_FORMAT);                 // 设备界面 用户名格式

        setDefaultValue(SPKey.SP_IS_NEED_SUBMIT_BIND, DATA_BOOLEAN, false);                                                     // 把绑定服务器置为false
        setDefaultValue(SPKey.SP_IS_NEED_SUBMIT_UNBIND, DATA_BOOLEAN, false);                                                   // 把解绑服务器置为false

        setDefaultValue(SPKey.SP_INACTIVITY_ALERT_SWITCH, DATA_BOOLEAN, SPDefaultValue.DEFAULT_INACTIVITY_ALERT_SWITCH);        // 久坐提醒开关
        setDefaultValue(SPKey.SP_INACTIVITY_ALERT_INTERVAL, DATA_INT, SPDefaultValue.DEFAULT_INACTIVITY_ALERT_INTERVAL);        // 久坐提醒间隔
        setDefaultValue(SPKey.SP_INACTIVITY_ALERT_START_HOUR, DATA_INT, SPDefaultValue.DEFAULT_INACTIVITY_ALERT_START_HOUR);    // 久坐提醒开始 时
        setDefaultValue(SPKey.SP_INACTIVITY_ALERT_START_MIN, DATA_INT, SPDefaultValue.DEFAULT_INACTIVITY_ALERT_START_MIN);      // 久坐提醒开始 分
        setDefaultValue(SPKey.SP_INACTIVITY_ALERT_END_HOUR, DATA_INT, SPDefaultValue.DEFAULT_INACTIVITY_ALERT_END_HOUR);        // 久坐提醒结束 时
        setDefaultValue(SPKey.SP_INACTIVITY_ALERT_END_MIN, DATA_INT, SPDefaultValue.DEFAULT_INACTIVITY_ALERT_END_MIN);          // 久坐提醒结束 分
        setDefaultValue(SPKey.SP_INACTIVITY_ALERT_CYCLE, DATA_INT, SPDefaultValue.DEFAULT_INACTIVITY_ALERT_CYCLE);              // 久坐提醒周期

        setDefaultValue(SPKey.SP_AUTO_LOGIN, DATA_BOOLEAN, SPDefaultValue.DEFAULT_AUTO_LOGIN);                                  // 自动登录
        setDefaultValue(SPKey.SP_THIRD_PARTY_LOGIN, DATA_BOOLEAN, SPDefaultValue.DEFAULT_THIRD_PARTY_LOGIN);                    // 第三方登录
        setDefaultValue(SPKey.SP_HEART_RATE_FUNCTION, DATA_BOOLEAN, SPDefaultValue.DEFAULT_HEART_RATE_FUNCTION);                // 是否支持心率功能
    }

    // 如果没有找到key对应的value，则设置默认值
    private void setDefaultValue(String configName, int dataType, Object defaultValue) {
        if (getSPValue(configName, dataType) == null) {
            setSPValue(configName, defaultValue);
        }
    }
}
