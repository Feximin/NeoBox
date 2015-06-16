package com.neo.box.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neo.box.interfaces.InflateViewFromRes;
import com.neo.box.tools.ViewTool;

public abstract class BaseView implements InflateViewFromRes {

	protected ViewGroup mRootView;
	protected Activity mActivity;
	protected LayoutInflater mInflater;
	
	
	public BaseView(Activity activity){
		this.mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		inflateView();
	}
	
	protected void inflateView() {
		if(getLayoutResId() > 0) mRootView = (ViewGroup) mInflater.inflate(getLayoutResId(), null);
		initViews();
	}
	
	public View getView(){
		return mRootView;
	}

	protected <T extends View> T getViewById (int id, String...methodName) {
		return ViewTool.getViewById(this, id, methodName);
	}
	
	protected void setOnViewClickListener(View view, String methodName) {
		ViewTool.setOnViewClickListener(view, mActivity, methodName);
	}
	
	protected void getImageViewById(int id, int resId, String...m) {
		ViewTool.getImageView(id, resId, mActivity, m);
	}
	
}
