package com.xxwolo.netlib.net.retrofit;

import com.xxwolo.netlib.NetLibConfig;
import com.xxwolo.netlib.net.IRetrofitService;
import com.xxwolo.netlib.net.retrofit.listener.FastJsonConverterFactory;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * retrofit
 */

public enum RetrofitUtil {
    INSTANCE;
    private Retrofit mRetrofit;
    private Map<String, Object> interfaceMap = new HashMap<>();

    RetrofitUtil() {
        initRetrofit();
    }

    void initRetrofit() {
        OkHttpClient okHttpClient = OkHttpClientFactory.INSTANCE.getmOkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetLibConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }
    
    /**
     * 主要为了重置公共参数，用于退出登录后重新登录时使用
     */
    public void reset() {
        OkHttpClientFactory.INSTANCE.resetOkHttp();
        initRetrofit();
    }
    
    public <T> T getService(final Class<T> serviceClass) {
        if (serviceClass != null) {
            T service = (T) interfaceMap.get(serviceClass.getClass().getName());
            if (service == null) {
                service = mRetrofit.create(serviceClass);
                interfaceMap.put(serviceClass.getClass().getName(), service);
            }
            return service;
        } else {
            return null;
        }
    }

    public IRetrofitService getServiceWithBaseUrl(String baseUrl) {
        OkHttpClient okHttpClient = OkHttpClientFactory.INSTANCE.getmOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        return retrofit.create(IRetrofitService.class);
    }

}
