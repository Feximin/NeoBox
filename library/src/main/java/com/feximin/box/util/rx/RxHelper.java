package com.feximin.box.util.rx;


import com.feximin.box.util.Tool;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Neo on 16/3/14.
 */
public class RxHelper {

    public static <T> Observable<T> ioOb(T t){
        return Observable.just(t).subscribeOn(Schedulers.io());
    }

    private static final Object sEmptyObject = new Object();
    public static Observable<Object> emptyIoOb(){
        return Observable.just(sEmptyObject).subscribeOn(Schedulers.io());
    }

    public static void emptyIoMap(Func func){
        emptyIoOb().map(o -> {
            func.call();
            return null;
        }).subscribe(o -> {}, throwable -> {});
    }

    public static Observable<Object> emptyMainThreadOb(){
        return Observable.just(sEmptyObject).observeOn(AndroidSchedulers.mainThread());
    }

    public static void mainThread(Func func){
        if (Tool.isMainThread()){
            func.call();
        }else {
            Observable.just(sEmptyObject)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .map(o -> {
                        func.call();
                        return null;
                    })
                    .subscribe(o -> {}, throwable -> {});
        }
    }



    public static <T> Transformer<T, T> transform(){
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void unSubscribe(Subscription subscription){
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    public static Observable<Long> timer(int millis){
        return Observable.timer(millis, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread());
    }



}
