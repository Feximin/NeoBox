package com.feximin.library.util;

import android.content.Context;
import android.widget.ImageView;

import com.mianmian.guild.view.OverlayImageView;
import com.mianmian.guild.view.RatioImageView;

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
        ImageView img = new ImageView(context);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return img;
    }

    public static OverlayImageView getCCOverlayImageView(Context context){
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
