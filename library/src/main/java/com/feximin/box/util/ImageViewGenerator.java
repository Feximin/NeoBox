package com.feximin.box.util;

import android.content.Context;
import android.widget.ImageView;

import com.feximin.box.view.OverlayImageView;
import com.feximin.box.view.RatioImageView;


/**
 * Created by Neo on 16/3/15.
 */
public class ImageViewGenerator {

    /**
     * 获取CENTER_CROP的ImageView
     * @param context
     * @return
     */
    public static ImageView generateCCImageView(Context context){
        return generateImageView(context, ImageView.ScaleType.CENTER_CROP);
    }

    private static ImageView generateImageView(Context context, ImageView.ScaleType type){
        ImageView img = new ImageView(context);
        img.setScaleType(type);
        return img;
    }
    public static ImageView generateCIImageView(Context context){
        return generateImageView(context, ImageView.ScaleType.CENTER_INSIDE);
    }

    public static OverlayImageView generateCCOverlayImageView(Context context){
        OverlayImageView img = new OverlayImageView(context);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return img;
    }

    public static ImageView generateCCRatioImageView(Context context, float ratio){
        RatioImageView iv = new RatioImageView(context);
        iv.setRatio(ratio);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return iv;
    }

}
