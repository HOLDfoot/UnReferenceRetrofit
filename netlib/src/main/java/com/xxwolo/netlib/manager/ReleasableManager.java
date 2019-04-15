package com.xxwolo.netlib.manager;

import com.xxwolo.netlib.tool.util.NetLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author    ZhuMingren
 * Date      2018/11/30
 * Time      下午1:27
 * DESC      管理可能造成内存泄露的对象
 */
public class ReleasableManager {
    
    protected static ReleasableManager mInstance;
    private Map<Object, List<Releasable>> objectListMap = new HashMap<>();
    
    public static ReleasableManager getInstance() {
        if (mInstance == null) {
            synchronized (ReleasableManager.class) {
                if (mInstance == null) {
                    mInstance = new ReleasableManager();
                }
            }
        }
        return mInstance;
    }
    
    /**
     * 添加可释放对象
     *
     * @param object 可释放对象共有的Tag
     * @param releasable
     */
    public void addReference(Object object, Releasable releasable) {
        List<Releasable> list = objectListMap.get(object);
        if (list == null) {
            list = new ArrayList();
            objectListMap.put(object, list);
        }
        list.add(releasable);
    }
    
    /**
     * 释放掉以object为Tag的Releasable的List集合
     * @param object
     */
    public void releaseReference(Object object) {
        List<Releasable> list = objectListMap.get(object);
        NetLog.V("releaseReference list= " + list);
        if (list != null) {
            for (Releasable releasable : list) {
                releasable.release();
            }
            objectListMap.remove(object);
        }
    }
    
}
