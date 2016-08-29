package com.feximin.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Neo on 16/3/14.
 * 图片宽高比固定
 */
public class RatioImageView extends ImageView {
    private RatioHelper mRatioHelper;
    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRatioHelper = RatioHelper.obtain(this, attrs);
    }

    public void setRatio(float ratio){
        mRatioHelper.setRatio(ratio);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        heightMeasureSpec = mRatioHelper.getRatioHeightMeasureSpec(getSuggestedMinimumWidth(), widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), mRatioHelper.getRatioHeight(this));

    }

}
