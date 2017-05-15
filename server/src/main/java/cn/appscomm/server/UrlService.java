package cn.appscomm.server;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.account.AccountQuery;
import cn.appscomm.server.mode.account.ForgetPassword;
import cn.appscomm.server.mode.account.Login;
import cn.appscomm.server.mode.account.LoginFr;
import cn.appscomm.server.mode.account.LoginNet;
import cn.appscomm.server.mode.account.ModifyPassword;
import cn.appscomm.server.mode.account.Register;
import cn.appscomm.server.mode.account.RegisterNet;
import cn.appscomm.server.mode.account.UploadImage;
import cn.appscomm.server.mode.account.UploadImgNet;
import cn.appscomm.server.mode.account.UserInfo;
import cn.appscomm.server.mode.account.UserInfoNet;
import cn.appscomm.server.mode.base.BaseResponse;
import cn.appscomm.server.mode.device.FirmwareVersion;
import cn.appscomm.server.mode.device.FirmwareVersionNet;
import cn.appscomm.server.mode.device.Pair;
import cn.appscomm.server.mode.device.PairDevice;
import cn.appscomm.server.mode.device.PairDeviceNet;
import cn.appscomm.server.mode.device.UnPair;
import cn.appscomm.server.mode.sport.GetHeartRateData;
import cn.appscomm.server.mode.sport.GetHeartRateDataNet;
import cn.appscomm.server.mode.sport.GetSleepData;
import cn.appscomm.server.mode.sport.GetSleepDataNet;
import cn.appscomm.server.mode.sport.GetSportData;
import cn.appscomm.server.mode.sport.GetSportDataNet;
import cn.appscomm.server.mode.sport.UploadHeartRateData;
import cn.appscomm.server.mode.sport.UploadSleepData;
import cn.appscomm.server.mode.sport.UploadSportData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface UrlService {

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("v1/account/auth")
    Call<String> loginex(@Body LoginFr loginFr);

    @POST(Urls.ACCOUNT_LOGIN)
    Observable<LoginNet> login(@Body Login login);

    @POST(Urls.ACCOUNT_REGISTER)
    Observable<RegisterNet> register(@Body Register register);

    @POST(Urls.ACCOUNT_QUERY)
    Observable<UserInfoNet> accountQuery(@Body AccountQuery accountQuery);

    @POST(Urls.ACCOUNT_EDIT)
    Observable<UserInfoNet> accountEdit(@Body UserInfo userInfo);

    @POST(Urls.ACCOUNT_FORGET_PASSWORD)
    Observable<BaseResponse> forgetPassword(@Body ForgetPassword forgetPassword);

    @POST(Urls.ACCOUNT_MODIFY_PASSWORD)
    Observable<BaseResponse> modifyPassword(@Body ModifyPassword modifyPassword);

    @POST(Urls.ACCOUNT_UPLOAD_ICON)
    Observable<UploadImgNet> uploadImage(@Body UploadImage uploadImage);

    @POST(Urls.DEVICE_DEVICE_PAIR)
    Observable<BaseResponse> pair(@Body Pair pair);

    @POST(Urls.DEVICE_DEVICE_UNPAIR)
    Observable<BaseResponse> unPair(@Body UnPair unPair);

    @POST(Urls.DEVICE_GET_PAIR_DEVICE)
    Observable<PairDeviceNet> getPairDevice(@Body PairDevice pairDevice);

    @POST(Urls.SPORT_GET_DATA)
    Observable<GetSportDataNet> getSportData(@Body GetSportData getSportData);

    @POST(Urls.SPORT_UPLOAD_DATA)
    Observable<BaseResponse> uploadSportData(@Body UploadSportData uploadSportData);

    @POST(Urls.SLEEP_UPLOAD_DATA)
    Observable<BaseResponse> uploadSleepData(@Body UploadSleepData uploadSleepData);

    @POST(Urls.SLEEP_GET_DATA)
    Observable<GetSleepDataNet> getSleepData(@Body GetSleepData getSleepData);

    @POST(Urls.HEART_RATE_UPLOAD_DATA)
    Observable<BaseResponse> uploadHeartRateData(@Body UploadHeartRateData uploadHeartRateData);

    @POST(Urls.HEART_RATE_GET_DATA)
    Observable<GetHeartRateDataNet> getHeartRateData(@Body GetHeartRateData getHeartRateData);

    @POST(Urls.DEVICE_GET_FIRMWARE_VERSION)
    Observable<FirmwareVersionNet> getFirmwareVersion(@Body FirmwareVersion firmwareVersion);

    @GET
    Call<ResponseBody> downloadFirmware(@Url String netFirmwareUrl);

    @GET("security/tokenRetrieve")
    Call<String> refreshToken(@Query("accessToken") String token);


}
