package cn.appscomm.bluetooth.protocol.AboutSport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.mode.SportBT;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 上传运动数据
 * Created by Administrator on 2016/1/27.
 */
public class GetSportData extends Leaf {
    private int sportCount;

    /**
     * 上传运动数据
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     * @param sportCount               需要获取的运动条数
     */
    public GetSportData(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70, int sportCount) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_GET_SPORT_DATA, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        this.sportCount = sportCount;
        super.setContentLen(contentLen);
        super.setContent(content);
        LogUtil.i(TAG, "查询 : 共" + sportCount + "条运动数据,准备获取...");
    }

    /**
     * contents字节数组解析：
     * 长度固定为18
     * 例子:
     * 设备端持续发送直到最后一条，每条数据的步数，卡路里，距离都为相对值（相对于前一条）
     * 6F 54 80   12 00   01 00 79 44 3C 56 64 00 00 00 0A 00 00 00 32 00 00 00   8F
     * 6F 54 80   12 00   02 00 79 45 3C 56 32 00 00 00 05 00 00 00 19 00 00 00   8F
     * 1~2、	    数据索引值(1~65535)
     * 3~6、	    时间戳
     * 7~10、	    步数值(单位:步)
     * 11~14、	   卡路里(单位:卡)
     * 15~18、	   距离(单位:米)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            int index = (int) ParseUtil.bytesToLong(contents, 0, 1);                                // 索引值
            long timeStamp = ParseUtil.bytesToLong(contents, 2, 5);                                 // 时间戳
            int step = (int) ParseUtil.bytesToLong(contents, 6, 9);                                 // 步数
            int calorie = (int) ParseUtil.bytesToLong(contents, 10, 13);                            // 卡路里
            int distance = (int) ParseUtil.bytesToLong(contents, 14, 17);                           // 距离
            int sportTime = 0;
            if (len > 18) {
                sportTime = (int) ParseUtil.bytesToLong(contents, 18, 21);                          // 运动时长
            }
            LogUtil.i(TAG, "查询返回 : 运动数据(索引值:" + index + " 时间(" + timeStamp + "):" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(timeStamp * 1000)) + " 步数:" + step + "步 卡路里:" + calorie + "卡 距离:" + distance + "米");

            if (BluetoothVar.sportBTDataList == null || BluetoothVar.sportBTDataList.size() == 0 || index == 1) {
                BluetoothVar.sportBTDataList = new LinkedList<>();
            }
            BluetoothVar.sportBTDataList.add(new SportBT(index, step, calorie, distance, sportTime, timeStamp));
            ret = BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE;

            if (BluetoothVar.sportBTDataList.size() == sportCount) {
                LogUtil.i(TAG, "获取完所有运动数据!!!");
                ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
            } else {
                if (index == sportCount) { // 获取到最后一条，但没有收到完整的数据，需要重新获取
                    if (BluetoothVar.sportBTDataList != null) {
                        BluetoothVar.sportBTDataList.clear();
                    }
                    LogUtil.i(TAG, "有运动数据丢失，需要重新获取!!!");
                    ret = BluetoothCommandConstant.RESULT_CODE_RE_SEND;
                }
            }

            /*
            // 如果GlobalVar.indexsResendCommand为null 或 大小为0，则说明是获取全部索引的命令
            if (BluetoothVar.indexResendCommand == null || BluetoothVar.indexResendCommand.size() == 0) {
                if (index == sportCount) { // 是否到达最后一包 : 在收到最后一包数据的情况下(如果没有收到最后一包，则需要超时判断有没有接收到最后一包了)
                    if (BluetoothVar.sportBTDataList.size() != sportCount) { // 获取到的数据和总数量值不相同
                        LogUtil.i(TAG, "运动:存在数据丢失,共" + sportCount + "条数据 接收到的数量是" + BluetoothVar.sportBTDataList.size());
                        if ((sportCount - BluetoothVar.sportBTDataList.size()) > 5) { // 如果丢失的包超过5条，则需要重新获取一次全部的数据
                            ret = Commands.RESULTCODE_RE_SEND;
                        } else {
                            ArrayList<Integer> indexsAlreadyGet = new ArrayList<Integer>();
                            BluetoothVar.indexResendCommand = new LinkedList<Integer>();
                            for (SportCacheData sd : BluetoothVar.sportBTDataList) {
                                indexsAlreadyGet.add(sd.id);
                            }
                            for (int i = 1; i < sportCount + 1; i++) {
                                if (!indexsAlreadyGet.contains(i)) {
                                    BluetoothVar.indexResendCommand.addLast(i);
                                }
                            }
                            ret = Commands.RESULTCODE_INDEXS_COMMAND;
                        }
                    } else {
                        LogUtil.i(TAG, "获取完所有运动数据!!!");
                        ret = Commands.RESULTCODE_SUCCESS;
                    }
                }
            }
            // 接收单独发送索引号的命令解析
            else {
                LogUtil.i(TAG, "运动:这条是根据索引号获取的,索引号为:" + index);
                BluetoothVar.indexResendCommand.remove(index);
                if (BluetoothVar.indexResendCommand.size() == 0) { // 单独根据索引号发送的命令已全部接收完
                    ret = Commands.RESULTCODE_SUCCESS;
                } else { // 需要继续获取
                    ret = Commands.RESULTCODE_CONTINUE_RECEIVING;
                }
            }
            */

        }
        return ret;
    }
}
