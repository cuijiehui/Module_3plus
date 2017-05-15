package cn.appscomm.bluetooth.protocol.AboutSport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 所有数据类型的条数(运动、睡眠、心率、情绪疲劳度、血压)
 * Created by Administrator on 2016/1/27.
 */
public class AllDataTypeCount extends Leaf {

    /**
     * 所有数据类型的条数(运动、睡眠、心率、情绪疲劳度、血压)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public AllDataTypeCount(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_TOTAL_SPORT_SLEEP_COUNT, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 准备获取运动和睡眠条数...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定,目前有运动、睡眠、心率、情绪疲劳度、血压，后面还可能扩展
     * 例子:
     * 6F 55 80   04 00   64 00 06 00  …… 8F(100条运动、6条睡眠)
     * 1~2、运动总数
     * 3~4、睡眠总数
     * 5~6、心率总数
     * 7~8、情绪和疲劳度总数
     * 9~10、血压总数
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0 && len % 2 == 0) {
            BluetoothVar.sportCount = (int) ParseUtil.bytesToLong(contents, 0, 1);                     // 运动数据总数
            BluetoothVar.sleepCount = (int) ParseUtil.bytesToLong(contents, 2, 3);                     // 睡眠数据总数
            if (len > 4) {
                BluetoothVar.heartRateCount = (int) ParseUtil.bytesToLong(contents, 4, 5);             // 心率数据总数
            }
            if (len > 6) {
                BluetoothVar.moodCount = (int) ParseUtil.bytesToLong(contents, 6, 7);                  // 情绪和疲劳度总数
            }
            if (len > 8) {
                BluetoothVar.bloodPressureCount = (int) ParseUtil.bytesToLong(contents, 8, 9);         // 血压总数
            }
            LogUtil.i(TAG, "查询返回 : 运动数据" + BluetoothVar.sportCount + "条 睡眠数据" + BluetoothVar.sleepCount + "条 心率数据" + BluetoothVar.heartRateCount + "条 情绪和疲劳度" + BluetoothVar.moodCount + "条 血压" + BluetoothVar.bloodPressureCount + "条!!!");
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
