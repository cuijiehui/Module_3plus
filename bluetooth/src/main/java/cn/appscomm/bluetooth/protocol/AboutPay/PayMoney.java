package cn.appscomm.bluetooth.protocol.AboutPay;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 支付:读取余额
 * Created by Administrator on 2016/1/27.
 */
public class PayMoney extends Leaf {

    /**
     * 支付:读取余额
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public PayMoney(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PAY_MONEY, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
        LogUtil.i(TAG, "查询 : 准备获取卡号余额...");
    }

    /**
     * contents字节数组解析：
     * 长度固定为4
     * 例子:
     * 6F B1 80   04 00   88 13 00 00   8F(余额为50.00元)
     * 1~4:余额(单位为分)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 6) {
            long lMoney = ParseUtil.bytesToLong(contents, 0, 5);
            BluetoothVar.money = lMoney / 100 + "." + lMoney % 100;
            LogUtil.i(TAG, "余额 : " + BluetoothVar.money);
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
