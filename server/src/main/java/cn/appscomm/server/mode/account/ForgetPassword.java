package cn.appscomm.server.mode.account;

import cn.appscomm.server.mode.base.BaseRequest;

/**
 * Created by duzhe on 17-4-5-0005.
 */

public class ForgetPassword extends BaseRequest {

    public String accountId;
    public int accountType;

    public ForgetPassword(String accountId, int accountType) {
        this.accountId = accountId;
        this.accountType = accountType;
    }
}
