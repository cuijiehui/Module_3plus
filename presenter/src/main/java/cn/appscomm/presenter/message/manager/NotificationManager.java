package cn.appscomm.presenter.message.manager;

import android.os.Handler;
import android.text.TextUtils;

import cn.appscomm.presenter.message.appparse.FacebookMessenger;
import cn.appscomm.presenter.message.PackageMap;
import cn.appscomm.presenter.message.appparse.QQEmail;
import cn.appscomm.presenter.message.appparse.Skype;
import cn.appscomm.presenter.message.appparse.WhatsApp;
import cn.appscomm.presenter.mode.NotificationInfo;
import cn.appscomm.presenter.mode.NotificationRecord;
import cn.appscomm.presenter.mode.Notifications;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/24
 * 说明：
 */

public enum NotificationManager {
    INSTANCE;

    private boolean socialSwitch = false;
    private boolean emailSwitch = false;
    private boolean calendarSwitch = false;

    private static int emailCount;
    private static int calendarCount;

    private void checkNotificationSwitch() {
        boolean[] notificationSwitch = MessageManager.INSTANCE.checkNotificationSwitch();
        if (notificationSwitch != null && notificationSwitch.length == 3) {
            socialSwitch = notificationSwitch[0];
            emailSwitch = notificationSwitch[1];
            calendarSwitch = notificationSwitch[2];
        }
        LogUtil.i("NotificationReceiveService", "社交开关:" + socialSwitch + " 邮件开关:" + emailSwitch + " 日历开关:" + calendarSwitch);
    }

    NotificationRecord removeNotifications;
    Runnable removeMsgCountRunnable = new Runnable() {
        @Override
        public void run() {
            if (removeNotifications != null) {
                LogUtil.i("NotificationReceiveService", "社交清零...");
                removeNotifications.notificationCount = 0;
            }
        }
    };

    // 移除消息
    public void removeNotification(Handler myHandler, String packageName) {
        NotificationRecord notificationRecord = PackageMap.getMap().get(packageName);
        if (notificationRecord != null && (Math.abs(System.currentTimeMillis() - notificationRecord.removeTime) > 500)) {
            switch (notificationRecord.notificationType) {
                case PackageMap.TYPE_EMAIL:
                    emailCount--;
                    emailCount = emailCount < 0 ? 0 : emailCount;
                    break;
                case PackageMap.TYPE_CALENDAR:
                    calendarCount--;
                    calendarCount = calendarCount < 0 ? 0 : calendarCount;
                    break;
                case PackageMap.TYPE_SOCIAL:
                case PackageMap.TYPE_WECHAT:
                case PackageMap.TYPE_VIBER:
                case PackageMap.TYPE_SNAPCHAT:
                case PackageMap.TYPE_WHATSAPP:
                case PackageMap.TYPE_FACEBOOK:
                case PackageMap.TYPE_HANGOUTS:
                case PackageMap.TYPE_GMAIL:
                case PackageMap.TYPE_MESSENGER:
                case PackageMap.TYPE_INSTAGRAM:
                case PackageMap.TYPE_TWITTER:
                case PackageMap.TYPE_LINKEDIN:
                case PackageMap.TYPE_UBER:
                case PackageMap.TYPE_LINE:
                case PackageMap.TYPE_SKYPE:
                    notificationRecord.notificationCount = 0;
                    break;
                case PackageMap.TYPE_QQ:
                    removeNotifications = notificationRecord;
                    myHandler.postDelayed(removeMsgCountRunnable, 1000);                             // 延迟1秒钟执行，若1秒钟内有新消息过来，则取消执行(解决QQ有新消息过来会先移除消息)
                    break;
            }
            notificationRecord.removeTime = System.currentTimeMillis();
        }
    }

    // 解析消息并发送
    public void parseNotification(Handler myHandler, Notifications notifications) {
        myHandler.removeCallbacks(removeMsgCountRunnable);
        boolean result = true;
        NotificationRecord notificationRecord = PackageMap.getMap().get(notifications.packageName);
        if (notificationRecord != null && (Math.abs(System.currentTimeMillis() - notificationRecord.addTime) > 100)) {
            notificationRecord.addTime = System.currentTimeMillis();
            if (!TextUtils.isEmpty(notificationRecord.note)) {
                switch (notificationRecord.note) {
                    case PackageMap.TYPE_NOTE_SOCIAL_WHATS_APP:
                        LogUtil.i("NotificationReceiveService", "WhatsApp特殊处理");
                        notificationRecord.addTime = 0L;
                        result = WhatsApp.parse(notifications);
                        break;
                    case PackageMap.TYPE_NOTE_SOCIAL_FACEBOOK_MESSENGER:
                        LogUtil.i("NotificationReceiveService", "Facebook Messenger特殊处理");
                        result = FacebookMessenger.parse(notifications);
                        break;
                    case PackageMap.TYPE_NOTE_SOCIAL_SKYPE:
                        LogUtil.i("NotificationReceiveService", "Skype特殊处理");
                        result = Skype.parse(notifications);
                        break;
                    case PackageMap.TYPE_NOTE_EMAIL_QQ:
                        LogUtil.i("NotificationReceiveService", "QQ邮件特殊处理");
                        result = QQEmail.parse(notifications);
                        break;
                }
            }
            if (result) {
                checkNotificationSwitch();
                switch (notificationRecord.notificationType) {
                    case PackageMap.TYPE_SOCIAL:
                    case PackageMap.TYPE_WECHAT:
                    case PackageMap.TYPE_VIBER:
                    case PackageMap.TYPE_SNAPCHAT:
                    case PackageMap.TYPE_WHATSAPP:
                    case PackageMap.TYPE_QQ:
                    case PackageMap.TYPE_FACEBOOK:
                    case PackageMap.TYPE_HANGOUTS:
                    case PackageMap.TYPE_GMAIL:
                    case PackageMap.TYPE_MESSENGER:
                    case PackageMap.TYPE_INSTAGRAM:
                    case PackageMap.TYPE_TWITTER:
                    case PackageMap.TYPE_LINKEDIN:
                    case PackageMap.TYPE_UBER:
                    case PackageMap.TYPE_LINE:
                    case PackageMap.TYPE_SKYPE:
                        if (socialSwitch) {
                            LogUtil.i("NotificationReceiveService", "发送社交");
                            notificationRecord.notificationCount++;
                            MessageManager.INSTANCE.sendSocial(new NotificationInfo(notifications.title, notifications.content, notifications.timeStamp, notificationRecord.notificationType, notificationRecord.notificationCount));
                        }
                        break;
                    case PackageMap.TYPE_EMAIL:
                        if (emailSwitch) {
                            LogUtil.i("NotificationReceiveService", "发送邮件");
                            calendarCount = calendarCount < 0 ? 0 : calendarCount;
                            calendarCount++;
                            MessageManager.INSTANCE.sendCalendar(calendarCount);
                        }
                        break;
                    case PackageMap.TYPE_CALENDAR:
                        if (calendarSwitch) {
                            LogUtil.i("NotificationReceiveService", "发送日历");
                            emailCount = emailCount < 0 ? 0 : emailCount;
                            emailCount++;
                            MessageManager.INSTANCE.sendEmail(new NotificationInfo(notifications.title, notifications.content, notifications.timeStamp, notificationRecord.notificationType, emailCount));
                        }
                        break;
                }
            }
        }
    }


}
