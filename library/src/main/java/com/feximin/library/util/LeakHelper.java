package com.feximin.library.util;

import android.app.Application;

import com.mianmian.guild.Config;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Neo on 16/5/7.
 */
public class LeakHelper {

    private static RefWatcher sRefWatcher;
    public static void init(Application context){
        if (Config.sIsTest){
            sRefWatcher = LeakCanary.install(context);
        }
    }

    public static void watch(Object object){
        if (Config.sIsTest && sRefWatcher != null) {
            sRefWatcher.watch(object);
        }
    }
}
