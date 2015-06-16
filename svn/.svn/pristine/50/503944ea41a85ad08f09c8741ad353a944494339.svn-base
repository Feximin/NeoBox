package com.neo.box.activity;

import com.neo.box.fragment.BaseFragment;
import com.neo.box.interfaces.InflateViewFromRes;
import com.neo.box.tools.FragmentTool;
import com.neo.box.tools.L;
import com.neo.box.tools.Tool;
import com.neo.box.view.TitleBar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

/*
 * view 完全由getLayoutResId控制
 */
public abstract class BaseActivity extends FragmentActivity implements InflateViewFromRes{
	
	protected Activity mActivityThis;
	protected LayoutInflater mInflater;
	protected FragmentManager mFragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivityThis = this;
		this.mInflater = LayoutInflater.from(this);
		this.mFragmentManager = getSupportFragmentManager();
		onBeforeSetContentView();
		createActivityView();
		initViews();
	}
	
	protected void createActivityView() {
		setContentView(getLayoutResId());
	}
	
	protected void onBeforeSetContentView() {}
	
	
	protected void startFragmentInNewActivity() {
		
	}
	
}
