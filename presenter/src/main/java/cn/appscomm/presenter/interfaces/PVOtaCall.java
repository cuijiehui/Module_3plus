package cn.appscomm.presenter.interfaces;

import cn.appscomm.ota.interfaces.IUpdateProgressCallBack;

/**
 * 作者：hsh
 * 日期：2017/5/3
 * 说明：
 */

public interface PVOtaCall {

    int UPDATE_TYPE_TOUCH = 1;
    int UPDATE_TYPE_HEARTRATE = 1 << 1;
    int UPDATE_TYPE_FREESCALE = 1 << 2;
    int UPDATE_TYPE_PICTURE = 1 << 3;
    int UPDATE_TYPE_LANGUAGE = 1 << 4;
    int UPDATE_TYPE_NORDIC = 1 << 5;

    /**
     * 升级
     *
     * @param dfuName                 空中升级的蓝牙名称
     * @param touchPanelPath          触摸芯片升级文件路径
     * @param heartRatePath           心率升级文件路径
     * @param freescalePath           freescale升级文件路径
     * @param picturePath             图片升级文件路径
     * @param languagePath            语言升级文件路径
     * @param nordicPath              nordic升级文件路径
     * @param iUpdateProgressCallBack 升级进度、结果的回调
     */
    void update(String dfuName, String touchPanelPath, String heartRatePath, String freescalePath, String picturePath, String languagePath, String nordicPath, IUpdateProgressCallBack iUpdateProgressCallBack, String[] macs);

    /**
     * 升级(路径默认在Context.getFilesDir()中)
     *
     * @param dfuName                 空中升级的蓝牙名称
     * @param updateType              升级类型
     * @param iUpdateProgressCallBack 升级进度、结果的回调
     */
    void update(String dfuName, int updateType, IUpdateProgressCallBack iUpdateProgressCallBack);
}

