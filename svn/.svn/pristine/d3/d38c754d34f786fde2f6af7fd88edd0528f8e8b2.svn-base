package com.neo.box.adapter;

import java.util.ArrayList;
import java.util.List;

import com.neo.box.tools.Tool;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<T> extends BaseAdapter {

	private List<T> mData;
	private LayoutInflater mInflater;
	private Activity mActivity;
	public BaseListAdapter(Activity activity){
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(activity);
		this.mData = new ArrayList<T>();
	}
	
	public void clearAndRefresh(List<T> list){
		if(Tool.isListEmpty(list)) return;
		mData.clear();
		add(list);
	}
	
	public void add(T t){
		if(t == null) return;
		mData.add(t);
		notifyDataSetChanged();
	}
	
	public void add(List<T> list){
		if(Tool.isListEmpty(list)) return;
		mData.addAll(list);
		notifyDataSetChanged();
	}
	
	
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public T getItem(int position) {
		if(position >= 0 && position < mData.size()-1){
			return mData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(getItemLayoutResId(), parent);
		}
		inflateViews();
		return convertView;
	}

	protected abstract void inflateViews();
	protected abstract int getItemLayoutResId();
}
