package com.feximin.box.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.feximin.library.R;


/**
 * Created by Neo on 16/3/12.
 * 点击之后有一个遮罩效果
 */
public class OverlayImageView extends ImageView {
    private int mOverlayColor = 0x60000000;
    public OverlayImageView(Context context) {
        this(context, null);
    }

    public OverlayImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverlayImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.OverlayImageView);
            mOverlayColor = ta.getColor(R.styleable.OverlayImageView_overlay_color, 0x60000000);
            ta.recycle();
        }
        setClickable(true);         //必须得是clickable，否则不会执行到action_up
    }

    public void setOverlayColor(int color){
        this.mOverlayColor = color;
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        Drawable d = getDrawable();
        if (d != null && !d.isStateful()) {
            if (pressed){
                d.setColorFilter(new PorterDuffColorFilter(mOverlayColor, PorterDuff.Mode.DARKEN));
            }else{
                d.clearColorFilter();
            }
            invalidate();
        }
    }
}
