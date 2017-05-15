package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 音量
 * Created by Administrator on 2016/1/27.
 */
public class Volume extends Leaf {

    /**
     * 音量
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public Volume(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_VOLUME, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 准备获取音量值...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 音量
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param volume                   音量值(byte类型,例如亮度为100,即0x64)
     */
    public Volume(IBluetoothResultCallback iBluetoothResultCallback, int len, byte volume) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_VOLUME, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{volume};
        LogUtil.i(TAG, "设置 : 准备设置音量值...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }


    /**
     * contents字节数组解析：
     * 长度固定为1
     * 例子:
     * 6F 09 80   01 00   64   8F(0x64:100)
     * 十六进制转十进制
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 1) {
            BluetoothVar.volume = (int) (contents[0] & 0xFF);
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
