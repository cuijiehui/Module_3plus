package cn.appscomm.presenter.implement;

import android.util.SparseArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.db.implement.MDB;
import cn.appscomm.db.interfaces.PMDBCall;
import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SportCacheDB;
import cn.appscomm.db.mode.SportDB;
import cn.appscomm.presenter.util.TimeUtil;
import cn.appscomm.presenter.interfaces.PVDBCall;

/**
 * Created by Administrator on 2017/3/6.
 */
public enum PDB implements PVDBCall {
    INSTANCE {

    };

    private static final String TAG = PDB.class.getSimpleName();
    private PMDBCall mCall = MDB.INSTANCE;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf_h = new SimpleDateFormat("hh");

    /*---------------------------------------------------------------------运动-----------------------------------------------------------------------*/

    @Override
    public void addSport(SportDB sportDB) {
        mCall.addSport(sportDB);
    }

    @Override
    public void addSportList(List<SportDB> sportDBList) {
        mCall.addSportList(sportDBList);
    }

    @Override
    public void addSportBySportCache(List<SportCacheDB> synSportCacheDBList) {
        Map<String, SportDB> sportDBMap = new HashMap<>();
        for (SportCacheDB sportCacheDB : synSportCacheDBList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(sportCacheDB.getTimeStamp() * 1000L);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String date = year + "-" + (month > 9 ? month + "" : "0" + month) + "-" + (day > 9 ? day + "" : "0" + day);
            int step = sportCacheDB.getStep();
            int distance = sportCacheDB.getDistance();
            int calories = sportCacheDB.getCalories();
            int sportTime = sportCacheDB.getSportTime();
            SportDB tempSportDB = sportDBMap.get(date);
            if (tempSportDB == null) {
                tempSportDB = new SportDB();
                sportDBMap.put(date, tempSportDB);
            }
            tempSportDB.setStep(tempSportDB.getStep() + sportCacheDB.getStep());
            tempSportDB.setDistance(tempSportDB.getDistance() + sportCacheDB.getDistance());
            tempSportDB.setCalories(tempSportDB.getCalories() + sportCacheDB.getCalories());
            tempSportDB.setSportTime(tempSportDB.getSportTime() + sportCacheDB.getSportTime());
            tempSportDB.setStepDetail(addSportDetail(tempSportDB.getStepDetail(), hour, step));
            tempSportDB.setCaloriesDetail(addSportDetail(tempSportDB.getCaloriesDetail(), hour, calories));
            tempSportDB.setDistanceDetail(addSportDetail(tempSportDB.getDistanceDetail(), hour, distance));
            tempSportDB.setSportTimeDetail(addSportDetail(tempSportDB.getSportTimeDetail(), hour, sportTime));
            tempSportDB.setDate(date);
        }
        if (sportDBMap.size() > 0) {
            for (Map.Entry<String, SportDB> entry : sportDBMap.entrySet()) {
                String date = entry.getKey();
                SportDB addSportDB = entry.getValue();
                SportDB oldSportDB = getSport(date);
                SportDB newSportDB = combineSportDB(oldSportDB, addSportDB);
                mCall.addOrUpdateSport(date, newSportDB);
            }
        }
    }

    @Override
    public void delSport(int id) {
        mCall.delSport(id);
    }

    @Override
    public void delSports(List<Integer> ids) {
        for (Integer id : ids) {
            delSport(id);
        }
    }

    @Override
    public SportDB getSport(String date) {
        return mCall.getSport(date);
    }

    @Override
    public List<SportDB> getSports(String startDate, String endDate) {
        return mCall.getSportList(startDate, endDate);
    }

