package com.feximin.library.fragment;

import android.os.Bundle;
import android.view.View;

import com.mianmian.guild.R;
import com.mianmian.guild.view.TitleBar;

/**
 * Created by Neo on 16/2/19.
 */
public abstract class BaseFragmentWithPagerAndTitle extends BaseFragmentWithPager {
    protected TitleBar mTitleBar;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitleBar = getViewById(R.id.title_bar);
        bindClick(mTitleBar.txtRight, "onRightTextClick");
    }

    protected void onRightTextClick(){}

    protected void setLeftButDefaultListener(){
        setLeftBut(R.mipmap.img_back, "finish");
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
        setRightText(getString(textResId), methodName);

    }
    protected void setRightTextListener(String methodName){
        bindClick(mTitleBar.txtRight, methodName);
    }

    protected void setRightTextVisibility(boolean visibility){
        mTitleBar.txtRight.setVisibility(visibility?View.VISIBLE:View.GONE);
    }


    protected void setRightButVisibility(boolean visibility){
        mTitleBar.butRight.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    protected void setLeftButVisibility(boolean visibility){
        mTitleBar.butLeft.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    protected void setTitleText(String title){
        mTitleBar.txtTitle.setVisibility(View.VISIBLE);
        mTitleBar.txtTitle.setText(title);
    }

    protected void setTitleText(int resId){
        setTitleText(getString(resId));
    }

}
