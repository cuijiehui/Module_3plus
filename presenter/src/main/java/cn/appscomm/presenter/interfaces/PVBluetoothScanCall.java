package cn.appscomm.presenter.interfaces;


import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;

/**
 * 作者：hsh
 * 日期：2017/3/06
 * 说明：蓝牙扫描接口
 */
public interface PVBluetoothScanCall {
    /**
     * 开始扫描
     *
     * @param callBack 扫描结果回调
     */
    void startScan(IBluetoothScanResultCallBack callBack);

    /**
     * 结束扫描
     */
    void stopScan();
}
