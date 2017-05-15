package cn.appscomm.bluetooth.protocol.AboutSetting;


import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 屏幕亮度设置
 * Created by Administrator on 2016/1/27.
 */
public class ScreenBrightnessSetting extends Leaf {

    /**
     * 屏幕亮度
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public ScreenBrightnessSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SCREEN_BRIGHTNESS_SETTING, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 屏幕亮度
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param brightnessValue          亮度值(byte类型,例如亮度为100,即0x64)
     */
    public ScreenBrightnessSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, byte brightnessValue) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SCREEN_BRIGHTNESS_SETTING, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{brightnessValue};
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为1
     * 例子:
     * 6F 07 80   01 00   64   8F(0x64:100)
     * 十六进制转十进制
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 1) {
            BluetoothVar.screenBrightness = (int) (contents[0] & 0xFF);
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
