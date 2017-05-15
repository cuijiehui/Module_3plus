package cn.appscomm.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import cn.appscomm.bluetoothscan.BluetoothScanAppContext;
import cn.appscomm.bluetooth.BluetoothAppContext;
import cn.appscomm.db.DBAppContext;
import cn.appscomm.ota.util.OtaAppContext;
import cn.appscomm.presenter.message.manager.CallSMSManager;
import cn.appscomm.presenter.remotecontrol.RemoteControlManager;
import cn.appscomm.presenter.server.NotificationReceiveService;
import cn.appscomm.presenter.util.LogUtil;
import cn.appscomm.server.util.ServerAppContext;
import cn.appscomm.sp.SpAppContext;
import cn.appscomm.thirdpartyloginshare.ThirdPartLoginShareAppContext;

/**
 * Created by Administrator on 2017/2/28.
 */

public enum PresenterAppContext {
    INSTANCE;
    private final String TAG = PresenterAppContext.class.getSimpleName();
    private Context context;

    /**
     * PresenterAppContext，主要是获取应用的Application的Context
     *
     * @param context     应用的Application的Context
     * @param isPrintfLog 是否打印日志
     * @param isWriteLog  是否写日志
     */
    public void init(Context context, boolean isPrintfLog, boolean isWriteLog) {
        LogUtil.init(isPrintfLog, isWriteLog);
        this.context = context;
        ServerAppContext.INSTANCE.init(context);
        SpAppContext.INSTANCE.init(context);
        DBAppContext.INSTANCE.init(context);
        ThirdPartLoginShareAppContext.INSTANCE.init(context);
        BluetoothAppContext.INSTANCE.init(context);
        BluetoothScanAppContext.INSTANCE.init(context);
        OtaAppContext.INSTANCE.init(context);

        initMessagePush();
        RemoteControlManager.INSTANCE.init();
    }

    private void initMessagePush() {
        Intent intent = new Intent(context, CallSMSManager.class);
        context.stopService(intent);
        context.startService(intent);
        try {
            PackageManager pm = context.getPackageManager();
            ComponentName componentName = new ComponentName(context, NotificationReceiveService.class);
            pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        } catch (NoClassDefFoundError e) {
            LogUtil.i(TAG, "没找到该类的错误，先不管...!!!");
        }
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
