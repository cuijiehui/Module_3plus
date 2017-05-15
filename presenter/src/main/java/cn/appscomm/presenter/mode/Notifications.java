package cn.appscomm.presenter.mode;

/**
 * 作者：hsh
 * 日期：2017/4/24
 * 说明：
 */

public class Notifications {
    public String packageName = "";
    public String content = "";
    public long timeStamp = 0L;
    public String title = "";
    public String name = "";
    public String text = "";
    public String summaryText = "";
    public String bigText = "";

    @Override
    public String toString() {
        return "Notifications{" +
                "packageName='" + packageName + '\'' +
                ", content='" + content + '\'' +
                ", timeStamp=" + timeStamp +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", summaryText='" + summaryText + '\'' +
                ", bigText='" + bigText + '\'' +
                '}';
    }
}
