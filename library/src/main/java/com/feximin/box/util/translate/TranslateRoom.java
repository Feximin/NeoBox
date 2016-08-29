package com.feximin.box.util.translate;

import com.feximin.box.exceptions.BaseException;
import com.feximin.box.util.SingletonFactory;
import com.feximin.box.util.Tool;
import com.feximin.box.util.rx.RxHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 16/6/18.
 */

public class TranslateRoom {

    private Map<String, List<TranslateListener>> mQueryWordMap;
    private Translator mTranslator;
    private TranslateRoom(){
        mQueryWordMap = new HashMap<>(5);
    }

    public static TranslateRoom getInstance(){
        if (INSTANCE == null){
            throw new BaseException("pls call init before getInstance");
        }
        return INSTANCE;
    }

    private static TranslateRoom INSTANCE;

    public static void init(Translator translator){
        if (INSTANCE == null){
            INSTANCE = SingletonFactory.getInstance(TranslateRoom.class);
            INSTANCE.mTranslator = translator;
        }
    }

    public static void initShanBay(){
        init(SingletonFactory.getInstance(ShanBayTranslator.class));
    }


    public void translate(String word, TranslateListener listener){
        if (mQueryWordMap.containsKey(word)){
            List<TranslateListener> list = mQueryWordMap.get(word);
            if (Tool.isNotEmpty(list) && !list.contains(listener)){
                list.add(listener);
            }
        }else{
            List<TranslateListener> list = new ArrayList<>(1);
            list.add(listener);
            mQueryWordMap.put(word, list);
            RxHelper.emptyIoOb().map(o -> mTranslator.translate(word))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        for (TranslateListener l : mQueryWordMap.get(word)){
                            l.onFinish(word, result);
                        }
                        mQueryWordMap.remove(word);
                    });
        }

    }
}
