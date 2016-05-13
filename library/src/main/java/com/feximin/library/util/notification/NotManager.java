package com.feximin.library.util.notification;

import android.app.NotificationManager;
import android.content.Context;

import com.mianmian.guild.util.SingletonFactory;

/**
 * Created by Neo on 16/3/24.
 */
public class NotManager {

    private NotificationManager mNotificationManager;

    private NotManager(){ }

    public NotManager getInstance(Context context){
        NotManager manager = SingletonFactory.getInstance(NotManager.class);
        if (manager.mNotificationManager == null){
            manager.mNotificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }


    public void publish(Not not){
        mNotificationManager.notify(not.getNid(), not.generateNotMeta().build());
    }

    public void cancel(Not noti){

    }
}
