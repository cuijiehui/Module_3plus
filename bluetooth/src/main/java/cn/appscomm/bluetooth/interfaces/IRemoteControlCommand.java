package cn.appscomm.bluetooth.interfaces;

/**
 * 作者：hsh
 * 日期：2017/4/27
 * 说明：
 */

public interface IRemoteControlCommand {
    void checkMusicStatus();

    void setPhoneNextSong();

    void setPhonePreSong();

    void setPhonePlay();

    void setPhonePause();

    void startTakePhoto();

    void endTakePhoto();

    void startFindPhone();

    void endFindPhone();

    void setPhoneVolumes(int volumes);
}
