package com.feximin.box.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.fragment.BaseFragment;
import com.feximin.box.util.Tool;
import com.feximin.library.R;

import java.util.List;

public class NavigationView extends LinearLayout implements View.OnClickListener{

    private int mMaxItemSize = 6;

	private List<BaseTabItem> mTabList;
	
	private int mLastTabIndex = -1;
	
	private FragmentManager mFragmentManager;

	private int mFragmentContainerId;

	public NavigationView(Context context) {
		this(context, null);
	}

	public NavigationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        if (attrs != null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NavigationView);
            mFragmentContainerId = ta.getResourceId(R.styleable.NavigationView_containerId, 0);
            ta.recycle();
        }
        if(mFragmentContainerId == 0){
            throw new IllegalArgumentException("container_id not assigned");
        }
        mFragmentManager = ((BaseActivity)getContext()).getSupportFragmentManager();
	}

    public void setMaxItemSize(int size){
        if (size > 0) this.mMaxItemSize = size;
    }

	public void build(List<BaseTabItem> tabs){
        if (Tool.isEmpty(tabs)) throw new IllegalArgumentException("tabs can not be empty or null !!");
        int N = tabs.size();
        if (N > mMaxItemSize) throw new IllegalArgumentException(String.format("tabs size can not more than -->%s", N));
		this.mTabList = tabs;
		for(int i = 0; i<N; i++){
			LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
			final int index = i;
			BaseTabItem item = tabs.get(i);
			final View view = item.getView();
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					switchTab(index);
				}
			});
			addView(view, lp);
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
				mTabList.get(i).setSelected(index == i);
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
	
}
