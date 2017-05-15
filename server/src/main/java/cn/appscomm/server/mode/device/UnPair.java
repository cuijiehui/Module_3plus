package cn.appscomm.server.mode.device;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/7
 * 说明：
 */

public class UnPair extends BaseRequest {
    int userInfoId;
    List<String> deviceIds = new ArrayList<>();

    public UnPair(int userInfoId, String deviceId) {
        this.userInfoId = userInfoId;
        this.deviceIds.add(deviceId);
    }
}
