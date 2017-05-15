package cn.appscomm.bluetooth.protocol.AboutState;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 绑定开始
 * Created by Administrator on 2016/1/27.
 */
public class BindStart extends Leaf {

    /**
     * 绑定开始
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public BindStart(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_BIND_START, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 绑定开始
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param type                     0:设定UID 1:绑定开始
     * @param uid                      若type为0时，则为UID；若type为1时，则不关心该值
     */
    public BindStart(IBluetoothResultCallback iBluetoothResultCallback, int len, byte type, int uid) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_BIND_START, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content;
        if (type == BluetoothCommandConstant.BIND_START_SET_UID) {
            content = new byte[5];
            content[0] = type;
            System.arraycopy(ParseUtil.intToByteArray(uid, 4), 0, content, 1, 4);
        } else {
            content = new byte[]{type};
        }
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 绑定开始没有0x80命令
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            BluetoothVar.UID = (int) ParseUtil.bytesToLong(contents, 0, contents.length - 1);
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
