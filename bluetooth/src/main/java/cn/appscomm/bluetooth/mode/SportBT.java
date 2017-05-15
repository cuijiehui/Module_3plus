package cn.appscomm.bluetooth.mode;

public class SportBT {

    public int index;
    public int step;                                                                                // 步数
    public int calories;                                                                            // 卡路里
    public int distance;                                                                            // 距离
    public int sportTime;                                                                           // 运动时长
    public long timeStamp;                                                                          // 时间戳

    public SportBT(int index, int step, int calories, int distance, int sportTime, long timeStamp) {
        this.index = index;
        this.step = step;
        this.calories = calories;
        this.distance = distance;
        this.sportTime = sportTime;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "SportBT{" +
                "index=" + index +
                ", step=" + step +
                ", calories=" + calories +
                ", distance=" + distance +
                ", sportTime=" + sportTime +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
