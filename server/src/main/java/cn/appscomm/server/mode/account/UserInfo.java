package cn.appscomm.server.mode.account;

import cn.appscomm.server.mode.base.BaseRequest;

/**
 * Created by Administrator on 2017/3/1.
 */

public class UserInfo extends BaseRequest {

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

    public UserInfo() {
    }

    public UserInfo(int userInfoId, String userName, String nickName, int sex, int year, int month, int day, float height, float weight, int unit, String country, String province, String city, String area, int isManager) {
        this.userInfoId = userInfoId;
        this.userName = userName;
        this.nickname = nickName;
        this.sex = sex;
        this.birthday = year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day);
        this.height = height;
        this.weight = weight;
        this.heightUnit = unit;
        this.weightUnit = unit;
        this.country = country;
        this.province = province;
        this.city = city;
        this.area = area;
        this.isManage = isManager;
    }

    public UserInfo(String area, int userInfoId, int refUserId, String userName, String nickname, int sex, String birthday, String iconUrl, float height, int heightUnit, float weight, int weightUnit, String country, String province, String city, int isManage, String backgroundWall, String addreddIp) {
        this.area = area;
        this.userInfoId = userInfoId;
        this.refUserId = refUserId;
        this.userName = userName;
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.iconUrl = iconUrl;
        this.height = height;
        this.heightUnit = heightUnit;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.country = country;
        this.province = province;
        this.city = city;
        this.isManage = isManage;
        this.backgroundWall = backgroundWall;
        this.addreddIp = addreddIp;
    }

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
