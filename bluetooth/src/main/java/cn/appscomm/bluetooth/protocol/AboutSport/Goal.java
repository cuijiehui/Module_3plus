package cn.appscomm.bluetooth.protocol.AboutSport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 目标设置
 * Created by Administrator on 2016/1/27.
 */
public class Goal extends Leaf {

    /**
     * 目标设置
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public Goal(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_GOAL, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 目标设置
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param targetType               目标类型
     * @param targetValue              目标值
     * @param targetFlag               主要目标标志
     */
    public Goal(IBluetoothResultCallback iBluetoothResultCallback, int len, byte targetType, int targetValue, byte targetFlag) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_GOAL, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        byte[] bTargetValue = ParseUtil.intToByteArray(targetValue, 2);
        content[0] = targetType;
        content[3] = targetFlag;
        System.arraycopy(bTargetValue, 0, content, 1, 2);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度可变，但为3的倍数
     * 例子:
     * 6F 50 80   0C 00   E8 03 00 E8 03 01 E8 03 00 08 00 00   8F(步数(0x00非主要目标):1000 ……)
     * 1~2、步数
     * 3、步数的主要目标标志
     * 4~5、卡路里
     * 6、卡路里的主要目标标志
     * ……
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if ((len > 0) && ((len % 3) == 0)) {
            int targetSettingCount = len / 3; // 需要设置目标的数量
            for (int i = 0; i < targetSettingCount; i++) {
                int targetValue = (int) ParseUtil.bytesToLong(contents, 3 * i, (3 * i) + 1);        // 目标值
                int targetFlag = (int) (contents[3 * i + 2] & 0xff);                                // 是否为主要目标
                switch (i) {
                    case 0:                                                                         // 步数(返回的单位是百步，需要转换为步)
                        BluetoothVar.stepGoalsValue = targetValue * 100;
                        BluetoothVar.stepGoalsFlag = targetFlag;
                        LogUtil.i(TAG, "步数:" + BluetoothVar.stepGoalsValue);
                        break;

                    case 1:                                                                         // 卡路里(返回的单位是千卡)
                        BluetoothVar.calorieGoalsValue = targetValue;
                        BluetoothVar.calorieGoalsFlag = targetFlag;
                        LogUtil.i(TAG, "卡路里:" + BluetoothVar.calorieGoalsValue);
                        break;

                    case 2:                                                                         // 距离(返回的单位是千米)
                        BluetoothVar.distanceGoalsValue = targetValue;
                        BluetoothVar.distanceGoalsFlag = targetFlag;
                        LogUtil.i(TAG, "距离:" + BluetoothVar.distanceGoalsValue);
                        break;

                    case 3:                                                                         // 睡眠时间(返回的单位是小时)
                        BluetoothVar.sleepGoalsValue = targetValue;
                        BluetoothVar.sleepGoalsFlag = targetFlag;
                        LogUtil.i(TAG, "睡眠时间:" + BluetoothVar.sleepGoalsValue);
                        break;

                    case 4:
                        BluetoothVar.sportTimeGoalsValue = targetValue;
                        BluetoothVar.sportTimeGoalsFlag = targetFlag;
                        LogUtil.i(TAG, "运动时长:" + BluetoothVar.sportTimeGoalsValue);
                        break;
                }
            }
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
