package cn.appscomm.server.mode.base;

import android.content.Context;
import android.content.pm.PackageManager;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.util.ServerAppContext;


/**
 * Created by Administrator on 2017/2/28.
 */

public class BaseRequest {

    public String seq;
    public String versionNo;
    public int language;
    public String clientType;

    public BaseRequest() {
        seq = (int) (System.currentTimeMillis() / 1000) + "";
        versionNo = "1.0.0";
        try {
            Context context = ServerAppContext.INSTANCE.getContext();
            versionNo = ((context.getPackageManager().getPackageInfo(context.getPackageName(), 0)).versionName) + "";
        } catch (PackageManager.NameNotFoundException e) {
        }
        this.language = Urls.DEFAULT_LANGUAGE;
        this.clientType = Urls.DEFAULT_CLIENT_TYPE;
    }
}
