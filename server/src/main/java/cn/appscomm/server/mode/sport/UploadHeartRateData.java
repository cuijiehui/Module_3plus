package cn.appscomm.server.mode.sport;

import java.util.List;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/13
 * 说明：
 */

public class UploadHeartRateData extends BaseRequest {
    public String customerCode = Urls.DEFAULT_CUSTOMER_CODE;
    public String accountId;
    public String deviceId;
    public String deviceType;
    public int timeZone;
    public List<HeartRateSER> details;

    public UploadHeartRateData(String seq, String accountId, String deviceId, String deviceType, int timeZone, List<HeartRateSER> details) {
        this.seq = seq;
        this.accountId = accountId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.timeZone = timeZone;
        this.details = details;
    }
}
