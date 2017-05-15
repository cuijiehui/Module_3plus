package cn.appscomm.server;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.util.LogUtil;
import cn.appscomm.server.util.ServerAppContext;
import cn.appscomm.server.util.ServerUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/2/20.
 */
class ServerManager {

    private static ServerManager serverManager = new ServerManager();
    private UrlService urlService = null;
    private OkHttpClient mOkHttpClient;
    private final int CACHE_TIME = 60 * 60 * 24 * 7;                                                // 缓存时间(7天)
    private final int CACHE_SIZE = 1024 * 1024 * 10;                                                // 缓存大小为10M
    private final int CONNECT_TIMEOUT = 10;                                                         // 连接超时10秒

    // 创建单例对象时，初始化
    private ServerManager() {
        init();
    }

    // 获取Retrofit实例
    public static ServerManager getInstance() {
        return serverManager;
    }

    // 获取urlService
    public UrlService getUrlService() {
        return urlService;
    }

    // 初始化
    private void init() {
        initOkHttpClient();
        initRetrofit();
    }

    // 初始化Retrofit
    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())                                 // 支持Gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())                           // 支持RxJava
                .baseUrl(Urls.HOST)                                                                 // 设置Host
//                .baseUrl(Urls.HOST1)                                                                 // 设置Host
                .build();
        urlService = retrofit.create(UrlService.class);
    }

    // 初始化OKHttpClient
    private void initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (ServerManager.class) {
                LogUtil.i("test", "context : " + (ServerAppContext.INSTANCE.getContext() != null));
                Cache cache = new Cache(new File(ServerAppContext.INSTANCE.getContext().getCacheDir(), "HttpCache"), CACHE_SIZE);
                mOkHttpClient = new OkHttpClient.Builder()
                        .cache(cache)                                                               // 设置缓存路径，缓存大小为10M
                        .retryOnConnectionFailure(true)                                             // 错误重连
                        .addInterceptor(mNetworkCacheInterceptor2)                                  // 设置缓存策略
                        .addNetworkInterceptor(mNetworkCacheInterceptor2)
                        .addInterceptor(httpLoggingInterceptor())                                   // 打印日志
                        .addInterceptor(tokenAndSeqInterceptor())                                   // 设置应用拦截器，可用于设置公共参数，头信息，日志拦截等
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)                          // 设置连接超时为10秒
//                        .socketFactory(getSSLSocketFactory())
//                        .hostnameVerifier(getHostnameVerifier())
                        .build();
            }
        }
    }

    // 缓存策略1(有网无网都读取缓存)
    private Interceptor mNetworkCacheInterceptor1 = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            cacheControl = TextUtils.isEmpty(cacheControl) ? "public, max-age=60" : cacheControl;
            return response.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
        }
    };

    // 缓存策略2(无网读缓存 有网读网络)
    private Interceptor mNetworkCacheInterceptor2 = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = !ServerUtil.checkNetWork() ? request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build() : request;

            Response response = chain.proceed(request);
            String cacheControl = ServerUtil.checkNetWork() ? request.cacheControl().toString() : "public, only-if-cached, max-stale=" + CACHE_TIME;
            return response.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
        }
    };

    // 日志打印
    private HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    // accessToken和accessSeq的设置(因为所有的请求都要带上这个)
    private Interceptor tokenAndSeqInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("accessToken", ServerVal.accessToken)
                        .addHeader("accessSeq", System.currentTimeMillis() + "")
//                        .addHeader("Content-Type", "application/json; charset=utf-8")
//                        .addHeader("Accept", "Application/json")
//                        .addHeader("Authorization", "Basic NjM5MjI0QzEwODUyQzRCNDo5MWFlMjc0NGU5MWNkZWEzMmQ3Zjg2YmFmMWQ0MjUzZg==")
                        .build();
                return chain.proceed(request);
            }
        };
    }


    protected static SSLSocketFactory getSSLSocketFactory() {
        if (ServerAppContext.INSTANCE.getContext() == null) {
            throw new NullPointerException("context == null");
        }

        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            InputStream certificate = ServerAppContext.INSTANCE.getContext().getResources().openRawResource(R.raw.mykronoz);
            keyStore.setCertificateEntry(String.valueOf(0), certificateFactory.generateCertificate(certificate));

            if (certificate != null) {
                certificate.close();
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
        }
        return null;
    }

    protected HostnameVerifier getHostnameVerifier() {

        HostnameVerifier TRUSTED_VERIFIER = new HostnameVerifier() {

            public boolean verify(String hostname, SSLSession session) {
                boolean ret = false;
                if (Urls.HOST1.equalsIgnoreCase(hostname)) {
                    ret = true;
                }
                return ret;
            }
        };

        return TRUSTED_VERIFIER;
    }
}
