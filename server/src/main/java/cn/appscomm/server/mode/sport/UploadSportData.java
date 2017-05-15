package cn.appscomm.server.mode.sport;

import java.util.List;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/8
 * 说明：
 */

public class UploadSportData extends BaseRequest {
    public String customerCode = Urls.DEFAULT_CUSTOMER_CODE;
    public String accountId;
    public String deviceId;
    public String deviceType;
    public int timeZone;
    public List<SportSER> details;

    public UploadSportData(String seq, String accountId, String deviceId, String deviceType, int timeZone, List<SportSER> sportSERList) {
        this.seq = seq;
        this.accountId = accountId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.timeZone = timeZone;
        this.details = sportSERList;
    }
}
