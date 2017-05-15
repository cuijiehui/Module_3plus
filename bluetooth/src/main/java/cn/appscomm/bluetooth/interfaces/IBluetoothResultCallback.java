package cn.appscomm.bluetooth.interfaces;

import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * Created by duzhe on 16-7-30-0030.
 */
public interface IBluetoothResultCallback {
    /**
     * 发送蓝牙命令成功时回调
     *
     * @param obj 返回发送的内容
     * @param mac 子设备的mac
     */
    void onSuccess(Leaf obj, String mac);

    /**
     * 发送蓝牙命令失败时回调
     *
     * @param obj 返回发送的内容
     * @param mac 子设备的mac
     */
    void onFailed(Leaf obj, String mac);
}
