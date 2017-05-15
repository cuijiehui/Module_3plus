package cn.appscomm.bluetoothscan.interfaces;

/**
 * 作者：hsh
 * 日期：2017/3/27
 * 说明：
 */

public interface PMBluetoothScanCall {
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
