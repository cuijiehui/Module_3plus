package cn.appscomm.presenter.message;

import java.util.HashMap;
import java.util.Map;

import cn.appscomm.presenter.mode.NotificationRecord;

/**
 * Created by Administrator on 2017/3/9.
 */

public class PackageMap {

    public static final byte TYPE_SOCIAL = (byte) 0x02;                                             // 社交
    public static final byte TYPE_EMAIL = (byte) 0x03;                                              // 邮件
    public static final byte TYPE_CALENDAR = (byte) 0x04;                                           // 农历

    public static final byte TYPE_WECHAT = (byte) 0x07;                                             // 微信
    public static final byte TYPE_VIBER = (byte) 0x08;                                              // Viber
    public static final byte TYPE_SNAPCHAT = (byte) 0x09;                                           // Snapchat
    public static final byte TYPE_WHATSAPP = (byte) 0x0A;                                           // WhatsApp
    public static final byte TYPE_QQ = (byte) 0x0B;                                                 // QQ
    public static final byte TYPE_FACEBOOK = (byte) 0x0C;                                           // Facebook
    public static final byte TYPE_HANGOUTS = (byte) 0x0D;                                           // Hangouts
    public static final byte TYPE_GMAIL = (byte) 0x0E;                                              // Gmail
    public static final byte TYPE_MESSENGER = (byte) 0x0F;                                          // Facebook Messenger
    public static final byte TYPE_INSTAGRAM = (byte) 0x10;                                          // Instagram
    public static final byte TYPE_TWITTER = (byte) 0x11;                                            // Twitter
    public static final byte TYPE_LINKEDIN = (byte) 0x12;                                           // Linkedin
    public static final byte TYPE_UBER = (byte) 0x13;                                               // Uber
    public static final byte TYPE_LINE = (byte) 0x14;                                               // Line
    public static final byte TYPE_SKYPE = (byte) 0x15;                                              // Skype

    public static final String TYPE_NOTE_EMAIL_QQ = "TYPE_NOTE_EMAIL_QQ";
    public static final String TYPE_NOTE_SOCIAL_WHATS_APP = "TYPE_NOTE_SOCIAL_WHATS_APP";
    public static final String TYPE_NOTE_SOCIAL_FACEBOOK_MESSENGER = "TYPE_NOTE_SOCIAL_FACEBOOK_MESSENGER";
    public static final String TYPE_NOTE_SOCIAL_SKYPE = "TYPE_NOTE_SOCIAL_SKYPE";

    private static Map<String, NotificationRecord> packageMap = new HashMap<>();

    public static Map<String, NotificationRecord> getMap() {
        if (packageMap == null) {
            packageMap = new HashMap<>();
        }
        if (packageMap.size() == 0) {
            initData();
        }
        return packageMap;
    }

    private static void initData() {
        packageMap.put("com.android.email", new NotificationRecord(TYPE_EMAIL));                                            // android邮箱
        packageMap.put("com.google.android.gm", new NotificationRecord(TYPE_EMAIL));                                        // 谷歌邮箱
        packageMap.put("com.outlook.Z7", new NotificationRecord(TYPE_EMAIL));                                               // outlook邮箱
        packageMap.put("com.tencent.androidqqmail", new NotificationRecord(TYPE_EMAIL, TYPE_NOTE_EMAIL_QQ));                // QQ邮箱
        packageMap.put("cn.cj.pe", new NotificationRecord(TYPE_EMAIL));                                                     // 139邮箱
        packageMap.put("com.kingsoft.email", new NotificationRecord(TYPE_EMAIL));                                           // wps邮箱
        packageMap.put("com.netease.mobimail", new NotificationRecord(TYPE_EMAIL));                                         // 网易邮箱
        packageMap.put("com.sina.mail", new NotificationRecord(TYPE_EMAIL));                                                // 新浪邮箱
        packageMap.put("com.yahoo.mobile.client.android.mail", new NotificationRecord(TYPE_EMAIL));                         // 雅虎邮箱

        packageMap.put("com.android.calendar", new NotificationRecord(TYPE_CALENDAR));                                      // android日历
        packageMap.put("com.htc.calendar", new NotificationRecord(TYPE_CALENDAR));                                          // htc日历
        packageMap.put("com.bbk.calendar", new NotificationRecord(TYPE_CALENDAR));                                          // bbk日历
        packageMap.put("com.google.android.calendar", new NotificationRecord(TYPE_CALENDAR));                               // 谷歌日历

        packageMap.put("com.tencent.mm", new NotificationRecord(TYPE_WECHAT));                                              // 微信         OK
        packageMap.put("com.viber.voip", new NotificationRecord(TYPE_VIBER));                                               // Viber        不能推送内容 xxx给您发送了一条消息
        packageMap.put("com.snapchat.android", new NotificationRecord(TYPE_SNAPCHAT));                                      // Snapchat     不能推送内容
        packageMap.put("com.whatsapp", new NotificationRecord(TYPE_WHATSAPP, TYPE_NOTE_SOCIAL_WHATS_APP));                  // Whatsapp     OK
        packageMap.put("com.tencent.mobileqq", new NotificationRecord(TYPE_QQ));                                            // 手机QQ        OK
        packageMap.put("com.tencent.qqlite", new NotificationRecord(TYPE_QQ));                                              // QQ轻聊版      OK
        packageMap.put("com.facebook.katana", new NotificationRecord(TYPE_FACEBOOK));                                       // Facebook     OK
        packageMap.put("com.facebook.orca", new NotificationRecord(TYPE_MESSENGER, TYPE_NOTE_SOCIAL_FACEBOOK_MESSENGER));   // Facebook Messenger(如果不能推送,请确定"浮动聊天头像"功能有没有关闭) OK
        packageMap.put("com.google.android.talk", new NotificationRecord(TYPE_HANGOUTS));                                   // Hangouts     没测试
        packageMap.put("com.google.android.gm", new NotificationRecord(TYPE_GMAIL));                                        // gmail
        packageMap.put("com.instagram.android", new NotificationRecord(TYPE_INSTAGRAM));                                    // Instagram    OK
        packageMap.put("com.twitter.android", new NotificationRecord(TYPE_TWITTER));                                        // Twitter      OK
        packageMap.put("com.linkedin.android", new NotificationRecord(TYPE_LINKEDIN));                                      // Linkedin     没测试
        packageMap.put("com.ubercab", new NotificationRecord(TYPE_UBER));                                                   // Uber         没测试
        packageMap.put("jp.naver.line.android", new NotificationRecord(TYPE_LINE));                                         // LINE         OK
        packageMap.put("com.skype.android.verizon", new NotificationRecord(TYPE_SKYPE, TYPE_NOTE_SOCIAL_SKYPE));            // Skype        OK
        packageMap.put("com.skype.polaris", new NotificationRecord(TYPE_SKYPE, TYPE_NOTE_SOCIAL_SKYPE));                    // Skype        OK
        packageMap.put("com.skype.rover", new NotificationRecord(TYPE_SKYPE, TYPE_NOTE_SOCIAL_SKYPE));                      // Skype        OK
        packageMap.put("com.skype.raider", new NotificationRecord(TYPE_SKYPE, TYPE_NOTE_SOCIAL_SKYPE));                     // Skype        OK
    }
}
