package com.feximin.box.adatper;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.util.Tool;
import com.feximin.box.util.glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePagerAdapter<T> extends PagerAdapter {

	protected List<T> mData;
	protected LayoutInflater mInflater;
	protected BaseActivity mActivity;

	public BasePagerAdapter(BaseActivity activity){
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(activity);
		mData = new ArrayList<>();
	}
	// 销毁position位置的界面
	@Override
	public void destroyItem(ViewGroup v, int position, Object arg2) {
		v.removeView((View) arg2);
	}

	public T getItem(int position){
		return mData.get(position);
	}

	public void add(T t){
		if(t == null) return;
		mData.add(t);
		notifyDataSetChanged();
	}

	public void add(List<T> data){
		if(Tool.isEmpty(data)) return;
		mData.addAll(data);
		notifyDataSetChanged();
	}

	public void clearAndAddData(List<T> data){
		boolean needNotify = false;
		if(Tool.isNotEmpty(mData)){
			mData.clear();
			needNotify = true;
		}
		if(Tool.isNotEmpty(data)){
			mData.addAll(data);
			needNotify = true;
		}
		if(needNotify) notifyDataSetChanged();
	}
	// 获取当前窗体界面数
	@Override
	public int getCount() {
		return mData.size();
	}

	// 初始化position位置的界面
	@Override
	public Object instantiateItem(ViewGroup v, int position) {
		View child = getView(position);
		v.addView(child);
		return child;
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View v, Object arg1) {
		return v == arg1;
	}

	protected View getView(int position){
		View view = inflateView(position);
		fillViewContent(view, position);
		return view;
	}

	protected View inflateView(int position){
		View view = mInflater.inflate(getItemLayoutResId(position), null);
		return view;
	}

	protected void loadImage(ImageView img, String url){
		GlideHelper.obtain(mActivity).load(img, url);
	}

	protected void loadCircleImage(ImageView img, String url){
		GlideHelper.obtain(mActivity).loadCircleImage(img, url);
	}

	protected abstract void fillViewContent(View view, int position);
	protected abstract int getItemLayoutResId(int position);
	protected int getViewType(int position){
		return 0;
	}
}
