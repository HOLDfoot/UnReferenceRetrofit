package app.xiaoming.retrofitunreference;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xxwolo.netlib.base.BasePresenterActivity;
import com.xxwolo.netlib.net.retrofit.listener.OnSubscriberNextListener;

import app.xiaoming.retrofitunreference.bean.WeekWeatherBean;
import app.xiaoming.retrofitunreference.presenter.WeatherPresenter;

public class MainActivity extends BasePresenterActivity {
    
    private WeatherPresenter weatherPresenter;
    private TextView textView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherPresenter = new WeatherPresenter(getThisActivity());
        textView = findViewById(R.id.tv_today);
        Button button = findViewById(R.id.btn_weather);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather();
            }
        });
    }
    
    private void getWeather() {
        weatherPresenter.getWeather(true, new OnSubscriberNextListener<WeekWeatherBean>() {
            @Override
            public void onNext(WeekWeatherBean weekWeatherBean) {
                textView.setText("今日天气:\n" + JSON.toJSONString(weekWeatherBean.getResult().get(0)));
            }
            
            @Override
            public void onFailure(String msg) {
            
            }
        });
    }
}
