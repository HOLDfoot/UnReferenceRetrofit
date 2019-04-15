package com.xxwolo.netlib.net.retrofit.listener;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxwolo.netlib.tool.util.LogUtil;
import com.xxwolo.netlib.tool.util.NetLogger;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Author    ZhuMingren
 * Date      2018/12/6
 * Time      下午4:51
 * DESC      网络返回的Response转换器
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    
    private static NetLogger logger = new NetLogger("okHttp/Response");
    
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        JSONObject jsonObj = JSON.parseObject(tempStr);
        if (jsonObj != null && jsonObj.toJSONString().length() > 2500) {
            String responsePath = LogUtil.printStringToFile(null, jsonObj.toJSONString(), "okHttpResponse", false);
            logger.E("responsePath= " + responsePath);
        } else if (jsonObj != null){
            logger.E(jsonObj.toJSONString());
        } else {
            logger.E(tempStr);
            tempStr = "{\"code\":100,\"message\":\"response is null\"}";
        }
        //LogUtil.printBigStr("okHttp/Response", jsonObj);
        return JSON.parseObject(tempStr, type);

    }
}
