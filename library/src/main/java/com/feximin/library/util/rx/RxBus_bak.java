package com.feximin.library.util.rx;

import com.mianmian.guild.util.SingletonFactory;

import java.util.Map;
import java.util.WeakHashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Neo on 16/4/9.
 * 因为是根据类名来判断是否添加过，因此必须保证每个页面对应的类是不同的
 */
public class RxBus_bak {

    private final Subject<Object, Object> mBus;


    private Map<String, OnEventListener> mOnEventListenerMap;
    private Object mLock = new Object();

    private RxBus_bak(){
        mOnEventListenerMap = new WeakHashMap<>();
        mBus = new SerializedSubject<>(PublishSubject.create());
        mBus.observeOn(AndroidSchedulers.mainThread())
            .subscribe(o -> {
                synchronized (mLock) {
                    for (OnEventListener listener : mOnEventListenerMap.values()) {
                        listener.onEvent(o);
                    }
                }
            });
    }

    public static RxBus_bak getDefault(){
        return SingletonFactory.getInstance(RxBus_bak.class);
    }

    public void post(Object ev){
        if (mOnEventListenerMap.size() > 0 && mBus.hasObservers()) {
            mBus.onNext(ev);
        }
    }

    public void register(OnEventListener listener){
        if (listener == null) return;
        synchronized (mLock) {
            String tag = listener.getClass().getName();
            if (mOnEventListenerMap.containsKey(tag)) {
                mOnEventListenerMap.remove(tag);
            }
            mOnEventListenerMap.put(tag, listener);
        }
    }

    public void unregister(OnEventListener listener){
        synchronized (mLock) {

            String tag = listener.getClass().getName();
            if (listener != null && mOnEventListenerMap.containsKey(tag)) {
                mOnEventListenerMap.remove(tag);
            }
        }
    }


    public static interface OnEventListener{
        void onEvent(Object ev);
    }


}
