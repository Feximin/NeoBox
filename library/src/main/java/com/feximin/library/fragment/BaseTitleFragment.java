package com.feximin.library.fragment;

import android.os.Bundle;
import android.view.View;

import com.feximin.library.fragment.BaseFragment;
import com.mianmian.guild.R;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.view.TitleBar;

/**
 * Created by Neo on 15/11/17.
 */
public abstract class BaseTitleFragment extends BaseFragment {


    protected TitleBar mTitleBar;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTitleBar = getViewById(R.id.title_bar);
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
    protected void setRightText(String text, View.OnClickListener listener){
        mTitleBar.txtRight.setText(text);
        mTitleBar.txtRight.setVisibility(View.VISIBLE);
        mTitleBar.txtRight.setOnClickListener(listener);
    }
    protected void setRightText(int textResId, View.OnClickListener listener){
        setRightText(Tool.getString(textResId), listener);
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



}
