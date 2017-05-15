package cn.appscomm.bluetooth.protocol.AboutSport;


import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 心率数据总数
 * Created by Administrator on 2016/1/27.
 */
public class HeartRateCount extends Leaf {

    /**
     * 心率数据总数
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public HeartRateCount(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_TOTAL_HEART_RATE_COUNT, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 准备获取心率条数...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为2
     * 例子:
     * 6F 58 80   02 00   64 00   8F(100条心率数据)
     * 1~2、心率总数
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 2) {
            BluetoothVar.heartRateCount = (int) ParseUtil.bytesToLong(contents, 0, 1); // 心率总数
            LogUtil.i(TAG, "查询返回 : 心率数据" + BluetoothVar.heartRateCount + "条!!!");
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
