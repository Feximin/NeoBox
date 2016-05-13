package com.feximin.library.util.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.mianmian.guild.R;

/**
 * Created by Neo on 16/3/24.
 */
public class NotMeta {
    int nid;
    long when = System.currentTimeMillis();
    int smallIcon = R.mipmap.ic_launcher;
    boolean silence;
    boolean autoCancel = true;
    String content;
    String ticker;
    String title;
    Context context;
    Bitmap largeIcon;
    Class<?> targetClass;
    Bundle bundle;


    public NotMeta nid(int id){
        this.nid = id;
        return this;
    }

    public NotMeta autoCancel(boolean b){
        this.autoCancel = b;
        return this;
    }

    public NotMeta bundle(Bundle bundle){
        this.bundle = bundle;
        return this;
    }

    public NotMeta target(Class<?> targetClass){
        this.targetClass = targetClass;
        return this;
    }

    public NotMeta smallIcon(int resId){
        this.smallIcon = resId;
        return this;
    }

    public NotMeta largeIcon(Bitmap bmp){
        this.largeIcon = bmp;
        return this;
    }

    public NotMeta when(long when){
        this.when = when;
        return this;
    }

    public NotMeta context(Context context){
        this.context = context;
        return this;
    }

    public NotMeta title(String title){
        this.title = title;
        return this;
    }

    public NotMeta content(String content){
        this.content = content;
        return this;
    }

    public NotMeta ticker(String sticker){
        this.ticker = sticker;
        return this;
    }

    public NotMeta silence(boolean b){
        this.silence = b;
        return this;
    }

    public Notification build(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context)
            .setContentText(content)
            .setContentTitle(title)
            .setTicker(ticker)
            .setSmallIcon(smallIcon)
            .setLargeIcon(largeIcon)
            .setWhen(when)
            .setAutoCancel(autoCancel);
        Intent intent = new Intent(context, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (largeIcon == null) largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        if (bundle != null) intent.putExtra("bundle", bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, nid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        if (!silence) builder.setDefaults(Notification.DEFAULT_ALL);

        return builder.build();
    }
}
