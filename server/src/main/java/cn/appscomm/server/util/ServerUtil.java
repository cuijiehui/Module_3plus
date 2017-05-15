package cn.appscomm.server.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ServerUtil {

    /**
     * 检查网络是否连接
     *
     * @return true : 连接 ; false : 断开
     */
    public static boolean checkNetWork() {
        Context context = ServerAppContext.INSTANCE.getContext();
        if (context == null)
            return false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager)
            return false;

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
