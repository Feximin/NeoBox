package com.feximin.library.adatper;

import android.view.View;
import android.view.ViewGroup;

import com.feximin.library.activity.BaseActivity;

public abstract class BaseInfinityPagerAdapter<T> extends BasePagerAdapter<T> {


	public BaseInfinityPagerAdapter(BaseActivity activity){
		super(activity);

	}

	// 初始化position位置的界面
	@Override
	public Object instantiateItem(ViewGroup v, int position) {
		View child = getView(getRealPosition(position));
		v.addView(child);
		return child;
	}

	@Override
	public int getCount() {
		return mData.size() <= 1 ? mData.size(): Integer.MAX_VALUE;
	}

	public int getRealPosition(int position) {
		int N = mData.size();
		return position % N;
	}

}
