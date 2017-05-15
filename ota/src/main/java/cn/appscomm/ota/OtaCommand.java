package cn.appscomm.ota;

import java.util.LinkedList;
import java.util.List;

import cn.appscomm.ota.interfaces.IUpdateProgressCallBack;
import cn.appscomm.ota.util.LogUtil;
import cn.appscomm.ota.util.OtaAppContext;
import cn.appscomm.ota.util.OtaPrintf;
import cn.appscomm.ota.util.OtaUtil;

/**
 * 作者：hsh
 * 日期：2017/5/3
 * 说明：OTA升级命令管理
 * 1、OTA命令的生成
 * 2、OTA命令的发送
 * 3、OTA命令的解析
 */

public enum OtaCommand {
    INSTANCE;

    private static final String TAG = OtaCommand.class.getSimpleName();
    private LinkedList<CommandInfo> commandInfoList = new LinkedList<>();
    private final byte PACKAGE_COUNT = 0x0a;                                                        // 一次发送的包数

    private final byte UPDATE_TYPE_NORDIC = (byte) 0x04;                                            // 升级类型 : Nordic
    private final byte UPDATE_TYPE_FREESCALE = (byte) 0x08;                                         // 升级类型 : Freescale
    private final byte UPDATE_TYPE_TOUCH = (byte) 0x10;                                             // 升级类型 : 触摸芯片
    private final byte UPDATE_TYPE_HEARTRATE = (byte) 0x20;                                         // 升级类型 : 心率
    private final byte UPDATE_TYPE_PICTURE_LANGUAGE = (byte) 0x40;                                  // 升级类型 : 图库或字库

    private final String NOTE_BIN_TOTAL_LENGTH = "NOTE_BIN_TOTAL_LENGTH";
    private final String NOTE_BIN_LENGTH = "NOTE_BIN_LENGTH";
    private final String NOTE_CRC = "NOTE_CRC";
    private final String NOTE_BIN_CONTENT = "NOTE_BIN_CONTENT";
    private final String NOTE_ASK_FOR_CHECK_CRC = "NOTE_ASK_FOR_CHECK_CRC";

    private final boolean UUID_1531 = true;
    private final boolean UUID_1532 = false;

    public static final int UPDATE_SUCCESS = 0;
    public static final int UPDATE_ING = 1;
    public static final int UPDATE_FAIL = 2;

    private OtaService otaService;
    private IUpdateProgressCallBack iUpdateProgressCallBack;
    private int totalPackage = 0;

    class CommandInfo {
        byte[] content;
        boolean is1531Flag;
        String note;

        CommandInfo(byte[] content, boolean is1531Flag, String note) {
            this.content = content;
            this.is1531Flag = is1531Flag;
            this.note = note;
        }
    }

    /**
     * 开始升级
     *
     * @param otaService              OtaService:用于发送命令
     * @param iUpdateProgressCallBack 升级的进度、结果回调
     */
    public boolean start(OtaService otaService, IUpdateProgressCallBack iUpdateProgressCallBack) {
        this.otaService = otaService;
        this.iUpdateProgressCallBack = iUpdateProgressCallBack;
        if (this.otaService == null) return false;
        if (commandInfoList == null || commandInfoList.size() == 0) return false;
        if (this.iUpdateProgressCallBack != null) {
            this.iUpdateProgressCallBack.curUpdateMax(totalPackage);
        }
        send();
        return true;
    }

    private void printfCommand() {
        if (commandInfoList != null && commandInfoList.size() > 0) {
            for (CommandInfo commandInfo : commandInfoList) {
                LogUtil.i(TAG, "" + OtaUtil.byteArrayToHexString(commandInfo.content));
            }
        }
    }

