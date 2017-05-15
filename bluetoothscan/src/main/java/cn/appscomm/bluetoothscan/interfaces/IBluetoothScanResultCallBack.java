package cn.appscomm.bluetoothscan.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * 作者：hsh
 * 日期：2017/3/27
 * 说明：
 */

public interface IBluetoothScanResultCallBack {
    /**
     * 扫描回调
     *
     * @param device 扫描到的蓝牙设备
     * @param rssi   蓝牙设备的信号强度
     */
    void onLeScan(BluetoothDevice device, int rssi);
}
