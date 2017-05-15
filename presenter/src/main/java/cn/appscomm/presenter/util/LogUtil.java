package cn.appscomm.presenter.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 作者：hsh
 * 日期：2017/3/21
 * 说明：日志打印类(所有的日志都用该类来管理)
 */

public class LogUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM月dd HH:mm:ss");

    private static boolean isPrintfLog = false;                                                     // 是否打印日志
    private static boolean isWriteLog = false;                                                      // 是否写日志

    private static FileOutputStream outputStream;

    private static final int TYPE_INFO = 0;
    private static final int TYPE_WRITE = 1;
    private static final int TYPE_ERROR = 2;
    private static final int TYPE_VERBOSE = 3;

    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            String path = externalStorageDirectory.getAbsolutePath() + "/AppscommLog/";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(new File(path), "log.txt");
            try {
                outputStream = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
            }
        }
    }

    /**
     * 初始化各个M层日志及设置打印或写日志标志
     *
     * @param isPrintfLog 是否打印日志
     * @param isWriteLog  是否写日志
     */
    public static void init(boolean isPrintfLog, boolean isWriteLog) {
        LogUtil.isPrintfLog = isPrintfLog;
        LogUtil.isWriteLog = isWriteLog;
        initMLogPrintf();
    }

    // 初始化各个M层的日志打印
    private static void initMLogPrintf() {
        cn.appscomm.bluetooth.util.LogUtil.init(bluetoothLogCall);
        cn.appscomm.bluetoothscan.util.LogUtil.init(bluetoothScanLogCall);
        cn.appscomm.db.util.LogUtil.init(dbLogCall);
        cn.appscomm.ota.util.LogUtil.init(otaLogCall);
        cn.appscomm.server.util.LogUtil.init(serverLogCall);
        cn.appscomm.sp.util.LogUtil.init(spLogCall);
        cn.appscomm.thirdpartyloginshare.util.LogUtil.init(thirdPartyLoginShareLogCall);
    }

    private static void printf(String tag, String msg, int type) {
        if (isPrintfLog) {
            switch (type) {
                case TYPE_WRITE:
                    Log.w(tag, msg);
                    break;
                case TYPE_ERROR:
                    Log.e(tag, msg);
                    break;
                case TYPE_VERBOSE:
                    Log.v(tag, msg);
                    break;
                case TYPE_INFO:
                default:
                    Log.i(tag, msg);
                    break;
            }
        }
        if (isWriteLog) {
            String time = sdf.format(System.currentTimeMillis());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (outputStream != null) {
                    String className = tag;
                    String content = "(" + time + ") : " + msg + "\r\n";
//                        writeAndFlush(("*******" + className + "(" + time + ")*******\r\n").getBytes());
                    writeAndFlush(content.getBytes());
                }
            }
        }
    }

    public static void i(String TAG, String msg) {
        printf(TAG, msg, TYPE_INFO);
    }

    public static void w(String TAG, String msg) {
        printf(TAG, msg, TYPE_WRITE);
    }

    public static void e(String TAG, String msg) {
        printf(TAG, msg, TYPE_ERROR);
    }

    public static void v(String TAG, String msg) {
        printf(TAG, msg, TYPE_VERBOSE);
    }

    public static void writeAndFlush(byte[] buf) {
        try {
            outputStream.write(buf);
            outputStream.flush();
        } catch (IOException e) {
        }
    }

    private static cn.appscomm.bluetooth.util.LogUtil.ILogPrintfCall bluetoothLogCall = new cn.appscomm.bluetooth.util.LogUtil.ILogPrintfCall() {
        @Override
        public void iPrintf(String TAG, String msg) {
            i(TAG, msg);
        }

        @Override
        public void wPrintf(String TAG, String msg) {
            w(TAG, msg);
        }

        @Override
        public void ePrintf(String TAG, String msg) {
            e(TAG, msg);
        }

        @Override
        public void vPrintf(String TAG, String msg) {
            v(TAG, msg);
        }
    };
    private static cn.appscomm.bluetoothscan.util.LogUtil.ILogPrintfCall bluetoothScanLogCall = new cn.appscomm.bluetoothscan.util.LogUtil.ILogPrintfCall() {
        @Override
        public void iPrintf(String TAG, String msg) {
            i(TAG, msg);
        }

        @Override
        public void wPrintf(String TAG, String msg) {
            w(TAG, msg);
        }

        @Override
        public void ePrintf(String TAG, String msg) {
            e(TAG, msg);
        }

        @Override
        public void vPrintf(String TAG, String msg) {
            v(TAG, msg);
        }
    };
    private static cn.appscomm.db.util.LogUtil.ILogPrintfCall dbLogCall = new cn.appscomm.db.util.LogUtil.ILogPrintfCall() {
        @Override
        public void iPrintf(String TAG, String msg) {
            i(TAG, msg);
        }

        @Override
        public void wPrintf(String TAG, String msg) {
            w(TAG, msg);
        }

        @Override
        public void ePrintf(String TAG, String msg) {
            e(TAG, msg);
        }

        @Override
        public void vPrintf(String TAG, String msg) {
            v(TAG, msg);
        }
    };
    private static cn.appscomm.ota.util.LogUtil.ILogPrintfCall otaLogCall = new cn.appscomm.ota.util.LogUtil.ILogPrintfCall() {
        @Override
        public void iPrintf(String TAG, String msg) {
            i(TAG, msg);
        }

        @Override
        public void wPrintf(String TAG, String msg) {
            w(TAG, msg);
        }

        @Override
        public void ePrintf(String TAG, String msg) {
            e(TAG, msg);
        }

        @Override
        public void vPrintf(String TAG, String msg) {
            v(TAG, msg);
        }
    };
    private static cn.appscomm.server.util.LogUtil.ILogPrintfCall serverLogCall = new cn.appscomm.server.util.LogUtil.ILogPrintfCall() {
        @Override
        public void iPrintf(String TAG, String msg) {
            i(TAG, msg);
        }

        @Override
        public void wPrintf(String TAG, String msg) {
            w(TAG, msg);
        }

        @Override
        public void ePrintf(String TAG, String msg) {
            e(TAG, msg);
        }

        @Override
        public void vPrintf(String TAG, String msg) {
            v(TAG, msg);
        }
    };
    private static cn.appscomm.sp.util.LogUtil.ILogPrintfCall spLogCall = new cn.appscomm.sp.util.LogUtil.ILogPrintfCall() {
        @Override
        public void iPrintf(String TAG, String msg) {
            i(TAG, msg);
        }

        @Override
        public void wPrintf(String TAG, String msg) {
            w(TAG, msg);
        }

        @Override
        public void ePrintf(String TAG, String msg) {
            e(TAG, msg);
        }

        @Override
        public void vPrintf(String TAG, String msg) {
            v(TAG, msg);
        }
    };
    private static cn.appscomm.thirdpartyloginshare.util.LogUtil.ILogPrintfCall thirdPartyLoginShareLogCall = new cn.appscomm.thirdpartyloginshare.util.LogUtil.ILogPrintfCall() {
        @Override
        public void iPrintf(String TAG, String msg) {
            i(TAG, msg);
        }

        @Override
        public void wPrintf(String TAG, String msg) {
            w(TAG, msg);
        }

        @Override
        public void ePrintf(String TAG, String msg) {
            e(TAG, msg);
        }

        @Override
        public void vPrintf(String TAG, String msg) {
            v(TAG, msg);
        }
    };
}
