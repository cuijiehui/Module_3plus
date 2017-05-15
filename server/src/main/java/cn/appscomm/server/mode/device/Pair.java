package cn.appscomm.server.mode.device;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/7
 * 说明：
 */

public class Pair extends BaseRequest {
    int userInfoId;
    int userId;
    int isDefault;
    List<String> deviceIds = new ArrayList<>();
    String customerCode = Urls.DEFAULT_CUSTOMER_CODE;
    String productCode;
    String deviceVersion;

    public Pair(int userInfoId, int userId, int isDefault, String deviceId, String productCode, String deviceVersion) {
        this.userInfoId = userInfoId;
        this.userId = userId;
        this.isDefault = isDefault;
        this.productCode = productCode;
        this.deviceVersion = deviceVersion;
        deviceIds.add(deviceId);
    }
}
