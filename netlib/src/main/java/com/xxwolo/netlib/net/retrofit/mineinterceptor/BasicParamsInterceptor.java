package com.xxwolo.netlib.net.retrofit.mineinterceptor;

import android.text.TextUtils;

import com.xxwolo.netlib.NetLibConfig;
import com.xxwolo.netlib.net.util.NetParamUtil;
import com.xxwolo.netlib.tool.util.NetLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


public class BasicParamsInterceptor implements Interceptor {
    
    private static final NetLogger logger = new NetLogger("okHttp/inject");
    private static final MediaType jsonType = MediaType.parse("application/json; charset=UTF-8");
    private static final MediaType urlencodedType = MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");
    
    private Map<String, String> queryParamsMap = new HashMap<>();
    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, String> headerParamsMap = new HashMap<>();
    private List<String> headerLinesList = new ArrayList<>();
    
    private BasicParamsInterceptor() {
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        // process header params inject
        Headers.Builder headerBuilder = request.headers().newBuilder();
        if (headerParamsMap.size() > 0) {
            Iterator iterator = headerParamsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        
        if (headerLinesList.size() > 0) {
            for (String line : headerLinesList) {
                headerBuilder.add(line);
            }
        }
        
        requestBuilder.headers(headerBuilder.build());
        // process header params end
        // process queryParams inject whatever it'dsfds GET or POST
        // if (queryParamsMap.size() > 0) {
        //     injectParamsIntoUrl(request, requestBuilder, queryParamsMap);
        // }
        // process header params end
        // process post body inject
        
        /*if (canInjectIntoBody(request)) { // POST & application/x-www-form-urlencoded
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (paramsMap.size() > 0) {
                Iterator iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }
            }
            RequestBody formBody = formBodyBuilder.build();
            String postBodyString = bodyToString(request.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
            logger.D("injectParamsIntoBody method= " + request.method());
        }*/
        if ("POST".equalsIgnoreCase(request.method())) {
            MediaType mediaType = request.body().contentType();
            if (jsonType.equals(mediaType)) {
                logger.E("mediaType: " + mediaType);
                String postBodyString = bodyToString(request.body());
                try {
                    JSONObject jsonObject = new JSONObject(postBodyString);

                    jsonObject.put("area", NetParamUtil.getCacheLocLang());
                    jsonObject.put("version", NetLibConfig.paramMap.get("version"));
                    jsonObject.put("user_id", NetLibConfig.paramMap.get("user_id"));
                    requestBuilder.post(RequestBody.create(mediaType, jsonObject.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (urlencodedType.equals(mediaType)) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                if (paramsMap.size() > 0) {
                    Iterator iterator = paramsMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                RequestBody formBody = formBodyBuilder.build();
                String postBodyString = bodyToString(request.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
                requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
            }
        }
        // can't inject into body, then inject into url
        else if ("GET".equalsIgnoreCase(request.method())) {
            injectParamsIntoUrl(request, requestBuilder, paramsMap);
        }
        request = requestBuilder.cacheControl(CacheControl.FORCE_NETWORK).build(); // 设置每次使用网络
        String postBodyString = bodyToString(request.body());
        logger.E("request.body= " + postBodyString);
        return chain.proceed(request);
    }
    
    private boolean canInjectIntoBody(Request request) {
        if (request == null) {
            return false;
        }
        if (!TextUtils.equals(request.method(), "POST")) {
            return false;
        }
        RequestBody body = request.body();
        if (body == null) {
            return false;
        }
        MediaType mediaType = body.contentType();
        if (mediaType == null) {
            return false;
        }
        //NetLog.E("canInjectIntoBody mediaType= " + mediaType + " mediaType.subtype()= " + mediaType.subtype());
        // E/TAROT/ZMR: canInjectIntoBody mediaType= application/json; charset=UTF-8 mediaType.subtype()= json
        if (!TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
            return false;
        }
        //NetLog.E("canInjectIntoBody");
        return true;
    }
    
    // func to inject params into url
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        paramsMap.putAll(NetLibConfig.paramMap); // baseParam: version, User:id&token
        //paramsMap.put("uuid", IdentifyUtil.getIdentifyId());
        paramsMap.put("area", NetParamUtil.getCacheLocLang());
        logger.D( "injectParamsIntoUrl method= " + request.method() + " paramsMap= " + paramsMap.toString());
        // POST中application/x-www-form-urlencoded;charset=UTF-8的mediaType不需要在URL中加参数
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }
        
        requestBuilder.url(httpUrlBuilder.build());
    }
    
    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
    
    public static class Builder {
        
        BasicParamsInterceptor interceptor;
        
        public Builder() {
            interceptor = new BasicParamsInterceptor();
        }
        
        public Builder addParam(String key, String value) {
            interceptor.paramsMap.put(key, value);
            return this;
        }
        
        public Builder addParamsMap(Map<String, String> paramsMap) {
            interceptor.paramsMap.putAll(paramsMap);
            return this;
        }
        
        public Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }
        
        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.headerParamsMap.putAll(headerParamsMap);
            return this;
        }
        
        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + headerLine);
            }
            interceptor.headerLinesList.add(headerLine);
            return this;
        }
        
        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine : headerLinesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                interceptor.headerLinesList.add(headerLine);
            }
            return this;
        }
        
        public Builder addQueryParam(String key, String value) {
            interceptor.queryParamsMap.put(key, value);
            return this;
        }
        
        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            interceptor.queryParamsMap.putAll(queryParamsMap);
            return this;
        }
        
        public BasicParamsInterceptor build() {
            return interceptor;
        }
        
    }
    
}
