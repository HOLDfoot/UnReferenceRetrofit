package com.xxwolo.netlib.net.retrofit.subscriber;

import android.content.Context;

import com.xxwolo.netlib.net.bean.BaseRespBean;
import com.xxwolo.netlib.net.retrofit.ProgressDialogHandler;
import com.xxwolo.netlib.net.retrofit.listener.OnProgressCancelListener;
import com.xxwolo.netlib.net.retrofit.listener.OnSubscriberNextListener;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * Author    ZhuMingren
 * DESC      有进度条的订阅者
 */

public class ProgressSubscriber<T extends BaseRespBean> extends BaseSubscriber<T> implements OnProgressCancelListener {
    
    private ProgressDialogHandler mHandler;
    
    public ProgressSubscriber(WeakReference<Context> context, String msg, OnSubscriberNextListener<T> listener) {
        super(context, listener);
        this.mHandler = new ProgressDialogHandler(mContext, msg, this, false);
    }
    
    public ProgressSubscriber(WeakReference<Context> context, OnSubscriberNextListener<T> listener) {
        super(context, listener);
        this.mHandler = new ProgressDialogHandler(mContext, this, false);
    }
    
    public ProgressSubscriber(WeakReference<Context> context, boolean cancelable, OnSubscriberNextListener<T> listener) {
        super(context, listener);
        this.mHandler = new ProgressDialogHandler(mContext, this, cancelable);
    }
    
    private void showDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW).sendToTarget();
        }
    }
    
    private void dismissDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS).sendToTarget();
            mHandler = null;
        }
    }
    
    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        showDialog();
    }
    
    @Override
    public void onProgressCancel() {
        dismissDialog();
    }
    
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissDialog();
    }
    
    @Override
    public void onNext(T t) {
        super.onNext(t);
        dismissDialog();
    }
}
