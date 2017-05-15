package cn.appscomm.server.mode.sport;

import java.util.List;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/12
 * 说明：
 */

public class UploadSleepData extends BaseRequest {
    public String customerCode = Urls.DEFAULT_CUSTOMER_CODE;
    public String accountId;
    public List<SleepSER> sleeps;

    public UploadSleepData(String seq, String accountId, List<SleepSER> sleeps) {
        this.seq = seq;
        this.accountId = accountId;
        this.sleeps = sleeps;
    }
}
