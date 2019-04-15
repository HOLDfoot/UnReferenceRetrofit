package app.xiaoming.retrofitunreference.presenter;

import android.content.Context;

import com.xxwolo.netlib.net.retrofit.listener.OnSubscriberNextListener;
import com.xxwolo.netlib.net.retrofit.subscriber.BaseSubscriber;
import com.xxwolo.netlib.net.retrofit.subscriber.ProgressSubscriber;
import com.xxwolo.netlib.net.retrofit.subscriber.SchedulersCompat;

import app.xiaoming.retrofitunreference.bean.WeekWeatherBean;
import app.xiaoming.retrofitunreference.net.IPresenter;

/**
 * Author    ZhuMingren
 * Date      2019/4/15
 * Time      下午7:41
 * DESC      RetrofitUnReference
 */
public class WeatherPresenter extends IPresenter {
    
    public WeatherPresenter(Context context) {
        super(context);
    }
    
    public void getWeather(OnSubscriberNextListener nextListener) {
        BaseSubscriber subscriber = new ProgressSubscriber<WeekWeatherBean>(mContext, nextListener);
        iService.getWeather()
                .compose(SchedulersCompat.<WeekWeatherBean>applyIoSchedulers())
                .subscribe(subscriber);
        addObserver(subscriber);
    }
    
    public void getWeather(boolean progress, OnSubscriberNextListener nextListener) {
        getDataWith(iService.getWeather(), progress, true, true, nextListener);
    }
    
}
