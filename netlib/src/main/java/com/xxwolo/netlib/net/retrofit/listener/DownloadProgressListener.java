package com.xxwolo.netlib.net.retrofit.listener;

/**
 * Created by ChuyaoShi on 16/10/18.
 * 数据下载进度显示
 */

public interface DownloadProgressListener {
    void onProgress(long current, long total, boolean finish);

    void onError();
}
