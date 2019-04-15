package com.xxwolo.netlib.net.bean;

/**
 * 全局回复基类bean
 * 返回基础数据
 */
public class BaseRespBean {

    public int code;
    public String message;
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
