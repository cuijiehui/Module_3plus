package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * 睡眠数据模型（包含所有未上传的和已上传的）
 * 一笔睡眠数据为一条记录，所以一天可能有多笔数据
 * 已上传的睡眠flag可以为服务器给予的ID或其他
 * 未上传的睡眠flag为-1
 */
public class SleepDB extends DataSupport {

    private int id;
    private int total;                                                                              // 总睡眠
    private int deep;                                                                               // 深睡
    private int light;                                                                              // 浅睡
    private int awake;                                                                              // 清醒
    private int sleep;                                                                              // 睡眠总时间(浅睡+深睡)
    private int awakeTime;                                                                          // 清醒次数
    private String detail;                                                                          // 一笔睡眠的详情，如：2016-07-28 07:15:17&BEGIN,2016-07-28 07:16:17&DEEP,2016-07-28 07:17:17&END,
    private String date;                                                                            // 日期，如：2016-08-08
    private int flag;                                                                               // -1：未上传 >0：已上传

    @Override
    public String toString() {
        return "SleepDB{" +
                "id=" + id +
                ", 总睡眠=" + total +
                ", 深睡=" + deep +
                ", 浅睡=" + light +
                ", 清醒=" + awake +
                ", 睡眠时间=" + sleep +
                ", 清醒次数=" + awakeTime +
                ", 详情='" + detail + '\'' +
                ", 日期='" + date + '\'' +
                ", 标志=" + flag +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAwake() {
        return awake;
    }

    public void setAwake(int awake) {
        this.awake = awake;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getAwakeTime() {
        return awakeTime;
    }

    public void setAwakeTime(int awakeTime) {
        this.awakeTime = awakeTime;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
