package cn.appscomm.db.util;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：hsh
 * 日期：2017/4/12
 * 说明：
 */

public class DBUtil {
    /**
     * 计算最新的心率详情(旧的心率详情和需要添加的心率详情进行组合)
     *
     * @param oldDetail 旧的心率详情(包括待上传和已上传)
     * @param addDetail 需要加上的心率详情(包括待上传和已上传)
     * @return 最新的心率详情
     */
    public static String combineNewHeartRateDetail(String oldDetail, String addDetail) {
        String newDetail = addDetail;
        if (!TextUtils.isEmpty(oldDetail)) {
//            LogUtil.i(TAG, "旧的心率值:" + oldValue + " 新增的:" + newValue);
            newDetail = oldDetail;
            Map<String, Integer> addSubmitMap = new HashMap<>();
            Map<String, Integer> oldSubmitMap = new HashMap<>();

            for (String submit : oldDetail.split(",")) {                                            // 如100&60,101&70 -> [100,60] [101,70]
                String[] minValue = submit.split("&");
                oldSubmitMap.put(minValue[0], Integer.parseInt(minValue[1]));
            }
            for (String submit : addDetail.split(",")) {                                            // 如100&65,102&80 -> [100,65] [102,80]
                String[] minValue = submit.split("&");
                addSubmitMap.put(minValue[0], Integer.parseInt(minValue[1]));
            }
            for (Map.Entry<String, Integer> entry : addSubmitMap.entrySet()) {                      // 按上面的例子,最终的结果为 [100,(60+65)/2 + (60+65)%2] [102,80] [101,70] -> 100&63,102&80,101&70
                String min = entry.getKey();
                int addAvg = entry.getValue();
                if (oldSubmitMap.containsKey(min)) {
                    int oldAvg = oldSubmitMap.get(min);
                    int totalAvg = oldAvg + addAvg;
                    int newAvg = totalAvg / 2 + totalAvg % 2;
                    newDetail = newDetail.replace(min + "&" + oldAvg, min + "&" + newAvg);
                } else {
                    newDetail += min + "&" + addAvg + ",";
                }
            }
        }
        return newDetail;
    }

    /**
     * 计算心率平均值
     *
     * @param heartRateSubmitDetail 心率详情(未上传或已上传)
     * @return 心率平均值
     */
    public static int countHeartRateAvg(String heartRateSubmitDetail) {
        String[] submits = heartRateSubmitDetail.split(",");
        int i = 0;
        int total = 0;
        for (String submit : submits) {
            i++;
            total += Integer.parseInt(submit.split("&")[1]);
        }
        return i == 0 ? 0 : total / i;
    }
}
