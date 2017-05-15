package cn.appscomm.bluetooth.protocol.AboutState;

import java.util.LinkedList;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.protocol.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 提醒设置
 * Created by Administrator on 2016/1/27.
 */
public class RemindSetting extends Leaf {
    private int remindCount;

    /**
     * 提醒设置
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     * @param remindCount              需要获取的提醒条数
     */
    public RemindSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70, int remindCount) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_REMIND_SETTING, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        this.remindCount = remindCount;
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定
     * 例子:
     * 6F 92 80   05 00   01 01 08 08 1F   8F
     * 1、提醒索引值(1~255)
     * 2、提醒类型(吃饭(00)/吃药(01)/喝水(02)/睡觉(03)/ 清醒(04)/ 运动(05)/会议(06)/自定义(07)……)
     * 3、时(24小时制)
     * 4、分
     * 5、提醒周期(位对应从最低位到次高位对应周一到周日)
     * 6、提醒开关
     * 6~N、自定义提醒内容(utf8格式,只用于提醒类型为自定义提醒)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len > 0) {
            int index = (int) (contents[0] & 0xFF);                     // 索引值
            int reminderType = (int) (contents[1] & 0xFF);              // 提醒类型
            int reminderHour = (int) (contents[2] & 0xFF);              // 提醒时
            int reminderMin = (int) (contents[3] & 0xFF);               // 提醒分
            byte reminderCycle = contents[4];                           // 提醒周期
            int remindSwitchStatus = (int) (contents[5] & 0xFF);        // 提醒开关
            String remindContent = "";
            if (len > 6) {
                try {
                    remindContent = new String(contents, 6, len - 6, "UTF-8");  // 提醒内容
                } catch (Exception e) {
                }
            }

            if (BluetoothVar.reminderBTDataList == null || BluetoothVar.reminderBTDataList.size() == 0 || index == 1) {
                BluetoothVar.reminderBTDataList = new LinkedList<>();
            }

            BluetoothVar.reminderBTDataList.add(new ReminderBT(index, reminderType, reminderHour, reminderMin, reminderCycle, remindSwitchStatus != 0, remindContent));
            ret = BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE;
            LogUtil.i(TAG, "提醒:索引值(" + index + ") 类型(" + reminderType + ") 时(" + reminderHour + ") 分(" + reminderMin + ") 周期(" + reminderCycle + ") 开关(" + remindSwitchStatus + ") 自定义内容(" + remindContent + ")");

            if (BluetoothVar.reminderBTDataList.size() == remindCount) {
                LogUtil.i(TAG, "获取完所有提醒数据!!!");
                ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
            } else {
                if (index == remindCount) { // 获取到最后一条，但没有收到完整的数据，需要重新获取
                    if (BluetoothVar.reminderBTDataList != null) {
                        BluetoothVar.reminderBTDataList.clear();
                    }
                    LogUtil.i(TAG, "有提醒数据丢失，需要重新获取!!!");
                    ret = BluetoothCommandConstant.RESULT_CODE_RE_SEND;
                }
            }

            /*
            // 如果GlobalVar.indexsResendCommand为null 或 大小为0，则说明是获取全部索引的命令
            if (BluetoothVar.indexResendCommand == null || BluetoothVar.indexResendCommand.size() == 0) {
                if (index == remindCount) { // 是否到达最后一包 : 在收到最后一包数据的情况下(如果没有收到最后一包，则需要超时判断有没有接收到最后一包了)
                    if (BluetoothVar.reminderBTDataList.size() != remindCount) { // 获取到的数据和总数量值不相同
                        LogUtil.i(TAG, "提醒:存在数据丢失,共" + remindCount + "条数据 接收到的数量是" + BluetoothVar.reminderBTDataList.size());
                        if ((remindCount - BluetoothVar.reminderBTDataList.size()) > 5) { // 如果丢失的包超过5条，则需要重新获取一次全部的数据
                            ret = Commands.RESULTCODE_RE_SEND;
                        } else {
                            ArrayList<Integer> indexsAlreadyGet = new ArrayList<Integer>();
                            BluetoothVar.indexResendCommand = new LinkedList<Integer>();
                            for (ReminderData sd : BluetoothVar.reminderBTDataList) {
                                indexsAlreadyGet.add(sd.id);
                            }
                            for (int i = 1; i < remindCount + 1; i++) {
                                if (!indexsAlreadyGet.contains(i)) {
                                    BluetoothVar.indexResendCommand.addLast(i);
                                }
                            }
                            ret = Commands.RESULTCODE_INDEXS_COMMAND;
                        }
                    } else {
                        LogUtil.i(TAG, "获取完所有提醒数据!!!");
                        ret = Commands.RESULTCODE_SUCCESS;
                    }
                }
            }
            // 接收单独发送索引号的命令解析
            else {
                LogUtil.i(TAG, "提醒:这条是根据索引号获取的,索引号为:" + index);
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

    /**
     * 提醒设置
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param operation                提醒操作(0x00:新增 0x01:修改 0x02:删除 0x03:全部删除)
     * @param remindType               新增或单条删除 提醒类型
     * @param remindHour               新增或单条删除 提醒时
     * @param remindMin                新增或单条删除 提醒分
     * @param remindCycle              新增或单条删除 提醒周期
     * @param remindSwitchStatus       新增或单条删除 提醒开光状态
     * @param remindContent            新增或单条删除或修改 提醒内容
     * @param remindType1              修改的        提醒类型
     * @param remindHour1              修改的        提醒时
     * @param remindMin1               修改的        提醒分
     * @param remindCycle1             修改的        提醒周期
     * @param remindSwitchStatus1      修改的        提醒开光状态
     */
    public RemindSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, byte operation, byte remindType, byte remindHour, byte remindMin, byte remindCycle, byte remindSwitchStatus, byte[] remindContent,
                         byte remindType1, byte remindHour1, byte remindMin1, byte remindCycle1, byte remindSwitchStatus1) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_REMIND_SETTING, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[len];
        if (operation == 0x03) { // 全部删除
            content[0] = operation;
            LogUtil.i(TAG, "设置 : 准备设置(全部删除)!!!");
        } else {
            // 删除不需要提醒内容
            content[0] = operation;
            content[1] = remindType;
            content[2] = remindHour;
            content[3] = remindMin;
            content[4] = remindCycle;
            content[5] = remindSwitchStatus;
            switch (operation) {
                case 0x00:  // 新增
                    if (remindType == BluetoothCommandConstant.REMIND_TYPE_CUSTOM && remindContent != null) {
                        System.arraycopy(remindContent, 0, content, 6, remindContent.length);
                    }
                    LogUtil.i(TAG, "设置 : 准备设置(新增)!!!");
                    break;
                case 0x01:  // 修改
                    content[6] = remindType1;
                    content[7] = remindHour1;
                    content[8] = remindMin1;
                    content[9] = remindCycle1;
                    content[10] = remindSwitchStatus1;
                    if (remindType == BluetoothCommandConstant.REMIND_TYPE_CUSTOM && remindContent != null) {
                        System.arraycopy(remindContent, 0, content, 11, remindContent.length);
                    }
                    LogUtil.i(TAG, "设置 : 准备设置(修改)!!!");
                    break;
                case 0x02:  // 删除
                    LogUtil.i(TAG, "设置 : 准备设置(单条删除)!!!");
                    break;
            }
        }
        super.setContentLen(contentLen);
        super.setContent(content);
    }
}
