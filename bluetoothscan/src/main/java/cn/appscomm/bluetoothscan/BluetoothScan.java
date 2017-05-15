package cn.appscomm.bluetoothscan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.bluetoothscan.util.LogUtil;

/**
 * Created by Administrator on 2017/2/14.
 */

public enum BluetoothScan {
    INSTANCE;

    private static final String TAG = BluetoothScan.class.getSimpleName();
    private IBluetoothScanResultCallBack iBluetoothScanResultCallBack = null;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning = false;

    private boolean init() {
        if (mBluetoothManager == null) {
            mBluetoothManager = ((BluetoothManager) BluetoothScanAppContext.INSTANCE.getContext().getSystemService(Context.BLUETOOTH_SERVICE));
            if (mBluetoothManager == null) {
                return false;
            }
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        return this.mBluetoothAdapter != null;
    }

    public boolean startScan(IBluetoothScanResultCallBack bluetoothScanResultCallBack) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            return BluetoothScanEx.INSTANCE.startScan(bluetoothScanResultCallBack);
        } else {
            if (!init()) return false;
            this.iBluetoothScanResultCallBack = bluetoothScanResultCallBack;
            return scanLeDevice(true);
        }
    }

    public void stopScan() {
        if (!init()) return;
        this.iBluetoothScanResultCallBack = null;
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            BluetoothScanEx.INSTANCE.stopScan();
        } else {
            scanLeDevice(false);
        }
    }

    private boolean scanLeDevice(boolean enable) {
        LogUtil.i(TAG, "BluetoothScan 处理扫描");
        if (enable) {
            if (mScanning)
                return true;
            boolean flag = false;
            for (int i = 0; i < 30; i++) {
                flag = mBluetoothAdapter.startLeScan(mLeScanCallback);
                LogUtil.e(TAG, "当前为第" + i + "次开启扫描...flag : " + flag);
                if (flag) {
                    break;
                }
            }
            mScanning = flag;
        } else {
            if (!mScanning)
                return true;
            if (mBluetoothAdapter != null && mLeScanCallback != null)
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }
        return mScanning;
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (iBluetoothScanResultCallBack != null) {
                iBluetoothScanResultCallBack.onLeScan(device, rssi);
            }
        }
    };

}
