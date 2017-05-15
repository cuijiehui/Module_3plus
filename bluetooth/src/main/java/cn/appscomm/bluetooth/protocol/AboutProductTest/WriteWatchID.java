package cn.appscomm.bluetooth.protocol.AboutProductTest;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * Created by Administrator on 2016/11/18.
 */

public class WriteWatchID extends Leaf {

    /**
     * 写WatchID
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param bWatchID                 新的WatchID
     */
    public WriteWatchID(IBluetoothResultCallback iBluetoothResultCallback, int len, byte[] bWatchID) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_WRITE_WATCHID, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[bWatchID.length + 4];
        content[0] = (byte) 0x24;
        content[1] = (byte) 0x24;
        content[content.length - 1] = (byte) 0x26;
        content[content.length - 2] = (byte) 0x26;
        System.arraycopy(bWatchID, 0, content, 2, bWatchID.length);
        LogUtil.i(TAG, "写WatchID");
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
