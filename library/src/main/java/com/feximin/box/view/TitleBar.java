package com.feximin.box.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feximin.box.util.Tool;
import com.feximin.box.util.ViewSpanner;
import com.feximin.library.R;


/**
 * Created by Neo on 15/11/19.
 */
public class TitleBar extends RelativeLayout {

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImageButton butLeft;
    public ImageButton butRight;
    public TextView txtTitle;
    public TextView txtRight;
    public View lineBottom;
    public Dot dotRight;
    public TextView tvLeft;
    public ImageView ivCenter;

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
        int leftImgSrc = ta.getResourceId(R.styleable.TitleBar_left_src, 0);
        int rightImgSrc = ta.getResourceId(R.styleable.TitleBar_right_src, 0);
        String titleText = ta.getString(R.styleable.TitleBar_title_text);
        String rightText = ta.getString(R.styleable.TitleBar_right_text);
        String leftText = ta.getString(R.styleable.TitleBar_left_text);
        boolean showLine = ta.getBoolean(R.styleable.TitleBar_show_line, true);
        boolean showDot = ta.getBoolean(R.styleable.TitleBar_show_right_dot, false);
        ta.recycle();
        inflate(getContext(), R.layout.view_title_bar, this);
        butLeft = ViewSpanner.getViewById(this, R.id.but_title_bar_left);
        butRight = ViewSpanner.getViewById(this, R.id.but_title_bar_right);
        txtTitle = ViewSpanner.getViewById(this, R.id.txt_title_bar_title);
        txtRight = ViewSpanner.getViewById(this, R.id.txt_title_bar_right);
        lineBottom = ViewSpanner.getViewById(this, R.id.line_title_bar);
        dotRight = ViewSpanner.getViewById(this, R.id.dot_title_bar_right);
        tvLeft = ViewSpanner.getViewById(this, R.id.txt_title_bar_left);
        this.ivCenter = ViewSpanner.getViewById(this, R.id.iv_title_bar_center);
        if (leftImgSrc > 0) {
            butLeft.setVisibility(View.VISIBLE);
            butLeft.setImageResource(leftImgSrc);
        }

        if (rightImgSrc > 0) {
            butRight.setVisibility(View.VISIBLE);
            butRight.setImageResource(rightImgSrc);
        }

        if (Tool.isNotEmpty(titleText)) {
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText(titleText);
        }
        if (Tool.isNotEmpty(rightText)) {
            txtRight.setVisibility(View.VISIBLE);
            txtRight.setText(rightText);
        }

        if (Tool.isNotEmpty(leftText)){
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(leftText);
        }

        lineBottom.setVisibility(showLine ? View.VISIBLE : View.GONE);
        dotRight.setVisibility(showDot ? View.VISIBLE : View.GONE);
    }

    public void setLineBottom(boolean visibility) {
        this.lineBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void setLeftButDefaultListener(Activity activity){
        this.butLeft.setVisibility(VISIBLE);
        this.butLeft.setImageResource(R.mipmap.img_back);
        this.butLeft.setOnClickListener(v -> activity.finish());
    }

    public void setCenterImage(int srcId){
        this.ivCenter.setVisibility(VISIBLE);
        this.ivCenter.setImageResource(srcId);
    }

    public void setLeftBut(int srcId, OnClickListener listener) {
        butLeft.setVisibility(View.VISIBLE);
        butLeft.setImageResource(srcId);
        butLeft.setOnClickListener(listener);
    }

    public void setRightBut(int srcId, OnClickListener listener) {
        butRight.setVisibility(View.VISIBLE);
        butRight.setImageResource(srcId);
        butRight.setOnClickListener(listener);
    }

    public void setRightBut(OnClickListener listener){
        butRight.setOnClickListener(listener);
    }


    public void setRightText(String text, OnClickListener listener) {
        txtRight.setText(text);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(listener);
    }

    public void setRightTextListener(OnClickListener listener){
        txtRight.setOnClickListener(listener);
    }

    public void setRightText(int textResId, OnClickListener listener) {
        setRightText(Tool.getString(textResId), listener);
    }
    public void setLeftText(String text, OnClickListener listener) {
        tvLeft.setText(text);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setOnClickListener(listener);
    }

    public void setLeftText(int textResId, OnClickListener listener) {
        setLeftText(Tool.getString(textResId), listener);
    }



    public void setTitle(String title) {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(title);
    }

    public void setTitle(int resId) {
        setTitle(Tool.getString(resId));
    }


}
