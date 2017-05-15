package cn.appscomm.thirdpartyloginshare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

import cn.appscomm.thirdpartyloginshare.util.LogUtil;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Administrator on 2017/3/9.
 */

public enum ThirdPartLoginShareManager {
    INSTANCE;
    private static final String TAG = ThirdPartLoginShareManager.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;

    private final int REQUEST_CODE_GOOGLE_LOGIN = 666;
    private final int LOGIN_TYPE_FACEBOOK = 0;
    private final int LOGIN_TYPE_TWITTER = 1;
    private final int LOGIN_TYPE_WECHAT = 2;
    private final int LOGIN_TYPE_GOOGLE = 3;
    private int loginType = -1;
    private boolean initFacebookFlag = false;
    private boolean initTwitterFlag = false;
    private boolean initGoogleFlag = false;
    private Context context = ThirdPartLoginShareAppContext.INSTANCE.getContext();

    // Facebook登录
    public void facebookLogin(Context context) {
        this.context = context;
        initFacebookLogin();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
        }
        loginType = LOGIN_TYPE_FACEBOOK;
        LoginManager.getInstance().logInWithReadPermissions(((Activity) (context)), Arrays.asList("public_profile", "user_friends"));
    }

    // Facebook第三方登录初始化
    private void initFacebookLogin() {
        if (!initFacebookFlag) {
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    try {
                        String token = loginResult != null ? loginResult.getAccessToken().getToken() : "";
                        if (!TextUtils.isEmpty(token))
                            LogUtil.i(TAG, "Facebook登录成功,token:" + token);
                        else {
                            LogUtil.i(TAG, "Facebook登录失败!!!");
                        }
                    } catch (Exception e) {
                        LogUtil.i(TAG, "Facebook登录异常!!!");
                    }
                }

                @Override
                public void onCancel() {
                    LogUtil.i(TAG, "用户取消Facebook登录!!!");
                }

                @Override
                public void onError(FacebookException e) {
                    LogUtil.i(TAG, "Facebook登录失败!!!");
                }
            });
        }
        initFacebookFlag = true;
    }

    public void twitterLogin(Context context) {
        this.context = context;
        initTwitter();
        loginType = LOGIN_TYPE_TWITTER;
        twitterAuthClient.authorize((Activity) context, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                try {
                    TwitterAuthToken authToken = result.data.getAuthToken();
                    String token = authToken.token;
                    String secret = authToken.secret;
                    if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(secret)) {
                        LogUtil.i(TAG, "Twitter登录成功,token : " + token + " secret : " + secret);
                    } else {
                        LogUtil.i(TAG, "Twitter登录成功但token或secret为空!!!");
                    }
                } catch (Exception e) {
                    LogUtil.i(TAG, "Twitter登录异常!!!");
                }
            }

            @Override
            public void failure(TwitterException e) {
                LogUtil.i(TAG, "Twitter登录失败!!!");
            }
        });
    }

    private void initTwitter() {
        if (!initTwitterFlag) {
            TwitterAuthConfig authConfig = new TwitterAuthConfig("key", "value");
            Fabric.with(context, new TwitterCore(authConfig));
            twitterAuthClient = new TwitterAuthClient();
        }
        initTwitterFlag = true;
    }

    // 谷歌登录
    public void googleLogin(Context context) {
        this.context = context;
        loginType = LOGIN_TYPE_GOOGLE;
        initGoogleLogin();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity) (context)).startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_LOGIN);
    }

    // 谷歌第三方登录初始化
    private void initGoogleLogin() {
        if (!initGoogleFlag) {
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("google client id")                                    // 设置Client_id，返回的token才不为空
                    .requestEmail()
                    .requestId()
                    .build();

            mGoogleApiClient = new GoogleApiClient
                    .Builder(context)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            LogUtil.i(TAG, "Google登录连接");
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            LogUtil.i(TAG, "Google登录");
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            LogUtil.i(TAG, "Google登录失败");
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            mGoogleApiClient.connect();
        }
        initGoogleFlag = true;
    }

    public void onPause() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    // 处理谷歌登录结果
    private void proGoogleLoginResult(GoogleSignInResult result) {
        try {
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                String token = acct != null ? acct.getIdToken() : "";
                if (!TextUtils.isEmpty(token)) {
                    LogUtil.i(TAG, "邮件:" + acct.getEmail() + " 姓名:" + acct.getDisplayName() + " 性别:" + acct.getAccount().toString());
                    LogUtil.i(TAG, "谷歌登录成功,token:" + token);
                } else {
                    LogUtil.i(TAG, "谷歌登录失败!!!");
                }
            } else {
                LogUtil.i(TAG, "谷歌登录失败!!!");
            }
        } catch (Exception e) {
            LogUtil.i(TAG, "谷歌登录异常!!!");
        }
    }

    // 结果回调
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (loginType) {
            case LOGIN_TYPE_FACEBOOK:
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case LOGIN_TYPE_TWITTER:
                if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
                    twitterAuthClient.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case LOGIN_TYPE_WECHAT:
                break;
            case LOGIN_TYPE_GOOGLE:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                proGoogleLoginResult(result);
                break;
            default:
                LogUtil.i(TAG, "结果返回但登录类型不正确:" + loginType);
                break;
        }
    }
}
