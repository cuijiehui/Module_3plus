package cn.appscomm.bluetooth.protocol.AboutSetting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 一级界面显示设置
 * Created by Administrator on 2016/1/27.
 */
public class PrimarySurfaceDisplaySetting extends Leaf {

    /**
     * 一级界面显示设置
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public PrimarySurfaceDisplaySetting(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PRIMARY_SURFACE_DISPLAY_SETTING, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 一级界面显示设置
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容
     * @param interfaces               界面设置
     */
    public PrimarySurfaceDisplaySetting(IBluetoothResultCallback iBluetoothResultCallback, int len, byte[] interfaces) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PRIMARY_SURFACE_DISPLAY_SETTING, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = interfaces;
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不大于7
     * 例子:
     * 6F 06 80   04 00   01 00 03 04   8F(步数 时间 距离 睡眠)
     * 1、主界面
     * 2、第二界面
     * 3、第三界面
     * 4、第四界面
     * ……
     * <p/>
     * 时间(0x00)   步数(0x01)   卡路里(0x02)   距离(0x03)   睡眠(0x04)   金额(0x05)   卡号(0x06)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len <= 7) {
            BluetoothVar.arrItems = new int[]{
                    0, 0, 0, 0, 0, 0, 0 // 0:时间  1:步数   2:卡路里   3:距离   4:睡眠   5:金额   6:卡号
            };
            for (int i = 0; i < contents.length; i++) {
                switch (contents[i]) {
                    case 0x01: // 时间
                        BluetoothVar.arrItems[0] = i + 1;
                        break;

                    case 0x02: // 步数
                        BluetoothVar.arrItems[1] = i + 1;
                        break;

                    case 0x03: // 卡路里
                        BluetoothVar.arrItems[2] = i + 1;
                        break;

                    case 0x04: // 距离
                        BluetoothVar.arrItems[3] = i + 1;
                        break;

                    case 0x05: // 睡眠
                        BluetoothVar.arrItems[4] = i + 1;
                        break;

                    case 0x06: // 金额
                        BluetoothVar.arrItems[5] = i + 1;
                        break;

                    case 0x07: // 卡号
                        BluetoothVar.arrItems[6] = i + 1;
                        break;
                }
            }
            LogUtil.i(TAG, "BluetoothVar.arrItems : " + BluetoothVar.arrItems.toString());
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
