package com.xxwolo.netlib.base;

import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

import com.xxwolo.netlib.manager.ReferenceManager;

/**
 * Author    ZhuMingren
 * Date      2019/4/15
 * Time      下午7:38
 * DESC      RetrofitUnReference
 */
public class BasePresenterActivity extends AppCompatActivity {
    
    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReferenceManager.getInstance().releaseReference(this);
    }
    
    public BasePresenterActivity getThisActivity() {
        return this;
    }
}
