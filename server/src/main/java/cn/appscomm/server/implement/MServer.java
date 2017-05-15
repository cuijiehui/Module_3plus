package cn.appscomm.server.implement;

import java.io.File;
import java.util.List;

import cn.appscomm.server.ServerRequest;
import cn.appscomm.server.ServerVal;
import cn.appscomm.server.interfaces.INetResultCallBack;
import cn.appscomm.server.interfaces.PMServerCall;
import cn.appscomm.server.mode.account.AccountQuery;
import cn.appscomm.server.mode.account.ForgetPassword;
import cn.appscomm.server.mode.account.Login;
import cn.appscomm.server.mode.account.ModifyPassword;
import cn.appscomm.server.mode.account.Register;
import cn.appscomm.server.mode.account.UploadImage;
import cn.appscomm.server.mode.account.UserInfo;
import cn.appscomm.server.mode.device.FirmwareVersion;
import cn.appscomm.server.mode.sport.GetHeartRateData;
import cn.appscomm.server.mode.sport.GetSleepData;
import cn.appscomm.server.mode.sport.HeartRateSER;
import cn.appscomm.server.mode.sport.SleepSER;
import cn.appscomm.server.mode.sport.SportSER;
import cn.appscomm.server.mode.device.Pair;
import cn.appscomm.server.mode.device.PairDevice;
import cn.appscomm.server.mode.device.UnPair;
import cn.appscomm.server.mode.sport.GetSportData;
import cn.appscomm.server.mode.sport.UploadHeartRateData;
import cn.appscomm.server.mode.sport.UploadSleepData;
import cn.appscomm.server.mode.sport.UploadSportData;

/**
 * Created by Administrator on 2017/3/6.
 */
public enum MServer implements PMServerCall {
    INSTANCE {

    };

    @Override
    public void setToken(String token) {
        ServerVal.accessToken = token;
    }

    @Override
    public void login(Login login, INetResultCallBack callback) {
        ServerRequest.INSTANCE.login(login, callback);
    }

    @Override
    public void register(Register register, INetResultCallBack callback) {
        ServerRequest.INSTANCE.register(register, callback);
    }

    @Override
    public void accountQuery(AccountQuery accountQuery, INetResultCallBack callback) {
        ServerRequest.INSTANCE.accountQuery(accountQuery, callback);
    }

    @Override
    public void accountEdit(int userInfoId, String userName, String nickName, int sex, int year, int month, int day, float height, float weight, int unit, String country, String province, String city, String area, int isManager, INetResultCallBack callback) {
        ServerRequest.INSTANCE.accountEdit(new UserInfo(userInfoId, userName, nickName, sex, year, month, day, height, weight, unit, country, province, city, area, isManager), callback);
    }

    @Override
    public void forgetPassword(String accountId, int accountType, INetResultCallBack callback) {
        ServerRequest.INSTANCE.forgetPassword(new ForgetPassword(accountId, accountType), callback);
    }

    @Override
    public void modifyPassword(String accountId, String oldPassword, String newPassword, INetResultCallBack callback) {
        ServerRequest.INSTANCE.modifyPassword(new ModifyPassword(accountId, oldPassword, newPassword), callback);
    }

    @Override
    public void pair(int userInfoId, int userId, String productCode, int isDefault, String deviceVersion, String deviceId, INetResultCallBack callback) {
        ServerRequest.INSTANCE.pair(new Pair(userInfoId, userId, isDefault, deviceId, productCode, deviceVersion), callback);
    }

    @Override
    public void unPair(int userInfoId, String deviceId, INetResultCallBack callback) {
        ServerRequest.INSTANCE.unPair(new UnPair(userInfoId, deviceId), callback);
    }

    @Override
    public void getPairDevice(int userId, String productCode, INetResultCallBack callback) {
        ServerRequest.INSTANCE.getPairDevice(new PairDevice(userId, productCode), callback);
    }

    @Override
    public void uploadImage(int userInfoId, String image, String imageSuffix, INetResultCallBack callback) {
        ServerRequest.INSTANCE.uploadImage(new UploadImage(userInfoId, image, imageSuffix), callback);
    }

    @Override
    public void uploadSportData(String seq, String accountId, String deviceId, String deviceType, int timeZone, List<SportSER> sportData, INetResultCallBack callBack) {
        ServerRequest.INSTANCE.uploadSportData(new UploadSportData(seq, accountId, deviceId, deviceType, timeZone, sportData), callBack);
    }

    @Override
    public void getSportData(String accountId, String deviceId, int timeZone, String startTime, String endTime, INetResultCallBack callback) {
        ServerRequest.INSTANCE.getSportData(new GetSportData(accountId, deviceId, timeZone, startTime, endTime), callback);
    }

    @Override
    public void uploadSleepData(String seq, String accountId, List<SleepSER> sleepData, INetResultCallBack callBack) {
        ServerRequest.INSTANCE.uploadSleepData(new UploadSleepData(seq, accountId, sleepData), callBack);
    }

    @Override
    public void getSleepData(String accountId, String deviceId, int timeZone, String startTime, String endTime, INetResultCallBack callback) {
        ServerRequest.INSTANCE.getSleepData(new GetSleepData(accountId, deviceId, timeZone, startTime, endTime), callback);
    }

    @Override
    public void uploadHeartRateData(String seq, String accountId, String deviceId, String deviceType, int timeZone, List<HeartRateSER> heartRateSERList, INetResultCallBack callBack) {
        ServerRequest.INSTANCE.uploadHeartRateData(new UploadHeartRateData(seq, accountId, deviceId, deviceType, 8, heartRateSERList), callBack);
    }

    @Override
    public void getHeartRateData(String accountId, String deviceId, int timeZone, String startTime, String endTime, INetResultCallBack callback) {
        ServerRequest.INSTANCE.getHeartRateData(new GetHeartRateData(accountId, deviceId, timeZone, startTime, endTime), callback);
    }

    @Override
    public void getFirmwareVersion(String productCode, INetResultCallBack callback) {
        ServerRequest.INSTANCE.getFirmwareVersion(new FirmwareVersion(productCode), callback);
    }

    @Override
    public void downloadFirmware(String url, File savePath, INetResultCallBack callBack) {
        ServerRequest.INSTANCE.downloadFirmware(url, savePath, callBack);
    }
}
