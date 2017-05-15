package cn.appscomm.ota.util;

/**
 * 作者：hsh
 * 日期：2017/5/5
 * 说明：
 */

public enum OtaPrintf {
    INSTANCE;

    private static final String TAG = OtaPrintf.class.getSimpleName();
    private int max = 0;
    private int nordicLen = 0;
    private int freescaleLen = 0;
    private int touchLen = 0;
    private int heartRateLen = 0;
    private int languagePictureLen = 0;
    private int lastPre = 0;
    private boolean nordicUpdateFlag = false;
    private boolean freescaleUpdateFlag = false;
    private boolean touchUpdateFlag = false;
    private boolean heartRateUpdateFlag = false;
    private boolean languageUpdateFlag = false;
    private long haveUseTime = 0L;

    public void init() {
        max = 0;
        nordicLen = 0;
        freescaleLen = 0;
        touchLen = 0;
        heartRateLen = 0;
        languagePictureLen = 0;
        lastPre = 0;
        nordicUpdateFlag = false;
        freescaleUpdateFlag = false;
        touchUpdateFlag = false;
        heartRateUpdateFlag = false;
        languageUpdateFlag = false;
        haveUseTime = System.currentTimeMillis();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void send(int size, byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
//            LogUtil.i(TAG, ">>>>>>>>>>发送(" + size + "):" + OtaUtil.byteArrayToHexString(bytes));
        }
    }

    public void receive(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
//            LogUtil.i(TAG, "<<<<<<<<<<接收:" + OtaUtil.byteArrayToHexString(bytes));
        }
    }

    public void setUpdateTypeLen(byte updateType, int len) {
        switch (updateType) {
            case (byte) 0x04:
                nordicLen = len;
                break;
            case (byte) 0x08:
                freescaleLen = len;
                break;
            case (byte) 0x10:
                touchLen = len;
                break;
            case (byte) 0x20:
                heartRateLen = len;
                break;
            case (byte) 0x40:
                languagePictureLen = len;
                break;
        }
    }

    public void progress(int len) {
        int haveSend = max - len;
        int totalPre = (int) (haveSend * 100f / max);
        int showProgress = 1;

        if (totalPre % showProgress == 0 && lastPre != totalPre) {
            String str = "总进度(" + totalPre + "%)\t现在升级(";
            haveSend -= 2;
            if (haveSend > 0 && haveSend - touchLen < 0) {
                str += "触摸芯片" + (int) (haveSend * 100f / touchLen) + "%)\t";
            }
            haveSend -= touchLen;
            if (haveSend > 0 && haveSend - heartRateLen < 0) {
                touchUpdateFlag = true;
                str += "心率" + (int) (haveSend * 100f / heartRateLen) + "%)\t";
            }
            haveSend -= heartRateLen;
            if (haveSend > 0 && haveSend - languagePictureLen < 0) {
                touchUpdateFlag = true;
                heartRateUpdateFlag = true;
                str += "字库或照片" + (int) (haveSend * 100f / languagePictureLen) + "%)\t";
            }
            haveSend -= languagePictureLen;
            if (haveSend > 0 && haveSend - freescaleLen < 0) {
                touchUpdateFlag = true;
                heartRateUpdateFlag = true;
                languageUpdateFlag = true;
                str += "飞思卡尔" + (int) (haveSend * 100f / freescaleLen) + "%)\t";
            }
            haveSend -= freescaleLen;
            if (haveSend > 0 && haveSend - nordicLen < 0) {
                touchUpdateFlag = true;
                heartRateUpdateFlag = true;
                languageUpdateFlag = true;
                freescaleUpdateFlag = true;
                str += "Nordic" + (int) (haveSend * 100f / nordicLen) + "%)\t";
            }
            str += "已经升级( " + (touchLen > 0 && touchUpdateFlag ? "触摸芯片 " : "")
                    + (heartRateLen > 0 && heartRateUpdateFlag ? "心率 " : "")
                    + (languagePictureLen > 0 && languageUpdateFlag ? "字库或照片 " : "")
                    + (freescaleLen > 0 && freescaleUpdateFlag ? "飞思卡尔 " : "")
                    + (nordicLen > 0 && nordicUpdateFlag ? "Nordic " : "")
                    + ")\t";
            int sec = (int) (System.currentTimeMillis() - haveUseTime) / 1000;
            str += "用时(" + (sec / 60 > 0 ? sec / 60 + "分" : "") + sec % 60 + "秒)";
            LogUtil.i(TAG, str);
        }
        lastPre = totalPre;
    }


}
