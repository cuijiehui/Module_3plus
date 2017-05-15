package cn.appscomm.bluetooth.mode;

import java.io.Serializable;

public class ReminderBT implements Serializable {

    public int index;
    public int type;                                                                                // 提醒类型
    public int hour;                                                                                // 提醒小时
    public int min;                                                                                 // 提醒分钟
    public int cycle;                                                                               // 提醒周期
    public boolean enable;                                                                          // 提醒开关
    public String content;                                                                          // 提醒内容

    public ReminderBT() {
    }

    public ReminderBT(int index, int type, int hour, int min, byte cycle, boolean enable, String content) {
        this.index = index;
        this.type = type;
        this.hour = hour;
        this.min = min;
        this.cycle = cycle;
        this.enable = enable;
        this.content = content;
    }
}
