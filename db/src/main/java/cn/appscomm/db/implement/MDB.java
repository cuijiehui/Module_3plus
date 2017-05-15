package cn.appscomm.db.implement;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.appscomm.db.interfaces.PMDBCall;
import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.ReminderDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SportDB;
import cn.appscomm.db.mode.SportCacheDB;
import cn.appscomm.db.util.DBUtil;
import cn.appscomm.db.util.LogUtil;

/**
 * Created by Administrator on 2017/3/6.
 */

public enum MDB implements PMDBCall {
    INSTANCE {
    };

    private String TAG = MDB.class.getSimpleName();

    /*--------------------------------------------------------------------运动数据--------------------------------------------------------------------*/
    @Override
    public void addSport(SportDB sportDB) {
        sportDB.save();
    }

    @Override
    public void addSportList(List<SportDB> sportDBList) {
        if (sportDBList != null && sportDBList.size() > 0) {
            for (SportDB sportDB : sportDBList) {
                LogUtil.i(TAG, "数据库:该笔运动记录已存在,准备删除...");
                delSport(sportDB.getDate());
            }
            LogUtil.i(TAG, "数据库:新增多笔运动数据!!!");
            DataSupport.saveAll(sportDBList);
        }
    }

    @Override
    public void delSport(int id) {
        DataSupport.delete(SportDB.class, id);
    }

    @Override
    public void delSport(String date) {
        DataSupport.deleteAll(SportDB.class, "date = ?", date);
    }

    @Override
    public void delAllSport() {
        DataSupport.deleteAll(SportDB.class);
    }

    @Override
    public SportDB getSport(String date) {
        List<SportDB> sportDBs = DataSupport.where("date = ?", date).find(SportDB.class);
        return sportDBs != null && sportDBs.size() > 0 ? sportDBs.get(0) : null;
    }

    @Override
    public List<SportDB> getSportList(String startDate, String endDate) {
        return DataSupport.where("date >= ? and date <= ?", startDate, endDate).find(SportDB.class);
    }

    @Override
    public void updateSport(int id, SportDB sportDB) {
        sportDB.update(id);
    }

    @Override
    public void addOrUpdateSport(String date, SportDB sportDB) {
        if (sportDB.getId() > 0) {
            updateSport(sportDB.getId(), sportDB);
        } else {
            addSport(sportDB);
        }
    }

    /*------------------------------------------------------------------运动缓存数据-------------------------------------------------------------------*/
    @Override
    public void addSportCacheList(List<SportCacheDB> sportCacheDBList) {
        DataSupport.saveAll(sportCacheDBList);
    }

    @Override
    public void delSportCache(int id) {
        DataSupport.delete(SportCacheDB.class, id);
    }

    @Override
    public void delSportCachesByIdList(List<Integer> ids) {
        for (int id : ids) {
            delSportCache(id);
        }
    }

    @Override
    public void delAllSportCache() {
        DataSupport.deleteAll(SportCacheDB.class);
    }

    @Override
    public SportCacheDB getSportCache(int id) {
        return DataSupport.where("id = " + id).findFirst(SportCacheDB.class);
    }

    @Override
    public List<SportCacheDB> getSportCacheList() {
        return DataSupport.findAll(SportCacheDB.class);
    }

    @Override
    public List<SportCacheDB> getSportCacheList(long startTimeStamp, long endTimeStamp) {
        return DataSupport.where("timeStamp >= ? and timeStamp <= ?", startTimeStamp + "", endTimeStamp + "").find(SportCacheDB.class);
    }

    /*--------------------------------------------------------------------睡眠数据--------------------------------------------------------------------*/
    @Override
    public void addSleep(SleepDB sleepDB) {
        sleepDB.save();
    }

    @Override
    public void addSleepList(List<SleepDB> sleepDBList) {
        if (sleepDBList != null && sleepDBList.size() > 0) {
            List<SleepDB> addSleepDBList = new LinkedList<>();
            for (SleepDB sleepDB : sleepDBList) {
                List<SleepDB> exitSleepDBList = DataSupport.where("detail = ?", sleepDB.getDetail()).find(SleepDB.class);
                if (exitSleepDBList != null && exitSleepDBList.size() > 0) {
                    LogUtil.i(TAG, "数据库:该笔睡眠已存在");
                    SleepDB exitSleepDB = exitSleepDBList.get(0);
                    if (exitSleepDB.getFlag() == -1 && sleepDB.getFlag() > 0) {
                        LogUtil.i(TAG, "数据库:但该笔睡眠flag不一样，这里需要更新flag(数据库的flag" + exitSleepDB.getFlag() + " 需要保存的flag" + sleepDB.getFlag() + ")");
                        exitSleepDB.setFlag(sleepDB.getFlag());
                        exitSleepDB.update(exitSleepDB.getId());
                    }
                } else {
                    addSleepDBList.add(sleepDB);
                }
            }
            if (addSleepDBList.size() > 0) {
                LogUtil.i(TAG, "数据库:新增多笔睡眠!!!");
                DataSupport.saveAll(addSleepDBList);
            }
        }
    }

    @Override
    public void delSleep(int id) {
        DataSupport.delete(SleepDB.class, id);
    }

    @Override
    public void delSleepByIdList(List<Integer> ids) {
        for (int id : ids) {
            delSport(id);
        }
    }

    @Override
    public void delAllSleep() {
        DataSupport.deleteAll(SleepDB.class);
    }

