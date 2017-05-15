package cn.appscomm.bluetooth.protocol.AboutMsgPush;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 消息条数推送
 * Created by Administrator on 2016/1/27.
 */
public class MsgCountPush extends Leaf {

    /**
     * 消息条数推送
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param msgType                  消息类型(0x00：未接来电   0x01：短信息   0x02：社交消息   0x03：邮件   0x04：日历   0x05：来电   0x06：来电挂断 ……)
     * @param msgCount                 消息数量
     */
    public MsgCountPush(IBluetoothResultCallback iBluetoothResultCallback, int len, byte msgType, byte msgCount) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_MSG_COUNT_PUSH, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = msgType;
        content[1] = msgCount;
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
