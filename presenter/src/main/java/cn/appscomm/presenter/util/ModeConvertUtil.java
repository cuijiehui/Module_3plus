package cn.appscomm.presenter.util;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import cn.appscomm.bluetooth.mode.HeartRateBT;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.mode.SleepBT;
import cn.appscomm.bluetooth.mode.SportBT;
import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SportCacheDB;
import cn.appscomm.db.mode.SportDB;
import cn.appscomm.db.util.DBUtil;
import cn.appscomm.server.mode.sport.HeartRateSER;
import cn.appscomm.server.mode.sport.SleepDetailSER;
import cn.appscomm.server.mode.sport.SleepSER;
import cn.appscomm.server.mode.sport.SportSER;

/**
 * 作者：hsh
 * 日期：2017/3/30
 * 说明：mode转换工具类
 * a、该类主要处理各种mode间的转换
 * b、主要的mode有：网络SER、数据库DB、蓝牙BT
 * c、蓝牙->数据库(同步) 数据库->网络(上传) 网络->数据库(下载)
 */

public class ModeConvertUtil {

    private static final int SLEEP_TYPE_BEGIN = 16;
    private static final int SLEEP_TYPE_DEEP = 0;
    private static final int SLEEP_TYPE_LIGHT = 1;
    private static final int SLEEP_TYPE_AWAKE = 2;
    private static final int SLEEP_TYPE_READY_SLEEP = 3;
    private static final int SLEEP_TYPE_EXIT_SLEEP = 4;
    private static final int SLEEP_TYPE_END = 17;

    /*---------------------------------------------------------------------运动-----------------------------------------------------------------------*/

    /**
     * 蓝牙运动数据转为数据库运动缓存数据(常用于同步)
     *
     * @param sportBTList 蓝牙运动数据
     * @return 数据库运动缓存数据
     */
    public static List<SportCacheDB> sportBTToSportCacheDBList(List<SportBT> sportBTList) {
        List<SportCacheDB> sportCacheDBs = null;
        if (sportBTList != null && sportBTList.size() > 0) {
            sportCacheDBs = new LinkedList<>();
            for (SportBT sportBT : sportBTList) {
                sportCacheDBs.add(new SportCacheDB(sportBT.step, sportBT.calories, sportBT.distance, sportBT.sportTime, sportBT.timeStamp));
            }
        }
        return sportCacheDBs;
    }

    /**
     * 数据库运动缓存数据转为网络运动数据(常用于上传)
     *
     * @param sportCacheDBList 数据库运动缓存数据
     * @param sportSERList     网络运动数据
     * @return seq
     */
    public static String sportCacheDBToSportSERList(List<SportCacheDB> sportCacheDBList, List<SportSER> sportSERList) {
        String requestSeq = "";
        if (sportCacheDBList != null && sportCacheDBList.size() > 0) {
            SimpleDateFormat sportDataSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (SportCacheDB sportCacheDB : sportCacheDBList) {
                requestSeq += sportCacheDB.getId() + ",";
                String startTime = sportDataSDF.format(sportCacheDB.getTimeStamp() * 1000L);
                sportSERList.add(new SportSER(sportCacheDB.getStep(), sportCacheDB.getCalories(), sportCacheDB.getDistance(), sportCacheDB.getSportTime(), 0, startTime, startTime));
            }
        }
        return requestSeq;
    }

