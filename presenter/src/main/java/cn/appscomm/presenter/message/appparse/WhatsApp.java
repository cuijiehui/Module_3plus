package cn.appscomm.presenter.message.appparse;

import android.text.TextUtils;

import cn.appscomm.presenter.mode.Notifications;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/24
 * 说明：
 */

public class WhatsApp {

    public static boolean parse(Notifications notifications) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            if (!TextUtils.isEmpty(notifications.summaryText) && TextUtils.isEmpty(notifications.bigText)) {                // 对于whatApp来说,summaryText装的是xx条消息,如果不为空则不推送
                LogUtil.i("NotificationReceiveService", "whatApp推送的是xxx条消息,不推送给设备...!!!");
                return false;
            }
            notifications.text = TextUtils.isEmpty(notifications.bigText) ? notifications.text : notifications.bigText;
        }
        notifications.content = notifications.name + (TextUtils.isEmpty(notifications.text) ? notifications.content : notifications.text);
        if (TextUtils.isEmpty(notifications.content) && !TextUtils.isEmpty(notifications.text)) {
            notifications.content = notifications.text;
        }
        return true;
    }
}
