package cn.appscomm.ota.util;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 作者：hsh
 * 日期：2017/3/21
 * 说明：
 */

public class OtaUtil {

    /**
     * 二进制转换到十六进制字符串
     *
     * @param bytes 二进制数组
     * @return 十六进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        String result = "";
        String hex;
        String hexStr = "0123456789ABCDEF";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));                                   //字节高4位
            hex += String.valueOf(hexStr.charAt(b & 0x0F));                                         //字节低4位
            result += hex + " ";
        }
        return result;
    }

    /**
     * 解压zip
     *
     * @param path 路径
     */
    public static boolean unZip(String path) {
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(path));                     // 输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = OtaAppContext.INSTANCE.getContext().getFilesDir().getAbsolutePath();    // 输出路径（文件夹目录）
            ZipEntry entry;
            while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
                File file = new File(Parent, entry.getName());
                if (!file.exists()) {
                    (new File(file.getParent())).mkdirs();
                }
                FileOutputStream out = new FileOutputStream(file);
                BufferedOutputStream Bout = new BufferedOutputStream(out);
                int b;
                while ((b = Bin.read()) != -1) {
                    Bout.write(b);
                }
                Bout.close();
                out.close();
            }
            Bin.close();
            Zin.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 计算crc16的值
    private static byte[] crc16(byte[] bytes) {
        byte[] crcBytes = new byte[2];
        int len = bytes.length;
        int crc = 0xFFFF;
        for (int i = 0; i < len; i++) {
            crc = ((short) ((crc >> 8) & 0xFF) | (crc << 8)) & 0xFFFF;
            crc ^= (short) (bytes[i] & 0xFF);
            crc ^= ((short) (crc & 0xFF) >> 4) & 0xFFFF;
            crc ^= ((crc << 8) << 4) & 0xFFFF;
            crc ^= ((crc & 0xff) << 4) << 1 & 0xFFFF;
        }
        crcBytes[0] = (byte) (crc & 0xFF);
        crcBytes[1] = (byte) ((crc >> 8) & 0xFF);
        return crcBytes;
    }

    /**
     * 获取bin文件的内容
     *
     * @param path bin的路径
     * @return bin内容的字节数组
     */
    public static byte[] getBinContent(String path, int addressSectorCountLength) {
        byte[] binContent = null;
        File file = new File(path);
        if (file.exists()) {
            try {
                byte[] totalContent = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                fis.read(totalContent);
                fis.close();
                binContent = new byte[(int) file.length() - addressSectorCountLength];
                System.arraycopy(totalContent, addressSectorCountLength, binContent, 0, binContent.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return binContent;
    }

    /**
     * 获取bin文件的长度
     *
     * @param fileLen 文件长度
     * @return bin文件长度的字节数组
     */
    public static byte[] getBinLength(int fileLen) {
        byte[] binLength = new byte[12];
        for (int i = 0; i < 4; i++) {
            binLength[8 + i] = (byte) ((fileLen >> (8 * i)) & 0xFF);
        }
        return binLength;
    }

    /**
     * 获取crc
     *
     * @param datPath     dat的路径(Nordic需要该路径，其他升级文件不需要)
     * @param binContents bin内容(Nordic不需要传，其他升级文件需要)
     * @return crc字节数组
     */
    public static byte[] getCrcCheck(String datPath, byte[] binContents) {
        byte[] crcCheck = null;
        if (!TextUtils.isEmpty(datPath)) {
            File file = new File(datPath);
            if (file.exists()) {                                                                    // 如果Nordic的dat文件存在,并且现在是升级Nordic，则直接读取dat里的CRC校验
                try {                                                                               // 读取Nordic的crc校验
                    FileInputStream fis = new FileInputStream(file);
                    crcCheck = new byte[(int) file.length()];
                    fis.read(crcCheck);
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (binContents != null) {
            crcCheck = new byte[14];
            byte[] crcBytes = OtaUtil.crc16(binContents);
            crcCheck[crcCheck.length - 2] = crcBytes[0];
            crcCheck[crcCheck.length - 1] = crcBytes[1];
        }
        return crcCheck;
    }

    public static byte[] getBinTotalLength(int total, byte[] languageAddress) {
        byte[] binTotalLength = new byte[8];
        for (int i = 0; i < 4; i++) {
            binTotalLength[i] = (byte) ((total >> (8 * i)) & 0xFF);                                 // 1-4字节 bin大小(所有待升级的bin总大小)
        }
        if (languageAddress != null) {                                                              // 5-8字节 字库的地址
            System.arraycopy(languageAddress, 0, binTotalLength, 4, 4);
        }
        return binTotalLength;
    }

    /**
     * 获取地址和扇区个数
     *
     * @param path 升级文件的路径
     * @return 地址和扇区个数的字节数组
     */
    public static byte[] getAddressSectorCount(String path) {
        byte[] bAddressSectorCount = new byte[5];
        File file = new File(path);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                fis.read(bAddressSectorCount, 0, 5);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bAddressSectorCount;
    }

}
