package com.feximin.library.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.mianmian.guild.util.glide.GlideHelper;

/**
 * Created by Neo on 16/2/16.
 */
public abstract class BasePresenter<T> {
    private Context mContext;
    public BasePresenter(Context context){
        this.mContext = context;
    }

    protected void loadImage(ImageView img, String url){
        GlideHelper.obtain(img.getContext()).loadImage(img, url);
    }

    public abstract void setupContent(View convertView, T t);

    protected void loadCircleImage(ImageView img, String url){
        GlideHelper.obtain(img.getContext()).loadCircleImage(img, url);
    }

    public void onDestroy(){}

}
