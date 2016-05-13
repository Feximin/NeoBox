package com.feximin.library.util;

import android.os.StrictMode;

import com.mianmian.guild.Config;

/**
 * Created by Neo on 15/11/30.
 */
public class StrictModeUtil {
    public static void init(){
        if(Config.sIsTest){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .penaltyLog()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .detectLeakedSqlLiteObjects()
                    .build());
        }
    }
}
