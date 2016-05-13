package com.feximin.library.view;

import android.view.View;

import com.feximin.library.activity.BaseActivity;
import com.feximin.library.view.BaseView;
import com.mianmian.guild.R;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.ViewSpanner;
import com.mianmian.guild.view.TitleBar;

/**
 * Created by Neo on 16/4/19.
 */
public abstract class BaseViewWithTitle extends BaseView {
    public BaseViewWithTitle(BaseActivity activity) {
        super(activity);
    }
    protected TitleBar mTitleBar;

    @Override
    protected void initViews() {
        initTitleBar();
        bindClick(mTitleBar.txtRight, "onRightTextClick");
    }

    protected void initTitleBar(){
        mTitleBar = ViewSpanner.getViewById(mActivity, R.id.title_bar);
    }

    protected void onRightTextClick(){}

    protected void setLeftButDefaultListener(){
        mTitleBar.butLeft.setVisibility(View.VISIBLE);
        mTitleBar.butLeft.setOnClickListener(v -> mActivity.finish());
    }

    protected void setLeftBut(int srcId, String methodName){
        mTitleBar.butLeft.setVisibility(View.VISIBLE);
        mTitleBar.butLeft.setImageResource(srcId);
        bindClick(mTitleBar.butLeft, methodName);
    }

    protected void setRightBut(int srcId, String methodName){
        mTitleBar.butRight.setVisibility(View.VISIBLE);
        mTitleBar.butRight.setImageResource(srcId);
        bindClick(mTitleBar.butRight, methodName);
    }

    protected void setRightText(String text, String methodName){
        mTitleBar.txtRight.setVisibility(View.VISIBLE);
        mTitleBar.txtRight.setText(text);
        setRightTextListener(methodName);
    }

    protected void setRightText(int textResId, String methodName){
        setRightText(Tool.getString(textResId), methodName);

    }
    protected void setRightTextListener(String methodName){
        bindClick(mTitleBar.txtRight, methodName);
    }

    protected void setTitleText(String title){
        mTitleBar.txtTitle.setVisibility(View.VISIBLE);
        mTitleBar.txtTitle.setText(title);
    }

    protected void setTitleText(int resId){
        setTitleText(Tool.getString(resId));
    }


}
