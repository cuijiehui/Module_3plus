package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 恢复出厂
 * Created by Administrator on 2016/1/27.
 */
public class RestoreFactory extends Leaf {

    /**
     * 恢复出厂
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content71                内容
     */
    public RestoreFactory(IBluetoothResultCallback iBluetoothResultCallback, int len, byte content71) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_RESTORE_FACTORY, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{content71};
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
