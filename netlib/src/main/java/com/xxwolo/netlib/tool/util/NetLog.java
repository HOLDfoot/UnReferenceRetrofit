package com.xxwolo.netlib.tool.util;

import com.xxwolo.netlib.tool.ToolConfig;

/**
 * Author    ZhuMingren
 * Date      2018/11/15
 * Time      下午2:24
 * DESC      日志打印工具
 */
public class NetLog {
    
    private static String TAG;
    
    private static boolean isDebug;
    
    static {
        isDebug = ToolConfig.DEBUG;
        android.util.Log.e("zmr", "isDebug= " + isDebug);
        TAG = ToolConfig.TAG;
    }
   
    /**
     * 直接打印E日志信息
     */
    public static void E(String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.e(TAG, msg);
        } else if (isDebug) {
            android.util.Log.e(TAG, "msg == null");
        }
    }
    
    /**
     * 直接打印E日志信息, 加tag
     */
    public static void E(String tag, String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.e(TAG + "/" + tag, msg);
        } else if (isDebug) {
            android.util.Log.e(TAG + "/" + tag, "msg == null");
        }
    }
    
    /**
     * 直接打印V日志信息, 加tag
     */
    public static void V(String tag, String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.v(TAG + "/" + tag, msg);
        } else if (isDebug) {
            android.util.Log.v(TAG + "/" + tag, "msg == null");
        }
    }
    
    /**
     * 直接打印V日志信息, 加tag
     */
    public static void V(String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.v(TAG, msg);
        } else if (isDebug) {
            android.util.Log.v(TAG, "msg == null");
        }
    }
    
    /**
     * 直接打印E异常信息
     */
    public static void E(String tag, String msg, Throwable e) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.e(TAG + "/" + tag, msg, e);
        } else if (isDebug) {
            android.util.Log.e(TAG, "msg == null");
        }
    }
    
    
    /**
     * 直接打印I日志信息
     */
    public static void I(String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.i(TAG, msg);
        } else if (isDebug) {
            android.util.Log.i(TAG, "msg == null");
        }
    }
    
    /**
     * 直接打印D日志信息, 加tag
     */
    public static void I(String tag, String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.i(TAG + "/" + tag, msg);
        } else if (isDebug) {
            android.util.Log.i(TAG, "msg == null");
        }
    }
    
    /**
     * 直接打印D日志信息
     */
    public static void D(String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.d(TAG, msg);
        } else if (isDebug) {
            android.util.Log.d(TAG, "msg == null");
        }
    }
    
    /**
     * 直接打印D日志信息, 加tag
     */
    public static void D(String tag, String msg) {
        if (isDebug && msg != null && msg.length() > 0) {
            android.util.Log.d(TAG + "/" + tag, msg);
        } else if (isDebug) {
            android.util.Log.d(TAG, "msg == null");
        }
    }
    
}
