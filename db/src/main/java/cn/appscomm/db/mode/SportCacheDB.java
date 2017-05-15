package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * 运动数据缓存模型：
 * 从设备上获取到的运动数据，直接存储到该模型
 * 需要上传运动数据到服务器时，则上传该数据库，上传成功后清空
 * 每个成员变量都是按照蓝牙协议来设计
 */
public class SportCacheDB extends DataSupport {
    private int id;
    private int step;                                                                               // 步数
    private int calories;                                                                           // 卡路里
    private int distance;                                                                           // 距离
    private int sportTime;                                                                          // 运动时长
    private long timeStamp;                                                                         // 时间戳

    public SportCacheDB(int step, int calories, int distance, int sportTime, long timeStamp) {
        this.step = step;
        this.calories = calories;
        this.distance = distance;
        this.sportTime = sportTime;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "SportCacheDB{" +
                "id=" + id +
                ", step=" + step +
                ", calories=" + calories +
                ", distance=" + distance +
                ", sportTime=" + sportTime +
                ", timeStamp=" + timeStamp +
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
