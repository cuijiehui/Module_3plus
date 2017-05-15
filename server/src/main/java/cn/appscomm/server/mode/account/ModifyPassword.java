package cn.appscomm.server.mode.account;

import cn.appscomm.server.mode.base.BaseRequest;

/**
 * Created by duzhe on 17-4-5-0005.
 */

public class ModifyPassword extends BaseRequest {
    public String accountId;
    public String oldPassword;
    public String newPassword;

    public ModifyPassword(String accountId, String oldPassword, String newPassword) {
        this.accountId = accountId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