    /**
     * 网络运动数据转为数据库运动数据(常用于下载)
     *
     * @param sportSERList 网络运动数据
     * @return 数据库运动数据
     */
    public static List<SportDB> sportSERToSportDBList(List<SportSER> sportSERList) {
        if (sportSERList == null || sportSERList.size() == 0) {
            return null;
        }
        Map<String, SportDB> sportDBMap = new HashMap<>();
        for (SportSER sportSER : sportSERList) {
            String date = sportSER.startTime.split(" ")[0];
            String time = sportSER.startTime.split(" ")[1];
            int hour = Integer.parseInt(time.split(":")[0]);
            SportDB tempSportDB = sportDBMap.get(date);
            if (!sportDBMap.containsKey(date)) {
                tempSportDB = new SportDB();
                sportDBMap.put(date, tempSportDB);
            }
            tempSportDB.setDate(date);
            tempSportDB.setStep(tempSportDB.getStep() + sportSER.sportStep);
            tempSportDB.setCalories(tempSportDB.getCalories() + sportSER.sportCalorie);
            tempSportDB.setDistance(tempSportDB.getDistance() + sportSER.sportDistance);
            tempSportDB.setSportTime(tempSportDB.getSportTime() + sportSER.sportDuration);
            tempSportDB.setStepDetail(proSportDetail(tempSportDB.getStepDetail(), hour, sportSER.sportStep));
            tempSportDB.setCaloriesDetail(proSportDetail(tempSportDB.getCaloriesDetail(), hour, sportSER.sportCalorie));
            tempSportDB.setDistanceDetail(proSportDetail(tempSportDB.getDistanceDetail(), hour, sportSER.sportDistance));
            tempSportDB.setSportTimeDetail(proSportDetail(tempSportDB.getSportTimeDetail(), hour, sportSER.sportDuration));
        }
        return new ArrayList<>(sportDBMap.values());
    }

    /*---------------------------------------------------------------------睡眠-----------------------------------------------------------------------*/

    /**
     * 蓝牙睡眠数据转为数据库睡眠数据(常用于同步)
     *
     * @param sleepBTList 蓝牙睡眠数据
     * @return 数据库睡眠数据
     */
    public static List<SleepDB> sleepBTToSleepDBList(List<SleepBT> sleepBTList) {
        sleepBTList = proSleepDataList(sleepBTList);
        return sleepConvert(sleepBTList);
    }

    /**
     * 数据库睡眠数据转为网络睡眠数据(常用于上传)
     *
     * @param deviceId     watchId
     * @param deviceType   设备类型
     * @param sleepDBList  数据库睡眠数据
     * @param sleepSERList 网络睡眠数据
     * @return seq
     */
    public static String sleepDBToSleepSERList(String deviceId, String deviceType, List<SleepDB> sleepDBList, List<SleepSER> sleepSERList) {
        String requestSeq = "";
        if (sleepDBList != null && sleepDBList.size() > 0) {
            for (SleepDB sleepDB : sleepDBList) {                                                       // SleepDB -> SleepSER
                requestSeq += sleepDB.getId() + ",";
                SleepSER sleepSER = new SleepSER(deviceId, deviceType, sleepDB.getTotal(), sleepDB.getDeep(), sleepDB.getLight(), sleepDB.getAwake(), sleepDB.getSleep(), sleepDB.getAwakeTime(), 8, 0);
                List<SleepDetailSER> sleepDetailSERList = new LinkedList<>();
                String startTime = "";
                String endTime = "";
                for (String sleepTimeSleepType : sleepDB.getDetail().split(",")) {
                    String sleepTime = sleepTimeSleepType.split("&")[0];
                    int sleepType = ModeConvertUtil.sleepTypeStringToInt(sleepTimeSleepType.split("&")[1]);
                    sleepDetailSERList.add(new SleepDetailSER(sleepTime, sleepType));
                    startTime = sleepTimeSleepType.contains("BEGIN") ? sleepTime : startTime;
                    endTime = sleepTimeSleepType.contains("END") ? sleepTime : endTime;
                }
                sleepSER.startTime = startTime;
                sleepSER.endTime = endTime;
                sleepSER.details = sleepDetailSERList;
                sleepSERList.add(sleepSER);
            }
        }
        return requestSeq;
    }

    /**
     * 网络睡眠数据转为数据库睡眠数据(常用于下载)
     *
     * @param sleepSERList 网络睡眠数据
     * @return 数据库睡眠数据
     */
    public static List<SleepDB> sleepSERToSleepDBList(List<SleepSER> sleepSERList) {
        if (sleepSERList == null || sleepSERList.size() == 0) {
            return null;
        }
        List<SleepDB> sleepDBList = new LinkedList<>();
        for (SleepSER sleepSER : sleepSERList) {
            SleepDB sleepDB = new SleepDB();
            sleepDB.setTotal(sleepSER.totalDuration);
            sleepDB.setDeep(sleepSER.deepDuration);
            sleepDB.setLight(sleepSER.lightDuration);
            sleepDB.setAwake(sleepSER.awakeDuration);
            sleepDB.setSleep(sleepSER.sleepDuration);
            sleepDB.setAwakeTime(sleepSER.awakeCount);
            sleepDB.setDetail(sleepDetailSERListToStringDetail(sleepSER.details));
            sleepDB.setDate(sleepSER.endTime.split(" ")[0]);
            sleepDB.setFlag(1);
            sleepDBList.add(sleepDB);
        }
        return sleepDBList;
    }

