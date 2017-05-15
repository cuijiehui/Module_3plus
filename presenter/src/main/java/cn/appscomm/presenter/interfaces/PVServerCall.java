package cn.appscomm.presenter.interfaces;

import java.io.File;
import java.util.Calendar;

import cn.appscomm.server.interfaces.INetResultCallBack;
import cn.appscomm.server.mode.account.Register;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface PVServerCall {
    int ACCOUNT_TYPE_EMAIL = Register.ACCOUNT_TYPE_EMAIL;
    int ACCOUNT_TYPE_PHONE = Register.ACCOUNT_TYPE_PHONE;

    /**
     * 登录
     *
     * @param accountID 账号
     * @param password  密码
     * @param callback  结果回调
     */
    void login(String accountID, String password, PVServerCallback callback);

    /**
     * 注册
     *
     * @param accountID   账号
     * @param password    密码
     * @param accountType 账号类型(邮箱/电话)
     * @param callback    结果回调
     */
    void register(String accountID, String password, int accountType, PVServerCallback callback);

    /**
     * 账户查询
     *
     * @param callback 结果回调
     */
    void accountQuery(PVServerCallback callback);

    /**
     * 账户编辑
     *
     * @param userName  用户名
     * @param nickName  别名
     * @param sex       性别
     * @param year      年
     * @param month     月
     * @param day       日
     * @param height    身高
     * @param weight    体重
     * @param unit      单位
     * @param country   国家
     * @param province  省份
     * @param city      城市
     * @param area      地区
     * @param isManager 是否管理者
     * @param callback  结果回调
     */
    void accountEdit(String userName, String nickName, int sex, int year, int month, int day, float height, float weight, int unit, String country, String province, String city, String area, int isManager, PVServerCallback callback);

    /**
     * 忘记密码
     *
     * @param accountId   账号
     * @param accountType 账户类型
     * @param callback    结果回调
     */
    void forgetPassword(String accountId, int accountType, PVServerCallback callback);

    /**
     * 修改密码
     *
     * @param accountId   账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param callback    结果回调
     */
    void modifyPassword(String accountId, String oldPassword, String newPassword, PVServerCallback callback);

    /**
     * 配对
     *
     * @param callback 结果回调
     */
    void pair(PVServerCallback callback);

    /**
     * 解绑
     *
     * @param callback 结果回调
     */
    void unPair(PVServerCallback callback);

    /**
     * 获取绑定设备
     *
     * @param deviceType 设备类型
     * @param callback   结果回调
     */
    void getPairDevice(String deviceType, PVServerCallback callback);

    /**
     * 上传头像
     *
     * @param imagePath 头像路径
     * @param callback  结果回调
     */
    void uploadImage(String imagePath, PVServerCallback callback);

    /**
     * 上传运动数据
     *
     * @param callback 结果回调
     */
    void uploadSportData(PVServerCallback callback);

    /**
     * 获取指定日期的运动数据
     *
     * @param calendar 指定日期
     * @param callback 结果回调
     */
    void getDaySportData(Calendar calendar, PVServerCallback callback);

    /**
     * 获取指定日期对应的整周运动数据
     *
     * @param calendar         指定日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @param callback         结果回调
     */
    void getWeekSportData(Calendar calendar, boolean isMondayFirstDay, PVServerCallback callback);

    /**
     * 获取指定日期对应的整月运动数据
     *
     * @param calendar 指定日期
     * @param callback 结果回调
     */
    void getMonthSportData(Calendar calendar, PVServerCallback callback);

    /**
     * 获取运动数据
     *
     * @param startTime 开始时间(例如：2017-04-08 09:00:00)
     * @param endTime   结束时间(例如：2017-04-08 10:00:00)
     * @param callback  结果回调
     */
    void getSportData(String startTime, String endTime, PVServerCallback callback);

    /**
     * 上传睡眠数据
     *
     * @param callback 结果回调
     */
    void uploadSleepData(PVServerCallback callback);

    /**
     * 获取睡眠数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param callback  结果回调
     */
    void getSleepData(String startTime, String endTime, PVServerCallback callback);

    /**
     * 上传心率数据
     *
     * @param callback 结果回调
     */
    void uploadHeartRateData(PVServerCallback callback);

    /**
     * 获取心率数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param callback  结果回调
     */
    void getHeartRateData(String startTime, String endTime, PVServerCallback callback);

    /**
     * 获取固件版本
     *
     * @param callback 结果回调
     */
    void getFirmwareVersion(PVServerCallback callback);

    /**
     * 下载固件(url使用保存在sp的netFirmwareUrl,savePath使用Context.getFilesDir())
     *
     * @param callback 结果回调
     */
    void downloadFirmware(PVServerCallback callback);

    /**
     * 下载固件
     *
     * @param url      固件的url
     * @param savePath 固件保存路径(包括文件名)
     * @param callback 结果回调
     */
    void downloadFirmware(String url, File savePath, PVServerCallback callback);


}
