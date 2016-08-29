package com.feximin.box.util.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.widget.TextView;

import com.feximin.box.Constant;
import com.feximin.box.util.SingletonFactory;
import com.feximin.box.util.Tool;
import com.feximin.box.util.sr.SoftReferenceUtil;

/**
 * Created by Neo on 16/7/15.
 */

public class DrawableHelper extends SoftReferenceUtil<Integer, Drawable> {
    private DrawableHelper(){}

    public static DrawableHelper obtain(){
        return SingletonFactory.getInstance(DrawableHelper.class);
    }

    public void setDrawableRight(TextView tv, int resId){
        setDrawableRight(tv, resId, 1);
    }

    public void setDrawableRight(TextView tv, int resId, float scale ){
        setDrawable(tv, 0, 0, resId, 0, scale);
    }

    public void setDrawableLeft(TextView tv, int resId){
        setDrawableLeft(tv, resId, 1);
    }

    public void setDrawableLeft(TextView tv, int resId, float scale){
        setDrawable(tv, resId, 0, 0, 0, scale);
    }

    public void setDrawable(TextView tv, int left, int top, int right, int bottom, float scale){
        Drawable dLeft = left > 0 ? get(left) : null;
        Drawable dTop = top > 0 ? get(top) : null;
        Drawable dRight = right > 0 ? get(right) : null;
        Drawable dBottom = bottom > 0 ? get(bottom) : null;
        refreshWidthAndHeight(dLeft, scale);
        refreshWidthAndHeight(dTop, scale);
        refreshWidthAndHeight(dRight, scale);
        refreshWidthAndHeight(dBottom, scale);
        tv.setCompoundDrawables(dLeft, dTop, dRight, dBottom);
    }

    private void refreshWidthAndHeight(Drawable drawable, float scale){
        if (drawable == null) return;
        int w = (int) (drawable.getIntrinsicWidth() * scale);
        int h = (int) (drawable.getIntrinsicHeight() * scale);
        drawable.setBounds(0, 0, w, h);
    }

    @Override
    public Drawable generate(Integer integer) {
        return Tool.getDrawable(integer);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable getSizeDrawable(int w, int h){

        ShapeDrawable divider = new ShapeDrawable();
        if (w > 0) divider.setIntrinsicWidth(w);
        if (h > 0) divider.setIntrinsicHeight(h);
        divider.getPaint().setColor(Constant.c_transparent);
        return divider;
    }
}
