package cn.appscomm.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.appscomm.server.interfaces.INetResultCallBack;
import cn.appscomm.server.mode.account.AccountQuery;
import cn.appscomm.server.mode.account.ForgetPassword;
import cn.appscomm.server.mode.account.Login;
import cn.appscomm.server.mode.account.ModifyPassword;
import cn.appscomm.server.mode.account.Register;
import cn.appscomm.server.mode.account.UploadImage;
import cn.appscomm.server.mode.account.UserInfo;
import cn.appscomm.server.mode.base.BaseResponse;
import cn.appscomm.server.mode.device.FirmwareVersion;
import cn.appscomm.server.mode.device.Pair;
import cn.appscomm.server.mode.device.PairDevice;
import cn.appscomm.server.mode.device.UnPair;
import cn.appscomm.server.mode.sport.GetHeartRateData;
import cn.appscomm.server.mode.sport.GetSleepData;
import cn.appscomm.server.mode.sport.GetSportData;
import cn.appscomm.server.mode.sport.UploadHeartRateData;
import cn.appscomm.server.mode.sport.UploadSleepData;
import cn.appscomm.server.mode.sport.UploadSportData;
import cn.appscomm.server.util.LogUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * <pre>
 * ServerRequest是提供给外部调用的请求类，调用的方法如：
 * Register register = new Register("123456@qq.com", Register.ACCOUNT_TYPE_EMAIL, "123456");
 * ServerRequest.getInstance().register(register, new INetResultCallBack() {
 * public void onSuccess(int responseCode, BaseResponse baseResponse) {
 *          if (ServerResponseCode.getInstance().checkResult(responseCode) && (baseResponse instanceof RegisterNet)) {
 *                  RegisterNet registerNet = (RegisterNet) baseResponse;
 *                  UserInfoNet userInfo = registerNet.resMap.userInfo;
 *                  LogUtil.i("test", "register token : " + registerNet.accessToken);
 *                  LogUtil.i("test", "userInfo : " + userInfo.toString());
 *          } else {
 *              Toast.makeText(MainActivity.this, ServerResponseCode.getInstance().getResponseMessage(responseCode), Toast.LENGTH_SHORT).show();
 *          }
 * }
 *
 * public void onFail(int responseCode) {
 * }
 * });
 * </pre>
 */
public enum ServerRequest {
    INSTANCE;

    private UrlService urlService;

    // 创建单例对象时，先拿到UrlService
    ServerRequest() {
        urlService = ServerManager.getInstance().getUrlService();
    }

