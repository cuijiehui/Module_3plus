package cn.appscomm.presenter.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 作者：hsh
 * 日期：2017/4/14
 * 说明：图片工具类
 */

public class ImageUtil {

    private static final String TAG = ImageUtil.class.getSimpleName();

    /**
     * 通过图片路径获取Bitmap
     *
     * @param ImagePath 图片路径
     * @return Bitmap
     */
    public static Bitmap getBitmapByImagePath(String ImagePath) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();                               // 图片解析的设置
            opts.inJustDecodeBounds = true;                                                         // 不真的去解析图片,只是获取图片的头部信息,例如宽高等
            BitmapFactory.decodeFile(ImagePath, opts);
            int screenWidth = 180;                                                                  // 设置窗口的宽
            int screenHeight = 180;                                                                 // 设置窗口的高
            int scaleX = opts.outWidth / screenWidth;
            int scaleY = opts.outHeight / screenHeight;
            int scale = 1;
            if (scaleX > scaleY && scaleY > 1) {
                scale = scaleX;
            } else if (scaleY > scaleX && scaleX > 1) {
                scale = scaleY;
            } else if (scaleY == scaleX && scaleX > 1) {
                scale = scaleX;
            }
            LogUtil.i(TAG, "scale : " + scale);
            opts.inJustDecodeBounds = false;                                                        // 真的解析图片
            opts.inSampleSize = scale;                                                              // 采样率

            int degree = readPictureDegree(ImagePath);
            Matrix matrix = new Matrix();                                                           // 旋转图片 动作
            matrix.postRotate(degree);
            Bitmap bitmap = BitmapFactory.decodeFile(ImagePath, opts);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取图片的属性,查看旋转了多少角度,
     *
     * @param path 路径
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.i(TAG, "图片旋转了" + degree + "度...");
        return degree;
    }

    /**
     * 获取图片的Base64字符串
     *
     * @param imagePath 图片的路径
     * @return 图片的Base64字符串
     */
    public static String getImageBase64(String imagePath) {
        Bitmap bitmap = getBitmapByImagePath(imagePath);
        if (bitmap == null)
            return "";
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        bitmap.recycle();
        return Base64.encodeToString(bStream.toByteArray(), Base64.DEFAULT);
    }
}