    /**
     * 生成要升级的命令
     *
     * @param pathList 升级文件路径集合
     * @return true:成功 false:失败
     */
    public boolean create(List<String> pathList) {
        try {
            OtaPrintf.INSTANCE.init();
            commandInfoList.clear();
            totalPackage = 0;
            int iBinFileTotalLength = 0;
            byte[] languageAddress = null;
            for (String path : pathList) {
                String binPath = path;
                String datPath = "";
                byte updateType = getUpdateType(binPath);
                byte[] binContent;
                if (binPath.contains(".zip")) {                                                     // Nordic
                    OtaUtil.unZip(path);
                    binPath = OtaAppContext.INSTANCE.getContext().getFileStreamPath("application.bin").getPath();
                    datPath = OtaAppContext.INSTANCE.getContext().getFileStreamPath("application.dat").getPath();
                    binContent = OtaUtil.getBinContent(binPath, 0);
                } else if (binPath.contains("Language") || binPath.contains("Picture")) {           // 字库 或 图片
                    binContent = OtaUtil.getBinContent(binPath, 0);
                    byte[] tempBinContent = new byte[binContent.length - 4];
                    languageAddress = new byte[4];
                    System.arraycopy(binContent, 0, languageAddress, 0, 4);
                    System.arraycopy(binContent, 4, tempBinContent, 0, tempBinContent.length);
                    binContent = tempBinContent;
                } else {                                                                            // 飞思卡尔 或 触摸 或 心率
                    binContent = OtaUtil.getBinContent(binPath, 5);
                }
                iBinFileTotalLength += binContent.length;
                byte[] binLength = OtaUtil.getBinLength(binContent.length);
                byte[] crcCheck = OtaUtil.getCrcCheck(datPath, binContent);
                if (binContent.length > 0 && binLength.length > 0 && crcCheck.length > 0) {
                    int curUpdateTypeLen = addMiddleCommand(binContent, binLength, crcCheck, updateType);
                    OtaPrintf.INSTANCE.setUpdateTypeLen(updateType, curUpdateTypeLen);
                }
            }
            if (iBinFileTotalLength > 0) {
                byte[] binTotalLength = OtaUtil.getBinTotalLength(iBinFileTotalLength, languageAddress);
                addStartCommand(binTotalLength);
                addEndCommand();
            }
            printfCommand();
            totalPackage = commandInfoList.size();
            OtaPrintf.INSTANCE.setMax(totalPackage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 加入要升级命令的头部
    private void addStartCommand(byte[] binTotalLength) {
        commandInfoList.addFirst(new CommandInfo(binTotalLength, UUID_1532, NOTE_BIN_TOTAL_LENGTH));// 总bin文件大小
        commandInfoList.addFirst(new CommandInfo(new byte[]{0x09}, UUID_1531, ""));                 // 09
    }

    // 加入要升级命令的中间部分
    private int addMiddleCommand(byte[] binContents, byte[] binLength, byte[] crcCheck, byte updateType) {
        int len = commandInfoList.size();
        commandInfoList.addLast(new CommandInfo(new byte[]{0x01, updateType}, UUID_1531, ""));      // 01 08
        commandInfoList.addLast(new CommandInfo(binLength, UUID_1532, NOTE_BIN_LENGTH));            // bin文件大小
        commandInfoList.addLast(new CommandInfo(new byte[]{0x02, 0x00}, UUID_1531, ""));            // 02 00
        commandInfoList.addLast(new CommandInfo(crcCheck, UUID_1532, NOTE_CRC));                    // CRC校验值
        commandInfoList.addLast(new CommandInfo(new byte[]{0x02, 0x01}, UUID_1531, ""));            // 02 01
        commandInfoList.addLast(new CommandInfo(new byte[]{0x08, PACKAGE_COUNT, 0x00}, UUID_1531, "")); // 08 发送包数 00
        commandInfoList.addLast(new CommandInfo(new byte[]{0x03}, UUID_1531, ""));                  // 03
        if (binContents != null) {                                                                  // bin内容
            int binContentLen = binContents.length;
            int onePackageLen = PACKAGE_COUNT * 20;
            int totalPackageCount = binContentLen % onePackageLen == 0 ? binContentLen / onePackageLen : (binContentLen / onePackageLen) + 1;
            LogUtil.i(TAG, "内容总长度为:" + binContentLen + "   每包发送的最大长度为:" + onePackageLen + "   总包数为:" + totalPackageCount);
            for (int i = 0; i < totalPackageCount; i++) {
                int tempLen = ((i + 1) == totalPackageCount) ? binContentLen - (onePackageLen * i) : onePackageLen;
                byte[] tempBytes = new byte[tempLen];
                System.arraycopy(binContents, onePackageLen * i, tempBytes, 0, tempLen);
                commandInfoList.addLast(new CommandInfo(tempBytes, UUID_1532, NOTE_BIN_CONTENT));
            }
        }
        commandInfoList.addLast(new CommandInfo(new byte[]{0x04}, UUID_1531, NOTE_ASK_FOR_CHECK_CRC)); // 04
        return commandInfoList.size() - len;
    }

    // 加入要升级命令的尾部
    private void addEndCommand() {
        commandInfoList.addLast(new CommandInfo(new byte[]{0x05}, true, ""));                       // 05
    }

    private byte getUpdateType(String path) {
        if (path.contains(".zip")) {
            return UPDATE_TYPE_NORDIC;
        }
        if (path.contains("kl17")) {
            return UPDATE_TYPE_FREESCALE;
        }
        if (path.contains("TouchPanel")) {
            return UPDATE_TYPE_TOUCH;
        }
        if (path.contains("Heartrate")) {
            return UPDATE_TYPE_HEARTRATE;
        }
        return UPDATE_TYPE_PICTURE_LANGUAGE;
    }

    /**
     * 解析数据
     *
     * @param bytes 回调时传入null,设备返回时传入具体数据
     * @return 0:升级成功 1:正在升级 2:升级错误
     */
    public int parse(byte[] bytes) {
        if (commandInfoList.size() > 0) {                                                           // 集合里还有命令才需要解析
            String note = commandInfoList.getFirst().note;
            byte[] content = commandInfoList.getFirst().content;

            // 回调返回(一般为1532通道)
            if (bytes == null) {
                if (note.equals(NOTE_BIN_LENGTH) || note.equals(NOTE_BIN_CONTENT) || note.endsWith(NOTE_ASK_FOR_CHECK_CRC)) { // 需要等设备返回的命令
//                    LogUtil.i(TAG, "不需要继续发送，等设备返回数据");
                } else if (content.length == 1 && content[0] == 0x05) {                             // 05
                    commandInfoList.clear();
                    return UPDATE_SUCCESS;
                } else {                                                                            // 其他命令
//                    LogUtil.i(TAG, "继续发送...");
                    commandInfoList.removeFirst();
                    if (!send()) {
                        return UPDATE_FAIL;
                    }
                }
            }
            // 设备返回数据(一般为1531通道)
            else {
                OtaPrintf.INSTANCE.receive(bytes);
                boolean flag = false;
                // 11 xx xx xx xx
                if (bytes[0] == 0x11) {
                    flag = true;
                }
                // 10 xx 01
                else if (bytes[0] == 0x10 && bytes[2] == 0x01 && bytes.length == 3) {
                    switch (bytes[1]) {
                        case 0x01:                                                                  // 10 01 01
                            flag = note.equals(NOTE_BIN_LENGTH);
                            break;
                        case 0x03:                                                                  // 10 03 01
                            flag = true;
                            break;
                        case 0x04:                                                                  // 10 04 01
                            flag = note.equals(NOTE_ASK_FOR_CHECK_CRC);
                            break;
                        case 0x09:                                                                  // 10 09 01
                            flag = note.equals(NOTE_BIN_TOTAL_LENGTH);
                            break;
                    }
                }
                // 错误:其他命令
                else {
                    LogUtil.i(TAG, "存在错误，错误结果为:" + OtaUtil.byteArrayToHexString(bytes) + " 上次发送的命令是:" + OtaUtil.byteArrayToHexString(commandInfoList.getFirst().content));
                    return UPDATE_FAIL;
                }
                if (flag) {
                    commandInfoList.removeFirst();
                    if (!send()) {
                        return UPDATE_FAIL;
                    }
                }
            }
            return UPDATE_ING;
        } else {
            return UPDATE_FAIL;
        }
    }

    /**
     * 发送数据到设备,顺便把进度回调给apk
     */
    private boolean send() {
        if (commandInfoList != null && commandInfoList.size() > 0 && otaService != null) {
            byte[] content = commandInfoList.getFirst().content;
            OtaPrintf.INSTANCE.send(commandInfoList.size(), content);
            int sendType = commandInfoList.getFirst().is1531Flag ? OtaService.SEND_TYPE_1531 : OtaService.SEND_TYPE_1532;
            otaService.sendDataToDevice(content, sendType);
            OtaPrintf.INSTANCE.progress(commandInfoList.size());
            if (iUpdateProgressCallBack != null && totalPackage > 0) {
                iUpdateProgressCallBack.curUpdateProgress(totalPackage - commandInfoList.size());
            }
            return true;
        }
        return false;
    }
}