    /*---------------------------------------------------------------------心率-----------------------------------------------------------------------*/

    /**
     * 蓝牙心率数据转为Map<日期,未上传详情>(常用于同步)
     *
     * @param heartRateDataList 蓝牙心率数据
     * @return Map
     */
    public static Map<String, String> heartRateBTListToMap(LinkedList<HeartRateBT> heartRateDataList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> maps = new HashMap<>();
        for (HeartRateBT heartRateBT : heartRateDataList) {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String date = sdf.format(heartRateBT.timeStamp * 1000L);
            String min = timeStampToMin(heartRateBT.timeStamp * 1000L) + "";
            String submit = maps.get(date);
            if (!TextUtils.isEmpty(submit) && submit.contains(min + "&")) {
                for (String str : submit.split(",")) {
                    if (str.contains(min + "&")) {
                        int oldAvg = Integer.parseInt(str.split("&")[1]);
                        int newAvg = (oldAvg + heartRateBT.avg) / 2;
                        submit = submit.replace(min + "&" + oldAvg, min + "&" + newAvg);
                        break;
                    }
                }
            } else {
                submit = TextUtils.isEmpty(submit) ? "" : submit;
                submit += min + "&" + heartRateBT.avg + ",";
            }
            maps.put(date, submit);
        }
        return maps;
    }

    /**
     * 数据库心率数据转为网络心率数据(常用于上传)
     *
     * @param heartRateDBList  数据库心率数据
     * @param heartRateSERList 网络心率数据
     * @return seq
     */
    public static String heartRateDBToHeartRateSERList(List<HeartRateDB> heartRateDBList, List<HeartRateSER> heartRateSERList) {
        String requestSeq = "";
        if (heartRateDBList != null && heartRateDBList.size() > 0) {
            for (HeartRateDB heartRateDB : heartRateDBList) {
                requestSeq += heartRateDB.getId() + ",";
                String submit = heartRateDB.getSubmit();
                for (String minAvg : submit.split(",")) {
                    String dateTime = heartRateDB.getDate() + " " + minToTime(Integer.parseInt(minAvg.split("&")[0]));
                    int avg = Integer.parseInt(minAvg.split("&")[1]);
                    heartRateSERList.add(new HeartRateSER(avg, dateTime, dateTime));
                }
            }
        }
        return requestSeq;
    }

    /**
     * 数据库心率数据转为蓝牙心率数据(常用于心率日详情显示)
     *
     * @param heartRateDBList 数据库心率数据
     * @return 蓝牙心率数据
     */
    public static List<HeartRateBT> heartRateDBToHeartRateBTList(List<HeartRateDB> heartRateDBList) {
        List<HeartRateBT> heartRateBTList = null;
        if (heartRateDBList != null && heartRateDBList.size() > 0) {
            heartRateBTList = new ArrayList<>();
            for (HeartRateDB heartRateDB : heartRateDBList) {
                String detail = heartRateDB.getDetail();
                String submit = heartRateDB.getSubmit();
                String minAvgCombine = (!TextUtils.isEmpty(detail) ? detail : "") + "," + (!TextUtils.isEmpty(submit) ? submit : "");
                for (String minAvg : minAvgCombine.split(",")) {
                    if (!TextUtils.isEmpty(minAvg)) {
                        int min = Integer.parseInt(minAvg.split("&")[0]);
                        int avg = Integer.parseInt(minAvg.split("&")[1]);
                        long timeStamp = dateMinToTimeStamp(heartRateDB.getDate(), min);
                        heartRateBTList.add(new HeartRateBT(0, avg, timeStamp));
                    }
                }
            }
        }
        return heartRateBTList;
    }

