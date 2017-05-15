package cn.appscomm.bluetooth.protocol.AboutProductTest;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * Created by Administrator on 2016/11/18.
 */

public class ShockTest extends Leaf {

    /**
     * 震动测试
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content71                内容
     */
    public ShockTest(IBluetoothResultCallback iBluetoothResultCallback, int len, byte content71) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SHOCK_TEST, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{content71};
        LogUtil.i(TAG, "震动测试");
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
