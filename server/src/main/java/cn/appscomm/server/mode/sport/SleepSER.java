package cn.appscomm.server.mode.sport;

import java.util.List;

/**
 * 作者：hsh
 * 日期：2017/4/12
 * 说明：
 */

public class SleepSER {
    public String deviceId;
    public String deviceType;
    public int timeZone;
    public String startTime;
    public String endTime;
    public int quality;
    public int sleepDuration;
    public int awakeDuration;
    public int lightDuration;
    public int deepDuration;
    public int totalDuration;
    public int awakeCount;
    public List<SleepDetailSER> details;

    public SleepSER(String deviceId, String deviceType, int totalDuration, int deepDuration, int lightDuration, int awakeDuration, int sleepDuration, int awakeCount, int timeZone, int quality) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.totalDuration = totalDuration;
        this.deepDuration = deepDuration;
        this.lightDuration = lightDuration;
        this.awakeDuration = awakeDuration;
        this.sleepDuration = sleepDuration;
        this.awakeCount = awakeCount;
        this.timeZone = timeZone;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "SleepSER{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", timeZone=" + timeZone +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", quality=" + quality +
                ", sleepDuration=" + sleepDuration +
                ", awakeDuration=" + awakeDuration +
                ", lightDuration=" + lightDuration +
                ", deepDuration=" + deepDuration +
                ", totalDuration=" + totalDuration +
                ", awakeCount=" + awakeCount +
                ", details=" + details +
                '}';
    }


}
