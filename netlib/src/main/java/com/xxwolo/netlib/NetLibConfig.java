package com.xxwolo.netlib;

import android.content.Context;
import android.support.annotation.NonNull;


import com.xxwolo.netlib.tool.ToolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Author    ZhuMingren
 * Date      2018/11/28
 * Time      下午8:40
 * DESC      com.xxwolo.netlib
 */
public class NetLibConfig {
    
    public static String BASE_URL;
    public static Map<String, String> headerMap = new HashMap<>();
    public static Map<String, String> paramMap = new HashMap<>();
    private static Map<String, String> baseParamMap = new HashMap<>();
    
    public static Context appContext;
    
    public static void setDebug(Context context, boolean b, String tag) {
        ToolConfig.setDebug(context, b, tag);
        appContext = context.getApplicationContext();
    }
    
    /**
     * 初始化不变的参数
     * @param baseUrl
     * @param baseParam
     */
    public static void initBase(String baseUrl, Map<String, String> baseParam) {
        BASE_URL = baseUrl;
        baseParamMap = baseParam;
    }
    
    public static void initBase(Context context, String baseUrl, Map<String, String> baseParam) {
        appContext = context.getApplicationContext();
        BASE_URL = baseUrl;
        baseParamMap = baseParam;
    }
    
    /**
     * 初始化可变的参数
      */
    public static void init(Map<String, String> header, @NonNull Map<String, String> param) {
        headerMap = header;
        paramMap = param;
        paramMap.putAll(baseParamMap);
    }
    
}
