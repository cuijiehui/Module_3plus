package cn.appscomm.bluetooth.protocol.AboutSport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 设备端显示各种类型的值
 * Created by Administrator on 2016/1/27.
 */
public class DeviceDisplayData extends Leaf {

    /**
     * 设备端显示各种类型的值
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public DeviceDisplayData(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_DEVICE_DISPLAY_DATA, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, 2);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定，但为4的倍数
     * 例子:
     * 6F 58 80   0E 00   64 00 00 00 32 00 00 00 32 00 00 00 58 02   8F(100步   50卡   50米   600分钟)
     * 1~4:	    步数(步)
     * 5~8:	    卡路里(卡)
     * 9~12:	距离(米)
     * 13~14:	睡眠时间(分)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0 && (len % 4) == 0) {
            int count = len / 4;
            for (int i = 0; i < count; i++) {
                int value = (int) ParseUtil.bytesToLong(contents, 4 * i, 4 * i + 3); // 数值
                switch (i) {
                    case 0:                                                                         // 步数
                        BluetoothVar.deviceDisplayStep = value;
                        break;
                    case 1:                                                                         // 卡路里
                        BluetoothVar.deviceDisplayCalorie = value;
                        break;
                    case 2:                                                                         // 距离
                        BluetoothVar.deviceDisplayDistance = value;
                        break;
                    case 3:                                                                         // 睡眠时间
                        BluetoothVar.deviceDisplaySleep = value;
                        break;
                    case 4:                                                                         // 运动时长
                        BluetoothVar.deviceDisplaySportTime = value;
                        break;
                    case 5:                                                                         // 心率值
                        BluetoothVar.deviceDisplayHeartRate = value;
                        break;
                    case 6:                                                                         // 情绪和疲劳度值
                        BluetoothVar.deviceDisplayMood = value;
                        break;
                    default:
                        break;
                }
            }
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
