package cn.appscomm.server.interfaces;

import java.io.File;
import java.util.List;

import cn.appscomm.server.mode.account.AccountQuery;
import cn.appscomm.server.mode.account.Login;
import cn.appscomm.server.mode.account.Register;
import cn.appscomm.server.mode.sport.HeartRateSER;
import cn.appscomm.server.mode.sport.SleepSER;
import cn.appscomm.server.mode.sport.SportSER;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface PMServerCall {

    /**
     * P层告诉M层最新的token
     *
     * @param token 最新的token
     */
    void setToken(String token);

    /**
     * 登录
     *
     * @param login    登录mode
     * @param callback 结果回调
     */
    void login(Login login, INetResultCallBack callback);

    /**
     * 注册
     *
     * @param register 注册mode
     * @param callback 结果回调
     */
    void register(Register register, INetResultCallBack callback);

    /**
     * 账户查询
     *
     * @param accountQuery 用户查询mode
     * @param callback     结果回调
     */
    void accountQuery(AccountQuery accountQuery, INetResultCallBack callback);

    /**
     * 账户编辑
     *
     * @param userInfoId 用户信息ID
     * @param userName   用户名
     * @param nickName   别名
     * @param sex        性别
     * @param year       年
     * @param month      月
     * @param day        日
     * @param height     身高
     * @param weight     体重
     * @param unit       单位
     * @param country    国家
     * @param province   省份
     * @param city       城市
     * @param area       地区
     * @param isManager  是否管理者
     * @param callback   结果回调
     */
    void accountEdit(int userInfoId, String userName, String nickName, int sex, int year, int month, int day, float height, float weight, int unit, String country, String province, String city, String area, int isManager, INetResultCallBack callback);

    /**
     * 忘记密码
     *
     * @param accountId   用户ID
     * @param accountType 账户类型
     * @param callback    结果回调
     */
    void forgetPassword(String accountId, int accountType, INetResultCallBack callback);

    /**
     * 修改密码
     *
     * @param accountId   账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param callback    结果回调
     */
    void modifyPassword(String accountId, String oldPassword, String newPassword, INetResultCallBack callback);

    /**
     * 配对
     *
     * @param userInfoId    用户信息ID
     * @param userId        用户ID
     * @param productCode   产品代码
     * @param isDefault     是否默认
     * @param deviceVersion 设备版本
     * @param deviceId      设备id
     * @param callback      结果回调
     */
    void pair(int userInfoId, int userId, String productCode, int isDefault, String deviceVersion, String deviceId, INetResultCallBack callback);

    /**
     * 解绑
     *
     * @param userInfoId 用户信息ID
     * @param deviceId   设备ID
     * @param callback   结果回调
     */
    void unPair(int userInfoId, String deviceId, INetResultCallBack callback);

    /**
     * 获取绑定设备
     *
     * @param userId      用户ID
     * @param productCode 产品代码
     * @param callback    结果回调
     */
    void getPairDevice(int userId, String productCode, INetResultCallBack callback);

    /**
     * 上传头像
     *
     * @param userInfoId  用户信息Id
     * @param image       头像的Base64字符串
     * @param imageSuffix 头像的格式
     * @param callback    结果回调
     */
    void uploadImage(int userInfoId, String image, String imageSuffix, INetResultCallBack callback);

    /**
     * 上传运动数据
     *
     * @param seq        上传的标记
     * @param accountId  账户
     * @param deviceId   watchId
     * @param deviceType 设备类型
     * @param timeZone   时区
     * @param sportData  需要上传的运动数据
     * @param callBack   结果回调
     */
    void uploadSportData(String seq, String accountId, String deviceId, String deviceType, int timeZone, List<SportSER> sportData, INetResultCallBack callBack);

    /**
     * 获取运动数据
     *
     * @param accountId 账号
     * @param deviceId  watchId
     * @param timeZone  时区
     * @param startTime 开始时间(例如：2017-04-08 09:00:00)
     * @param endTime   结束时间(例如：2017-04-08 10:00:00)
     * @param callback  结果回调
     */
    void getSportData(String accountId, String deviceId, int timeZone, String startTime, String endTime, INetResultCallBack callback);

    /**
     * 上传睡眠数据
     *
     * @param seq       上传的标记
     * @param accountId 账户
     * @param sleepData 需要上传的睡眠数据
     * @param callback  结果回调
     */
    void uploadSleepData(String seq, String accountId, List<SleepSER> sleepData, INetResultCallBack callback);

    /**
     * 获取睡眠数据
     *
     * @param accountId 账号
     * @param deviceId  watchId
     * @param timeZone  时区
     * @param startTime 开始时间(例如：2017-04-08 09:00:00)
     * @param endTime   结束时间(例如：2017-04-08 10:00:00)
     * @param callback  结果回调
     */
    void getSleepData(String accountId, String deviceId, int timeZone, String startTime, String endTime, INetResultCallBack callback);

    /**
     * @param seq              上传的标记
     * @param accountId        账户
     * @param deviceId         watchId
     * @param deviceType       设备类型
     * @param timeZone         时区
     * @param heartRateSERList 心率数据
     * @param callBack         结果回调
     */
    void uploadHeartRateData(String seq, String accountId, String deviceId, String deviceType, int timeZone, List<HeartRateSER> heartRateSERList, INetResultCallBack callBack);

    /**
     * 获取心率数据
     *
     * @param accountId 账号
     * @param deviceId  watchId
     * @param timeZone  时区
     * @param startTime 开始时间(例如：2017-04-08 09:00:00)
     * @param endTime   结束时间(例如：2017-04-08 10:00:00)
     * @param callback  结果回调
     */
    void getHeartRateData(String accountId, String deviceId, int timeZone, String startTime, String endTime, INetResultCallBack callback);

    /**
     * 获取固件版本
     *
     * @param productCode 产品代号
     * @param callback    结果回调
     */
    void getFirmwareVersion(String productCode, INetResultCallBack callback);

    /**
     * 下载固件版本
     *
     * @param url      固件url
     * @param savePath 保存路径(包括文件名)
     * @param callBack 结果回调
     */
    void downloadFirmware(String url, File savePath, INetResultCallBack callBack);

}
