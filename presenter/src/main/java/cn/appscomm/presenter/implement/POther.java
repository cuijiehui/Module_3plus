package cn.appscomm.presenter.implement;

import cn.appscomm.presenter.interfaces.IRemoteControl;
import cn.appscomm.presenter.interfaces.PVOtherCall;
import cn.appscomm.presenter.interfaces.PVOtherCallback;
import cn.appscomm.presenter.remotecontrol.RemoteControlManager;

/**
 * Created by Administrator on 2017/3/6.
 */

public enum POther implements PVOtherCall {
    INSTANCE;

    @Override
    public void registerRemoteTakePhoto(IRemoteControl iRemoteControl) {
        RemoteControlManager.INSTANCE.registerRemoteTakePhoto(iRemoteControl);
    }
}
