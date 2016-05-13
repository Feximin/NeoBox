package com.feximin.library.activity;

import android.os.Bundle;
import android.view.View;

import com.mianmian.guild.R;
import com.mianmian.guild.view.TitleBar;

/**
 * Created by Neo on 15/11/18.
 * 可以下拉刷新的list
 * 泛型数据类型
 */
public abstract class BaseRefreshableListActivityWithTitle<T> extends BaseRefreshableListActivity<T>{

    protected TitleBar mTitleBar;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTitleBar = getViewById(R.id.title_bar);
        super.initViews(savedInstanceState);
        bindClick(mTitleBar.txtRight, "onRightTextClick");
    }

    protected void onRightTextClick(){}

    protected void setLeftButDefaultListener(){
        mTitleBar.butLeft.setVisibility(View.VISIBLE);
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
        return R.layout.common_listview_refreshable_with_title;
    }

}
