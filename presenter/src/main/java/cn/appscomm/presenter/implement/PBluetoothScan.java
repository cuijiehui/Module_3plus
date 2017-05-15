package cn.appscomm.presenter.implement;

import cn.appscomm.bluetoothscan.implement.MBluetoothScan;
import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.bluetoothscan.interfaces.PMBluetoothScanCall;
import cn.appscomm.presenter.interfaces.PVBluetoothScanCall;

/**
 * Created by Administrator on 2017/3/6.
 */
public enum PBluetoothScan implements PVBluetoothScanCall {
    INSTANCE;

    private PMBluetoothScanCall mCall = MBluetoothScan.INSTANCE;                               // 用户调用M层的方法

    @Override
    public void startScan(IBluetoothScanResultCallBack callBack) {
        mCall.startScan(callBack);
    }

    @Override
    public void stopScan() {
        mCall.stopScan();
    }
}
