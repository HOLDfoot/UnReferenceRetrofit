package com.xxwolo.netlib.net.retrofit.listener;

/**
 * sub list
 */

public interface OnSubscriberNextListener<T> {
    void onNext(T t);

    void onFailure(String msg);
}
