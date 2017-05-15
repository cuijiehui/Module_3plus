package cn.appscomm.server.mode.account;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * Created by Administrator on 2017/3/1.
 */
public class Register extends BaseRequest {
    public static final int ACCOUNT_TYPE_EMAIL = 111;
    public static final int ACCOUNT_TYPE_PHONE = 222;

    public String accountId;
    public String customerCode = Urls.DEFAULT_CUSTOMER_CODE;
    public int accountType;
    public String accountPassword;

    public Register(String accountId, String accountPassword, int accountType) {
        this.accountId = accountId;
        this.accountPassword = accountPassword;
        this.accountType = accountType;
    }
}
