package com.xxwolo.netlib.tool.util;


import android.support.annotation.NonNull;

import com.xxwolo.netlib.tool.ToolConfig;

/**
 * Author    ZhuMingren
 * Date      2018/11/15
 * Time      下午2:24
 * DESC      日志打印工具
 */

public final class NetLogger {
    
    // 是否打印日志 可在application中初始化
    public static boolean isPrint = true;
    static {
        isPrint = ToolConfig.DEBUG;
    }
    
    private static String defaultTag = "NetLog";

    private NetLogger() {
    }

    // 设置默认的tag
    public static void setTag(String tag) {
        defaultTag = tag;
    }
    
    /**
     * ******************** Log对象, 只包含一个tag **************************
     */
    private String classTag = "classTag";
    
    public NetLogger(@NonNull Object object) {
        if (object instanceof String) {
            this.classTag = (String) object;
        } else {
            this.classTag = object.getClass().getSimpleName();
        }
    }
    
    public int V(String msg) {
        return isPrint ? android.util.Log
                .v(classTag, msg) : -1;
    }
    
    public int D(String msg) {
        return isPrint ? android.util.Log
                .d(classTag, msg) : -1;
    }
    
    public int I(String msg) {
        return isPrint ? android.util.Log
                .i(classTag, msg) : -1;
    }
    
    public int W(String msg) {
        return isPrint ? android.util.Log
                .w(classTag, msg) : -1;
    }
    
    public int E(String msg) {
        return isPrint ? android.util.Log
                .e(classTag, msg) : -1;
    }
}
