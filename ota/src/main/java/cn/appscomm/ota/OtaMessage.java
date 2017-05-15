package cn.appscomm.ota;

import java.util.Arrays;

/**
 * 作者：hsh
 * 日期：2017/3/17
 * 说明：蓝牙间的总线消息定义
 */
public class OtaMessage {
    private static final String MSG_HEAD = "cn.appscomm.ota.";

    public static final String ACTION_GATT_CONNECTED = MSG_HEAD + "ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = MSG_HEAD + "ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_DISCOVERED = MSG_HEAD + "ACTION_GATT_DISCOVERED";
    public static final String ACTION_DATA_AVAILABLE = MSG_HEAD + "ACTION_DATA_AVAILABLE";
    public static final String ACTION_DATA_WRITE_CALLBACK = MSG_HEAD + "ACTION_DATA_WRITE_CALLBACK";

    public String msgType;
    public byte[] msgContent;

    public OtaMessage(String msgType, byte[] msgContent) {
        this.msgType = msgType;
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "OTA总线消息{" +
                "类型=" + msgType +
                ", 内容=" + Arrays.toString(msgContent) +
                '}';
    }
}
