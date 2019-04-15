package com.xxwolo.netlib.net.retrofit.subscriber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xxwolo.netlib.NetLibConstant;
import com.xxwolo.netlib.net.bean.BaseRespBean;
import com.xxwolo.netlib.net.dialog.AuthDialog;
import com.xxwolo.netlib.net.retrofit.listener.OnSubscriberNextListener;
import com.xxwolo.netlib.tool.util.NetLog;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * Author    ZhuMingren
 * Date      2018/12/6
 * Time      下午4:51
 * DESC      网络请求订阅者基类
 */

public abstract class BaseSubscriber<T extends BaseRespBean> implements Observer<T>, Disposable {
    
    protected WeakReference<Context> mContext;
    protected WeakReference<Disposable> mDisposable;
    protected OnSubscriberNextListener<T> mListener;
    
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    
    public BaseSubscriber() {
    
    }
    
    public BaseSubscriber(WeakReference<Context> context) {
        this.mContext = context;
    }
    
    public BaseSubscriber(WeakReference<Context> context, OnSubscriberNextListener<T> listener) {
        this.mContext = context;
        this.mListener = listener;
    }
    
    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = new WeakReference<Disposable>(d);
    }
    
    @Override
    public void onComplete() {
    }
    
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (mListener != null) {
            mListener.onFailure(getErrorMsg(e));
            mListener = null;
        }
        NetLog.E("onError e= " + e == null ? "null" : e.getLocalizedMessage());
    }
    
    @Override
    public void onNext(T t) {
        handleStatus(t); // 如果有onError要处理的情况, onNext就不会再处理
        if (mListener != null) {
            mListener.onNext(t);
            mListener = null;
        }
    }
    
    @Override
    public void dispose() {
        if (mDisposable != null && mDisposable.get() != null) {
            Disposable disposable = mDisposable.get();
            boolean isDisposed = disposable.isDisposed();
            //NetLog.D("disposable isDisposed= " + isDisposed);
            if (!isDisposed) {
                disposable.dispose();
            }
        }
        mListener = null;
    }
    
    @Override
    public boolean isDisposed() {
        boolean isDisposed = true;
        if (mDisposable != null && mDisposable.get() != null) {
            isDisposed = mDisposable.get().isDisposed();
        }
        return isDisposed;
    }
    
    /**
     * 响应状态码统一处理
     *
     * @param t 范型
     */
    private void handleStatus(T t) {
        int code;
        String msg;
        code = t.code;
        msg = t.message;
        // 501是2.0.0以前的返回码
        // 70X是2.0.0以后的返回码
        if (code == 501 || code == 701 || code == 702) {
            showReLogin();
            onError(new Exception(msg));
        }
    }
    
    
    private void showReLogin() {
        if (mContext != null && mContext.get() != null) {
            final Context activity = mContext.get();
            if (activity instanceof Activity && AuthDialog.isNotShow() && !((Activity) activity).isFinishing()) { // 仅仅在Activity中请求的时候才会处理登录错误, 已经显示的时候不再显示
                AuthDialog authDialog = new AuthDialog(activity, new AuthDialog.ConfirmCallback() {
                    @Override
                    public void onConfirm() {
                        Intent intent = new Intent(NetLibConstant.ACTION_LOGOUT);
                        intent.setPackage(activity.getPackageName());
                        activity.sendBroadcast(intent);
                    }
                });
                authDialog.show();
            }
        }
    }
    
    protected String getErrorMsg(Throwable e) {
        String msg;
        if (e == null) {
            msg = "null";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            msg = httpException.getMessage();
            NetLog.E("code = " + code + " msg= " + msg);
            switch (code) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    msg = "Server error: " + code;
                    break;
            }
        } else if (e instanceof ConnectException) {
            msg = ConnectException.class.getSimpleName() + ": " + e.getLocalizedMessage();
        } else if (e instanceof SocketTimeoutException) {
            msg = SocketTimeoutException.class.getSimpleName() + ": " + e.getLocalizedMessage();
        } else {
            msg = e.getLocalizedMessage();
        }
        return msg;
    }
    
}
