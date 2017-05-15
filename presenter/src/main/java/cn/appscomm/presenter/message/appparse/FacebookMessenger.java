package cn.appscomm.presenter.message.appparse;

import android.text.TextUtils;

import cn.appscomm.presenter.mode.Notifications;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/24
 * 说明：
 */

public class FacebookMessenger {

    public static boolean parse(Notifications notifications) {
        if (!TextUtils.isEmpty(notifications.name) && !TextUtils.isEmpty(notifications.text)) {
            if (notifications.name.contains(":") && notifications.text.contains(notifications.name.split(":")[0])) {
                notifications.content = notifications.text;
            } else {
                notifications.content = notifications.name + notifications.text;
            }
        }
        if (notifications.content.startsWith("Chat heads active:") ||
                notifications.content.startsWith("Bulles de discussion activées:") ||
                notifications.content.startsWith("浮动聊天头像使用中:") ||
                notifications.content.startsWith("聊天大頭貼使用中:")) {
            LogUtil.e("", "过滤Facebook Messenger特定信息");
            return false;
        }
        return true;
    }
}
