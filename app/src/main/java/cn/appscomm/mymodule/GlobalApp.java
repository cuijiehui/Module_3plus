package cn.appscomm.mymodule;

import android.app.Application;

import cn.appscomm.presenter.PresenterAppContext;
import cn.appscomm.presenter.util.LogUtil;

/**
 * Created by Administrator on 2017/3/6.
 */

public class GlobalApp extends Application {
    private final String TAG = GlobalApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        PresenterAppContext.INSTANCE.init(getApplicationContext(), true, false);
    }
}