package cn.appscomm.bluetooth.protocol.AboutSport;


import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 运动/睡眠 模式
 * Created by Administrator on 2016/1/27.
 */
public class SportSleepMode extends Leaf {

    /**
     * 运动/睡眠 模式
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public SportSleepMode(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SPORT_SLEEP_MODE, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 获取运动睡眠模式...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为1
     * 6F 51 80   01 00   00   8F(运动模式)
     * 1、0x00:设备处于运动模式   0x01:设备处于睡眠模式
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 1) {
            BluetoothVar.sportSleepMode = (int) (contents[0] & 0xFF);
            LogUtil.i(TAG, "查询返回 : 设备处于" + (BluetoothVar.sportSleepMode == 0 ? "运动模式" : "睡眠模式"));
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
