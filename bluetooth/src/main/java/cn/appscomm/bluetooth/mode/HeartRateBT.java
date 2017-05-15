package cn.appscomm.bluetooth.mode;

public class HeartRateBT {

    public int index;                                                                               // 索引
    public int avg;                                                                                 // 心率平均值
    public long timeStamp;                                                                          // 时间戳

    public HeartRateBT(int index, int avg, long timeStamp) {
        this.index = index;
        this.avg = avg;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "HeartRateBT{" +
                "index=" + index +
                ", avg=" + avg +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
