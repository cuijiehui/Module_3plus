package cn.appscomm.presenter.mode;

/**
 * 作者：hsh
 * 日期：2017/4/24
 * 说明：
 */

public class NotificationInfo {
    public String title;
    public String content;
    public long timeStamp;
    public byte notificationType;
    public int notificationCount;

    public NotificationInfo(String title, String content, long timeStamp, byte notificationType, int notificationCount) {
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        this.notificationType = notificationType;
        this.notificationCount = notificationCount;
    }
}
