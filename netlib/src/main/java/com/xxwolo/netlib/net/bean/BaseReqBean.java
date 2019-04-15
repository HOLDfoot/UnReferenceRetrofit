package com.xxwolo.netlib.net.bean;

/**
 * description:json 请求公用参数
 */

public class BaseReqBean {
    
    // 基本App参数
    public String api_key;
    public String seed;
    public String hash;
    
    // 附加
    public String version; // 必传
    public String agent; // 必传
    public String uuid;
    public String area; // 必传
    
    // 用户相关
    public String user_id;
    
    /**
     * 无参构造函数
     */
    public BaseReqBean() {
    }
    
    public String getApi_key() {
        return api_key;
    }
    
    public String getSeed() {
        return seed;
    }
    
    public String getHash() {
        return hash;
    }
    
    public String getVersion() {
        return version;
    }
    
    public String getAgent() {
        return agent;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public String getArea() {
        return area;
    }
    
    public String getUser_id() {
        return user_id;
    }
}
