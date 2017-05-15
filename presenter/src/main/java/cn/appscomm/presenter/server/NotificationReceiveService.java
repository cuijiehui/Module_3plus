package cn.appscomm.presenter.server;

import android.app.Notification;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import cn.appscomm.presenter.message.manager.NotificationManager;
import cn.appscomm.presenter.mode.Notifications;
import cn.appscomm.presenter.remotecontrol.RemoteControlManager;
import cn.appscomm.presenter.util.LogUtil;


/**
 * Created by Administrator on 2016/8/12.
 */
public class NotificationReceiveService extends NotificationListenerService {
    private String TAG = this.getClass().getSimpleName();
    private Handler myHandler = new Handler();

    @Override
    public void onCreate() {
        LogUtil.i(TAG, "社交/日历/邮箱 推送服务启动...!!!");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, "关闭通知推送服务...!!!");
        super.onDestroy();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (sbn != null) {
            Log.e(TAG, "移除消息...");
            String packageName = sbn.getPackageName();
            if (!TextUtils.isEmpty(packageName)) {
                NotificationManager.INSTANCE.removeNotification(myHandler, packageName);
            }
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        LogUtil.v(TAG, "新消息到来...");
        Notifications notifications = new Notifications();
        try {
            Notification notification = sbn.getNotification();
            notifications.packageName = sbn.getPackageName();
            notifications.content = notification.tickerText != null ? notification.tickerText.toString() : "";
            notifications.timeStamp = sbn.getPostTime();
            notifications.title = getPackageManager().getApplicationLabel(getPackageManager().getPackageInfo(sbn.getPackageName(), 0).applicationInfo).toString();
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                notifications.name = notification.extras.getString(Notification.EXTRA_TITLE) + ": ";
                notifications.text = notification.extras.getString(Notification.EXTRA_TEXT);
                notifications.summaryText = notification.extras.getString(Notification.EXTRA_SUMMARY_TEXT);
                notifications.bigText = (android.os.Build.VERSION.SDK_INT >= 21) ? notification.extras.getString(Notification.EXTRA_BIG_TEXT) : "";
            }
            notifications.content = (TextUtils.isEmpty(notifications.content) && !TextUtils.isEmpty(notifications.text)) ? notifications.text : notifications.content;
        } catch (Exception e) {
        }
        LogUtil.i(TAG, "消息:" + notifications.toString());
        if (!TextUtils.isEmpty(notifications.packageName) && !TextUtils.isEmpty(notifications.title) && notifications.timeStamp > 0L) {
            NotificationManager.INSTANCE.parseNotification(myHandler, notifications);
        }
        if (!TextUtils.isEmpty(notifications.packageName)) {
            RemoteControlManager.INSTANCE.parseSongName(notifications);
        }
    }
}