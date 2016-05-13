package com.feximin.library.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.mianmian.guild.App;

/**
 * Created by Neo on 16/5/4.
 */
public class ClipboardHelper {
    private ClipboardManager mClipboardManager;
    private ClipboardHelper(){
        this.mClipboardManager = (ClipboardManager) App.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public static ClipboardHelper getInstance(){
        return SingletonFactory.getInstance(ClipboardHelper.class);
    }

    public void plainText(String label, String text){
        ClipData clipData = ClipData.newPlainText(label, text);
        this.mClipboardManager.setPrimaryClip(clipData);
    }


}
