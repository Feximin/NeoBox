package com.feximin.box.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.feximin.box.util.Tool;
import com.feximin.library.R;


/**
 * Created by Neo on 16/3/22.
 */
public class RatioHelper {
    private float mRatio = -1;
    private View mView;

    public static RatioHelper obtain(View view, AttributeSet attrs){
        RatioHelper helper = new RatioHelper();
        helper.mView = view;
        if (attrs != null) {
            int[] attrsArray = new int[]{R.attr.ratio};
            Context context = view.getContext();
            TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
            helper.mRatio = ta.getFloat(0, -1);
            ta.recycle();
        }
        return helper;
    }

    public static RatioHelper obtain(TypedArray ta, int index){
        RatioHelper helper = new RatioHelper();
        ta.getFloat(index, -1);
        return helper;
    }


    public int getRatioHeightMeasureSpec(int size, int widthMeasureSpec, int heightMeasureSpec){
        if (mRatio > 0){
            int width = Tool.getDefaultSize(size, widthMeasureSpec);
            int height = (int) (width / mRatio);
            int spec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            return spec;
        }else {
            return heightMeasureSpec;
        }
    }

    public int getRatioHeight(View view){
        int height = view.getMeasuredHeight();
        if (mRatio > 0) height = (int) (view.getMeasuredWidth() / mRatio);
        return height;
    }

    public float getRatio(){
        return mRatio;
    }

    public void setRatio(float ratio){
        if (ratio > 0 && ratio != mRatio){
            mRatio = ratio;
            mView.requestLayout();
        }
    }


}
