package app.xiaoming.retrofitunreference.net;


import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.xxwolo.netlib.manager.DisposableManager;
import com.xxwolo.netlib.manager.ReferenceManager;
import com.xxwolo.netlib.manager.Releasable;
import com.xxwolo.netlib.net.retrofit.RetrofitUtil;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * 基础Presenter
 */
public abstract class IPresenter implements Releasable {
    
    protected ApiService iService = RetrofitUtil.INSTANCE.getService(ApiService.class);
    protected WeakReference<Context> mContext; // 以Activity实例为Tag
    
    public IPresenter(Context context) {
        this.mContext = new WeakReference<>(context);
        ReferenceManager.getInstance().addReference(context, this);
    }
    
    public IPresenter(Fragment fragment) {
        this.mContext = new WeakReference<>(fragment.getContext());
        ReferenceManager.getInstance().addReference(fragment, this);
    }
    
    protected void addObserver(Disposable disposable) {
        DisposableManager.getInstance().add(this, disposable); // 将订阅者加入到引用管理
    }
    
    @CallSuper
    @Override
    public void release() {
        DisposableManager.getInstance().release(this);
    }
}
