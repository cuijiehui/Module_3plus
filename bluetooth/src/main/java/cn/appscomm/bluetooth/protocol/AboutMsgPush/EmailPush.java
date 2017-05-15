package cn.appscomm.bluetooth.protocol.AboutMsgPush;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * Created by Administrator on 2016/8/12.
 */
public class EmailPush extends Leaf {

    /**
     * 邮件推送
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param type                     类型(0x00：发送者的邮箱地址(或名称)   0x01：邮件内容   0x02：邮件时间日期(格式为年月日‘T’时分秒))
     * @param pushContent              推送内容
     */
    public EmailPush(IBluetoothResultCallback iBluetoothResultCallback, int len, byte type, byte[] pushContent) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_EMAIL_PUSH, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = type;
        System.arraycopy(pushContent, 0, content, 1, pushContent.length);
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}

