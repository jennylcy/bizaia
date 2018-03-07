package com.bizaia.zhongyin.util;

import android.text.TextUtils;
import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.entity.DownloadProListener;
import com.bizaia.zhongyin.util.entity.ProgressResponse;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by yan on 2016/12/12.
 */

public class AppRetrofit {
    private static final String TAG = "AppRetrofit";

    private static AppRetrofit appRetrofit;
    private static final int TIME_OUT = 20;
    private static boolean IS_LOG = true;

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private List<DownloadProListener> progressListeners;

    private static DownLoadInterceptor downloadInterceptor;
    private static final String httpsCer = "key.bks";// bks 密钥 bizaia


    public AppRetrofit addProgressListener(DownloadProListener progressListener) {
        this.progressListeners.add(progressListener);
        return appRetrofit;
    }

    public static AppRetrofit getAppRetrofit() {
        if (appRetrofit == null) {
            synchronized (AppRetrofit.class) {
                if (appRetrofit == null) {
                    appRetrofit = new AppRetrofit();
                }
            }
        }
        return appRetrofit;
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    private AppRetrofit() {
        progressListeners = new ArrayList<>();
        downloadInterceptor = new DownLoadInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

//        if (BuildConfig.DEBUG && IS_LOG) {
        builder.addInterceptor(getLogInterceptor());
//        }
//        okHttpClient = builder
//                .addInterceptor(addHeadersInterceptor)
//                .addNetworkInterceptor(downloadInterceptor)
//                .retryOnConnectionFailure(true)
//                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .build();
        okHttpClient = getBuilder().build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AddressConfiguration.MAIN_PATH)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public void removeProgressListener(DownloadProListener proListener) {
        progressListeners.remove(proListener);

    }

    public Retrofit retrofit() {
        return retrofit;
    }

    public static Interceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        boolean isJson;

                        try {
                            new JsonParser().parse(message);
                            isJson = true;
                        } catch (JsonParseException e) {
                            isJson = false;
                        }

                        Log.e("AppRetrofit", isJson ? format(message) : message);
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    private class DownLoadInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response orginalResponse = chain.proceed(chain.request());
            Response response = orginalResponse.newBuilder().body(
                    new ProgressResponse(orginalResponse.body(), progressListeners)).build();
            return response;
        }
    }

    private static Interceptor addHeadersInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request()
                    .newBuilder();
            if (!TextUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
//                Headers headers = new Headers.Builder()
                Headers headers = BIZAIAApplication.getInstance().getHeaders();
//                headers.add("token", BIZAIAApplication.getInstance().getToken())
//                        .build();
                builder.headers(headers);
                Log.e(TAG, "intercept: token" + BIZAIAApplication.getInstance().getToken());
            }

            return chain.proceed(builder.build());
        }
    };

    private static String format(String jsonStr) {
        int level = 0;
        StringBuilder jsonForMatStr = new StringBuilder();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c).append("\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c).append("\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    /**
     *  https 证书导入
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory() {
        final String CLIENT_TRUST_PASSWORD = "bizaia";//信任证书密码，该证书默认密码是bizaia
        final String CLIENT_AGREEMENT = "TLS";//使用协议
        final String CLIENT_TRUST_KEYSTORE = "BKS";
        SSLContext sslContext = null;
        try {
            //取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            InputStream is = BIZAIAApplication.getInstance().getAssets().open(httpsCer);
            try {
                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
            } catch (Exception e) {
                Log.e("SslContextFactory", e.getMessage());
            } finally {
                is.close();
            }
            //初始化密钥管理器
            trustManager.init(tks);
            //初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SslContextFactory", e.getMessage());
        }
        return null;
    }


    private static OkHttpClient.Builder getBuilder() {
        SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(getLogInterceptor());
        builder.addInterceptor(addHeadersInterceptor)
                .addNetworkInterceptor(downloadInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory, Platform.get().trustManager(sslSocketFactory));
        }
        return builder;
    }
}
