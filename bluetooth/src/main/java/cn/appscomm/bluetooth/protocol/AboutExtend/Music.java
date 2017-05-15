package cn.appscomm.bluetooth.protocol.AboutExtend;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothLeService;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 发送歌曲
 * Created by Administrator on 2016/1/27.
 */
public class Music extends Leaf {

    /**
     * 发送歌曲
     * 构造函数(0x80)
     *
     * @param len          内容长
     * @param musicState   音乐状态
     * @param musicContent 音乐内容(若无音乐播放，则传入null)
     */
    public Music(int len, byte musicState, byte[] musicContent) {
        super(null, BluetoothCommandConstant.COMMAND_CODE_MUSIC, BluetoothCommandConstant.ACTION_CHECK_RESPONSE);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        int musicLen = 1 + (musicContent != null && musicContent.length > 0 ? musicContent.length : 0);
        byte[] content = new byte[musicLen];
        content[0] = musicState;
        if (musicContent != null && musicContent.length > 0) {
            System.arraycopy(musicContent, 0, content, 1, musicContent.length);
        }
        super.setActiveCommand(false);                                                              // 设置为被动命令
        super.setBluetoothSendType(BluetoothLeService.SEND_TYPE_8003);                              // 设置为8003特征发送
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}