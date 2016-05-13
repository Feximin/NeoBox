package com.feximin.library.util.rx;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Neo on 16/5/9.
 */
public class RxBusHelper {
    private CompositeSubscription mCompositeSubscription;
    private RxBus.RxBusManager mRxBusManager;

    private RxBusHelper(){}

    public static RxBusHelper obtain(RxBus.RxBusManager manager){
        RxBusHelper helper = new RxBusHelper();
        helper.mRxBusManager = manager;
        return  helper;
    }

    public void register(){
        RxBus.getDefault().register(mRxBusManager);
    }

    public void unSubscribe(){
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) mCompositeSubscription.unsubscribe();
        if (mCompositeSubscription != null) mCompositeSubscription = null;
    }


    public void unRegister(){
        unSubscribe();
        RxBus.getDefault().unregister(mRxBusManager);
    }

    public void addSubscription(Subscription subscription){
        if (mCompositeSubscription == null) mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscription);
    }
}