    @Override
    public List<SleepDB> getNoUploadSleepList() {
        return DataSupport.where("flag = -1").find(SleepDB.class);
    }

    @Override
    public List<SleepDB> getSleepList(String date) {
        return DataSupport.where("date = ?", date).find(SleepDB.class);
    }

    @Override
    public List<SleepDB> getSleepList(String startDate, String endDate) {
        return DataSupport.where("date >= ? and date <= ?", startDate, endDate).find(SleepDB.class);
    }

    @Override
    public void uploadSleepFlag(int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("flag", id);
        DataSupport.update(SleepDB.class, contentValues, id);
    }

    /*--------------------------------------------------------------------提醒数据--------------------------------------------------------------------*/
    @Override
    public void addReminder(ReminderDB reminderDB) {
        reminderDB.save();
    }

    @Override
    public void addReminderList(List<ReminderDB> reminderDBList) {
        DataSupport.saveAll(reminderDBList);
    }

    @Override
    public void delReminder(int id) {
        DataSupport.delete(ReminderDB.class, id);
    }

    @Override
    public void delAllReminder() {
        DataSupport.deleteAll(ReminderDB.class);
    }

    @Override
    public void getReminderCount() {
        DataSupport.count(ReminderDB.class);
    }

    @Override
    public ReminderDB getReminder(int hour, int min) {
        List<ReminderDB> reminderDBs = DataSupport.where("hour = ? and min = ?", hour + "", min + "").find(ReminderDB.class);
        return reminderDBs != null && reminderDBs.size() > 0 ? reminderDBs.get(0) : null;
    }

    @Override
    public List<ReminderDB> gerReminderList() {
        return DataSupport.findAll(ReminderDB.class);
    }

    @Override
    public void updateReminder(int id, ReminderDB reminderDB) {
        reminderDB.update(id);
    }

    /*--------------------------------------------------------------------心率数据--------------------------------------------------------------------*/

    @Override
    public void updateHeartRateSubmitList(Map<String, String> dateSubmitMap) {
        if (dateSubmitMap != null && dateSubmitMap.size() > 0) {
            for (Map.Entry<String, String> entry : dateSubmitMap.entrySet()) {
                List<HeartRateDB> exitHeartRateDBList = DataSupport.where("date = ?", entry.getKey()).find(HeartRateDB.class);
                if (exitHeartRateDBList != null && exitHeartRateDBList.size() > 0) {                // 1、合并成新的待上传 2、根据待上传和已上传，计算出总的心率值 3、保存
                    HeartRateDB oldHeartRateDB = exitHeartRateDBList.get(0);
                    String newSubmit = DBUtil.combineNewHeartRateDetail(oldHeartRateDB.getSubmit(), entry.getValue());
                    String totalHeartRateData = TextUtils.isEmpty(oldHeartRateDB.getDetail()) ? newSubmit : oldHeartRateDB.getDetail() + newSubmit;
                    int newAvg = DBUtil.countHeartRateAvg(totalHeartRateData);
                    oldHeartRateDB.setSubmit(newSubmit);
                    oldHeartRateDB.setAvg(newAvg);
                    oldHeartRateDB.setFlag(HeartRateDB.FLAG_SUBMIT);
                    oldHeartRateDB.update(oldHeartRateDB.getId());
                } else {                                                                            // 直接保存待上传
                    String submit = entry.getValue();
                    int avg = DBUtil.countHeartRateAvg(submit);
                    String date = entry.getKey();
                    new HeartRateDB(avg, submit, date, HeartRateDB.FLAG_SUBMIT).save();
                }
            }
        }
    }

    @Override
    public List<HeartRateDB> getNoUploadHeartRateList() {
        return DataSupport.where("flag = " + HeartRateDB.FLAG_SUBMIT).find(HeartRateDB.class);
    }

    @Override
    public List<HeartRateDB> getHeartRateList(String date) {
        return DataSupport.where("date = ?", date).find(HeartRateDB.class);
    }

    @Override
    public void updateHeartRateSubmitToDetail(int id) {
        HeartRateDB heartRateDB = DataSupport.find(HeartRateDB.class, id);
        String detail = DBUtil.combineNewHeartRateDetail(heartRateDB.getDetail(), heartRateDB.getSubmit());
        int avg = DBUtil.countHeartRateAvg(detail);
        heartRateDB.setDetail(detail);
        heartRateDB.setAvg(avg);
        heartRateDB.setSubmit("");
        heartRateDB.setFlag(HeartRateDB.FLAG_DETAIL);
        LogUtil.i(TAG, heartRateDB.toString());
        heartRateDB.update(heartRateDB.getId());
    }

    @Override
    public void updateHeartRateDetailList(List<HeartRateDB> heartRateDBList) {
        for (HeartRateDB heartRateDB : heartRateDBList) {
            List<HeartRateDB> exitHeartRateDBList = DataSupport.where("date = ?", heartRateDB.getDate()).find(HeartRateDB.class);
            if (exitHeartRateDBList != null && exitHeartRateDBList.size() > 0) {
                HeartRateDB exitHeartRateDB = exitHeartRateDBList.get(0);
                exitHeartRateDB.setAvg(heartRateDB.getAvg());
                exitHeartRateDB.setDetail(heartRateDB.getDetail());
                exitHeartRateDB.update(exitHeartRateDB.getId());
            } else {
                heartRateDB.setFlag(HeartRateDB.FLAG_DETAIL);
                heartRateDB.save();
            }
        }
    }

    /*------------------------------------------------------------------本来私有方法------------------------------------------------------------------*/

}
