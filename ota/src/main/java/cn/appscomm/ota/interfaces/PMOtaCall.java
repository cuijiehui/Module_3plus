package cn.appscomm.ota.interfaces;

/**
 * 作者：hsh
 * 日期：2017/5/2
 * 说明：
 */

public interface PMOtaCall {
    /**
     * 升级
     *
     * @param mac                     需要升级的设备的mac地址
     * @param touchPanelPath          触摸芯片升级文件路径
     * @param heartRatePath           心率升级文件路径
     * @param freescalePath           freescale升级文件路径
     * @param picturePath             图片升级文件路径
     * @param languagePath            语言升级文件路径
     * @param nordicPath              nordic升级文件路径
     * @param iUpdateProgressCallBack 升级进度、结果的回调
     */
    void update(String mac, String touchPanelPath, String heartRatePath, String freescalePath, String picturePath, String languagePath, String nordicPath, IUpdateProgressCallBack iUpdateProgressCallBack);
}
