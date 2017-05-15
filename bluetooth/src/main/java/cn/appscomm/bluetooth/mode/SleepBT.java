package cn.appscomm.bluetooth.mode;

public class SleepBT {

    public int index;
    public long timeStamp;
    public int type;

    public SleepBT(int index, long timeStamp, int type) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.type = type;
    }

    public SleepBT(int type, long timeStamp) {
        this.type = type;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "SleepBT{" +
                "索引=" + index +
                ", 时间戳=" + timeStamp +
                ", 类型=" + type +
                '}';
    }
}
