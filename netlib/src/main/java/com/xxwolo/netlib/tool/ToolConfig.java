package com.xxwolo.netlib.tool;

import android.content.Context;

/**
 * Author    ZhuMingren
 * Date      2018/11/15
 * Time      下午5:13
 * DESC      com.xxwolo.netlib.toollib
 */
public class ToolConfig {
    
    /**
     * 设置是否为调试模式
     */
    public static void setDebug(Context context, boolean b, String tag) {
        appContext = context.getApplicationContext();
        DEBUG = b;
        TAG = tag;
    }
    
    public static boolean DEBUG = true;
    public static String TAG = "TOOL_LIB/ZMR";
    
    public static Context appContext;
}
