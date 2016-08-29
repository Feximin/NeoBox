package com.feximin.box.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.feximin.box.util.ScreenUtil;
import com.feximin.library.R;


/**
 * Created by Neo on 15/11/19.
 */
public class Dot extends View {

    private final int DEFAULT_RADIUS = ScreenUtil.dpToPx(3);
    private final int DEFAULT_COLOR = 0xFFFF0000;
    private int mDotColor;
    private int mRadius;             //默认的半径是3dp
    private Paint mPaint;

    public Dot(Context context){
        this(context, null);
    }
    public Dot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public Dot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs != null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.Dot);
            mDotColor = ta.getColor(R.styleable.Dot_dot_color, DEFAULT_COLOR);
            mRadius = ta.getDimensionPixelSize(R.styleable.Dot_dot_radius, DEFAULT_RADIUS);
            ta.recycle();
        }
        if (mRadius < 0) mRadius = DEFAULT_RADIUS;
        mPaint = new Paint();
        mPaint.setColor(mDotColor);
        mPaint.setAntiAlias(true);
    }

    public void setDotColor(int color) {
        if(mDotColor == color) return;
        mDotColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mRadius * 2, mRadius * 2);
    }

    public void setDotRadius(int px) {
        if (px != mRadius && px > 0) {
            this.mRadius = px;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }
}