    /**
     * 网络心率详情转为数据库心率详情(常用于下载)
     *
     * @param heartRateSERList 网络心率详情
     * @return 数据库心率详情
     */
    public static List<HeartRateDB> heartRateSERToHeartRateDBList(List<HeartRateSER> heartRateSERList) {
        if (heartRateSERList == null || heartRateSERList.size() == 0) {
            return null;
        }
        List<HeartRateDB> heartRateDBList = null;
        Collections.sort(heartRateSERList, heartRateSEREndTimeComparator);
        Map<String, String> dateDetailMap = new HashMap<>();
        for (HeartRateSER heartRateSER : heartRateSERList) {
            String date = heartRateSER.endTime.split(" ")[0];
            String minAvg = timeToMin(heartRateSER.endTime.split(" ")[1]) + "&" + heartRateSER.heartAvg + ",";
            dateDetailMap.put(date, dateDetailMap.containsKey(date) ? dateDetailMap.get(date) + minAvg : minAvg);
        }
        if (dateDetailMap.size() > 0) {
            heartRateDBList = new LinkedList<>();
            for (Map.Entry<String, String> dateDetail : dateDetailMap.entrySet()) {
                HeartRateDB heartRateDB = new HeartRateDB();
                heartRateDB.setDate(dateDetail.getKey());
                heartRateDB.setDetail(dateDetail.getValue());
                heartRateDB.setAvg(DBUtil.countHeartRateAvg(dateDetail.getValue()));
                heartRateDBList.add(heartRateDB);
            }
        }
        return heartRateDBList;
    }

    /*------------------------------------------------------------------本类私有方法-------------------------------------------------------------------*/

    // 网络睡眠详情转为字符串详情
    private static String sleepDetailSERListToStringDetail(List<SleepDetailSER> sleepDetailSERList) {
        String detail = "";
        if (sleepDetailSERList != null && sleepDetailSERList.size() > 0) {
            Collections.sort(sleepDetailSERList, sleepDetailSERStartTimeComparator);
            for (SleepDetailSER sleepDetailSER : sleepDetailSERList) {
                String startTime = sleepDetailSER.startTime;
                String sleepType = sleepTypeIntToString(sleepDetailSER.status);
                detail += startTime + "&" + sleepType + ",";
            }
        }
        return detail;
    }

    // 睡眠类型转换(整形转字符串 如 16 -> BEGIN)
    private static String sleepTypeIntToString(int iSleepType) {
        switch (iSleepType) {
            case SLEEP_TYPE_BEGIN:
                return "BEGIN";
            case SLEEP_TYPE_DEEP:
                return "DEEP";
            case SLEEP_TYPE_LIGHT:
                return "LIGHT";
            case SLEEP_TYPE_AWAKE:
                return "AWAKE";
            default:
                return "END";
        }
    }

    // 睡眠类型转换(字符串转整形 如 BEGIN -> 16)
    private static int sleepTypeStringToInt(String sSleepType) {
        switch (sSleepType) {
            case "BEGIN":
                return SLEEP_TYPE_BEGIN;
            case "DEEP":
                return SLEEP_TYPE_DEEP;
            case "LIGHT":
                return SLEEP_TYPE_LIGHT;
            case "AWAKE":
                return SLEEP_TYPE_AWAKE;
            default:
                return SLEEP_TYPE_END;
        }
    }

    // 东8区的时间戳转换为时间+时区,该时区是手环的时区,常用于上传数据
    private static String eightTZTimeStampToStringTZ(long timestamp, boolean isAddTZ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf_z = new SimpleDateFormat("ZZ");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        int len = (timestamp + "").length();
        timestamp = len > 10 ? timestamp : (timestamp * 1000L);
        String tz = sdf_z.format(timestamp);
        return sdf.format(timestamp) + (isAddTZ ? tz : "");
    }

