package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 时间界面设置
 * Created by Administrator on 2016/1/26.
 */
public class TimeSurfaceSetting extends Leaf {

    /**
     * 时间界面
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public TimeSurfaceSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_TIME_SURFACE_SETTING, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);

    }

    /**
     * 时间界面
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param dateFormat               日期格式
     * @param timeFormat               时间格式
     * @param batteryFormat            电池格式
     * @param lunarFormat              农历格式
     * @param screenFormat             屏幕格式
     * @param backgroundStyle          背景风格
     * @param sportDataFormat          运动数据格式
     * @param usernameFormat           用户名格式
     */
    public TimeSurfaceSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, byte dateFormat, byte timeFormat, byte batteryFormat, byte lunarFormat, byte screenFormat,
                              byte backgroundStyle, byte sportDataFormat, byte usernameFormat) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_TIME_SURFACE_SETTING, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        content[0] = dateFormat;
        content[1] = timeFormat;
        content[2] = batteryFormat;
        content[3] = lunarFormat;
        content[4] = screenFormat;
        if (len == 8) {
            content[5] = backgroundStyle;
            content[6] = sportDataFormat;
            content[7] = usernameFormat;
        }
        super.setContentLen(contentLen);
        super.setContent(content);

    }

    /**
     * contents字节数组解析：
     * 长度固定为5
     * 例子:
     * 6F 05 80   05 00   05 01 01 00 00   8F(mm/dd 24小时制 显示电池 不显示农历 横屏)
     * 1、日期格式(0x00:不显示日期   0x01:yy/mm/dd   0x02:dd/mm/yy   0x03:mm/dd/yy   0x04:星期几/mm/dd(见说明)   0x05:mm/dd   0x06:dd/mm)
     * 2、时间格式(0x00:不显示时间   0x01:24小时制   0x02:12小时制   0x03:模拟时钟1  0x04:模拟时钟 2   ……)
     * 3、电池显示(0x00:不显示电池   0x01:显示电池)
     * 4、农历显示(0x00:不显示农历   0x01:显示农历)
     * 5、屏幕格式(0x00:横屏显示     0x01:竖屏显示)
     * 6、背景风格
     * 7、运动数据格式
     * 8、用户名格式
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 5 || len == 8) { // 长度固定为5
            BluetoothVar.dateFormat = (int) (contents[0] & 0xFF);                                      // 日期格式
            BluetoothVar.timeFormat = (int) (contents[1] & 0xFF);                                      // 时间格式
            BluetoothVar.batteryShow = (int) (contents[2] & 0xFF);                                     // 电池格式
            BluetoothVar.lunarFormat = (int) (contents[3] & 0xFF);                                     // 农历格式
            BluetoothVar.screenFormat = (int) (contents[4] & 0xFF);                                    // 屏幕格式
            if (len == 8) {
                BluetoothVar.backgroundStyle = (int) (contents[5] & 0xFF);                                 // 背景风格
                BluetoothVar.sportDataShow = (int) (contents[6] & 0xFF);                                   // 运动数据格式
                BluetoothVar.usernameFormat = (int) (contents[7] & 0xFF);                                  // 用户名格式
            }
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
