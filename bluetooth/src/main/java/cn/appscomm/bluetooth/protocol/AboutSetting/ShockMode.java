package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 震动
 * Created by Administrator on 2016/1/27.
 */
public class ShockMode extends Leaf {

    /**
     * 震动
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public ShockMode(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SHOCK_MODE, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);

        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 震动
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param shockType                动作类型
     * @param shockMode                动作对应的震动类型
     */
    public ShockMode(IBluetoothResultCallback iBluetoothResultCallback, int len, byte shockType, byte shockMode) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SHOCK_MODE, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = shockType;
        content[1] = shockMode;
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定
     * 例子:
     * 6F 0A 80   0A 00   06 02 04 05 06 02 04 05 04 05   8F
     * 1、动作类型0x00对应的震动模式
     * 2、动作类型0x01对应的震动模式
     * ……
     * <p/>
     * 动作类型(0x00:防丢提醒   0x01:闹钟提醒   0x02:来电提醒   0x03:未接来电提醒   0x04:短信提醒   0x05:社交提醒   0x06:邮件提醒   0x07:日历提醒   0x08:久坐提醒   0x09:低电提醒   ……)
     * 震动模式(0x00:关闭,不震动   0x01:模式一(单次长震动)   0x02:模式二(单次短震动)   0x03:模式三(间歇2次长震动)   0x04:模式四(间歇2次短震动)   0x05:模式五(长震动-短震动交替)   0x06:一直长震   0x07:一直短震)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            for (int i = 0; i < contents.length; i++) {
                int value = contents[i];
                switch (i) {
                    case BluetoothCommandConstant.SHOCK_ACTION_ANTI:
                        BluetoothVar.antiShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_CLOCK:
                        BluetoothVar.clockShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_INCOME_CALL:
                        BluetoothVar.callShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_MISS_CALL:
                        BluetoothVar.missCallShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_SMS:
                        BluetoothVar.smsShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_SOCIAL:
                        BluetoothVar.socialShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_EMAIL:
                        BluetoothVar.emailShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_CALENDAR:
                        BluetoothVar.calendarShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_SEDENTARY:
                        BluetoothVar.sedentaryShock = value;
                        break;
                    case BluetoothCommandConstant.SHOCK_ACTION_LOW_POWER:
                        BluetoothVar.lowPowerShock = value;
                        break;
                }
            }
            LogUtil.i(TAG, "防丢震动(" + BluetoothVar.antiShock + ") 闹钟震动(" + BluetoothVar.clockShock + ") 来电震动(" + BluetoothVar.callShock + ") 未接来电震动(" + BluetoothVar.missCallShock + ") 短信震动(" + BluetoothVar.smsShock + ") 社交震动(" + BluetoothVar.socialShock + ") 邮件震动(" + BluetoothVar.emailShock + ") 日历震动(" + BluetoothVar.calendarShock + ") 久坐震动(" + BluetoothVar.sedentaryShock + ") 低电震动(" + BluetoothVar.lowPowerShock + ")");
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