    @Override
    public List<SportDB> getSportByCache(String startDate, String endDate) {
        List<SportDB> sportDBList = null;
        try {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            long startTimeStamp = sdf.parse(startDate).getTime();
            long endTimeStamp = sdf.parse(endDate).getTime();
            List<SportCacheDB> sportCacheDBList = mCall.getSportCacheList(startTimeStamp / 1000L, endTimeStamp / 1000L);
            if (sportCacheDBList != null && sportCacheDBList.size() > 0) {
                Map<String, SportDB> sportDBMap = new HashMap<>();
                for (SportCacheDB sportCacheDB : sportCacheDBList) {
                    String date = sdf.format(sportCacheDB.getTimeStamp() * 1000L).split(" ")[0];
                    int hour = Integer.parseInt(sdf_h.format(sportCacheDB.getTimeStamp() * 1000L));
                    SportDB tempSportDB = sportDBMap.get(date);
                    if (!sportDBMap.containsKey(date)) {
                        tempSportDB = new SportDB();
                        sportDBMap.put(date, tempSportDB);
                    }
                    tempSportDB.setDate(date);
                    tempSportDB.setStep(tempSportDB.getStep() + sportCacheDB.getStep());
                    tempSportDB.setCalories(tempSportDB.getCalories() + sportCacheDB.getCalories());
                    tempSportDB.setDistance(tempSportDB.getDistance() + sportCacheDB.getDistance());
                    tempSportDB.setSportTime(tempSportDB.getSportTime() + sportCacheDB.getSportTime());
                    tempSportDB.setStepDetail(proSportDetail(tempSportDB.getStepDetail(), hour, sportCacheDB.getStep()));
                    tempSportDB.setCaloriesDetail(proSportDetail(tempSportDB.getCaloriesDetail(), hour, sportCacheDB.getCalories()));
                    tempSportDB.setDistanceDetail(proSportDetail(tempSportDB.getDistanceDetail(), hour, sportCacheDB.getDistance()));
                    tempSportDB.setSportTimeDetail(proSportDetail(tempSportDB.getSportTimeDetail(), hour, sportCacheDB.getSportTime()));
                }
                if (sportDBMap.size() > 0) {
                    sportDBList = new ArrayList<>(sportDBMap.values());
                }
            }
        } catch (Exception e) {
        }
        return sportDBList;
    }

    /*-------------------------------------------------------------------运动缓存---------------------------------------------------------------------*/

    @Override
    public void addSportCacheList(List<SportCacheDB> sportCacheDBList) {
        mCall.addSportCacheList(sportCacheDBList);
    }

    @Override
    public void delSportCache(int id) {
        mCall.delSportCache(id);
    }

    @Override
    public SportCacheDB getSportCache(int id) {
        return mCall.getSportCache(id);
    }

    @Override
    public List<SportCacheDB> getSportCacheList() {
        return mCall.getSportCacheList();
    }

    @Override
    public SparseArray<float[]> getWeekSport(Calendar calendar, boolean isMondayFirstDay) {
        String weekFirstDay = TimeUtil.getFirstDayOfWeek(calendar, isMondayFirstDay);
        String weekLastDay = TimeUtil.getLastDayOfWeek(calendar, isMondayFirstDay);
        LogUtil.i(TAG, "获取运动周数据 指定日期:" + calendar.getTimeInMillis() + " 周首日 :" + weekFirstDay + " 周末日:" + weekLastDay);
        List<SportDB> sportDBList = getSports(weekFirstDay, weekLastDay);
        List<SportDB> sportCacheDBList = getSportByCache(weekFirstDay, weekLastDay);
        SparseArray<float[]> weekSportDataArray = new SparseArray<>();
        float[] weekStepData = new float[7];
        float[] weekCaloriesData = new float[7];
        float[] weekDistanceData = new float[7];
        float[] weekSportTimeData = new float[7];
        weekSportDataArray.put(0, weekStepData);
        weekSportDataArray.put(1, weekCaloriesData);
        weekSportDataArray.put(2, weekDistanceData);
        weekSportDataArray.put(3, weekSportTimeData);
        proWeekSportData(sportDBList, weekStepData, isMondayFirstDay, 0);
        proWeekSportData(sportCacheDBList, weekStepData, isMondayFirstDay, 0);
        proWeekSportData(sportDBList, weekCaloriesData, isMondayFirstDay, 1);
        proWeekSportData(sportCacheDBList, weekCaloriesData, isMondayFirstDay, 1);
        proWeekSportData(sportDBList, weekDistanceData, isMondayFirstDay, 2);
        proWeekSportData(sportCacheDBList, weekDistanceData, isMondayFirstDay, 2);
        proWeekSportData(sportDBList, weekSportTimeData, isMondayFirstDay, 3);
        proWeekSportData(sportCacheDBList, weekSportTimeData, isMondayFirstDay, 3);
        return weekSportDataArray;
    }

