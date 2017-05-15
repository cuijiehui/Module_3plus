package cn.appscomm.bluetooth;

import android.content.Context;

/**
 * 作者：hsh
 * 日期：2017/3/17
 * 说明：蓝牙的ApplicationContext
 */
public enum BluetoothAppContext {
    INSTANCE;
    private Context context;

    /**
     * BluetoothAppContext，主要是获取应用的Application的Context
     *
     * @param context 应用的Application的Context
     */
    public void init(Context context) {
        this.context = context;
    }

    /**
     * 获取Application的Context
     *
     * @return 返回Application的Context
     */
    public Context getContext() {
        return this.context;
    }
}
