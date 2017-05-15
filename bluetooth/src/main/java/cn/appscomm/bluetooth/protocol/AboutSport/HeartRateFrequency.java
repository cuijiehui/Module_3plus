package cn.appscomm.bluetooth.protocol.AboutSport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 自动睡眠监测
 * Created by Administrator on 2016/1/27.
 */
public class HeartRateFrequency extends Leaf {

    /**
     * 自动心率
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public HeartRateFrequency(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_AUTO_HEART_RATE, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 自动睡眠监测
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len              内容长度
     */
    /**
     * 自动心率设置
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param heartRateFrequency       频次
     */
    public HeartRateFrequency(IBluetoothResultCallback iBluetoothResultCallback, int len, byte heartRateFrequency) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_AUTO_HEART_RATE, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{heartRateFrequency};
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为5
     * 例子:
     * 6F 5C 80    01 00    05    8F(05为5分钟)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 1) {
            BluetoothVar.heartRateFrequency = contents[0];
            BluetoothVar.heartRateAutoTrackSwitch = BluetoothVar.heartRateFrequency > 0;
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
