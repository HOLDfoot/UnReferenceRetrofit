package app.xiaoming.retrofitunreference;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.xxwolo.netlib.NetLibConfig;

import java.util.HashMap;

/**
 * Author    ZhuMingren
 * Date      2019/4/15
 * Time      下午8:00
 * DESC      RetrofitUnReference
 */
public class BaseApplication extends MultiDexApplication {
    
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        // 给netlib传递初始化公共参数
        NetLibConfig.setDebug(this, BuildConfig.DEBUG, "ZMR");
        NetLibConfig.initBase("http://api.k780.com:88/", new HashMap<String, String>());
        NetLibConfig.init(null, new HashMap<String, String>());
    }
}
