package cn.appscomm.bluetooth.protocol.AboutSport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 睡眠数据删除命令
 * Created by Administrator on 2016/1/27.
 */
public class DeleteSleepData extends Leaf {

    /**
     * 睡眠数据删除命令
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content71                内容
     */
    public DeleteSleepData(IBluetoothResultCallback iBluetoothResultCallback, int len, int content71) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_DELETE_SLEEP_DATA, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content71, len);
        super.setContentLen(contentLen);
        super.setContent(content);
        LogUtil.i(TAG, "修改 : 准备删除设备中的睡眠数据...");
    }
}
