package com.feximin.box.util.translate;

import com.feximin.box.exceptions.BaseException;

/**
 * Created by Neo on 16/6/17.
 */

public class TranslateHelper {

    private static TranslateHelper INSTANCE = new TranslateHelper();
    private Config config;
    public static TranslateHelper getInstance(){
        if (INSTANCE == null){
            throw new BaseException("pls call init before getInstance() !!");
        }
        return INSTANCE;
    }

    public static void init(Config config){
        if (config == null){
            throw new BaseException("config can not be null !!");
        }else{
            if (INSTANCE == null){
                synchronized (TranslateHelper.class){
                    if (INSTANCE == null){
                        INSTANCE = new TranslateHelper();
                        INSTANCE.config = config;
                    }
                }
            }
        }
    }

    public void translate(String word){

    }

}
