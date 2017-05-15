package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * 提醒模型
 * 一笔提醒代表一条记录
 */
public class ReminderDB extends DataSupport {

    private int id;
    private int type;                                                                               // 提醒类型
    private int hour;                                                                               // 提醒小时
    private int min;                                                                                // 提醒分钟
    private int shock;                                                                              // 提醒震动
    private int cycle;                                                                              // 提醒周期
    private boolean isCheck;                                                                        // 提醒开关
    private String content;                                                                         // 提醒内容

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
