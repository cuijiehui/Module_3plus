package cn.appscomm.bluetooth.protocol.AboutPay;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 支付:透传
 * Created by Administrator on 2016/1/27.
 */
public class PayPassthrough extends Leaf {

    /**
     * 支付:透传
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content                  内容
     */
    public PayPassthrough(IBluetoothResultCallback iBluetoothResultCallback, int len, byte[] content) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_PAY_PASSTHROUGH, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定
     * 1、AID参数(0 ,1,2….)
     * 2~N、透传数据
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            LogUtil.i(TAG, "透传数据:" + ParseUtil.byteArrayToHexString(contents));
            int cardAID = (int) ParseUtil.bytesToLong(contents, 0, 0);          // cardAID
            int totalBytesSize = (int) ParseUtil.bytesToLong(contents, 1, 1);   // 总字节数
            int count = (int) ParseUtil.bytesToLong(contents, 2, 3);            // 记录条数
            int contentLen = (int) ParseUtil.bytesToLong(contents, 4, 4);       // 记录字节数
            BluetoothVar.record = new StringBuffer();
            BluetoothVar.recordCount = count;
            if (count > 0 && ((contentLen / count) == 13)) { // 判断count条数 和 是否每条记录字节数是13
                int year, month, day, hour, min, sec;
                for (int i = 0; i < count; i++) {
                    String str = "";
                    int index = 5 + i * 13;
                    year = binToInt(contents[0 + index]) + 2000;
                    month = binToInt(contents[1 + index]);
                    day = binToInt(contents[2 + index]);
                    hour = binToInt(contents[3 + index]);
                    min = binToInt(contents[4 + index]);
                    sec = binToInt(contents[5 + index]);
                    str = year + "-" + fillZero(month) + "-" + fillZero(day) + "," + fillZero(hour) + ":" + fillZero(min) + ":" + fillZero(sec) + ";";
                    if (contents[12 + index] == (byte) 0x06) {
                        str += "-;";
                    } else if (contents[12 + index] == (byte) 0x02) {
                        str += "+;";
                    } else {
                        LogUtil.i(TAG, "监测到圈提的数据，丢弃");
                        continue;
                    }
                    long money = binToInt(contents[6 + index]) * 10000000000L + binToInt(contents[7 + index]) * 100000000 + binToInt(contents[8 + index]) * 1000000 +
                            binToInt(contents[9 + index]) * 10000 + binToInt(contents[10 + index]) * 100 + binToInt(contents[11 + index]) * 1;
                    if (money == 0) {
                        LogUtil.i(TAG, "发现0.00元记录，丢弃");
                        continue;
                    }
                    str += money / 100 + "." + String.format("%02d", money % 100);
                    LogUtil.i(TAG, "解析到的交易记录:" + str);
                    BluetoothVar.record.append(str + "&");
                    BluetoothVar.haveRecord = true;
                }
            } else {
                LogUtil.i(TAG, "没有记录!!!");
                BluetoothVar.haveRecord = false;
            }
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }

    private static String hexStr = "0123456789ABCDEF";

    private int binToInt(byte b) {
        String hex = "";
        hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));   //字节高4位
        hex += String.valueOf(hexStr.charAt(b & 0x0F));         //字节低4位
        return Integer.valueOf(hex);
    }

    public static String fillZero(int data) {
        return data < 10 ? "0" + data : data + "";
    }
}