    // 计算清醒次数(str 类似:2016-08-23T22:48:52&BEGIN,2016-08-23T22:51:52&LIGHT,2016-08-23T23:21:52&DEEP,2016-08-24T00:18:46&DEEP,2016-08-24T00:18:46&END,)
    private static int countAwakeTime(String str) {
        List<String> sleepDataList = Arrays.asList(str.split(","));
        Collections.sort(sleepDataList);
        boolean flag = false;
        int count = 0;
        for (String sleepData : sleepDataList) {
            if (sleepData.contains("LIGHT") || sleepData.contains("DEEP")) {
                flag = true;
            }
            if (sleepData.contains("AWAKE") || sleepData.contains("END")) {
                if (flag) {
                    flag = false;
                    count++;
                }
            }
        }
        return count;
    }

    // 整理蓝牙睡眠数据
    private static List<SleepBT> proSleepDataList(List<SleepBT> sleepBTList) {
        Collections.sort(sleepBTList, new Comparator<SleepBT>() {
            @Override
            public int compare(SleepBT lhs, SleepBT rhs) {
                return (int) (lhs.timeStamp - rhs.timeStamp);
            }
        });
        long lastTimeStamp = 0L;
        int lastType = -1;
        boolean startFlag = false;
        LogUtil.i("sleepDB_test", "转换前：" + Arrays.toString(sleepBTList.toArray()));
        List<SleepBT> lastSleepBTList = new ArrayList<>();
        for (SleepBT sd : sleepBTList) {
            lastTimeStamp = lastTimeStamp == 0L ? sd.timeStamp : lastTimeStamp;
            if (sd.type == SLEEP_TYPE_BEGIN) {
                if (startFlag) {
                    lastSleepBTList.add(new SleepBT(17, lastTimeStamp));
                }
                startFlag = true;
            } else if (sd.type == SLEEP_TYPE_END) {
                if (!startFlag) {
                    lastSleepBTList.add(new SleepBT(16, lastTimeStamp));
                }
                startFlag = false;
            } else {
                if (!startFlag) {
                    lastSleepBTList.add(new SleepBT(16, sd.timeStamp));
                    startFlag = true;
                }
            }
            lastSleepBTList.add(sd);
            lastTimeStamp = sd.timeStamp;
            lastType = sd.type;
        }
        if (lastType != -1 && lastType != 17 && lastType != 18) {
            lastSleepBTList.add(new SleepBT(17, lastTimeStamp));
        }
        LogUtil.i("sleepDB_test", "转换后：" + Arrays.toString(lastSleepBTList.toArray()));

        return lastSleepBTList;
    }

