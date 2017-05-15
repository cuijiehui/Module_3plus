package cn.appscomm.bluetooth.protocol.AboutExtend;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothLeService;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 作者：hsh
 * 日期：2017/3/21
 * 说明：手机联系人
 */

public class PhoneContact extends Leaf {
    /**
     * 手机联系人
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param attribute                属性(0xA0:第一条联系人 0xA1:中间条数的联系人 0xA2:最后一条联系人,传输完成 0xA3:只有一条联系人)
     * @param ground                   所属分组
     * @param contactNumberLen         联系人号码长度
     * @param contactNameLen           联系人姓名长度
     * @param bNumberName              号码+姓名
     */
    public PhoneContact(IBluetoothResultCallback iBluetoothResultCallback, int len, byte attribute, byte ground, byte contactNumberLen, byte contactNameLen, byte[] bNumberName) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PHONE_CONTACT, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = attribute;
        content[1] = ground;
        content[2] = contactNumberLen;
        content[3] = contactNameLen;
        System.arraycopy(bNumberName, 0, content, 4, bNumberName.length);
        super.setBluetoothSendType(BluetoothLeService.SEND_TYPE_8003);                              // 8003特征发送
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
