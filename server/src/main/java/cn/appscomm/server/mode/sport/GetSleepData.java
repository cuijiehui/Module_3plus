package cn.appscomm.server.mode.sport;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/13
 * 说明：
 */

public class GetSleepData extends BaseRequest {
    public String accountId;
    public String customerCode = Urls.DEFAULT_CUSTOMER_CODE;
    public String deviceId;
    public int timeZone;
    public String startTime;
    public String endTime;

    public GetSleepData(String accountId, String deviceId, int timeZone, String startTime, String endTime) {
        this.accountId = accountId;
        this.customerCode = customerCode;
        this.deviceId = deviceId;
        this.timeZone = timeZone;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
