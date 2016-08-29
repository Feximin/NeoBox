package com.feximin.box.activity;

import android.os.Bundle;

import com.feximin.box.view.BaseTabItem;
import com.feximin.box.view.NavigationView;
import com.feximin.library.R;

import java.util.List;


/*
 * 20150528
 */
public abstract class BaseTabActivity extends BaseActivity{


	protected NavigationView mNavigationView;

	@Override
	public void initViews(Bundle savedInstanceState) {
		this.mNavigationView = getViewById(R.id.nav_view);
		mNavigationView.build(initTabs());
	}

	protected abstract List<BaseTabItem> initTabs();
}
