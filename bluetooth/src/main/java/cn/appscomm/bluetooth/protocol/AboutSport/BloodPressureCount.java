package cn.appscomm.bluetooth.protocol.AboutSport;


import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 血压数据总数
 * Created by Administrator on 2016/1/27.
 */
public class BloodPressureCount extends Leaf {

    /**
     * 血压数据总数
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public BloodPressureCount(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_TOTAL_BLOOD_PRESSURE_COUNT, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 准备获取血压条数...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为2
     * 例子:
     * 6F 58 80   02 00   64 00   8F(100条血压数据)
     * 1~2、血压总数
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 2) {
            BluetoothVar.bloodPressureCount = (int) ParseUtil.bytesToLong(contents, 0, 1);             // 心率总数
            LogUtil.i(TAG, "查询返回 : 血压数据" + BluetoothVar.bloodPressureCount + "条!!!");
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
