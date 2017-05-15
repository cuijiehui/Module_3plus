package cn.appscomm.presenter.interfaces;

import android.util.SparseArray;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SportCacheDB;
import cn.appscomm.db.mode.SportDB;

/**
 * Created by Administrator on 2017/3/6.
 */
public interface PVDBCall {

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
     * 新增运动数据通过运动缓存数据
     *
     * @param synSportCacheDBList 运动缓存数据
     */
    void addSportBySportCache(List<SportCacheDB> synSportCacheDBList);

    /**
     * 根据id删除运动数据
     *
     * @param id 运动id
     */
    void delSport(int id);

    /**
     * 根据id的集合删除运动数据
     *
     * @param ids 要删除运动数据的id集合
     */
    void delSports(List<Integer> ids);

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
    List<SportDB> getSports(String startDate, String endDate);

    /**
     * 获取指定日期对应整周的运动数据集合，0为步数、1为卡路里、2位距离、3位运动时长
     *
     * @param calendar         指定日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 整周的运动数据集合
     */
    SparseArray<float[]> getWeekSport(Calendar calendar, boolean isMondayFirstDay);

    /**
     * 获取指定日期对应整月的运动数据集合，0为步数、1为卡路里、2位距离、3位运动时长
     *
     * @param calendar 指定日期
     * @return 整周的运动数据集合
     */
    SparseArray<float[]> getMonthSport(Calendar calendar);

    /**
     * 通过运动缓存表获取运动数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 运动数据集合
     */
    List<SportDB> getSportByCache(String startDate, String endDate);

    /*------------------------------------------------------------------运动缓存数据-------------------------------------------------------------------*/

    /**
     * 新增多条运动缓存数据
     *
     * @param sportCacheDB 一条运动缓存数据
     */
    void addSportCacheList(List<SportCacheDB> sportCacheDB);

    /**
     * 删除一条运动缓存数据
     *
     * @param id id
     */
    void delSportCache(int id);

    /**
     * 获取指定id的运动缓存数据
     *
     * @param id 指定id
     * @return 返回相应的运动缓冲数据
     */
    SportCacheDB getSportCache(int id);

    /**
     * 获取多条运动缓存数据
     *
     * @return 运动缓存集合
     */
    List<SportCacheDB> getSportCacheList();

    /*--------------------------------------------------------------------睡眠数据--------------------------------------------------------------------*/

    /**
     * 新增一条睡眠数据
     *
     * @param sleepDB 睡眠数据
     */
    void addSleep(SleepDB sleepDB);

    /**
     * 新增多条睡眠数据(先判断detail，若找到则更新，若没有找到则新增)
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
     * 删除多条睡眠数据
     *
     * @param ids 睡眠id集合
     */
    void delSleeps(List<Integer> ids);

    /**
     * 获取指定日期的睡眠数据
     *
     * @param date 指定日期
     * @return 睡眠数据
     */
    List<SleepDB> getSleepList(String date);

    /**
     * 获取所有未上传的睡眠数据
     *
     * @return 所有未上传的睡眠数据
     */
    List<SleepDB> getNoUploadSleepList();

    /**
     * 获取睡眠一周的数据
     *
     * @param calendar         指定日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 睡眠一周的数据
     */
    int[] getWeekSleep(Calendar calendar, boolean isMondayFirstDay);

    /**
     * 获取睡眠一个月的数据
     *
     * @param calendar 指定日期
     * @return 睡眠一个月的数据
     */
    int[] getMonthSleep(Calendar calendar);

    /**
     * 修改睡眠标志(一般修改为数据库中睡眠的id)
     *
     * @param id 要修改后的flag
     */
    void uploadSleepFlag(int id);

    /*--------------------------------------------------------------------心率数据--------------------------------------------------------------------*/

    /**
     * 更新未上传的心率详情
     *
     * @param dateSubmitMap 未上传的心率数据集合
     */
    void addHeartRateSubmitList(Map<String, String> dateSubmitMap);

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
