package com.xxwolo.netlib.tool.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.xxwolo.netlib.tool.ToolConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ZhuMingren on 2017/5/28.
 */

public class LogUtil {
    
    private static final String TAG = ToolConfig.TAG;
    
    /**
     * 打印log到文件内
     * path folder is /storage/emulated/0/Android/data/com.xxwolo.tarot/files/Documents/
     * @param context
     * @param str
     * @param fileName 不需要txt
     * @param add 如果 false 每次都创建新的文件
     */
    public static String printStringToFile(@Nullable Context context, String str, String fileName, boolean add) {
        if (!ToolConfig.DEBUG) {
            NetLog.D(TAG, "LogUtil: not in debuggable mode");
            return "";
        }
        context = ToolConfig.appContext;
        File file = new File(DirectoryUtil.getCacheDirectory(context, Environment.DIRECTORY_DOCUMENTS), fileName + ".txt");
        String absPath = file.getAbsolutePath();
        NetLog.E(TAG, "LogUtil Path= " + absPath + " add= " + add);
        FileWriter writer = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else if (!add) {
                file.delete();
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
            String time = simpleDateFormat.format(new Date()).toString();
            writer.write("\n" + str + "\n" + time + "\n");
            writer.flush();
            writer.close();
            writer = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return absPath;
    }
    
    public static void printBigStr(String tag, String log) {
        int maxLength = 4000; // log长度应该小于4k
        if (log != null && log.length() <= maxLength) {
            NetLog.E(tag, log);
            return;
        }
        int index = 0;
        String sub;
        while (index < log.length()) {
            if (log.length() <= index + maxLength) {
                sub = log.substring(index);
            } else {
                sub = log.substring(index, index + maxLength);
            }
            
            index += maxLength;
            NetLog.E(tag, sub.trim());
        }
    }
}
