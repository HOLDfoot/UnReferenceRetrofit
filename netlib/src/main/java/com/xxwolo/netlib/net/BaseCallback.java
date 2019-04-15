package com.xxwolo.netlib.net;

import com.xxwolo.netlib.net.bean.BaseRespBean;

/**
 * Author    ZhuMingren
 * Date      2018/12/19
 * Time      上午11:26
 * DESC      com.xxwolo.netlib.net
 */
public interface BaseCallback {
    void onSuccess(BaseRespBean respBean);
    void onFail(String msg);
}
