package com.neo.box.fragment;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.neo.box.R;
import com.neo.box.tools.ScreenTool;
import com.neo.box.tools.Tool;
import com.neo.box.view.TitleBar;

public abstract class BaseFragmentWithTitle extends BaseFragment {

	protected TitleBar mTitleBar;
	protected View mContentView;
	@Override
	protected void createFragmentView() {
		mRootView = new RelativeLayout(mParentActivity);
		mTitleBar = new TitleBar(mParentActivity);
		RelativeLayout.LayoutParams lpTitle = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ScreenTool.getPxFromDimenRes(R.dimen.default_title_bar_height));
		lpTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		mTitleBar.getView().setId(Tool.generateViewId());
		((RelativeLayout)mRootView).addView(mTitleBar.getView(), lpTitle);
		
		mContentView = mInflater.inflate(getLayoutResId(), null);
		RelativeLayout.LayoutParams lpContent = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpContent.addRule(RelativeLayout.BELOW, mTitleBar.getView().getId());
		((RelativeLayout)mRootView).addView(mContentView, lpContent);
	}
	@Override
	public void initViews() {
	}
}
