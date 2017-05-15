package cn.appscomm.mymodule;

import java.util.List;

import cn.appscomm.bluetooth.mode.HeartRateBT;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.mode.SleepBT;
import cn.appscomm.bluetooth.mode.SportBT;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PBluetoothScan;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.implement.POta;
import cn.appscomm.presenter.implement.POther;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.presenter.implement.PServer;
import cn.appscomm.presenter.interfaces.PVBluetoothCall;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;
import cn.appscomm.presenter.interfaces.PVBluetoothScanCall;
import cn.appscomm.presenter.interfaces.PVDBCall;
import cn.appscomm.presenter.interfaces.PVOtaCall;
import cn.appscomm.presenter.interfaces.PVOtherCall;
import cn.appscomm.presenter.interfaces.PVSPCall;
import cn.appscomm.presenter.interfaces.PVServerCall;
import cn.appscomm.presenter.interfaces.PVServerCallback;
import cn.appscomm.server.mode.base.BaseResponse;

/**
 * Created by Administrator on 2017/3/7.
 */

public class BaseUI {

    public PVServerCall pvServerCall = PServer.INSTANCE;
    public PVSPCall pvSpCall = PSP.INSTANCE;
    public PVDBCall pvDbCall = PDB.INSTANCE;
    public PVBluetoothCall pvBluetoothCall = PBluetooth.INSTANCE;
    public PVOtaCall pvOtaCall = POta.INSTANCE;
    public PVOtherCall pvOtherCall = POther.INSTANCE;
    public PVBluetoothScanCall pvBluetoothScanCall = PBluetoothScan.INSTANCE;

    public int bluetoothDataInt;                                                                    // 蓝牙返回的结果类型：int
    public float bluetoothDataFloat;                                                                // 蓝牙返回的结果类型：float
    public boolean bluetoothDataBoolean;                                                            // 蓝牙返回的结果类型：boolean
    public String bluetoothDataString;                                                              // 蓝牙返回的结果类型：String
    public int[] bluetoothDataIntArray;                                                             // 蓝牙返回的结果类型：int[]
    public List<SportBT> bluetoothDataListSport;                                                    // 蓝牙返回的结果类型：List<SportBT>
    public List<SleepBT> bluetoothDataListSleep;                                                    // 蓝牙返回的结果类型：List<SleepBT>
    public List<HeartRateBT> bluetoothDataListHeartRate;                                            // 蓝牙返回的结果类型：List<HeartRateBT>
    public List<ReminderBT> bluetoothDataListReminder;                                              // 蓝牙返回的结果类型：List<ReminderBT>
    public List<Integer> bluetoothDataListInt;                                                      // 蓝牙返回的结果类型：List<Integer>

    public PVBluetoothCallback pvBluetoothCallback = new PVBluetoothCallback() {
        @Override
        public void onSuccess(Object[] objects, int len, int dataType, String mac, BluetoothCommandType bluetoothCommandType) {
            if (len <= 0 || objects == null || len != objects.length) {
                onBluetoothFail(bluetoothCommandType);
            }
            switch (dataType) {
                case PVBluetoothCallback.DATA_TYPE_INT:
                    bluetoothDataInt = (int) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_FLOAT:
                    bluetoothDataFloat = (float) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_BOOLEAN:
                    bluetoothDataBoolean = (boolean) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_STRING:
                    bluetoothDataString = (String) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_INT_ARRAY:
                    bluetoothDataIntArray = new int[len];
                    for (int i = 0; i < len; i++) {
                        bluetoothDataIntArray[i] = (int) objects[i];
                    }
                    break;
                case PVBluetoothCallback.DATA_TYPE_LIST_SPORT:
                    bluetoothDataListSport = (List<SportBT>) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_LIST_SLEEP:
                    bluetoothDataListSleep = (List<SleepBT>) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_LIST_HEART_RATE:
                    bluetoothDataListHeartRate = (List<HeartRateBT>) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_LIST_REMINDER:
                    bluetoothDataListReminder = (List<ReminderBT>) objects[0];
                    break;
                case PVBluetoothCallback.DATA_TYPE_LIST_INT:
                    bluetoothDataListInt = (List<Integer>) objects[0];
                    break;
            }
            onBluetoothSuccess(bluetoothCommandType);
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            onBluetoothFail(bluetoothCommandType);
        }
    };

    // 网络请求结果
    public PVServerCallback pvServerCallback = new PVServerCallback() {
        @Override
        public void onSuccess(RequestType requestType) {
            onServerSuccess(requestType);
        }

        @Override
        public void onSuccess(BaseResponse baseResponse, RequestType requestType) {
            onServerSuccess(baseResponse, requestType);
        }

        @Override
        public void onFail(RequestType requestType, int responseCode, String message) {
            onServerFail(requestType);
        }
    };

    public void onBluetoothSuccess(PVBluetoothCallback.BluetoothCommandType bluetoothCommandType) {
    }

    public void onBluetoothFail(PVBluetoothCallback.BluetoothCommandType bluetoothCommandType) {
    }

    public void onServerSuccess(PVServerCallback.RequestType requestType) {
    }

    public void onServerSuccess(BaseResponse baseResponse, PVServerCallback.RequestType requestType) {
    }

    public void onServerFail(PVServerCallback.RequestType requestType) {
    }

    public void onDestroy() {
        pvBluetoothCallback = null;
        pvServerCallback = null;
    }
}
