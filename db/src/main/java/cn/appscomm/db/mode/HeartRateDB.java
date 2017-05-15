package cn.appscomm.db.mode;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 心率数据模型(包括已上传的和未上传的)
 * detail：代表已上传的
 * submit：代表未上传的
 * detail和submit存储的内容格式是一样的，内容具体解释如下：
 * 一天有1440(24*60)分钟，但心率读取最小间隔是5分钟读取一次，于是一天最多有288(1440/5)笔心率记录
 * 每笔数据可以简化为：(0~287 = 总分钟/5)&该笔的心率值)
 * 注：
 * 若00:01分产生的心率为70，1 / 5 = 0，所以结果为0&70
 * 若02:05分产生的心率为80，(2*60 + 5) / 5 = 25，所以结果为25&80
 * 若23:59分产生的心率为75，(23*60 + 59) / 5 = 287，所以结果为287&75
 */
public class HeartRateDB extends DataSupport {

    private int id;
    private int avg;                                                                                // 心率平均值
    private int min;                                                                                // 心率最小值
    private int max;                                                                                // 心率最大值
    private String detail;                                                                          // 心率详情(已上传)，如(0&70,1&80,2&80……,287&80)其中0~288为0~24小时
    private String submit;                                                                          // 心率详情(未上传)，如(0&70,1&80,2&80……,287&80)其中0~288为0~24小时
    private String date;                                                                            // 日期
    private int flag;                                                                               // 标志(1：不存在需要上传的心率数据 -1：存在需要上传的心率数据)

    @Column(ignore = true)
    public static int FLAG_SUBMIT = -1;
    @Column(ignore = true)
    public static int FLAG_DETAIL = 1;

    public HeartRateDB() {
    }

    public HeartRateDB(int avg, String submit, String date, int flag) {
        this.avg = avg;
        this.submit = submit;
        this.date = date;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "HeartRateDB{" +
                "id=" + id +
                ", 心率=" + avg +
                ", 已上传详情='" + detail + '\'' +
                ", 未上传详情='" + submit + '\'' +
                ", 日期='" + date + '\'' +
                ", 标记=" + flag +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
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
