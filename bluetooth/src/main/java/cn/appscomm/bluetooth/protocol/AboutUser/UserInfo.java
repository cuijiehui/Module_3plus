package cn.appscomm.bluetooth.protocol.AboutUser;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 个人信息
 * Created by Administrator on 2016/1/27.
 */
public class UserInfo extends Leaf {

    /**
     * 个人信息
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public UserInfo(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_USER_INFO, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 个人信息
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param sex                      性别
     * @param age                      年龄
     * @param high                     身高
     * @param weight                   体重
     */
    public UserInfo(IBluetoothResultCallback iBluetoothResultCallback, int len, int sex, int age, int high, int weight) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_USER_INFO, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];

        byte[] bSex = ParseUtil.intToByteArray(sex, 1);
        byte[] bAge = ParseUtil.intToByteArray(age, 1);
        int iHigh = (int) high;
        byte[] bHigh = ParseUtil.intToByteArray(iHigh, 1);
        byte[] bWeight = ParseUtil.intToByteArray(weight, 2);

        System.arraycopy(bSex, 0, content, 0, 1);
        System.arraycopy(bAge, 0, content, 1, 1);
        System.arraycopy(bHigh, 0, content, 2, 1);
        System.arraycopy(bWeight, 0, content, 3, 2);

        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为5
     * 例子:
     * 6F 30 80   05 00   00 14 AA BA 02   8F(男 20岁 170cm 69.8kg)
     * 1、性别(0x00:男   0x01:女)
     * 2、年龄(岁)
     * 3、身高(cm)
     * 4~5、体重(0.1kg)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 5) {
            BluetoothVar.sex = (int) (contents[0] & 0xFF);
            BluetoothVar.age = (int) (contents[1] & 0xFF);
            BluetoothVar.height = (int) (contents[2] & 0xFF);
            BluetoothVar.weight = ((int) (contents[3] & 0xff) + (int) ((contents[4] & 0xff) << 8)) / 10f;
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
