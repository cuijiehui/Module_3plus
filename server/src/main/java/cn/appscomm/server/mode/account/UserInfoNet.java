package cn.appscomm.server.mode.account;

import cn.appscomm.server.mode.base.BaseResponse;

/**
 * Created by Administrator on 2017/3/1.
 */

public class UserInfoNet extends BaseResponse {

    public int userInfoId;
    public int refUserId;
    public String userName;
    public String nickname;
    public int sex;
    public String birthday;
    public String iconUrl;
    public float height;
    public int heightUnit;
    public float weight;
    public int weightUnit;
    public String country;
    public String province;
    public String city;
    public String area;
    public int isManage;
    public String backgroundWall;
    public String addreddIp;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoId=" + userInfoId +
                ", refUserId=" + refUserId +
                ", userName='" + userName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", height=" + height +
                ", heightUnit=" + heightUnit +
                ", weight=" + weight +
                ", weightUnit=" + weightUnit +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", isManage=" + isManage +
                ", backgroundWall='" + backgroundWall + '\'' +
                ", addreddIp='" + addreddIp + '\'' +
                '}';
    }

}
