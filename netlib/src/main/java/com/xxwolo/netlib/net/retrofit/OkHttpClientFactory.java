package com.xxwolo.netlib.net.retrofit;


import com.xxwolo.netlib.NetLibConfig;
import com.xxwolo.netlib.net.retrofit.mineinterceptor.BasicParamsInterceptor;
import com.xxwolo.netlib.tool.ToolConfig;
import com.xxwolo.netlib.tool.util.NetLog;
import com.xxwolo.netlib.tool.util.NetLogger;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Author    ZhuMingren
 * Date      2018/11/29
 * Time      下午7:04
 * DESC      构建OkHttpClient实例，进行一些全局的配置,比如UA
 */

public enum OkHttpClientFactory {
    INSTANCE;
    
    private static NetLogger logger = new NetLogger("okHttp/interceptor");
    
    private OkHttpClient mOkHttpClient;

    OkHttpClientFactory() {
        initOkHttp();
    }

    private void initOkHttp() {
        NetLog.I("initOkHttp");
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                logger.V(message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(back().build());
        if (ToolConfig.DEBUG) {
            builder.addInterceptor(logInterceptor);
        }
        mOkHttpClient = builder.build();
    }

    private BasicParamsInterceptor.Builder back() {
        BasicParamsInterceptor.Builder basic = new BasicParamsInterceptor.Builder();
        Map<String, String> map = new HashMap();
        // BasicParamsInterceptor每次重新生成
        
        basic.addParamsMap(map);
        basic.addHeaderParam("User-Agent", backUA());
        return basic;
    }

    private String backUA() {
        StringBuffer stringBuffer = new StringBuffer("MyUserAgent");
        if (NetLibConfig.headerMap != null) {
            for (String key : NetLibConfig.headerMap.keySet()) {
                stringBuffer.append(NetLibConfig.headerMap.get(key)).append(";"); // 只放value, 以':'分开
            }
        }
        return stringBuffer.toString();
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    public void resetOkHttp() {
        initOkHttp();
    }

}
