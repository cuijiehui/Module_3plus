package cn.appscomm.ota.implement;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.appscomm.ota.OtaManager;
import cn.appscomm.ota.interfaces.IUpdateProgressCallBack;
import cn.appscomm.ota.interfaces.PMOtaCall;
import cn.appscomm.ota.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/5/2
 * 说明：
 */

public enum MOta implements PMOtaCall {
    INSTANCE;

    private static final String TAG = MOta.class.getSimpleName();

    @Override
    public void update(String mac, String touchPanelPath, String heartRatePath, String freescalePath, String picturePath, String languagePath, String nordicPath, IUpdateProgressCallBack iUpdateProgressCallBack) {
        if (TextUtils.isEmpty(mac)) return;
        List<String> pathList = new LinkedList<>();
        String log = "要升级的芯片有 :";
        if (TextUtils.isEmpty(languagePath)) {
            if (!TextUtils.isEmpty(touchPanelPath)) {
                log += " 触摸芯片";
                pathList.add(touchPanelPath);
            }
            if (!TextUtils.isEmpty(heartRatePath)) {
                log += " 心率";
                pathList.add(heartRatePath);
            }
            if (!TextUtils.isEmpty(freescalePath)) {
                log += " 飞思卡尔";
                pathList.add(freescalePath);
            }
            if (!TextUtils.isEmpty(picturePath)) {
                log += " 图库";
                pathList.add(picturePath);
            }
            if (!TextUtils.isEmpty(nordicPath)) {
                log += " Nordic";
                pathList.add(nordicPath);
            }
        } else {
            log += " 语言";
            pathList.add(languagePath);
        }
        if (pathList.size() == 0) return;
        LogUtil.i(TAG, log);
        OtaManager.INSTANCE.update(mac, pathList, iUpdateProgressCallBack);
    }
}
