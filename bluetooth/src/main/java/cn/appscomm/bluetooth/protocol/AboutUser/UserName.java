package cn.appscomm.bluetooth.protocol.AboutUser;

import java.util.Arrays;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 用户名称
 * Created by Administrator on 2016/1/27.
 */
public class UserName extends Leaf {

    /**
     * 用户名称
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public UserName(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_USER_NAME, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 用户名称
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param userName                 用户名
     */
    public UserName(IBluetoothResultCallback iBluetoothResultCallback, int len, String userName) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_USER_NAME, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{16};
        byte[] bUserName = userName.getBytes();
        System.arraycopy(bUserName, 0, content, 0, bUserName.length > 16 ? 16 : bUserName.length);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为1
     * 例子:
     * 6F 32 71   10 00   30 31 32 33 34 00 00 00 00 00 00 00 00 00 00 00   8F(用户名：1234)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 16) {
            try {
                String userName = new String(Arrays.copyOfRange(contents, 0, len), "US-ASCII");
                BluetoothVar.userName = userName;
                LogUtil.i(TAG, "userName : " + userName);
                ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
            } catch (Exception e) {
            }
        } else {
            ret = BluetoothCommandConstant.RESULT_CODE_FAIL;
        }
        return ret;
    }
}
