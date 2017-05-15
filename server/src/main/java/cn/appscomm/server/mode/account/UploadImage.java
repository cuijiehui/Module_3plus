package cn.appscomm.server.mode.account;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * 作者：hsh
 * 日期：2017/4/14
 * 说明：
 */

public class UploadImage extends BaseRequest {

    private String customerCode = Urls.DEFAULT_CUSTOMER_CODE;
    private int userInfoId;
    private String image;
    private String imageSuffix;

    public UploadImage(int userInfoId, String image, String imageSuffix) {
        this.userInfoId = userInfoId;
        this.image = image;
        this.imageSuffix = imageSuffix;
    }
}
