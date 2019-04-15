package app.xiaoming.retrofitunreference.bean;

import com.xxwolo.netlib.net.bean.BaseRespBean;

import java.util.List;

/**
 * Author    ZhuMingren
 * Date      2019/4/15
 * Time      下午7:23
 * DESC      RetrofitUnReference
 */
public class WeekWeatherBean extends BaseRespBean {
    
    private String success;
    private List<Result> result;
    
    public void setSuccess(String success) {
        this.success = success;
    }
    
    public String getSuccess() {
        return success;
    }
    
    public void setResult(List<Result> result) {
        this.result = result;
    }
    
    public List<Result> getResult() {
        return result;
    }
}
