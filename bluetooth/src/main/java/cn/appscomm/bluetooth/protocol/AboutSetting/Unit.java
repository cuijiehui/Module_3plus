package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 单位
 * Created by Administrator on 2016/1/27.
 */
public class Unit extends Leaf {

    /**
     * 单位
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public Unit(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_UNIT, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 准备获取单位...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 单位
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param unitType                 单位类型
     */
    public Unit(IBluetoothResultCallback iBluetoothResultCallback, int len, byte unitType) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_UNIT, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{unitType};
        LogUtil.i(TAG, "设置 : 准备设置单位...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }


    /**
     * contents字节数组解析：
     * 长度固定为1
     * 6F 0C 80   01 00   01   8F(英里)
     * 0x00(公里)   0x01(英里)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 1) {
            BluetoothVar.unit = (int) (contents[0] & 0xFF);
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
