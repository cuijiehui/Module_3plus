package cn.appscomm.server.mode.sport;

/**
 * 作者：hsh
 * 日期：2017/4/13
 * 说明：
 */

public class HeartRateSER {
    public int heartMin;
    public int heartMax;
    public int heartAvg;
    public String startTime;
    public String endTime;

    public HeartRateSER(int avg, String startTime, String endTime) {
        this.heartMin = avg;
        this.heartMax = avg;
        this.heartAvg = avg;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
