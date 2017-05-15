package cn.appscomm.presenter.implement;

import android.text.TextUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import cn.appscomm.presenter.PresenterAppContext;
import cn.appscomm.presenter.util.ImageUtil;
import cn.appscomm.presenter.util.LogUtil;
import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SportCacheDB;
import cn.appscomm.db.mode.SportDB;
import cn.appscomm.presenter.util.ModeConvertUtil;
import cn.appscomm.presenter.util.TimeUtil;
import cn.appscomm.presenter.interfaces.PVDBCall;
import cn.appscomm.presenter.interfaces.PVSPCall;
import cn.appscomm.presenter.interfaces.PVServerCall;
import cn.appscomm.presenter.interfaces.PVServerCallback;
import cn.appscomm.server.implement.MServer;
import cn.appscomm.server.interfaces.INetResultCallBack;
import cn.appscomm.server.interfaces.PMServerCall;
import cn.appscomm.server.mode.account.AccountQuery;
import cn.appscomm.server.mode.account.Login;
import cn.appscomm.server.mode.account.LoginNet;
import cn.appscomm.server.mode.account.Register;
import cn.appscomm.server.mode.account.RegisterNet;
import cn.appscomm.server.mode.account.UploadImgNet;
import cn.appscomm.server.mode.account.UserInfoNet;
import cn.appscomm.server.mode.base.BaseResponse;
import cn.appscomm.server.mode.device.DeviceInfo;
import cn.appscomm.server.mode.device.FirmwareVersionNet;
import cn.appscomm.server.mode.sport.GetHeartRateDataNet;
import cn.appscomm.server.mode.sport.GetSleepDataNet;
import cn.appscomm.server.mode.sport.HeartRateSER;
import cn.appscomm.server.mode.sport.SleepSER;
import cn.appscomm.server.mode.sport.SportSER;
import cn.appscomm.server.mode.device.PairDeviceNet;
import cn.appscomm.server.mode.sport.GetSportDataNet;

/**
 * Created by Administrator on 2017/3/6.
 */

public enum PServer implements PVServerCall {
    INSTANCE {

    };

    private PMServerCall mCall = MServer.INSTANCE;
    private PVSPCall pvspCall = PSP.INSTANCE;
    private PVDBCall pvdbCall = PDB.INSTANCE;

    @Override
    public void login(final String accountID, final String password, final PVServerCallback callback) {
        // TODO 检查邮箱格式
        mCall.login(new Login(accountID, password), new INetResultCallBack() {
            @Override
            public void onSuccess(int responseCode, BaseResponse baseResponse) {
                if (baseResponse instanceof LoginNet) {
                    LoginNet loginNet = (LoginNet) baseResponse;
                    pvspCall.setAccountID(accountID);
                    pvspCall.setPassword(password);
                    pvspCall.setToken(loginNet.accessToken);
                    UserInfoNet userInfo = loginNet.resMap.userInfo;
                    if (userInfo != null) {
                        pvspCall.setUserInfoId(userInfo.userInfoId);
                        pvspCall.setUserId(userInfo.refUserId);
                    }
                    mCall.setToken(loginNet.accessToken);
                }
                onSuccessCallBack(callback, PVServerCallback.RequestType.LOGIN);
            }

            @Override
            public void onFail(int responseCode) {
                onFailCallBack(callback, PVServerCallback.RequestType.LOGIN, responseCode);
            }
        });
    }

    @Override
    public void register(String accountID, String password, int accountType, final PVServerCallback callback) {
        mCall.register(new Register(accountID, password, accountType), new INetResultCallBack() {
            @Override
            public void onSuccess(int responseCode, BaseResponse baseResponse) {
                if (baseResponse instanceof RegisterNet) {
                    RegisterNet registerNet = (RegisterNet) baseResponse;
                    pvspCall.setToken(registerNet.accessToken);
                    UserInfoNet userInfo = registerNet.resMap.userInfo;
                    if (userInfo != null) {
                        pvspCall.setUserInfoId(userInfo.userInfoId);
                        pvspCall.setUserId(userInfo.refUserId);
                    }
                    mCall.setToken(registerNet.accessToken);
                }
                onSuccessCallBack(callback, PVServerCallback.RequestType.REGISTER);
            }

            @Override
            public void onFail(int responseCode) {
                onFailCallBack(callback, PVServerCallback.RequestType.REGISTER, responseCode);
            }
        });
    }

