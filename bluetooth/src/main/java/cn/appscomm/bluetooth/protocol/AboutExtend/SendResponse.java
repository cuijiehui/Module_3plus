package cn.appscomm.bluetooth.protocol.AboutExtend;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothLeService;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 主要用于音乐控制、远程拍照、寻找手机
 * 返回结果给设备
 */
public class SendResponse extends Leaf {

    public SendResponse(byte commandCode, byte responseResult) {
        super(null, BluetoothCommandConstant.COMMAND_CODE_RESPONSE, BluetoothCommandConstant.ACTION_SET_RESPONSE);
        byte[] contentLen = new byte[]{0x02, 0x00};
        byte[] content = new byte[]{commandCode, responseResult};
        super.setActiveCommand(false);                                                              // 设置为被动命令
        super.setBluetoothSendType(BluetoothLeService.SEND_TYPE_8003);                              // 设置为8003特征发送
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
