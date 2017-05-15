package cn.appscomm.bluetooth.protocol.AboutPay;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 支付:当前使用的芯片应用卡号
 * Created by Administrator on 2016/1/27.
 */
public class PayCardNumber extends Leaf {

    /**
     * 支付:当前使用的芯片应用卡号
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public PayCardNumber(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PAY_CARDNUMBER, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 准备获取卡号...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 支付:当前使用的芯片应用卡号
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param cardAID                  AID
     * @param cardNumber               卡号
     */
    public PayCardNumber(IBluetoothResultCallback iBluetoothResultCallback, int len, byte cardAID, String cardNumber) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PAY_CARDNUMBER, BluetoothCommandConstant.ACTION_SET);
        byte[] content = null;
        try {
            len = cardNumber.getBytes("utf-8").length;
            len = len > 90 ? 90 : len;
            content = new byte[len + 1];
            content[0] = cardAID;
            System.arraycopy(cardNumber.getBytes("utf-8"), 0, content, 1, len);
            LogUtil.i(TAG, "设置 : 准备设置卡号...");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定
     * 例子:
     * 6F B0 80   14 00   38 36 31 32 30 31 30 30 30 30 30 30 30 30 33 34 30 30 30 30   8F(卡号:86120100000000340000)
     * 1~N:字符串类型，左靠齐
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            try {
                BluetoothVar.cardNumber = new String(Arrays.copyOfRange(contents, 0, len), "US-ASCII");
                LogUtil.i(TAG, "卡号:" + BluetoothVar.cardNumber + " len : " + len);
                ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
            } catch (Exception e) {
                ret = BluetoothCommandConstant.RESULT_CODE_FAIL;
            }
        }
        return ret;
    }
}
