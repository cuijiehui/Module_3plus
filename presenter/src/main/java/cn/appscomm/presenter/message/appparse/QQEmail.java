package cn.appscomm.presenter.message.appparse;

import cn.appscomm.presenter.mode.Notifications;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/24
 * 说明：
 */

public class QQEmail {

    public static boolean parse(Notifications notifications) {
        if (notifications.content.contains("正在发送") || notifications.content.contains("邮件发送")) {
            LogUtil.i("NotificationReceiveService", "QQ邮箱发送邮件时会监听到邮件推送通知,不推送!");
            return false;
        }
        return true;
    }
}
