package cn.appscomm.server.mode.account;

import cn.appscomm.server.mode.base.BaseRequest;

/**
 * Created by duzhe on 17-4-5-0005.
 */

public class AccountQuery extends BaseRequest {
    int userId;
    int userInfoId;

    public AccountQuery(int userId, int userInfoId) {
        this.userId = userId;
        this.userInfoId = userInfoId;
    }
}
