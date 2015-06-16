package com.neo.box.view;

import android.app.Activity;

import com.neo.box.interfaces.InflateViewFromCode;

public abstract class BaseViewFromCode extends BaseView implements InflateViewFromCode {

	public BaseViewFromCode(Activity activity) {
		super(activity);
	}

	@Override
	public void initViews() {
		inflateViewFromCode();
	}
	
	@Override
	public int getLayoutResId() {
		return -1;
	}

}
