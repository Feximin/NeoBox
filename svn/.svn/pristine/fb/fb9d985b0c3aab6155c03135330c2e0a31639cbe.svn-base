package com.neo.box.fragment;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.neo.box.NeoConstant;
import com.neo.box.R;
import com.neo.box.tools.ScreenTool;
import com.neo.box.tools.Tool;
import com.neo.box.view.TitleBar;
import com.neo.box.view.listview.XListView;

public abstract class BaseListFragmentWithTitle<T> extends BaseListFragment<T> {

	protected TitleBar mTitleBar;
	
	@Override
	protected void initSubViews() {
		mTitleBar = new TitleBar(mParentActivity);
		LayoutParams lpTitle = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenTool.getPxFromDimenRes(R.dimen.default_title_bar_height));
		lpTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		mTitleBar.getView().setId(Tool.generateViewId());
		((RelativeLayout)mRootView).addView(mTitleBar.getView(), lpTitle);
		
		mListView = new XListView(mParentActivity);

		LayoutParams lpListView = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpListView.addRule(RelativeLayout.BELOW, mTitleBar.getView().getId());
		((RelativeLayout)mRootView).addView(mListView, lpListView);
		
	}

}
