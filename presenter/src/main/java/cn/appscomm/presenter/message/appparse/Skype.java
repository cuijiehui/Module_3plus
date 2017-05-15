package cn.appscomm.presenter.message.appparse;

import android.text.TextUtils;

import cn.appscomm.presenter.mode.Notifications;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/24
 * 说明：
 */

public class Skype {

    public static boolean parse(Notifications notifications) {
        if (!notifications.content.contains("\n")) {
            LogUtil.i("NotificationReceiveService", "过滤不带名称的那一条Skype信息以及系统发的 未接通或在线 信息");
            return false;
        }
        notifications.name = TextUtils.isEmpty(notifications.name) ? "" : notifications.name.replace(":", "").trim();
        notifications.content = !TextUtils.isEmpty(notifications.content) && !TextUtils.isEmpty(notifications.name) ? notifications.content.replace(notifications.name, notifications.name + ":") : "";
        return true;
    }
}
