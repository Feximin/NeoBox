package com.feximin.library.activity;

import android.os.Bundle;
import android.view.View;

import com.mianmian.guild.R;
import com.mianmian.guild.view.TitleBar;

/**
 * Created by Neo on 16/3/17.
 */
public class BaseWebViewActivityWithTitle extends BaseWebViewActivity {

    protected TitleBar mTitleBar;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitleBar = getViewById(R.id.title_bar);
    }

    protected void setLeftButDefaultListener(){
        mTitleBar.butLeft.setVisibility(View.VISIBLE);
        mTitleBar.butLeft.setImageResource(R.mipmap.img_back);
        mTitleBar.butLeft.setOnClickListener(v -> finish());
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

    protected void setTitleText(String title){
        mTitleBar.txtTitle.setVisibility(View.VISIBLE);
        mTitleBar.txtTitle.setText(title);
    }

    protected void setTitleText(int resId){
        setTitleText(getString(resId));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_common_webview_with_title;
    }

}