    @Override
    public SparseArray<float[]> getMonthSport(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthFirstDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-01";
        String monthLastDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-31";
        LogUtil.i(TAG, "指定日期:" + calendar.getTimeInMillis() + " 月首日 :" + monthFirstDay + " 月末日:" + monthLastDay);
        List<SportDB> sportDBList = getSports(monthFirstDay, monthLastDay);
        List<SportDB> sportCacheDBList = getSportByCache(monthFirstDay, monthLastDay);
        SparseArray<float[]> monthSportDataArray = new SparseArray<>();
        float[] monthStepData = new float[31];
        float[] monthCaloriesData = new float[31];
        float[] monthDistanceData = new float[31];
        float[] monthSportTimeData = new float[31];
        monthSportDataArray.put(0, monthStepData);
        monthSportDataArray.put(1, monthCaloriesData);
        monthSportDataArray.put(2, monthDistanceData);
        monthSportDataArray.put(3, monthSportTimeData);
        proMonthSportData(sportDBList, monthStepData, 0);
        proMonthSportData(sportCacheDBList, monthStepData, 0);
        proMonthSportData(sportDBList, monthCaloriesData, 1);
        proMonthSportData(sportCacheDBList, monthCaloriesData, 1);
        proMonthSportData(sportDBList, monthDistanceData, 2);
        proMonthSportData(sportCacheDBList, monthDistanceData, 2);
        proMonthSportData(sportDBList, monthSportTimeData, 3);
        proMonthSportData(sportCacheDBList, monthSportTimeData, 3);
        return monthSportDataArray;
    }

    /*---------------------------------------------------------------------睡眠-----------------------------------------------------------------------*/

    @Override
    public void addSleep(SleepDB sleepDB) {
        mCall.addSleep(sleepDB);
    }

    @Override
    public void addSleepList(List<SleepDB> sleepDBList) {
        mCall.addSleepList(sleepDBList);
    }

    @Override
    public void delSleep(int id) {
        mCall.delSport(id);
    }

    @Override
    public void delSleeps(List<Integer> idList) {
        mCall.delSleepByIdList(idList);
    }

    @Override
    public List<SleepDB> getSleepList(String date) {
        return mCall.getSleepList(date);
    }

    @Override
    public List<SleepDB> getNoUploadSleepList() {
        return mCall.getNoUploadSleepList();
    }

    @Override
    public int[] getWeekSleep(Calendar calendar, boolean isMondayFirstDay) {
        String weekFirstDay = TimeUtil.getFirstDayOfWeek(calendar, isMondayFirstDay);
        String weekLastDay = TimeUtil.getLastDayOfWeek(calendar, isMondayFirstDay);
        LogUtil.i(TAG, "获取睡眠周数据 指定日期:" + calendar.getTimeInMillis() + " 周首日 :" + weekFirstDay + " 周末日:" + weekLastDay);
        List<SleepDB> sleepDBList = mCall.getSleepList(weekFirstDay, weekLastDay);
        int[] weekSleep = new int[7];
        for (SleepDB sleepDB : sleepDBList) {
            int index = TimeUtil.getWeekIndexByDate(sleepDB.getDate(), isMondayFirstDay);
            weekSleep[index] += sleepDB.getTotal();
        }
        return weekSleep;
    }

    @Override
    public int[] getMonthSleep(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthFirstDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-01";
        String monthLastDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-31";
        LogUtil.i(TAG, "获取睡眠月数据 指定日期:" + calendar.getTimeInMillis() + " 月首日 :" + monthFirstDay + " 月末日:" + monthLastDay);
        List<SleepDB> sleepDBList = mCall.getSleepList(monthFirstDay, monthLastDay);
        int[] monthSleep = new int[31];
        for (SleepDB sleepDB : sleepDBList) {
            int index = Integer.parseInt(sleepDB.getDate().split("-")[2]) - 1;
            monthSleep[index] += sleepDB.getTotal();
        }
        return monthSleep;
    }

    @Override
    public void uploadSleepFlag(int id) {
        mCall.uploadSleepFlag(id);
    }

    /*---------------------------------------------------------------------心率-----------------------------------------------------------------------*/

    @Override
    public List<HeartRateDB> getNoUploadHeartRateList() {
        return mCall.getNoUploadHeartRateList();
    }

    @Override
    public List<HeartRateDB> getHeartRateList(String date) {
        return mCall.getHeartRateList(date);
    }

    @Override
    public void addHeartRateSubmitList(Map<String, String> dateSubmitMap) {
        mCall.updateHeartRateSubmitList(dateSubmitMap);
    }

    @Override
    public void updateHeartRateSubmitToDetail(int id) {
        mCall.updateHeartRateSubmitToDetail(id);
    }

