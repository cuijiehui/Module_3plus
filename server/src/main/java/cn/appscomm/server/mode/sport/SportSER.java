package cn.appscomm.server.mode.sport;

/**
 * 作者：hsh
 * 日期：2017/4/8
 * 说明：
 */

public class SportSER {

    public int sportStep;
    public int sportCalorie;
    public int sportDistance;
    public int sportDuration;
    public int sportSpeed;
    public String startTime;
    public String endTime;

    public SportSER(int sportStep, int sportCalorie, int sportDistance, int sportDuration, int sportSpeed, String startTime, String endTime) {
        this.sportStep = sportStep;
        this.sportCalorie = sportCalorie;
        this.sportDistance = sportDistance;
        this.sportDuration = sportDuration;
        this.sportSpeed = sportSpeed;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SportDataSer{" +
                "sportStep=" + sportStep +
                ", sportCalorie=" + sportCalorie +
                ", sportDistance=" + sportDistance +
                ", sportDuration=" + sportDuration +
                ", sportSpeed=" + sportSpeed +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
