package cn.appscomm.server.mode.device;

import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/7
 * 说明：
 */

public class PairDevice extends BaseRequest {
    int userId;
    String productCode;

    public PairDevice(int userId, String productCode) {
        this.userId = userId;
        this.productCode = productCode;
    }
}
