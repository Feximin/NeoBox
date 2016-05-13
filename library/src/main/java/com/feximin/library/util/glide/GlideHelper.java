package com.feximin.library.util.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.mianmian.guild.util.SingletonFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neo on 16/3/11.
 */
public class GlideHelper {

    private GlideHelper(){}
    public static GlideHelper obtain(Context context){
        GlideHelper helper = SingletonFactory.getInstance(GlideHelper.class);
//        if (helper.mContext == null){
//            helper.mContext = context.getApplicationContext();
//        }
        //不能只使用application,因为不同的activity fragment 的生命周期会与之关联
        //还有一个操蛋的问题，activity被回收的情况
        helper.mContext = context;
        return helper;
    }

    private Context mContext;
    private RequestManager mRequestManager;
    private Map<String, Transformation> mTransformationMap = new HashMap<>(1);

    public void loadImage(ImageView img, String url){
        loadImage(img, url, 0);
    }

    public void loadImage(ImageView img, String url, int placeHolder){
        guaranteeGlide();
        DrawableRequestBuilder builder = mRequestManager.load(url).crossFade();
        if (placeHolder > 0) builder.placeholder(placeHolder);
        builder.into(img);
    }

    public void loadCircleImage(ImageView iv, int resId){
        loadCircleImage(iv, resId, 0);
    }

    public void loadCircleImage(ImageView iv, int resId, int placeHolder){
        guaranteeGlide();
        Transformation transformation = ensureCircleStrokeTransformation(0, 0);
        DrawableRequestBuilder builder = mRequestManager.load(resId).bitmapTransform(transformation).crossFade();
        if (placeHolder > 0) builder.placeholder(placeHolder);
        builder.into(iv);
    }

    public void loadCircleImage(ImageView img, String url){
        loadCircleImage(img, url, 0);
    }

    public void loadCircleImage(ImageView img, String url, int placeHolder){
        loadCircleStrokeImage(img, url, 0, 0);
    }

    public void loadCircleStrokeImage(ImageView img, String url, int strokeWidth, int strokeColor){
        loadCircleStrokeImage(img, url, strokeWidth, strokeColor, 0);
    }


    public void loadCircleStrokeImage(ImageView img, String url, int strokeWidth, int strokeColor, int placeHolder){
        guaranteeGlide();
        Transformation transformation = ensureCircleStrokeTransformation(strokeWidth, strokeColor);
        DrawableRequestBuilder builder = mRequestManager.load(url).bitmapTransform(transformation).crossFade();
        if (placeHolder > 0) builder.placeholder(placeHolder);
        builder.into(img);
    }


    public void loadImage(ImageView img, int resId){
        loadImage(img, resId, 0);
    }
    public void loadImage(ImageView img, @DrawableRes int resId, @DrawableRes int placeHolder){
        guaranteeGlide();
        DrawableRequestBuilder builder = mRequestManager.load(resId).crossFade();
        if (placeHolder > 0) builder.placeholder(placeHolder);
        builder.into(img);
    }

    public void loadRoundCornerImage(ImageView img, String url, int corner){
        loadRoundCornerImage(img, url, corner, 0);
    }

    public void loadRoundCornerImage(ImageView img, String url, int corner, int placeHolder){
        loadRoundCornerStrokeImage(img, url, corner, 0, 0, placeHolder);
    }

    public void loadRoundCornerStrokeImage(ImageView img, String url, int corner, int strokeWidth, int strokeColor){
        loadRoundCornerStrokeImage(img, url, corner, strokeWidth, strokeWidth, 0);
    }

    public void loadRoundCornerStrokeImage(ImageView img, String url, int corner, int strokeWidth, int strokeColor, int placeHolder){
        guaranteeGlide();
        Transformation transformation = ensureRoundCornerStrokeTransformation(corner, strokeWidth, strokeColor);
        DrawableRequestBuilder builder = mRequestManager.load(url).bitmapTransform(transformation).crossFade();
        if (placeHolder > 0) builder.placeholder(placeHolder);
        builder.into(img);
    }

    private Transformation ensureRoundCornerStrokeTransformation(int corner, int strokeWidth, int strokeColor){
        String key = String.format("RoundCornerStrokeTransformation_%s_%s_%s", corner, strokeWidth, strokeColor);
        Transformation transformation = mTransformationMap.get(key);
        if (transformation == null){
            transformation = new RoundedCornersStrokeTransformation(mContext, corner, strokeWidth, strokeColor);
            mTransformationMap.put(key, transformation);
        }
        return transformation;
    }

    private Transformation ensureCircleStrokeTransformation(int strokeWidth, int strokeColor){
        String key = String.format("CircleStrokeTransformation_%s_%s", strokeWidth, strokeColor);
        Transformation transformation = mTransformationMap.get(key);
        if (transformation == null){
            transformation = new CropCircleStrokeTransformation(mContext, strokeWidth, strokeColor);
            mTransformationMap.put(key, transformation);
        }
        return transformation;
    }

    private void guaranteeGlide(){
//        if(mRequestManager == null){
//            synchronized (GlideHelper.class){
//                if(mRequestManager == null){
                    mRequestManager = Glide.with(mContext);
//                }
//            }
//        }
    }
}
