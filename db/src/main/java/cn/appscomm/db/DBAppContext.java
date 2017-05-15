package cn.appscomm.db;

import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/2/28.
 */

public enum DBAppContext {
    INSTANCE;
    private Context context;

    /**
     * DBAppContext，主要是获取应用的Application的Context
     *
     * @param context 应用的Application的Context
     */
    public void init(Context context) {
        this.context = context;
        LitePal.initialize(context);                                                                // 第三方数据库LitePal初始化
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
