package com.feximin.box.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.feximin.box.util.Tool;
import com.feximin.library.R;

/**
 * Created by Neo on 16/3/14.
 * 其中的直接子view都是以方格的形式出现的
 */
public class SquareViewBed extends ViewGroup {

    protected int mColumn = 3;            //列数        ,最少是一列
    protected int mSpacing;               //每个view之间的间隙
    protected int mItemWidth;             //每个view的宽度
    protected boolean mKeepMinHeight;     //表示当没有子view的时候 是否保持最小高度

    public SquareViewBed(Context context) {
        this(context, null);
    }

    public SquareViewBed(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareViewBed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SquareViewBed);
            mColumn = ta.getInt(R.styleable.SquareViewBed_android_numColumns, 3);
            mSpacing = ta.getDimensionPixelSize(R.styleable.SquareViewBed_spacing, 0);
            mKeepMinHeight = ta.getBoolean(R.styleable.SquareViewBed_keepMinHeight, false);
            ta.recycle();
        }
        if (mColumn < 1) mColumn = 1;
        if (mSpacing < 0) mSpacing = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int validWidth = width - getPaddingLeft() - getPaddingRight();
        mItemWidth = (validWidth - (mColumn - 1) * mSpacing) / mColumn;
        int height;
        if (childCount > 0){
                if (childCount <= mColumn){
                    height = mItemWidth;
                }else {
                    int mod = childCount % mColumn;
                    int row = childCount / mColumn;
                    if (mod > 0) row ++;
                    height = mItemWidth * row + (row - 1) * mSpacing;
                }
                height = height + getPaddingTop() + getPaddingBottom();
        }else{
            if (mKeepMinHeight){
                height = mItemWidth + getPaddingTop() + getPaddingBottom();
            }else{
                height = Tool.getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i<childCount; i++){
            View child = getChildAt(i);
            int row = i / mColumn;
            int column = i % mColumn;

            int left = (mItemWidth + mSpacing) * column + getPaddingLeft();
            int top = (mItemWidth + mSpacing) * row + getPaddingTop();
            int right = left + mItemWidth;
            int bottom = top + mItemWidth;
            child.layout(left, top, right, bottom);
            if (child instanceof ViewGroup){
                child.requestLayout();
            }
        }
    }

    public abstract static class Adapter{
        public abstract int getCount();
        public abstract View getView(int position);
    }
}
