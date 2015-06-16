package com.neo.box.activity;

import java.util.List;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.neo.box.R;
import com.neo.box.tools.ScreenTool;
import com.neo.box.tools.Tool;
import com.neo.box.view.BaseTabItem;
import com.neo.box.view.NavigationView;
import com.neo.box.view.TabItem;

import de.greenrobot.event.EventBus;

/*
 * 20150528
 */
public abstract class BaseTabActivity extends BaseActivityWithTitle{


	protected NavigationView mNavigationView;
	protected FrameLayout mFragmentContainer;
	
	@Override
	protected View getContentView() {
		RelativeLayout contentView = new RelativeLayout(mActivityThis);
		int containerId = Tool.generateViewId();
		int navId = Tool.generateViewId();
		mNavigationView = new NavigationView(this, containerId);
		mNavigationView.getView().setId(navId);
		LayoutParams lpTab = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenTool.getPxFromDimenRes(R.dimen.default_navigation_height));
		lpTab.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		contentView.addView(mNavigationView.getView(), lpTab);
		
		mFragmentContainer = new FrameLayout(this);
		mFragmentContainer.setBackgroundColor(0xFF000000);
		mFragmentContainer.setId(containerId);
		LayoutParams lpContainer = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpContainer.addRule(RelativeLayout.ABOVE, navId);
		contentView.addView(mFragmentContainer, lpContainer);
	
		return contentView;
	}
	
	@Override
	public final void initViews() {
		mNavigationView.build(initTabs());
	}

	protected abstract List<BaseTabItem> initTabs();
}
