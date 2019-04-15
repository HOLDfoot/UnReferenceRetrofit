package com.xxwolo.netlib.net.retrofit.subscriber;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 管理scheduler线程
 * Created by ChuyaoShi on 16/9/13.
 */

public class SchedulersCompat {

    private static final ObservableTransformer ioTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(io.reactivex.Observable observable) {
            return observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), false, 100);
        }
    };
    
    public static <T> ObservableTransformer<T, T> applyIoSchedulers() {
        return (ObservableTransformer<T, T>) ioTransformer;
    }

    private static final ObservableTransformer backTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(io.reactivex.Observable observable) {
            return observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io(),false, 100);
        }
    };

    public static <T> ObservableTransformer<T, T> applyBackSchedulers() {
        return (ObservableTransformer<T, T>) backTransformer;
    }

}
