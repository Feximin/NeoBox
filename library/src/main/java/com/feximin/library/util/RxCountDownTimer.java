package com.feximin.library.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 16/4/9.
 */
public class RxCountDownTimer {

    private Subscription subscription;
    private int mInterval;
    private int mPeriod;

    public RxCountDownTimer(int period, int interval){
        this.mInterval = interval;
        this.mPeriod = period;
    }

    public Subscription start(){
        cancel();
        subscription = Observable.interval(mInterval, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aLong -> onInternalTick(aLong));
        return subscription;
    }

    private void onInternalTick(long times){
        long timePass = mInterval * times;
        if (timePass < mPeriod){
            onTick(timePass);
        }else{
            cancel();
            onFinish();
        }
    }

    public void onTick(long timePass){

    }

    public void onFinish(){

    }

    public void cancel(){
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

}
