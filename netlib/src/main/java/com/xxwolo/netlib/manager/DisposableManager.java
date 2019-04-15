package com.xxwolo.netlib.manager;

import com.xxwolo.netlib.tool.util.NetLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Author    ZhuMingren
 * Date      2018/11/30
 * Time      下午2:29
 * DESC
 */
public class DisposableManager {
    
    protected static DisposableManager mInstance;
    private Map<Object, List<Disposable>> objectListMap = new HashMap<>();
    
    public static DisposableManager getInstance() {
        if (mInstance == null) {
            synchronized (DisposableManager.class) {
                if (mInstance == null) {
                    mInstance = new DisposableManager();
                }
            }
        }
        return mInstance;
    }
    
    /**
     * 添加可释放对象
     *
     * @param object 可释放对象共有的Tag
     * @param disposable
     */
    public void add(Object object, Disposable disposable) {
        List<Disposable> list = objectListMap.get(object);
        if (list == null) {
            list = new ArrayList();
            objectListMap.put(object, list);
        }
        list.add(disposable);
    }
    
    /**
     * 释放掉以object为Tag的Disposable的List集合
     * @param object
     */
    public void release(Object object) {
        List<Disposable> list = objectListMap.get(object);
        NetLog.V("releaseReference list= " + list);
        if (list != null) {
            for (Disposable disposable : list) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
            list.clear();
            objectListMap.remove(object);
        }
    }
    
}
