package com.feximin.library.presenter;

import android.widget.ImageView;

import com.feximin.library.activity.BaseActivity;
import com.mianmian.guild.interfaces.ILifeCycle;
import com.mianmian.guild.util.glide.GlideHelper;

/**
 * Created by Neo on 16/2/16.
 */
public abstract class AbstractPresenter implements ILifeCycle {
    protected BaseActivity mActivity;
    public AbstractPresenter(BaseActivity context){
        this.mActivity = context;
    }

    protected void loadImage(ImageView img, String url){
        GlideHelper.obtain(img.getContext()).loadImage(img, url);
    }

    protected void loadCircleImage(ImageView img, String url){
        GlideHelper.obtain(img.getContext()).loadCircleImage(img, url);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy(){}


}