    @Override
    public void accountQuery(final PVServerCallback callback) {
        int userInfoId = pvspCall.getUserInfoId();
        int userId = pvspCall.getUserId();
        if (userInfoId > 0 && userId > 0) {
            mCall.accountQuery(new AccountQuery(userId, userInfoId), new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    if (baseResponse instanceof UserInfoNet) {
                        UserInfoNet userInfoNet = (UserInfoNet) baseResponse;
                        if (!TextUtils.isEmpty(userInfoNet.iconUrl))
                            pvspCall.setImagePath(userInfoNet.iconUrl);
                        if (!TextUtils.isEmpty(userInfoNet.userName))
                            pvspCall.setName(userInfoNet.userName);
                        if (!TextUtils.isEmpty(userInfoNet.nickname))
                            pvspCall.setNickName(userInfoNet.nickname);
                        if (!TextUtils.isEmpty(userInfoNet.country))
                            pvspCall.setCountry(userInfoNet.country);
                        if (!TextUtils.isEmpty(userInfoNet.birthday)) {
                            String[] birthdays = userInfoNet.birthday.split("-");
                            if (birthdays.length == 3) {
                                pvspCall.setBirthdayYear(Integer.parseInt(birthdays[0]));
                                pvspCall.setBirthdayMonth(Integer.parseInt(birthdays[1]));
                                pvspCall.setBirthdayDay(Integer.parseInt(birthdays[2]));
                            }
                        }
                        pvspCall.setGender(userInfoNet.sex);
                        if (userInfoNet.height > 0)
                            pvspCall.setHeight(userInfoNet.height);
                        if (userInfoNet.weight > 0)
                            pvspCall.setWeight(userInfoNet.weight);
                        pvspCall.setUnit(userInfoNet.heightUnit);
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.ACCOUNT_QUERY);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.ACCOUNT_QUERY, responseCode);
                }
            });
        }
    }

    @Override
    public void accountEdit(final String userName, final String nickName, final int sex, final int year, final int month, final int day, final float height, final float weight, int unit, final String country, String province, String city, String area, int isManager, final PVServerCallback callback) {
        int userInfoId = pvspCall.getUserInfoId();
        if (userInfoId > 0) {
            mCall.accountEdit(userInfoId, userName, nickName, sex, year, month, day, height, weight, unit, country, province, city, area, isManager,
                    new INetResultCallBack() {
                        @Override
                        public void onSuccess(int responseCode, BaseResponse baseResponse) {
                            pvspCall.setName(userName);
                            pvspCall.setNickName(nickName);
                            pvspCall.setGender(sex);
                            pvspCall.setBirthdayYear(year);
                            pvspCall.setBirthdayMonth(month);
                            pvspCall.setBirthdayDay(day);
                            pvspCall.setHeight(height);
                            pvspCall.setWeight(weight);
                            pvspCall.setCountry(country);
                            onSuccessCallBack(callback, PVServerCallback.RequestType.ACCOUNT_EDIT);
                        }

                        @Override
                        public void onFail(int responseCode) {
                            onFailCallBack(callback, PVServerCallback.RequestType.ACCOUNT_EDIT, responseCode);
                        }
                    });
        }
    }

    @Override
    public void forgetPassword(String accountId, int accountType, final PVServerCallback callback) {
        mCall.forgetPassword(accountId, accountType, new INetResultCallBack() {
            @Override
            public void onSuccess(int responseCode, BaseResponse baseResponse) {
                onSuccessCallBack(callback, PVServerCallback.RequestType.FORGET_PASSWORD);
            }

            @Override
            public void onFail(int responseCode) {
                onFailCallBack(callback, PVServerCallback.RequestType.FORGET_PASSWORD, responseCode);
            }
        });
    }

    @Override
    public void modifyPassword(String accountId, String oldPassword, final String newPassword, final PVServerCallback callback) {
        mCall.modifyPassword(accountId, oldPassword, newPassword, new INetResultCallBack() {
            @Override
            public void onSuccess(int responseCode, BaseResponse baseResponse) {
                pvspCall.setPassword(newPassword);
                onSuccessCallBack(callback, PVServerCallback.RequestType.MODIFY_PASSWORD);
            }

            @Override
            public void onFail(int responseCode) {
                onFailCallBack(callback, PVServerCallback.RequestType.MODIFY_PASSWORD, responseCode);
            }
        });
    }

    @Override
    public void pair(final PVServerCallback callback) {
        String deviceId = pvspCall.getWatchID();
        String deviceVersion = pvspCall.getDeviceVersion();
        int userInfoId = pvspCall.getUserInfoId();
        int userId = pvspCall.getUserId();
        int isDefault = 1;
        String productCode = pvspCall.getProductCode();
        if (userInfoId > 0 && userId > 0 && !TextUtils.isEmpty(productCode)) {
            mCall.pair(userInfoId, userId, productCode, isDefault, deviceVersion, deviceId, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    onSuccessCallBack(callback, PVServerCallback.RequestType.PAIR);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.PAIR, responseCode);
                }
            });
        }
    }

    @Override
    public void unPair(final PVServerCallback callback) {
        String deviceId = pvspCall.getWatchID();
        int userInfoId = pvspCall.getUserInfoId();
        if (userInfoId > 0) {
            mCall.unPair(userInfoId, deviceId, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    onSuccessCallBack(callback, PVServerCallback.RequestType.UN_PAIR);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.UN_PAIR, responseCode);
                }
            });
        }
    }

    @Override
    public void getPairDevice(final String deviceType, final PVServerCallback callback) {
        int userId = pvspCall.getUserId();
        if (userId > 0) {
            mCall.getPairDevice(userId, deviceType, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    /*if (baseResponse instanceof PairDeviceNet) {
                        PairDeviceNet pairDeviceNet = (PairDeviceNet) baseResponse;
                        List<DeviceInfo> deviceInfoList = pairDeviceNet.details;
                        if (deviceInfoList != null && deviceInfoList.size() > 0) {
                            for (DeviceInfo deviceInfo : deviceInfoList) {
                                if (deviceInfo.isBind == 1) {
                                    pvspCall.setWatchID(deviceInfo.deviceId);
                                }
                            }
                        }
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.GET_PAIR_DEVICE);*/
                    onSuccessCallBack(callback, baseResponse, PVServerCallback.RequestType.GET_PAIR_DEVICE);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.GET_PAIR_DEVICE, responseCode);
                }
            });
        }
    }

    @Override
    public void uploadImage(String imagePath, final PVServerCallback callback) {
        int userInfoId = pvspCall.getUserInfoId();
        String imageBase64 = ImageUtil.getImageBase64(imagePath);
        String[] imagePathSplit = imagePath.split(".");
        String imageFormat = imagePathSplit.length > 0 ? imagePathSplit[imagePathSplit.length - 1] : "";
        if (userInfoId > 0 && !TextUtils.isEmpty(imageBase64) && !TextUtils.isEmpty(imageFormat)) {
            mCall.uploadImage(userInfoId, imageBase64, imageFormat, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    if (baseResponse instanceof UploadImgNet) {
                        pvspCall.setImagePath(((UploadImgNet) baseResponse).iconUrl);
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.UPLOAD_IMAGE);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.UPLOAD_IMAGE, responseCode);
                }
            });
        }
    }

    /*---------------------------------------------------------------------运动-----------------------------------------------------------------------*/

    @Override
    public void uploadSportData(final PVServerCallback callback) {
        String accountId = pvspCall.getAccountID();
        String deviceId = pvspCall.getWatchID();
        String deviceType = pvspCall.getDeviceType();
        if (!TextUtils.isEmpty(accountId) && !TextUtils.isEmpty(deviceId)) {
            List<SportCacheDB> sportCacheDBList = pvdbCall.getSportCacheList();
            List<SportSER> sportSERList = new LinkedList<>();
            String requestSeq = ModeConvertUtil.sportCacheDBToSportSERList(sportCacheDBList, sportSERList);
            if (!TextUtils.isEmpty(requestSeq)) {
                mCall.uploadSportData(requestSeq, accountId, deviceId, deviceType, 8, sportSERList, new INetResultCallBack() {

                    @Override
                    public void onSuccess(int responseCode, BaseResponse baseResponse) {
                        String responseSeq = baseResponse.seq;
                        if (!TextUtils.isEmpty(responseSeq)) {
                            List<SportCacheDB> synSportCacheDBList = new LinkedList<>();
                            for (String sId : responseSeq.split(",")) {
                                int id = Integer.parseInt(sId);
                                SportCacheDB sportCacheDB = pvdbCall.getSportCache(id);
                                if (sportCacheDB != null) {
                                    synSportCacheDBList.add(sportCacheDB);
                                    pvdbCall.delSportCache(id);
                                }
                            }
                            if (synSportCacheDBList.size() > 0) {
                                pvdbCall.addSportBySportCache(synSportCacheDBList);

                            }
                        }
                        onSuccessCallBack(callback, PVServerCallback.RequestType.UPLOAD_SPORT_DATA);
                    }

                    @Override
                    public void onFail(int responseCode) {
                        LogUtil.i("test", "上传失败");
                        onFailCallBack(callback, PVServerCallback.RequestType.UPLOAD_SPORT_DATA, responseCode);
                    }
                });
            }
        }
    }

    @Override
    public void getDaySportData(Calendar calendar, PVServerCallback callback) {
        int month = calendar.get(Calendar.MONTH) + 1;
        String date = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        getSportData(startTime, endTime, callback);
    }

    @Override
    public void getWeekSportData(Calendar calendar, boolean isMondayFirstDay, PVServerCallback callback) {
        String weekFirstDayTime = TimeUtil.getFirstDayOfWeek(calendar, isMondayFirstDay) + " 00:00:00";
        String weekLastDayTime = TimeUtil.getLastDayOfWeek(calendar, isMondayFirstDay) + " 23:59:59";
        getSportData(weekFirstDayTime, weekLastDayTime, callback);
    }

    @Override
    public void getMonthSportData(Calendar calendar, PVServerCallback callback) {
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthFirstDayTime = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-01" + " 00:00:00";
        String monthLastDayTime = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-31" + " 23:59:59";
        getSportData(monthFirstDayTime, monthLastDayTime, callback);
    }

    @Override
    public void getSportData(String startTime, String endTime, final PVServerCallback callback) {
        String accountId = pvspCall.getAccountID();
        String deviceId = pvspCall.getWatchID();
        if (!TextUtils.isEmpty(accountId) && !TextUtils.isEmpty(deviceId)) {
            mCall.getSportData(accountId, deviceId, 8, startTime, endTime, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    if (baseResponse instanceof GetSportDataNet) {
                        List<SportSER> sportSERList = ((GetSportDataNet) baseResponse).details;
                        List<SportDB> sportDBList = ModeConvertUtil.sportSERToSportDBList(sportSERList);
                        if (sportDBList != null && sportDBList.size() > 0) {
                            LogUtil.i("test", "准备保存的运动数据是：" + Arrays.toString(sportDBList.toArray()) + " size : " + sportDBList.size());
                            pvdbCall.addSportList(sportDBList);
                        }
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.GET_SPORT_DATA);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.GET_SPORT_DATA, responseCode);
                }
            });
        }
    }

    /*---------------------------------------------------------------------睡眠-----------------------------------------------------------------------*/

    @Override
    public void uploadSleepData(final PVServerCallback callback) {
        String accountId = pvspCall.getAccountID();
        String deviceId = pvspCall.getWatchID();
        String deviceType = pvspCall.getDeviceType();
        if (!TextUtils.isEmpty(accountId) && !TextUtils.isEmpty(deviceId)) {
            List<SleepDB> sleepDBList = pvdbCall.getNoUploadSleepList();
            List<SleepSER> sleepSERList = new LinkedList<>();
            String requestSeq = ModeConvertUtil.sleepDBToSleepSERList(deviceId, deviceType, sleepDBList, sleepSERList);
            if (!TextUtils.isEmpty(requestSeq)) {
                mCall.uploadSleepData(requestSeq, accountId, sleepSERList, new INetResultCallBack() {
                    @Override
                    public void onSuccess(int responseCode, BaseResponse baseResponse) {
                        String responseSeq = baseResponse.seq;
                        if (!TextUtils.isEmpty(responseSeq)) {
                            for (String sId : responseSeq.split(",")) {
                                pvdbCall.uploadSleepFlag(Integer.parseInt(sId));
                            }
                        }
                        onSuccessCallBack(callback, PVServerCallback.RequestType.UPLOAD_SLEEP_DATA);
                    }

                    @Override
                    public void onFail(int responseCode) {
                        onFailCallBack(callback, PVServerCallback.RequestType.UPLOAD_SLEEP_DATA, responseCode);
                    }
                });
            }
        }
    }

    @Override
    public void getSleepData(String startTime, String endTime, final PVServerCallback callback) {
        String accountId = pvspCall.getAccountID();
        String deviceId = pvspCall.getWatchID();
        if (!TextUtils.isEmpty(accountId) && !TextUtils.isEmpty(deviceId)) {
            mCall.getSleepData(accountId, deviceId, 8, startTime, endTime, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    if (baseResponse instanceof GetSleepDataNet) {
                        List<SleepSER> sleepSERList = ((GetSleepDataNet) baseResponse).sleeps;
                        List<SleepDB> sleepDBList = ModeConvertUtil.sleepSERToSleepDBList(sleepSERList);
                        if (sleepDBList != null && sleepDBList.size() > 0) {
                            LogUtil.i("test", "准备保存的睡眠数据是：" + Arrays.toString(sleepDBList.toArray()) + " size : " + sleepDBList.size());
                            pvdbCall.addSleepList(sleepDBList);
                        }
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.GET_SLEEP_DATA);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.GET_SLEEP_DATA, responseCode);
                }
            });
        }
    }

    /*---------------------------------------------------------------------心率-----------------------------------------------------------------------*/

    @Override
    public void uploadHeartRateData(final PVServerCallback callback) {
        String accountId = pvspCall.getAccountID();
        String deviceId = pvspCall.getWatchID();
        String deviceType = pvspCall.getDeviceType();
        if (!TextUtils.isEmpty(accountId) && !TextUtils.isEmpty(deviceId)) {
            List<HeartRateDB> heartRateDBList = pvdbCall.getNoUploadHeartRateList();
            List<HeartRateSER> heartRateSERList = new LinkedList<>();
            String requestSeq = ModeConvertUtil.heartRateDBToHeartRateSERList(heartRateDBList, heartRateSERList);
            LogUtil.i("test", "requestSeq : " + requestSeq);
            mCall.uploadHeartRateData(requestSeq, accountId, deviceId, deviceType, 8, heartRateSERList, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    String responseSeq = baseResponse.seq;
                    LogUtil.i("test", "上传心率数据成功 responseSeq : " + responseSeq);
                    if (!TextUtils.isEmpty(responseSeq)) {
                        for (String sId : responseSeq.split(",")) {
                            pvdbCall.updateHeartRateSubmitToDetail(Integer.parseInt(sId));
                        }
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.UPLOAD_HEART_RATE_DATA);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.UPLOAD_HEART_RATE_DATA, responseCode);
                }
            });
        }
    }

    @Override
    public void getHeartRateData(String startTime, String endTime, final PVServerCallback callback) {
        String accountId = pvspCall.getAccountID();
        String deviceId = pvspCall.getWatchID();
        if (!TextUtils.isEmpty(accountId) && !TextUtils.isEmpty(deviceId)) {
            mCall.getHeartRateData(accountId, deviceId, 8, startTime, endTime, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    if (baseResponse instanceof GetHeartRateDataNet) {
                        List<HeartRateSER> heartRateSERList = ((GetHeartRateDataNet) baseResponse).details;
                        List<HeartRateDB> heartRateDBList = ModeConvertUtil.heartRateSERToHeartRateDBList(heartRateSERList);
                        if (heartRateDBList != null && heartRateDBList.size() > 0) {
                            pvdbCall.updateHeartRateDetailList(heartRateDBList);
                        }
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.GET_HEART_RATE_DATA);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.GET_HEART_RATE_DATA, responseCode);
                }
            });
        }
    }

    @Override
    public void getFirmwareVersion(final PVServerCallback callback) {
        String productCode = pvspCall.getProductCode();
        if (!TextUtils.isEmpty(productCode)) {
            mCall.getFirmwareVersion(productCode, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    if (baseResponse instanceof FirmwareVersionNet) {
                        FirmwareVersionNet firmwareVersionNet = (FirmwareVersionNet) baseResponse;
                        String netFirmwareVersion = firmwareVersionNet.crmProductVersion.deviceVersion;
                        String netFirmwareUrl = firmwareVersionNet.crmProductVersion.updateUrl;
                        if (!TextUtils.isEmpty(netFirmwareVersion))
                            pvspCall.setNetFirmwareVersion(netFirmwareVersion);
                        if (!TextUtils.isEmpty(netFirmwareUrl))
                            pvspCall.setNetFirmwareUrl(netFirmwareUrl);
                        LogUtil.i("test", "netFirmwareVersion : " + netFirmwareVersion + " netFirmwareUrl : " + netFirmwareUrl);
                    }
                    onSuccessCallBack(callback, PVServerCallback.RequestType.GET_FIRMWARE_VERSION);
                }

                @Override
                public void onFail(int responseCode) {
                    LogUtil.i("test", "version : 失败");
                    onFailCallBack(callback, PVServerCallback.RequestType.GET_FIRMWARE_VERSION, responseCode);
                }
            });
        }
    }

    @Override
    public void downloadFirmware(PVServerCallback callback) {
        String netFirmwareUrl = pvspCall.getNetFirmwareUrl();
        if (TextUtils.isEmpty(netFirmwareUrl)) return;
        downloadFirmware(netFirmwareUrl, PresenterAppContext.INSTANCE.getContext().getFilesDir(), callback);
    }

    @Override
    public void downloadFirmware(String url, File savePath, final PVServerCallback callback) {
        if (!TextUtils.isEmpty(url)) {
            mCall.downloadFirmware(url, savePath, new INetResultCallBack() {
                @Override
                public void onSuccess(int responseCode, BaseResponse baseResponse) {
                    onSuccessCallBack(callback, PVServerCallback.RequestType.DOWNLOAD_FIRMWARE);
                }

                @Override
                public void onFail(int responseCode) {
                    onFailCallBack(callback, PVServerCallback.RequestType.DOWNLOAD_FIRMWARE, responseCode);
                }
            });
        }
    }

    /*------------------------------------------------------------------本类私有方法-------------------------------------------------------------------*/

    private void onSuccessCallBack(PVServerCallback callback, PVServerCallback.RequestType requestType) {
        if (callback != null)
            callback.onSuccess(requestType);
    }

    private void onSuccessCallBack(PVServerCallback callback, BaseResponse baseResponse, PVServerCallback.RequestType requestType) {
        if (callback != null)
            callback.onSuccess(baseResponse, requestType);
    }


    private void onFailCallBack(PVServerCallback callback, PVServerCallback.RequestType requestType, int responseType) {
        if (callback != null)
            callback.onFail(requestType, responseType, "");
    }


}
