package cn.appscomm.bluetooth.protocol.AboutSport;

import java.util.LinkedList;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.mode.HeartRateBT;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 上传心率数据
 * Created by Administrator on 2016/1/27.
 */
public class GetHeartRateData extends Leaf {
    private int heartRateCount;

    /**
     * 上传心率数据
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     * @param heartRateCount           需要获取的心率条数
     */
    public GetHeartRateData(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70, int heartRateCount) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_GET_HEART_RATE_DATA, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        this.heartRateCount = heartRateCount;
        super.setContentLen(contentLen);
        super.setContent(content);
        LogUtil.i(TAG, "查询 : 共" + heartRateCount + "条心率数据,准备获取...");
    }

    /**
     * contents字节数组解析：
     * 长度固定为7
     * 例子:
     * 设备端持续发送直到最后一条
     * 6F 5B 80   07 00   01 00 79 44 3C 56 4B   8F
     * 6F 5B 80   07 00   02 00 79 45 3C 56 4A   8F
     * 1~2、	    数据索引值(1~65535)
     * 3~6、	    时间戳
     * 7、	    心率值
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 7) {
            int index = (int) ParseUtil.bytesToLong(contents, 0, 1);                                // 索引值
            long timeStamp = ParseUtil.bytesToLong(contents, 2, 5);                                 // 时间戳
            int heartRateValue = (int) (contents[6] & 0xFF);                                        // 心率值
            LogUtil.i(TAG, "查询返回 : 心率数据(索引值:" + index + " 时间(" + timeStamp + "):" + ParseUtil.eightTZTimeStampToStringTZ(timeStamp, false) + " 心率:" + heartRateValue);

            if (BluetoothVar.heartRateBTDataList == null || BluetoothVar.heartRateBTDataList.size() == 0 || index == 1) {
                BluetoothVar.heartRateBTDataList = new LinkedList<>();
            }
            BluetoothVar.heartRateBTDataList.add(new HeartRateBT(index, heartRateValue, timeStamp));
            ret = BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE;

            if (BluetoothVar.heartRateBTDataList.size() == heartRateCount) {
                LogUtil.i(TAG, "获取完所有心率数据!!!");
                ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
            } else {
                if (index == heartRateCount) { // 获取到最后一条，但没有收到完整的数据，需要重新获取
                    if (BluetoothVar.heartRateBTDataList != null) {
                        BluetoothVar.heartRateBTDataList.clear();
                    }
                    LogUtil.i(TAG, "有心率数据丢失，需要重新获取!!!");
                    ret = BluetoothCommandConstant.RESULT_CODE_RE_SEND;
                }
            }


            /*// 如果GlobalVar.indexsResendCommand为null 或 大小为0，则说明是获取全部索引的命令
            if (BluetoothVar.indexResendCommand == null || BluetoothVar.indexResendCommand.size() == 0) {
                if (index == heartRateCount) { // 是否到达最后一包 : 在收到最后一包数据的情况下(如果没有收到最后一包，则需要超时判断有没有接收到最后一包了)
                    if (BluetoothVar.heartRateBTDataList.size() != heartRateCount) { // 获取到的数据和总数量值不相同
                        LogUtil.i(TAG, "心率:存在数据丢失,共" + heartRateCount + "条数据 接收到的数量是" + BluetoothVar.heartRateBTDataList.size());
                        if ((heartRateCount - BluetoothVar.heartRateBTDataList.size()) > 5) { // 如果丢失的包超过5条，则需要重新获取一次全部的数据
                            ret = Commands.RESULTCODE_RE_SEND;
                        } else {
                            ArrayList<Integer> indexsAlreadyGet = new ArrayList<Integer>();
                            BluetoothVar.indexResendCommand = new LinkedList<Integer>();
                            for (HeartRateData hr : BluetoothVar.heartRateBTDataList) {
                                indexsAlreadyGet.add(hr.id);
                            }
                            for (int i = 1; i < heartRateCount + 1; i++) {
                                if (!indexsAlreadyGet.contains(i)) {
                                    BluetoothVar.indexResendCommand.addLast(i);
                                }
                            }
                            ret = Commands.RESULTCODE_INDEXS_COMMAND;
                        }
                    } else {
                        LogUtil.i(TAG, "获取完所有心率数据!!!");
                        ret = Commands.RESULTCODE_SUCCESS;
                    }
                }
            }
            // 接收单独发送索引号的命令解析
            else {
                LogUtil.i(TAG, "心率:这条是根据索引号获取的,索引号为:" + index);
                BluetoothVar.indexResendCommand.remove(index);
                if (BluetoothVar.indexResendCommand.size() == 0) { // 单独根据索引号发送的命令已全部接收完
                    ret = Commands.RESULTCODE_SUCCESS;
                } else { // 需要继续获取
                    ret = Commands.RESULTCODE_CONTINUE_RECEIVING;
                }
            }*/

        }
        return ret;
    }
}
