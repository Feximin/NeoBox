package com.neo.box.view;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.neo.box.R;
import com.neo.box.fragment.BaseFragment;

public class NavigationView extends BaseViewFromCode implements View.OnClickListener{

	private List<BaseTabItem> mTabList;
	
	private int mLastTabIndex = -1;
	
	private FragmentManager mFragmentManager;
	
	private OnTabSelectedListener mOnTabSelectedListener;
	
	
	private int mFragmentContainerId;
	public NavigationView(Activity activity, int containerId) {
		super(activity);
		this.mFragmentContainerId = containerId;
		mFragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
	}
	
	public void setBackgroundColor(int color){
		mRootView.setBackgroundColor(color);
	}
	
	public void setBackgroundRes(int resId){
		mRootView.setBackgroundResource(resId);
	}
	
	@Override
	public void inflateViewFromCode() {
		mRootView = new LinearLayout(mActivity);
		setBackgroundColor(0x10FF0000);
		mRootView.setClickable(false);
		((LinearLayout)mRootView).setOrientation(LinearLayout.HORIZONTAL);
	}
	public void build(List<BaseTabItem> tabs){
		if(tabs == null || tabs.size() < 1 || tabs.size() > 6){
			throw new RuntimeException("选项卡参数非法");
		}
		this.mTabList = tabs;
		LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		int tabSize = tabs.size();
		for(int i = 0; i<tabSize; i++){
			final int index = i;
			BaseTabItem item = tabs.get(i);
			final View view = item.getView();
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					switchTab(index);
				}
			});
			((LinearLayout)mRootView).addView(view, lp);
		}
		switchTab(0);
	}
	
	public void switchTab(int index){

		if(index != mLastTabIndex){
			FragmentTransaction transaction = mFragmentManager.beginTransaction();
			hideAll(transaction);
			BaseFragment fragment = mTabList.get(index).getFragment();
			if(fragment == null){
				fragment = mTabList.get(index).instance();
				transaction.add(mFragmentContainerId, fragment);
			}else{
				transaction.show(fragment);
			}
			transaction.commit();
			
			int size = mTabList.size();
			for(int i = 0; i<size; i++){
				if(index == i){
					mTabList.get(i).setSelected(true);
					if(mOnTabSelectedListener != null){
						mOnTabSelectedListener.onTabSelected(index);
					}
				}else{
					mTabList.get(i).setSelected(false);
				}
			}
			mLastTabIndex = index;
		}
	}

	private void hideAll(FragmentTransaction transaction){
		for(BaseTabItem item: mTabList){
			if(item.getFragment() != null){
				transaction.hide(item.getFragment());
			}
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getTag() != null && v.getTag() instanceof Integer){
			switchTab((Integer) v.getTag());
		}
	}
	
	public void setOnTabSelectedListener(OnTabSelectedListener listener){
		this.mOnTabSelectedListener = listener;
	}
	//选项卡选中之后的操作
	public interface OnTabSelectedListener{
		public void onTabSelected(int index);
	}

}
