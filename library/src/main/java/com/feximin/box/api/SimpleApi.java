package com.feximin.box.api;

import com.feximin.box.util.SingletonFactory;

/**
 * Created by Neo on 16/6/18.
 */

public class SimpleApi extends AbsApi {
    public static SimpleApi getInstance(){
        return SingletonFactory.getInstance(SimpleApi.class);
    }
}
