package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 升级模式
 * Created by Administrator on 2016/2/1.
 */
public class UpgradeMode extends Leaf {
    /**
     * 升级模式
     * 构造函数
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content71                内容
     */
    public UpgradeMode(IBluetoothResultCallback iBluetoothResultCallback, int len, byte content71) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_UPGRADE_MODE, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{content71};
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    public UpgradeMode(IBluetoothResultCallback iBluetoothResultCallback, int len, byte content71, byte[] freescaleAddress, byte freescaleSectorCount, byte[] touchAddress, byte touchSectorCount, byte[] heartRateAddress, byte heartRateSectorCount) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_UPGRADE_MODE, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = content71;
        System.arraycopy(freescaleAddress, 0, content, 1, 4);
        content[5] = freescaleSectorCount;
        System.arraycopy(touchAddress, 0, content, 6, 4);
        content[10] = touchSectorCount;
        System.arraycopy(heartRateAddress, 0, content, 11, 4);
        content[15] = heartRateSectorCount;
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
