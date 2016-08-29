package com.feximin.box.util;

import android.app.Application;

import com.feximin.box.Box;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Neo on 16/5/7.
 */
public class LeakHelper {

    private static RefWatcher sRefWatcher;
    public static void init(Application context){
        if (Box.sTestMode){
//            sRefWatcher = LeakCanary.install(context);
        }
    }

    public static void watch(Object object){
        if (Box.sTestMode && sRefWatcher != null) {
            sRefWatcher.watch(object);
        }
    }
}
