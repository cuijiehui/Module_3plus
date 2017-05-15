package cn.appscomm.bluetooth.protocol.AboutSetting;

import java.util.Arrays;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * watchID
 * Created by Administrator on 2016/1/26.
 */
public class WatchID extends Leaf {

    /**
     * WatchID
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public WatchID(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_WATCH_ID, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定
     * 例子:
     * 6F 02 80   14 00   46 43 4C 33 30 42 31 35 30 39 32 33 30 31 30 30 30 30 31 31   8F(watchID:FCL30B15092301000011)
     * 1~N:字符串类型，左靠齐
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            try {
                String watchID = new String(Arrays.copyOfRange(contents, 0, len), "US-ASCII");
                BluetoothVar.watchID = watchID;
                LogUtil.i(TAG, "watchID : " + watchID);
                ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
            } catch (Exception e) {
            }
        } else {
            ret = BluetoothCommandConstant.RESULT_CODE_FAIL;
        }
        return ret;
    }
}
