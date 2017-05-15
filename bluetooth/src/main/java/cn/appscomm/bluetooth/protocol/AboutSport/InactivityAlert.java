package cn.appscomm.bluetooth.protocol.AboutSport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * Created by Administrator on 2016/7/20.
 */
public class InactivityAlert extends Leaf {

    /**
     * 久坐提醒
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public InactivityAlert(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_INACTIVITY_ALERT, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 久坐提醒
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param cycle                    周期(开关在最高位，其他为周一~周日)
     * @param interval                 间隔
     * @param sHour                    开始时
     * @param sMin                     开始分
     * @param eHour                    结束时
     * @param eMin                     结束分
     * @param step                     有效步数
     */
    public InactivityAlert(IBluetoothResultCallback iBluetoothResultCallback, int len, int cycle, int interval, int sHour, int sMin, int eHour, int eMin, int step) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_INACTIVITY_ALERT, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = (byte) cycle;
        content[1] = (byte) interval;
        content[2] = (byte) sHour;
        content[3] = (byte) sMin;
        content[4] = (byte) eHour;
        content[5] = (byte) eMin;
        byte[] bStep = ParseUtil.intToByteArray(step, 2);
        System.arraycopy(bStep, 0, content, 6, bStep.length);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为3
     * 例子:
     * 设备端持续发送直到最后一条
     * 6F 5D 80   03 00   64 32 01   8F
     * 1、	    心率最大值(100)
     * 2、	    心率最小值(50)
     * 3、	    是否报警(0x01:报警 0x00:不报警)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 8) {
            BluetoothVar.inactivityAlertSwitch = (contents[0] & 0x80) > 0 ? true : false;
            BluetoothVar.inactivityAlertCycle = contents[0] & 0x7F;
            BluetoothVar.inactivityAlertInterval = contents[1] & 0xFF;
            BluetoothVar.inactivityAlertStartHour = contents[2] & 0xFF;
            BluetoothVar.inactivityAlertStartMin = contents[3] & 0xFF;
            BluetoothVar.inactivityAlertEndHour = contents[4] & 0xFF;
            BluetoothVar.inactivityAlertEndMin = contents[5] & 0xFF;
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
