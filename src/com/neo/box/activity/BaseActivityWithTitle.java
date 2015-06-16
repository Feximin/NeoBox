package com.neo.box.activity;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.neo.box.NeoConstant;
import com.neo.box.R;
import com.neo.box.tools.ScreenTool;
import com.neo.box.tools.Tool;
import com.neo.box.view.TitleBar;

/*
 * 最顶部有一个titleBar
 * getLayoutResId 返回的是不包含titlebar的内容布局
 * 20150529
 */
public abstract class BaseActivityWithTitle extends BaseActivity {

	protected TitleBar mTitleBar;
	protected RelativeLayout mRootView;
	
	@Override
	protected void createActivityView() {
		mRootView = new RelativeLayout(this);
		mTitleBar = new TitleBar(this);
		RelativeLayout.LayoutParams lpTitle = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ScreenTool.getPxFromDimenRes(R.dimen.default_title_bar_height));
		lpTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		mTitleBar.getView().setId(Tool.generateViewId());
		mRootView.addView(mTitleBar.getView(), lpTitle);
		View content = getContentView();
		RelativeLayout.LayoutParams lpContent = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpContent.addRule(RelativeLayout.BELOW, mTitleBar.getView().getId());
		mRootView.addView(content, lpContent);
		setContentView(mRootView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	
	protected View getContentView() {
		View view = mInflater.inflate(getLayoutResId(), null);
		return view;
	}
	
	protected void setTitleBarTitle(String title) {
		mTitleBar.setTitle(title);
	}
	
	protected void setTitleBarTitle(int resId) {
		setTitleBarTitle(getString(resId));
	}
	
	@Override
	public int getLayoutResId() {
		return 0;
	}


}
