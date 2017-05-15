package cn.appscomm.bluetooth.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 作者：hsh
 * 日期：2017/3/21
 * 说明：
 */

public class ParseUtil {
    private static String hexStr = "0123456789ABCDEF";

    /**
     * 解析日期时间 0~1:年 2:月 3:日 4:时 5:分 6:秒
     *
     * @param bytes
     * @return
     */
    public static String getDateTime(byte[] bytes, int start) {
        int year, month, day, hour, min, sec;
        year = (int) bytesToLong(bytes, start, start + 1);
        month = (int) (bytes[start + 2] & 0xff);
        day = (int) (bytes[start + 3] & 0xff);
        hour = (int) (bytes[start + 4] & 0xff);
        min = (int) (bytes[start + 5] & 0xff);
        sec = (int) (bytes[start + 6] & 0xff);
        return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
    }

    /**
     * bytes转换为long，原理如下:
     * sum = (long) (bytes[0] & 0xff) + (long) ((bytes[1] & 0xff) << 8) +
     * (long) ((bytes[2] & 0xff) << 16) + (long) ((bytes[3] & 0xff) << 24) +
     * (long) ((bytes[4] & 0xff) << 32) + (long) ((bytes[5] & 0xff) << 40)……
     *
     * @param bytes 需要转换的bytes
     * @param start 开始索引
     * @param end   结束索引(包含)
     * @return 转换后的long
     */
    public static long bytesToLong(byte[] bytes, int start, int end) {
        if (start > end) {
            return -1;
        }
        long sum = 0;
        for (int i = start, bit = 0; i < end + 1; i++, bit += 8) {
            long temp = (long) (bytes[i] & 0xff) << bit;
            sum += temp;
        }
        return sum;
    }

    /**
     * int转换到byte[]
     *
     * @param integer  需要转换的int
     * @param byteSize byte[]数组的大小
     * @return 转换后的byte[]
     */
    public static byte[] intToByteArray(final int integer, int byteSize) {
        byte[] bytes = new byte[byteSize];
        for (int i = 0; i < byteSize; i++) {
            bytes[i] = (byte) ((integer >> (8 * i)) & 0xFF);
        }
        return bytes;
    }

    /**
     * 二进制转换到十六进制字符串
     *
     * @param bytes 二进制数组
     * @return 十六进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        String result = "";
        String hex;
        for (int i = 0; i < bytes.length; i++) {
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));            //字节高4位
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));                  //字节低4位
            result += hex + " ";
        }
        return result;
    }

    /**
     * 东8区的时间戳转换为时间+时区,该时区是手环的时区,常用于上传数据
     *
     * @param timestamp 时间戳
     * @param isAddTZ   是否添加时区字段
     * @return 时间+时区
     */
    public static String eightTZTimeStampToStringTZ(long timestamp, boolean isAddTZ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdf_z = new SimpleDateFormat("ZZ");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        int len = (timestamp + "").length();
        timestamp = len > 10 ? timestamp : (timestamp * 1000L);
        String tz = sdf_z.format(timestamp);
        return sdf.format(timestamp) + (isAddTZ ? tz : "");
    }
}
