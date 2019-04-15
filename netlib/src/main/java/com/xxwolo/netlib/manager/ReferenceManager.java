package com.xxwolo.netlib.manager;

/**
 * Author    ZhuMingren
 * Date      2018/11/30
 * Time      下午2:29
 * DESC
 */
public class ReferenceManager extends ReleasableManager {
    
    public static ReferenceManager getInstance() {
        if (mInstance == null) {
            synchronized (ReferenceManager.class) {
                if (mInstance == null) {
                    mInstance = new ReferenceManager();
                }
            }
        }
        return (ReferenceManager) mInstance;
    }
    
/*    public void addPresenter(IPresenter iPresenter, Releasable releasable) {
        Context context = (Context) iPresenter.mContext.get();
        if (context != null) {
            addReference(context, releasable);
        }
    }*/
    
}
