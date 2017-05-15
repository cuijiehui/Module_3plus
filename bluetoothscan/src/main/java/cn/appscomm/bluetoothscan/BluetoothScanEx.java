package cn.appscomm.bluetoothscan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.bluetoothscan.util.LogUtil;

/**
 * Created by Administrator on 2017/2/14.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
enum BluetoothScanEx {
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
        LogUtil.i(TAG, "mBluetoothAdapter : " + (mBluetoothAdapter != null) + "getBluetoothLeScanner : " + (mBluetoothAdapter.getBluetoothLeScanner() != null) + " scanCallback : " + (scanCallback != null));
        return !(mBluetoothAdapter == null || mBluetoothAdapter.getBluetoothLeScanner() == null || scanCallback == null);
    }

    public boolean startScan(IBluetoothScanResultCallBack bluetoothScanResultCallBack) {
        LogUtil.i(TAG, "1、蓝牙新接口准备开始扫描");
        if (!init()) return false;
        LogUtil.i(TAG, "2、蓝牙新接口开始扫描");
        this.iBluetoothScanResultCallBack = bluetoothScanResultCallBack;
        return scanLeDevice(true);
    }

    public void stopScan() {
        if (!init()) return;
        this.iBluetoothScanResultCallBack = null;
        scanLeDevice(false);
    }

    private boolean scanLeDevice(boolean enable) {
        LogUtil.i(TAG, "BluetoothScanEx " + (enable ? "开启扫描" : "关闭扫描") + " mScanning : " + mScanning);
        if (enable) {
            ScanSettings.Builder builder = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);                               // 推荐使用该配置,扫描速度比较快
            mBluetoothAdapter.getBluetoothLeScanner().startScan(null, builder.build(), scanCallback);
            mScanning = true;
        } else {
            mBluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
            mScanning = false;
        }
        return mScanning;
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (iBluetoothScanResultCallBack != null) {
                iBluetoothScanResultCallBack.onLeScan(result.getDevice(), result.getRssi());
            }
        }
    };
}
