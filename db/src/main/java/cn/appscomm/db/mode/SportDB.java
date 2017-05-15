package cn.appscomm.db.mode;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 运动数据模型(已上传到服务器的运动数据)
 * 若运动数据缓存上传完毕，删除后，需要把删除的数据添加到该类
 * step、calories、distance、sportTime 保存的是一天的总数（常用于详情周、月）
 * stepDetail、caloriesDetail、distanceDetail、sportTimeDetail 保存的是一天0~24小时中每个小时的数据（常用于详情天）
 * date
 */
public class SportDB extends DataSupport {

    private int id = 0;
    private int step = 0;                                                                           // 单位为步，如100步
    private int calories = 0;                                                                       // 单位为卡，如1000卡
    private int distance = 0;                                                                       // 单位为厘米，如100厘米
    private int sportTime = 0;                                                                      // 单位为分，如60分
    @Column(defaultValue = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")
    private String stepDetail;                                                                      // 步数日详情，记录0~23小时的详情，存储如 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    @Column(defaultValue = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")
    private String caloriesDetail;                                                                  // 卡路里日详情，记录0~23小时的详情，存储如 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    @Column(defaultValue = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")
    private String distanceDetail;                                                                  // 距离日详情，记录0~23小时的详情，存储如 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    @Column(defaultValue = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")
    private String sportTimeDetail;                                                                 // 运动时长日详情，记录0~23小时的详情，存储如 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    private String date;                                                                            // 日期，格式如：2016-08-08

    public SportDB() {
        this.step = this.calories = this.distance = this.sportTime = 0;
        this.stepDetail = this.caloriesDetail = this.distanceDetail = this.sportTimeDetail = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
    }

    public SportDB(int step, int calories, int distance, int sportTime, String date) {
        this.step = step;
        this.calories = calories;
        this.distance = distance;
        this.sportTime = sportTime;
        this.date = date;
    }

    public SportDB(String stepDetail, String caloriesDetail, String distanceDetail, String sportTimeDetail, String date) {
        this.stepDetail = stepDetail;
        this.caloriesDetail = caloriesDetail;
        this.distanceDetail = distanceDetail;
        this.sportTimeDetail = sportTimeDetail;
        this.date = date;
    }

    public SportDB(int step, int calories, int distance, int sportTime, String stepDetail, String caloriesDetail, String distanceDetail, String sportTimeDetail, String date) {
        this.step = step;
        this.calories = calories;
        this.distance = distance;
        this.sportTime = sportTime;
        this.stepDetail = stepDetail;
        this.caloriesDetail = caloriesDetail;
        this.distanceDetail = distanceDetail;
        this.sportTimeDetail = sportTimeDetail;
        this.date = date;
    }

    @Override
    public String toString() {
        return "SportDB{" +
                "id=" + id +
                ", step=" + step +
                ", calories=" + calories +
                ", distance=" + distance +
                ", sportTime=" + sportTime +
                ", stepDetail='" + stepDetail + '\'' +
                ", caloriesDetail='" + caloriesDetail + '\'' +
                ", distanceDetail='" + distanceDetail + '\'' +
                ", sportTimeDetail='" + sportTimeDetail + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSportTime() {
        return sportTime;
    }

    public void setSportTime(int sportTime) {
        this.sportTime = sportTime;
    }

    public String getStepDetail() {
        return stepDetail;
    }

    public void setStepDetail(String stepDetail) {
        this.stepDetail = stepDetail;
    }

    public String getCaloriesDetail() {
        return caloriesDetail;
    }

    public void setCaloriesDetail(String caloriesDetail) {
        this.caloriesDetail = caloriesDetail;
    }

    public String getDistanceDetail() {
        return distanceDetail;
    }

    public void setDistanceDetail(String distanceDetail) {
        this.distanceDetail = distanceDetail;
    }

    public String getSportTimeDetail() {
        return sportTimeDetail;
    }

    public void setSportTimeDetail(String sportTimeDetail) {
        this.sportTimeDetail = sportTimeDetail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
