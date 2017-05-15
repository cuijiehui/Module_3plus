package cn.appscomm.db.interfaces;

import java.util.List;
import java.util.Map;

import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.ReminderDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SportDB;
import cn.appscomm.db.mode.SportCacheDB;

/**
 * Created by Administrator on 2017/3/6.
 */
public interface PMDBCall {

    /*--------------------------------------------------------------------运动数据--------------------------------------------------------------------*/

    /**
     * 新增一条运动数据
     *
     * @param sportDB 一条运动数据
     */
    void addSport(SportDB sportDB);

    /**
     * 新增多条运动数据
     *
     * @param sportDBList 运动数据集合
     */
    void addSportList(List<SportDB> sportDBList);

    /**
     * 根据id删除运动数据
     *
     * @param id 运动id
     */
    void delSport(int id);

    /**
     * 根据日期删除运动数据
     *
     * @param date 日期
     */
    void delSport(String date);

    /**
     * 删除所有运动数据
     */
    void delAllSport();

    /**
     * 获取一条运动数据
     *
     * @param date 日期
     * @return 返回一条运动数据
     */
    SportDB getSport(String date);

    /**
     * 获取多条运动数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 设定日期的运动数据集合
     */
    List<SportDB> getSportList(String startDate, String endDate);

    /**
     * 修改一条运动数据
     *
     * @param id      运动id
     * @param sportDB 要修改的运动数据
     */
    void updateSport(int id, SportDB sportDB);

    /**
     * 修改/新增一条运动数据
     *
     * @param date    指定日期
     * @param sportDB 运动数据
     */
    void addOrUpdateSport(String date, SportDB sportDB);

    /*------------------------------------------------------------------运动缓存数据-------------------------------------------------------------------*/

    /**
     * 新增一条运动缓存
     *
     * @param sportCacheDBList 运动缓存数据
     */
    void addSportCacheList(List<SportCacheDB> sportCacheDBList);

    /**
     * 删除一条运动缓存数据
     *
     * @param id id
     */
    void delSportCache(int id);

    /**
     * 删除多条运动缓存数据(通过id集合)
     *
     * @param ids 运动缓存数据的id集合
     */
    void delSportCachesByIdList(List<Integer> ids);

    /**
     * 删除所有运动缓存数据
     */
    void delAllSportCache();

    /**
     * 获取指定id的运动缓存数据
     *
     * @param id 指定的id
     * @return 返回相应的运动缓存数据
     */
    SportCacheDB getSportCache(int id);

    /**
     * 获取所有的运动缓存数据
     *
     * @return 所有的运动缓存数据
     */
    List<SportCacheDB> getSportCacheList();

    /**
     * 获取指定时间戳的运动缓存数据
     *
     * @param startTimeStamp 开始时间戳
     * @param endTimeStamp   结束时间戳
     * @return 运动缓存数据
     */
    List<SportCacheDB> getSportCacheList(long startTimeStamp, long endTimeStamp);

    /*--------------------------------------------------------------------睡眠数据--------------------------------------------------------------------*/

    /**
     * 新增一条睡眠数据
     *
     * @param sleepDB 睡眠数据
     */
    void addSleep(SleepDB sleepDB);

    /**
     * 新增多条睡眠数据
     *
     * @param sleepDBList 睡眠数据集合
     */
    void addSleepList(List<SleepDB> sleepDBList);

    /**
     * 删除一条睡眠数据
     *
     * @param id 睡眠id
     */
    void delSleep(int id);

    /**
     * 删除指定Id集合的提醒
     *
     * @param ids 睡眠Id集合
     */
    void delSleepByIdList(List<Integer> ids);

    /**
     * 删除所有睡眠数据
     */
    void delAllSleep();

    /**
     * 获取所有未上传的睡眠数据
     *
     * @return 所有未上传的睡眠数据
     */
    List<SleepDB> getNoUploadSleepList();

    /**
     * 获取指定日期的睡眠数据
     *
     * @param date 日期
     * @return 睡眠数据
     */
    List<SleepDB> getSleepList(String date);

    /**
     * 获取指定开始和结束日期的睡眠数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 睡眠数据
     */
    List<SleepDB> getSleepList(String startDate, String endDate);

    /**
     * 修改睡眠标志(一般修改为数据库中睡眠的id)
     *
     * @param id 要修改后的flag
     */
    void uploadSleepFlag(int id);

    /*--------------------------------------------------------------------提醒数据--------------------------------------------------------------------*/

    /**
     * 新增一条提醒
     *
     * @param reminderDB 提醒
     */
    void addReminder(ReminderDB reminderDB);

    /**
     * 新增多条提醒
     *
     * @param reminderDBList 提醒集合
     */
    void addReminderList(List<ReminderDB> reminderDBList);

    /**
     * 删除一条提醒
     *
     * @param id 要删除提醒的id
     */
    void delReminder(int id);

    /**
     * 删除所有提醒
     */
    void delAllReminder();

    /**
     * 获取提醒条数
     */
    void getReminderCount();

    /**
     * 获取一条提醒
     *
     * @param hour 小时
     * @param min  分钟
     * @return 提醒
     */
    ReminderDB getReminder(int hour, int min);

    /**
     * 获取所有提醒
     *
     * @return 所有提醒
     */
    List<ReminderDB> gerReminderList();

    /**
     * 修改一条提醒
     *
     * @param id         提醒ID
     * @param reminderDB 提醒
     */
    void updateReminder(int id, ReminderDB reminderDB);

    /*--------------------------------------------------------------------心率数据--------------------------------------------------------------------*/

    /**
     * 更新未上传的心率详情
     *
     * @param dateSubmitMap 未上传的心率数据集合
     */
    void updateHeartRateSubmitList(Map<String, String> dateSubmitMap);

    /**
     * 获取所有未上传的心率数据
     *
     * @return 所有未上传的心率数据
     */
    List<HeartRateDB> getNoUploadHeartRateList();

    /**
     * 通过日期获取心率数据
     *
     * @param date 日期
     * @return 心率数据
     */
    List<HeartRateDB> getHeartRateList(String date);

    /**
     * 更新心率(待上传转为已上传)
     *
     * @param id 要更新的心率
     */
    void updateHeartRateSubmitToDetail(int id);

    /**
     * 更新心率详情(已上传的)
     *
     * @param heartRateDBList 最新心率详情
     */
    void updateHeartRateDetailList(List<HeartRateDB> heartRateDBList);
}
