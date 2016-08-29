package com.feximin.box.util.translate;

import com.feximin.box.api.SimpleApi;
import com.feximin.box.util.SingletonFactory;

/**
 * Created by Neo on 16/6/18.
 */

public class ShanBayTranslator implements Translator {
    private String HOST = "https://api.shanbay.com/bdc/search/?word=";
    @Override
    public String translate(String word) {
        try {
            return SimpleApi.getInstance().callSimpleGet(String.format("%s%s", HOST, word));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ShanBayTranslator getInstance(){
        return SingletonFactory.getInstance(ShanBayTranslator.class);
    }
}
