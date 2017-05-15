package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 主界面和报警界面背景颜色
 * Created by Administrator on 2016/1/27.
 */
public class MainAlarmBackgroundColor extends Leaf {

    /**
     * 主界面和报警界面背景颜色
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public MainAlarmBackgroundColor(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_MAIN_ALARM_BACKGROUND_COLOR, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);

        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 主界面和报警界面背景颜色
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param mainBackgroundColor      主界面背景颜色
     * @param alarmBackgroundColor     报警界面背景颜色
     */
    public MainAlarmBackgroundColor(IBluetoothResultCallback iBluetoothResultCallback, int len, byte[] mainBackgroundColor, byte[] alarmBackgroundColor) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_MAIN_ALARM_BACKGROUND_COLOR, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[6];
        System.arraycopy(mainBackgroundColor, 0, content, 0, 3);
        System.arraycopy(alarmBackgroundColor, 0, content, 3, 3);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为6
     * 例子:
     * 6F 11 80   06 00  03 7D FF   03 7D FF   8F
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 6) {
            BluetoothVar.mainBackgroundColor = new int[3];
            BluetoothVar.alarmBackgroundColor = new int[3];
            BluetoothVar.mainBackgroundColor[0] = contents[0];
            BluetoothVar.mainBackgroundColor[1] = contents[1];
            BluetoothVar.mainBackgroundColor[2] = contents[2];
            BluetoothVar.alarmBackgroundColor[0] = contents[3];
            BluetoothVar.alarmBackgroundColor[1] = contents[4];
            BluetoothVar.alarmBackgroundColor[2] = contents[5];
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
