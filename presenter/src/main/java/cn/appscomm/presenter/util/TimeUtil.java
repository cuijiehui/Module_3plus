package cn.appscomm.presenter.util;

import java.util.Calendar;

/**
 * 作者：hsh
 * 日期：2017/4/11
 * 说明：时间工具类(主要处理与时间有关的工具类)
 */

public class TimeUtil {


    /**
     * 获取指定日期所在周的第一天
     *
     * @param curCalendar      指定的日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 一周的第一天
     */
    public static String getFirstDayOfWeek(Calendar curCalendar, boolean isMondayFirstDay) {
        try {
            Calendar calendar = (Calendar) curCalendar.clone();
            int firstDay = isMondayFirstDay ? Calendar.MONDAY : Calendar.SUNDAY;
            int interval = firstDay - calendar.get(Calendar.DAY_OF_WEEK);
            if (firstDay == Calendar.MONDAY && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                interval = -6;
            }
            calendar.add(Calendar.DAY_OF_YEAR, interval);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return year + "-" + (month > 9 ? month + "" : "0" + month) + "-" + (day > 9 ? day + "" : "0" + day);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取指定日期所在周的最后一天
     *
     * @param curCalendar      指定的日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 一周的最后一天
     */
    public static String getLastDayOfWeek(Calendar curCalendar, boolean isMondayFirstDay) {
        try {
            Calendar calendar = (Calendar) curCalendar.clone();
            int firstDay = isMondayFirstDay ? Calendar.MONDAY : Calendar.SUNDAY;
            int interval = firstDay == Calendar.MONDAY ? (Calendar.SATURDAY + Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK)) % 7 : Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DAY_OF_YEAR, interval);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return year + "-" + (month > 9 ? month + "" : "0" + month) + "-" + (day > 9 ? day + "" : "0" + day);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取指定日期的周索引
     *
     * @param date             指定日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 周索引
     */
    public static int getWeekIndexByDate(String date, boolean isMondayFirstDay) {
        String[] dates = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]));
        int index = isMondayFirstDay ? calendar.get(Calendar.DAY_OF_WEEK) - 2 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return index < 0 ? 6 : index;                                                               // 若周首日为周日，则index为-1，这时需要转为6
    }
}
