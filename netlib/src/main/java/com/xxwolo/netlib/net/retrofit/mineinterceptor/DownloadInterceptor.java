package com.xxwolo.netlib.net.retrofit.mineinterceptor;


import com.xxwolo.netlib.net.retrofit.DownloadResponseBody;
import com.xxwolo.netlib.net.retrofit.listener.DownloadProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;



public class DownloadInterceptor implements Interceptor {

    private DownloadProgressListener mListener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.mListener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(new DownloadResponseBody(response.body(), mListener)).build();
    }
}
