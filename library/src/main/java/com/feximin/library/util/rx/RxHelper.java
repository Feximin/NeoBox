package com.feximin.library.util.rx;

import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Neo on 16/3/14.
 */
public class RxHelper {

    public static <T> Observable<T> emptyNetOb(T t){
        return Observable.just(t).subscribeOn(Schedulers.io());
    }

    private static final Object sEmptyObject = new Object();
    public static Observable<Object> emptyNetOb(){
        return Observable.just(sEmptyObject).subscribeOn(Schedulers.io());
    }

    public static Observable<Object> emptyMainThreadOb(){
        return Observable.just(sEmptyObject).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Transformer<T, T> transform(){
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
