package com.xxwolo.netlib.net.retrofit.subscriber;

import android.content.Context;

import com.xxwolo.netlib.net.bean.BaseRespBean;
import com.xxwolo.netlib.net.retrofit.listener.OnSubscriberNextListener;

import java.lang.ref.WeakReference;

/**
 * Author    ZhuMingren
 * DESC      没有进度条的订阅者
 */

public class CommonSubscriber<T extends BaseRespBean> extends BaseSubscriber<T> {
    
    public CommonSubscriber(WeakReference<Context> context, OnSubscriberNextListener<T> listener) {
        super(context, listener);
    }
    
    @Override
    public void onComplete() {
        super.onComplete();
    }
    
    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }
    
    @Override
    public void onNext(T t) {
        super.onNext(t);
    }
}