    // 睡眠原始数据转为数据库存储数据
    private static List<SleepDB> sleepConvert(List<SleepBT> sleepBTList) {
        List<SleepDB> sleepDBList = new ArrayList<>();
        SleepDB sleepData = null;
        String MIDDLE_FLAG = "&";
        String END_FLAG = ",";
        String lastType = "";
        long lastTimeStamp = 0L;
        String str = "";
        SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd");

        for (SleepBT curSleepData : sleepBTList) {
            String time = eightTZTimeStampToStringTZ(curSleepData.timeStamp, false);
            LogUtil.i("sleepDB_test", "转换的睡眠时间 : " + time);
            switch (lastType) {
                case "BEGIN":
                    lastTimeStamp = curSleepData.timeStamp;
                    break;
                case "AWAKE":
                    sleepData.setAwake(sleepData.getAwake() + (int) (curSleepData.timeStamp - lastTimeStamp) / 60);
                    lastTimeStamp = curSleepData.timeStamp;
                    break;
                case "DEEP":
                    sleepData.setDeep(sleepData.getDeep() + (int) (curSleepData.timeStamp - lastTimeStamp) / 60);
                    lastTimeStamp = curSleepData.timeStamp;
                    break;
                case "LIGHT":
                    sleepData.setLight(sleepData.getLight() + (int) (curSleepData.timeStamp - lastTimeStamp) / 60);
                    lastTimeStamp = curSleepData.timeStamp;
                    break;
                case "END":
                    lastType = "";
                    lastTimeStamp = 0L;
                    break;
            }
            switch (curSleepData.type) {
                case SLEEP_TYPE_BEGIN:
                    sleepData = new SleepDB();
                    str = time + MIDDLE_FLAG + "BEGIN" + END_FLAG;
                    lastType = "BEGIN";
                    LogUtil.i("sleepDB_test", "str : " + str);
                    break;
                case SLEEP_TYPE_END:
                    str += time + MIDDLE_FLAG + "END" + END_FLAG;
                    LogUtil.i("sleepDB_test", "str : " + str);
                    int len = (curSleepData.timeStamp + "").length();
                    sleepData.setAwakeTime(countAwakeTime(str));
                    sleepData.setTotal(sleepData.getAwake() + sleepData.getLight() + sleepData.getDeep());
                    sleepData.setDate(sdf_ymd.format(len > 10 ? curSleepData.timeStamp : (curSleepData.timeStamp * 1000L)));
                    sleepData.setFlag(-1);
                    sleepData.setDetail(str);
                    LogUtil.i("sleepDB_test", "sleepData : " + sleepData.toString());
                    if (sleepData.getTotal() > 0) {
                        sleepDBList.add(sleepData);
                    }
                    lastType = "END";
                    break;
                case SLEEP_TYPE_DEEP:
                    str += time + MIDDLE_FLAG + "DEEP" + END_FLAG;
                    lastType = "DEEP";
                    LogUtil.i("sleepDB_test", "str : " + str);
                    break;
                case SLEEP_TYPE_LIGHT:
                    str += time + MIDDLE_FLAG + "LIGHT" + END_FLAG;
                    lastType = "LIGHT";
                    LogUtil.i("sleepDB_test", "str : " + str);
                    break;
                case SLEEP_TYPE_AWAKE:
                case SLEEP_TYPE_READY_SLEEP:
                case SLEEP_TYPE_EXIT_SLEEP:
                    str += time + MIDDLE_FLAG + "AWAKE" + END_FLAG;
                    lastType = "AWAKE";
                    LogUtil.i("sleepDB_test", "str : " + str);
                    break;
            }
        }
        return sleepDBList;
    }

    // 整理运动详情
    private static String proSportDetail(String detail, int hour, int value) {
        String[] details = detail.split(",");
        details[hour] = (Integer.parseInt(details[hour]) + value) + "";
        detail = Arrays.toString(details);
        return detail.replace(" ", "").replace("[", "").replace("]", "");
    }

    // 时间转为总分钟
    private static int timeToMin(String time) {
        String[] times = time.split(":");
        return (Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1])) / 5;
    }

    // 时间戳转为总分钟
    private static int timeStampToMin(long timeStamp) {
        SimpleDateFormat sdf_hm = new SimpleDateFormat("HH:mm");
        sdf_hm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String hm = sdf_hm.format(timeStamp);
        int hour = Integer.parseInt(hm.split(":")[0]);
        int min = Integer.parseInt(hm.split(":")[1]);
        return (hour * 60 + min) / 5;
    }

    // 心率的日期和分钟转换为时间戳
    private static long dateMinToTimeStamp(String date, int min) {
        String[] dates = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]), 0, 0, 0);
        return (calendar.getTimeInMillis() + min * 5 * 60 * 1000L) / 1000L;
    }

    // 开始时间排序器(根据网络睡眠详情的开始时间排序)
    private static Comparator sleepDetailSERStartTimeComparator = new Comparator<SleepDetailSER>() {
        @Override
        public int compare(SleepDetailSER lhs, SleepDetailSER rhs) {
            return lhs.startTime.compareTo(rhs.startTime);
        }
    };

    // 心率结束时间排序器(根据网络心率详情的结束时间排序)
    private static Comparator heartRateSEREndTimeComparator = new Comparator<HeartRateSER>() {
        @Override
        public int compare(HeartRateSER lhs, HeartRateSER rhs) {
            return lhs.endTime.compareTo(rhs.endTime);
        }
    };

    // 分钟转时间
    private static String minToTime(int minute) {
        int totalMinute = minute * 5;
        int hour = totalMinute / 60;
        int min = totalMinute % 60;
        return (hour > 9 ? hour + "" : "0" + hour) + ":" + (min > 9 ? min + "" : "0" + min) + ":00";
    }



}
