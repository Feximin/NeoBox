package com.feximin.box.util.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.feximin.box.util.SingletonFactory;
import com.feximin.box.util.Tool;
import com.feximin.library.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Neo on 16/3/11.
 */
public class GlideHelper {

    private GlideHelper(){}
    public static GlideHelper obtain(Context context){
        if (context == null) throw new IllegalArgumentException("context can not be null !!");
        GlideHelper helper = SingletonFactory.getInstance(GlideHelper.class);
//        if (helper.mContext == null){
//            helper.mContext = context.getApplicationContext();
//        }
        //不能只使用application,因为不同的activity fragment 的生命周期会与之关联
        //还有一个操蛋的问题，activity被回收的情况
        if (helper.mContext == null || helper.mContext != context){
            helper.mContext = context;
            helper.mIsNewContext = true;
        }
        helper.guaranteeGlide();
        return helper;
    }



    private Context mContext;
    private boolean mIsNewContext;
    private RequestManager mRequestManager;
    private CropCircleTransformation mCropCircleTransformation;
    private CropSquareTransformation mCropSquareTransformation;
    private Map<String, Transformation> mTransformationMap = new HashMap<>(1);

    public void load(ImageView img, String url){
        load(img, url, 0);
    }

    public void load(ImageView img, String url, int placeHolder){
        loadB(img, url, placeHolder);
    }

    public void loadB(ImageView img, String url, int placeHolder, Transformation...ts){
        guaranteeGlide();
        DrawableRequestBuilder builder = mRequestManager.load(url).crossFade().centerCrop();
        if (Tool.isNotEmpty(ts)){
            for (Transformation transformation : ts){
                builder.bitmapTransform(transformation);
            }
        }
        if (placeHolder > 0){
            builder.placeholder(placeHolder);
            builder.error(placeHolder);
        }
        builder.into(img);
    }

    public void loadCircleImage(ImageView iv, int resId){
        loadCircleImage(iv, resId, 0);
    }

    public void loadCircleImage(ImageView iv, int resId, int placeHolder){
        ensureCircleTransformer();
        loadB(iv, Tool.getDrawableUriString(resId), placeHolder, mCropCircleTransformation);
    }

    public GlideHelper circle(){
        ensureCircleTransformer();
        return this;
    }

    public void loadCircleImage(ImageView img, String url){
        loadCircleImage(img, url, 0);
    }
    public void loadSquareImage(ImageView img, String url){
        loadSquareImage(img, url, R.mipmap.img_default_square);
    }

    public void loadSquareImage(ImageView img, String url, int placeHolder){
        ensureSquareTransformer();
        loadB(img, url, placeHolder, mCropSquareTransformation);
    }
    public void loadCircleImage(ImageView img, String url, int placeHolder){
        ensureCircleTransformer();
        loadB(img, url, placeHolder, mCropCircleTransformation);
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


    //加载本地的图片的时候,不需要有placeHolder
    public void load(ImageView img, int resId){
        load(img, resId, 0);
    }
    public void load(ImageView img, @DrawableRes int resId, @DrawableRes int placeHolder){
        loadB(img, Tool.getDrawableUriString(resId), placeHolder);
    }

    public void loadRoundCornerImage(ImageView img, String url, int corner){
        loadRoundCornerImage(img, url, corner, 0);
    }


    //圆锥的时候是有问题的,有时间好好研究一下
    public void loadRoundCornerImage(ImageView img, String url, int corner, int placeHolder){
        ensureSquareTransformer();
        Transformation roundCorner = ensureRoundCornerTransformation(corner);
        loadB(img, url, placeHolder, mCropSquareTransformation, roundCorner);
    }

    public void loadRoundCornerStrokeImage(ImageView img, String url, int corner, int strokeWidth, int strokeColor){
        loadRoundCornerStrokeImage(img, url, corner, strokeWidth, strokeWidth, 0);
    }

    public void loadRoundCornerStrokeImage(ImageView img, String url, int corner, int strokeWidth, int strokeColor, int placeHolder){
        guaranteeGlide();
        Transformation transformation = ensureRoundCornerStrokeTransformation(corner, strokeWidth, strokeColor);
        DrawableRequestBuilder builder = mRequestManager.load(url).centerCrop().bitmapTransform(transformation).crossFade();
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

    private Transformation ensureRoundCornerTransformation(int corner){
        String key = String.format("RoundCornerTransformation_%s", corner);
        Transformation transformation = mTransformationMap.get(key);
        if (transformation == null){
            transformation = new RoundedCornersTransformation(mContext, corner);
            mTransformationMap.put(key, transformation);
        }
        return transformation;

    }

    private void guaranteeGlide(){
//        if(mRequestManager == null){
//            synchronized (GlideHelper.class){
//                if(mRequestManager == null){
        if (mRequestManager == null || mIsNewContext) {
            mRequestManager = Glide.with(mContext);
        }
//                }
//            }
//        }
    }

    private void ensureCircleTransformer(){
        if (mCropCircleTransformation == null || mIsNewContext){
            mCropCircleTransformation = new CropCircleTransformation(mContext);
        }
    }
    private void ensureSquareTransformer(){
        if (mCropSquareTransformation == null || mIsNewContext){
            mCropSquareTransformation = new CropSquareTransformation(mContext);
        }
    }
}
