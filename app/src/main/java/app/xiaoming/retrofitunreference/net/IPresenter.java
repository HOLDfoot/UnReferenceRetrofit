package app.xiaoming.retrofitunreference.net;


import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.xxwolo.netlib.net.bean.BaseRespBean;
import com.xxwolo.netlib.net.retrofit.RetrofitUtil;
import com.xxwolo.netlib.manager.DisposableManager;
import com.xxwolo.netlib.manager.ReferenceManager;
import com.xxwolo.netlib.manager.Releasable;
import com.xxwolo.netlib.net.retrofit.listener.OnSubscriberNextListener;
import com.xxwolo.netlib.net.retrofit.subscriber.BaseSubscriber;
import com.xxwolo.netlib.net.retrofit.subscriber.CommonSubscriber;
import com.xxwolo.netlib.net.retrofit.subscriber.ProgressSubscriber;
import com.xxwolo.netlib.net.retrofit.subscriber.SchedulersCompat;

import java.lang.ref.WeakReference;

import app.xiaoming.retrofitunreference.bean.WeekWeatherBean;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 基础Presenter
 */
public abstract class IPresenter<T extends BaseRespBean> implements Releasable {
    
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
    
    /**
     *
     * @param observable
     * @param progress 对话框是否可以取消
     * @param cancelable progress为true的时候是否可以取消进度条
     * @param reference 是否加入到引用管理
     * @param nextListener 网络请求回掉监听器
     */
    protected void getDataWith(Observable<T> observable, boolean progress, boolean cancelable, boolean reference, OnSubscriberNextListener nextListener) {
        BaseSubscriber subscriber;
        if (progress) {
            subscriber = new ProgressSubscriber<WeekWeatherBean>(mContext, cancelable, nextListener);
        } else {
            subscriber = new CommonSubscriber(mContext, nextListener);
        }
        observable.compose(SchedulersCompat.<T>applyIoSchedulers())
                .subscribe(subscriber);
        if (reference) {
            addObserver(subscriber);
        }
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
