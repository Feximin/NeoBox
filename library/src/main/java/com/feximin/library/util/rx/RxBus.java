package com.feximin.library.util.rx;

import com.mianmian.guild.util.SingletonFactory;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.WeakHashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Neo on 16/4/9.
 * 因为是根据类名来判断是否添加过，因此必须保证每个页面对应的类是不同的
 */
public class RxBus {

    private final Subject<Object, Object> mBus;


    private Map<String, SoftReference<RxBusManager>> mSoftOnEventListenerMap;
    private Object mLock = new Object();

    private RxBus(){
        mSoftOnEventListenerMap = new WeakHashMap<>();
        mBus = new SerializedSubject<>(PublishSubject.create());
        mBus.observeOn(AndroidSchedulers.mainThread())
            .subscribe(o -> {
                synchronized (mLock) {
                    for (SoftReference<RxBusManager> sr : mSoftOnEventListenerMap.values()) {
                        if (sr != null){
                            RxBusManager listener = sr.get();
                            if (listener != null){
                                listener.onEvent(o);
                            }
                        }
                    }
                }
            });
    }

    public static RxBus getDefault(){
        return SingletonFactory.getInstance(RxBus.class);
    }

    public void post(Object ev){
        if (mSoftOnEventListenerMap.size() > 0 && mBus.hasObservers()) {
            mBus.onNext(ev);
        }
    }

    public void register(RxBusManager listener){
        if (listener == null) return;
        synchronized (mLock) {
            String tag = listener.getClass().getName();
            SoftReference<RxBusManager> sr = new SoftReference<>(listener);
            mSoftOnEventListenerMap.put(tag, sr);
        }
    }
//    public void register(RxBusManager listener){
//        if (listener == null) return;
//        synchronized (mLock) {
//            String tag = listener.getClass().getName();
//            if (mOnEventListenerMap.containsKey(tag)) {
//                mOnEventListenerMap.remove(tag);
//            }
//            mOnEventListenerMap.put(tag, listener);
//        }
//    }

    public void unregister(RxBusManager listener){
        if (listener == null) return;
        synchronized (mLock) {
            String tag = listener.getClass().getName();
            mSoftOnEventListenerMap.remove(tag);
        }
    }


    public static interface RxBusManager  {
        void addSubscription(Subscription subscription);
        void onEvent(Object ev);
    }


}
