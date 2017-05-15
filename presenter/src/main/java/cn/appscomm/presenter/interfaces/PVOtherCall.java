package cn.appscomm.presenter.interfaces;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface PVOtherCall {

    /**
     * 注册远程拍照
     *
     * @param iRemoteControl 远程控制结果回调
     */
    void registerRemoteTakePhoto(IRemoteControl iRemoteControl);
}
