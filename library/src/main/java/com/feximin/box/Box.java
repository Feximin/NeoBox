package com.feximin.box;

import android.content.Context;

/**
 * Created by Neo on 16/4/14.
 */
public class Box {
    public static Context sApp;
    public static boolean sTestMode;
    public static void initialize(Context context, boolean test){
        if (sApp == null){
            sApp = context.getApplicationContext();
        }
        sTestMode = test;
    }

    public static void setServerUrl(String url){
        Constant.SERVER_URL = url;
    }
}
