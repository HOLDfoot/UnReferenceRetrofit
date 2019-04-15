package com.xxwolo.netlib.net.retrofit;

import com.xxwolo.netlib.net.retrofit.listener.DownloadProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 下载文件数据返回
 */

public class DownloadResponseBody extends ResponseBody {

    private ResponseBody mRes;
    private DownloadProgressListener mListener;
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody res, DownloadProgressListener listener) {
        this.mRes = res;
        this.mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mRes.contentType();
    }

    @Override
    public long contentLength() {
        return mRes.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(source(mRes.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (null != mListener) {
                    mListener.onProgress(totalBytesRead, mRes.contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }
}