    @Override
    public void updateHeartRateDetailList(List<HeartRateDB> heartRateDBList) {
        mCall.updateHeartRateDetailList(heartRateDBList);
    }


    /*------------------------------------------------------------------本类私有方法-------------------------------------------------------------------*/

    private String proSportDetail(String detail, int hour, int value) {
        String[] details = detail.split(",");
        details[hour] = (Integer.parseInt(details[hour]) + value) + "";
        detail = Arrays.toString(details);
        return detail.replace(" ", "").replace("[", "").replace("]", "");
    }

    private void proWeekSportData(List<SportDB> sportDBList, float[] weekSportData, boolean isMondayFirstDay, int type) {
        if (sportDBList != null && sportDBList.size() > 0) {
            for (SportDB sportDB : sportDBList) {
                int index = TimeUtil.getWeekIndexByDate(sportDB.getDate(), isMondayFirstDay);
//                LogUtil.i("test", "index : " + index + " notificationType : " + notificationType + " date : " + sportDB.getDate() + " step : " + sportDB.getStep());
                switch (type) {
                    case 0:
                        weekSportData[index] += sportDB.getStep();
                        break;
                    case 1:
                        weekSportData[index] += sportDB.getCalories();
                        break;
                    case 2:
                        weekSportData[index] += sportDB.getDistance();
                        break;
                    case 3:
                        weekSportData[index] += sportDB.getSportTime();
                        break;
                }
            }
        }
    }

    private void proMonthSportData(List<SportDB> sportDBList, float[] monthSportData, int type) {
        if (sportDBList != null && sportDBList.size() > 0) {
            for (SportDB sportDB : sportDBList) {
                int index = Integer.parseInt(sportDB.getDate().split("-")[2]) - 1;
//                LogUtil.i("test", "index : " + index + " notificationType : " + notificationType + " date : " + sportDB.getDate() + " step : " + sportDB.getStep());
                switch (type) {
                    case 0:
                        monthSportData[index] += sportDB.getStep();
                        break;
                    case 1:
                        monthSportData[index] += sportDB.getCalories();
                        break;
                    case 2:
                        monthSportData[index] += sportDB.getDistance();
                        break;
                    case 3:
                        monthSportData[index] += sportDB.getSportTime();
                        break;
                }
            }
        }
    }

    private String addSportDetail(String oldDetail, int hour, int val) {
        String[] details = oldDetail.split(",");
        if (details.length > hour) {
            details[hour] = (Integer.parseInt(details[hour]) + val) + "";
        }
        return Arrays.toString(details).replace("[", "").replace("]", "").replace(" ", "");
    }

    private String combineSportDetail(String oldDetail, String addDetail) {
        String[] addDetails = addDetail.split(",");
        String[] oldDetails = oldDetail.split(",");
        int[] newDetails = new int[24];
        if (addDetails.length == oldDetails.length) {
            for (int i = 0; i < addDetails.length; i++) {
                newDetails[i] = Integer.parseInt(oldDetails[i]) + Integer.parseInt(addDetails[i]);
            }
        }
        return Arrays.toString(newDetails).replace("[", "").replace("]", "").replace(" ", "");
    }

    private SportDB combineSportDB(SportDB oldSportDB, SportDB addSportDB) {
        if (oldSportDB != null) {
            addSportDB.setStep(oldSportDB.getStep() + addSportDB.getStep());
            addSportDB.setCalories(oldSportDB.getCalories() + addSportDB.getCalories());
            addSportDB.setDistance(oldSportDB.getDistance() + addSportDB.getDistance());
            addSportDB.setSportTime(oldSportDB.getSportTime() + addSportDB.getSportTime());
            addSportDB.setStepDetail(combineSportDetail(oldSportDB.getStepDetail(), addSportDB.getStepDetail()));
            addSportDB.setCaloriesDetail(combineSportDetail(oldSportDB.getCaloriesDetail(), addSportDB.getCaloriesDetail()));
            addSportDB.setDistanceDetail(combineSportDetail(oldSportDB.getDistanceDetail(), addSportDB.getDistanceDetail()));
            addSportDB.setSportTimeDetail(combineSportDetail(oldSportDB.getSportTimeDetail(), addSportDB.getSportTimeDetail()));
            addSportDB.setDate(oldSportDB.getDate());
            addSportDB.setId(oldSportDB.getId());
        }
        return addSportDB;
    }
}
