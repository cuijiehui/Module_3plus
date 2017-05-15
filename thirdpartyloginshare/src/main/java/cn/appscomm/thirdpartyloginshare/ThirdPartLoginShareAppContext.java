package cn.appscomm.thirdpartyloginshare;

import android.content.Context;

/**
 * Created by Administrator on 2017/2/28.
 */
public enum ThirdPartLoginShareAppContext {
    INSTANCE;
    private Context context;

    /**
     * ThirdPartLoginShareAppContext，主要是获取应用的Application的Context
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
