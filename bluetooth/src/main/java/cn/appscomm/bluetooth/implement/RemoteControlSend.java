package cn.appscomm.bluetooth.implement;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.interfaces.IRemoteControlCommand;
import cn.appscomm.bluetooth.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/27
 * 说明：远程控制的发送
 */

public enum RemoteControlSend {
    INSTANCE;

    private static final String TAG = RemoteControlSend.class.getSimpleName();
    private IRemoteControlCommand iRemoteControlCommand;

    public void init(IRemoteControlCommand iRemoteControlCommand) {
        this.iRemoteControlCommand = iRemoteControlCommand;
    }

    public void parse(byte[] bytes) {
        byte commandCode = bytes[1];
        byte action = bytes[2];
        byte musicState = bytes[5];
        switch (commandCode) {
            case BluetoothCommandConstant.COMMAND_CODE_MUSIC:                                       // 音乐
                // 音乐查询
                if (action == BluetoothCommandConstant.ACTION_CHECK) {
                    if (musicState == BluetoothCommandConstant.REMOTE_MUSIC_CHECK_MUSIC_STATE) {
                        LogUtil.i(TAG, "音乐：设备查询音乐状态");
                        checkMusicStatus();
                    }
                }
                // 音乐设置
                else if (action == BluetoothCommandConstant.ACTION_SET) {                           // 音乐设置
                    LogUtil.i(TAG, "音乐：设备设置音乐状态");
                    switch (musicState) {
                        case BluetoothCommandConstant.REMOTE_MUSIC_SET_PHONE_PLAY:                  // 设置手机播放
                            LogUtil.i("音乐：设置手机播放");
                            setPhonePlay();
                            break;

                        case BluetoothCommandConstant.REMOTE_MUSIC_SET_PHONE_PAUSE:                 // 设置手机暂停
                            LogUtil.i("音乐：设置手机暂停");
                            setPhonePause();
                            break;

                        case BluetoothCommandConstant.REMOTE_MUSIC_SET_PHONE_PRE_SONG:              // 设置手机上一曲
                            LogUtil.i("音乐：设置手机上一曲");
                            setPhonePreSong();
                            break;

                        case BluetoothCommandConstant.REMOTE_MUSIC_SET_PHONE_NEXT_SONG:             // 设置手机下一曲
                            LogUtil.i("音乐：设置手机下一曲");
                            setPhoneNextSong();
                            break;

                        case BluetoothCommandConstant.REMOTE_MUSIC_SET_PHONE_VOLUME:                // 设置手机音量
                            LogUtil.i("音乐：设置手机音量");
                            if (bytes.length == 8)
                                setPhoneVolumes(bytes[6] & 0xFF);
                            break;
                    }
                }
                break;

            case BluetoothCommandConstant.COMMAND_CODE_TAKE_PHOTO:                                  // 拍照
                if (action == BluetoothCommandConstant.ACTION_SET) {                                // 拍照设置
                    if (musicState == BluetoothCommandConstant.REMOTE_TAKE_PHONE_START) {           // 进行拍照
                        LogUtil.i(TAG,"-----------------------远程拍照");
                        startTakePhoto();
                    }
                }
                break;

            case BluetoothCommandConstant.COMMAND_CODE_FIND_PHONE:                                  // 寻找手机
                LogUtil.i(TAG, "寻找手机... action : " + action + " content : " + musicState);
                if (action == BluetoothCommandConstant.ACTION_SET) {                                // 寻找手机设置
                    if (musicState == BluetoothCommandConstant.REMOTE_FIND_PHONE_START) {              // 开始寻找:手机响铃及震动
                        startFindPhone();
                    } else if (musicState == BluetoothCommandConstant.REMOTE_FIND_PHONE_END) {         // 停止寻找:停止响铃及震动
                        endFindPhone();
                    }
                }
                break;
        }
    }

    private void checkMusicStatus() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.checkMusicStatus();
    }

    private void setPhoneNextSong() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.setPhoneNextSong();
    }

    private void setPhoneVolumes(int volumes) {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.setPhoneVolumes(volumes);
    }

    private void setPhonePreSong() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.setPhonePreSong();
    }

    private void setPhonePlay() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.setPhonePlay();
    }

    private void setPhonePause() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.setPhonePause();
    }

    private void startTakePhoto() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.startTakePhoto();
    }

    private void endTakePhoto() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.endTakePhoto();
    }

    private void startFindPhone() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.startFindPhone();
    }

    private void endFindPhone() {
        if (iRemoteControlCommand != null)
            iRemoteControlCommand.endFindPhone();
    }


}
