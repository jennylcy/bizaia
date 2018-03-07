package com.bizaia.zhongyin.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by jensen on 5/12/16.
 */
public class OkHttpUtils {
    private static OkHttpUtils instance;

    private OkHttpClient client;

    public OkHttpUtils() {
        this.client = AppRetrofit.getAppRetrofit().getClient();
    }

    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void cancelAll() {
        client.dispatcher().cancelAll();
    }

    public void cancel(Object tag) {
        if (tag == null)
            return;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
    }
}
