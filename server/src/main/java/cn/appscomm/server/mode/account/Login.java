package cn.appscomm.server.mode.account;

import cn.appscomm.server.mode.base.BaseRequest;

/**
 * Created by Administrator on 2017/2/21.
 */

public class Login extends BaseRequest {

    private String accountId;
    private String password;

    public Login(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }
}
