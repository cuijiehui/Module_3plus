package cn.appscomm.server.mode.device;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/5/5
 * 说明：
 */

public class FirmwareVersion extends BaseRequest {
    String productCode;
    String customerCode = Urls.DEFAULT_CUSTOMER_CODE;

    public FirmwareVersion(String productCode) {
        this.productCode = productCode;
    }
}
