package cn.appscomm.bluetooth.protocol.AboutSetting;

import java.util.Arrays;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 设备版本
 * Created by Administrator on 2016/1/26.
 */
public class DeviceVersion extends Leaf {

    /**
     * 设备版本
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public DeviceVersion(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_DEVICE_VERSION, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定
     * 例子:
     * 6F 03 80   14 00   00 56 30 2E 31   8F(软件版本v0.1)
     * 1、类型(0x00:软件版本	0x01:硬件版本	0x02:蓝牙协议版本	0x03:功能版本)
     * 2~N、字符串类型，左靠齐
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            try {
                String version = new String(Arrays.copyOfRange(contents, 1, len), "US-ASCII");
                switch (contents[0]) {

                    case 0x00: // 设备类型
                        BluetoothVar.deviceType = version;
                        break;

                    case 0x01: // 软件版本
                        BluetoothVar.softVersion = version;
                        break;

                    case 0x02: // 硬件版本
                        BluetoothVar.hardwareVersion = version;
                        break;

                    case 0x03: // 通信协议
                        BluetoothVar.commProtocol = version;
                        break;

                    case 0x04: // 功能版本
                        BluetoothVar.functionVersion = version;
                        break;

                    default: // 其他
                        BluetoothVar.otherVersion = version;
                        break;
                }
                ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
                LogUtil.i(TAG, "version : " + version);

            } catch (Exception e) {
            }
        } else {
            ret = BluetoothCommandConstant.RESULT_CODE_FAIL;
        }
        return ret;
    }
}
