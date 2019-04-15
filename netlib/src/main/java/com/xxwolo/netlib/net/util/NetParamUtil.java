package com.xxwolo.netlib.net.util;

import android.content.Context;
import android.text.TextUtils;

import com.xxwolo.netlib.NetLibConfig;

import java.util.Locale;

/**
 * Author    ZhuMingren
 * Date      2018/11/19
 * Time      下午7:19
 * DESC      com.tarotspace.app.net
 */
public class NetParamUtil {
    
    private static String mCountry = null;
    
    public static String getCacheLocLang() {
        Context context = NetLibConfig.appContext;
        if (!TextUtils.isEmpty(mCountry)) {
            return mCountry;
        } else {
            Locale locale = context.getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            if (language != null) {
                switch (language) {
                    case "zh":
                        mCountry = "china";
                        break;
                    case "ja":
                        mCountry = "japan";
                        break;
                    case "ko":
                        mCountry = "korea";
                        break;
                    default:
                        mCountry = language;
                }
                return mCountry;
            } else {
                return "";
            }
        }
    }
   
}