    // 响应失败观察者:直接返回给调用者
    private Action1<Throwable> responseFailObserver(final INetResultCallBack callBack) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                callBack.onFail(ServerResponseCode.RESPONSE_CODE_ERROR);
            }
        };
    }

    // 响应成功观察者:先处理该响应是否有问题，然后再返回给调用者
    private Action1<BaseResponse> responseSuccessObserver(final INetResultCallBack callBack) {
        return new Action1<BaseResponse>() {
            @Override
            public void call(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    int responseCode = baseResponse.code;
                    switch (responseCode) {
                        case ServerResponseCode.RESPONSE_CODE_ACCESS_TOKEN_EXPIRED:                 // token过期
                        case ServerResponseCode.RESPONSE_CODE_ACCESS_TOKEN_INVALID:                 // token无效
                        case ServerResponseCode.RESPONSE_CODE_ACCESS_TOKEN_NULL:                    // token为空
                            // TODO 这里可以重新在后台刷新token，然后重新访问网络
                            callBack.onFail(responseCode);
                            break;
                        case 0:
                            callBack.onSuccess(baseResponse.code, baseResponse);                    // 成功
                            break;
                        default:
                            callBack.onFail(ServerResponseCode.RESPONSE_CODE_ERROR);
//                            callBack.onSuccess(baseResponse.code, baseResponse);
                            break;
                    }
                } else {
                    callBack.onFail(ServerResponseCode.RESPONSE_CODE_ERROR);
                }
            }
        };
    }

    // 使用rxJava处理响应结果
    private void rxJavaProCallBack(Observable observable, INetResultCallBack callBack) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseSuccessObserver(callBack), responseFailObserver(callBack));
    }

    /**
     * 登录
     *
     * @param login    登录mode
     * @param callBack 网络结果回调
     */
    public void login(Login login, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.login(login), callBack);
    }

    /**
     * 注册
     *
     * @param register 注册mode
     * @param callBack 网络结果回调
     */
    public void register(Register register, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.register(register), callBack);
    }

    /**
     * 账户查询
     *
     * @param accountQuery 账户查询mode
     * @param callBack     网络结果回调
     */
    public void accountQuery(AccountQuery accountQuery, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.accountQuery(accountQuery), callBack);
    }

    /**
     * 账户编辑
     *
     * @param accountEdit 账户编辑mode
     * @param callBack    网络结果回调
     */
    public void accountEdit(UserInfo accountEdit, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.accountEdit(accountEdit), callBack);
    }

    /**
     * 忘记密码
     *
     * @param forgetPassword 忘记密码mode
     * @param callBack       网络结果回调
     */
    public void forgetPassword(ForgetPassword forgetPassword, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.forgetPassword(forgetPassword), callBack);
    }

    /**
     * 修改密码
     *
     * @param modifyPassword 修改密码mode
     * @param callBack       网络结果回调
     */
    public void modifyPassword(ModifyPassword modifyPassword, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.modifyPassword(modifyPassword), callBack);
    }

    /**
     * 配对
     *
     * @param pair     配对mode
     * @param callBack 网络结果回调
     */
    public void pair(Pair pair, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.pair(pair), callBack);
    }

    /**
     * 解绑
     *
     * @param unPair   解绑mode
     * @param callBack 网络结果回调
     */
    public void unPair(UnPair unPair, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.unPair(unPair), callBack);
    }

    /**
     * 获取绑定设备
     *
     * @param pairDevice 绑定设备mode
     * @param callBack   网络结果回调
     */
    public void getPairDevice(PairDevice pairDevice, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.getPairDevice(pairDevice), callBack);
    }

    /**
     * 上传运动数据
     *
     * @param uploadSportData 上传运动数据mode
     * @param callBack        网络结果回调
     */
    public void uploadSportData(UploadSportData uploadSportData, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.uploadSportData(uploadSportData), callBack);
    }

    /**
     * 上传头像
     *
     * @param uploadImage 上传头像mode
     * @param callBack    网络结果回调
     */
    public void uploadImage(UploadImage uploadImage, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.uploadImage(uploadImage), callBack);
    }

    /**
     * 获取运动数据
     *
     * @param getSportData 获取运动数据mode
     * @param callBack     网络结果回调
     */
    public void getSportData(GetSportData getSportData, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.getSportData(getSportData), callBack);
    }

    /**
     * 上传睡眠数据
     *
     * @param uploadSleepData 上传睡眠数据mode
     * @param callBack        网络结果回调
     */
    public void uploadSleepData(UploadSleepData uploadSleepData, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.uploadSleepData(uploadSleepData), callBack);
    }

    /**
     * 获取睡眠数据
     *
     * @param getSleepData 获取睡眠数据mode
     * @param callBack     网络结果回调
     */
    public void getSleepData(GetSleepData getSleepData, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.getSleepData(getSleepData), callBack);
    }

    /**
     * 上传心率数据
     *
     * @param uploadHeartRateData 上传心率数据mode
     * @param callBack            网络结果回调
     */
    public void uploadHeartRateData(UploadHeartRateData uploadHeartRateData, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.uploadHeartRateData(uploadHeartRateData), callBack);
    }

    /**
     * 获取心率数据
     *
     * @param getHeartRateData 获取心率数据mode
     * @param callBack         网络结果回调
     */
    public void getHeartRateData(GetHeartRateData getHeartRateData, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.getHeartRateData(getHeartRateData), callBack);
    }

    /**
     * 获取固件版本
     *
     * @param firmwareVersion 固件版本mode
     * @param callBack        网络结果回调
     */
    public void getFirmwareVersion(FirmwareVersion firmwareVersion, INetResultCallBack callBack) {
        rxJavaProCallBack(urlService.getFirmwareVersion(firmwareVersion), callBack);
    }

    /**
     * 下载固件
     *
     * @param url      固件的url
     * @param callBack 网络结果的回调
     */
    public void downloadFirmware(String url, final File savePath, final INetResultCallBack callBack) {
        Call<ResponseBody> call = urlService.downloadFirmware(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                boolean result = false;
                if (response.isSuccessful() && saveFirmware(savePath, response.body())) {
                    result = true;
                }
                if (result) {
                    LogUtil.i("test", "固件下载并保存成功");
                    callBack.onSuccess(0, null);
                } else {
                    LogUtil.i("test", "固件下载失败000");
                    callBack.onFail(-1000);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtil.i("test", "固件下载失败111");
                callBack.onFail(-1000);
            }
        });
    }

    // 保存固件到指定路径
    private boolean saveFirmware(File savePath, ResponseBody body) {
        try {
            LogUtil.i("test", "保存路径:" + savePath);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] bytes = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(savePath);

                while (true) {
                    int read = inputStream.read(bytes);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(bytes, 0, read);
                    fileSizeDownloaded += read;
                    LogUtil.i("test", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


}
