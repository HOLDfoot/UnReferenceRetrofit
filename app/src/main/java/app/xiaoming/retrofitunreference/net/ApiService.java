package app.xiaoming.retrofitunreference.net;

import app.xiaoming.retrofitunreference.bean.WeekWeatherBean;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 网络请求
 */

public interface ApiService {
    
    @GET("/?app=weather.future&weaid=1&&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json")
    Observable<WeekWeatherBean> getWeather();
    //Observable<WeekWeatherBean> getWeather(@Query("app") String app, @Query("weaid") int weaid, @Query("appkey")  int appkey, String sign, String json);
}















