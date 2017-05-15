package cn.appscomm.bluetooth.protocol.AboutMsgPush;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 电话姓名推送
 * Created by Administrator on 2016/1/27.
 */
public class PhoneNamePush extends Leaf {

    /**
     * 电话姓名推送
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param type                     类型(0x00:即时来电   0x01:重拨   0x02:未接来电)
     * @param bName                    姓名/号码(utf-8格式)
     */
    public PhoneNamePush(IBluetoothResultCallback iBluetoothResultCallback, int len, byte type, byte[] bName) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PHONE_NAME_PUSH, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = type;
        System.arraycopy(bName, 0, content, 1, bName.length);
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
