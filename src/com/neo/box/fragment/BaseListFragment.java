package com.neo.box.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.neo.box.adapter.BaseListAdapter;
import com.neo.box.task.SimpleTask;
import com.neo.box.view.listview.XListView;
import com.neo.box.view.listview.XListView.IXListViewListener;

public abstract class BaseListFragment<T> extends BaseFragment implements OnItemClickListener, IXListViewListener{

	protected XListView mListView;
	protected BaseListAdapter<T> mAdapter;
	protected boolean mIsRefreshing;
	protected boolean mIsLoadingMore;
	protected boolean mIsFirst;
	protected boolean mIsNeedRefreshOnInit;
	
	public static enum GetDataType{
		REFRESH, LOADMORE
	}
	
	protected void createFragmentView() {
		mRootView = new RelativeLayout(mParentActivity);
		mListView = new XListView(mParentActivity);
		((RelativeLayout)mRootView).addView(mListView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
	public final void initViews(){	
		initSubViews();
		initAdapter();
		onInitAdapterFinished();
	}
	
	protected void onInitAdapterFinished() {
		mListView.setAdapter(mAdapter);
		if(mIsNeedRefreshOnInit){
			refreshOnInit();
		}
		mListView.setOnItemClickListener(this);
	}


	protected abstract void initSubViews();

	
	public void refreshOnInit(){
		onRefresh();
	}
	
	protected List<T> doRefresh() {
		return null;
	}
	
	protected List<T> doLoadMore() {
		return null;
	}
	
	protected void onPreGetData() {
		
	}
	protected void onPostGetData() {
		
	}
	protected void onGetDataFailed() {
		
	}
	protected void doGetData(final GetDataType type) {
		if(mIsRefreshing || mIsLoadingMore){
			if(mIsRefreshing && type == GetDataType.LOADMORE){
				mListView.stopLoadMore();
			}
			if(mIsLoadingMore && type == GetDataType.REFRESH){
				mListView.stopRefresh();
			}
			return;			//只要有一个正在加载，就不去获取
		}
		onPreGetData();
		new SimpleTask<List<T>>() {
			@Override
			protected List<T> executeInBackground() {
				switch(type){
				case REFRESH:
					mIsRefreshing = true;
					return doRefresh();
				case LOADMORE:
					mIsLoadingMore = true;
					return doLoadMore();
				}
				return null;
			}
			
			@Override
			protected void onMyPostExecute(List<T> t) {
				if(t == null){
					onGetDataFailed();
				}else{
					if(type == GetDataType.REFRESH){
						mAdapter.clearAndRefresh(t);
						mListView.stopRefresh();
					}
					if(type == GetDataType.LOADMORE){
						mAdapter.add(t);
						mListView.stopLoadMore();
					}
					if(mIsRefreshing) mIsRefreshing = false;
					if(mIsLoadingMore) mIsLoadingMore = false;
				}
				onPostGetData();
			}
		};
	}
	
	
	public abstract void initAdapter();

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

	@Override
	public void onRefresh() {
		doGetData(GetDataType.REFRESH);
	}

	@Override
	public void onLoadMore() {
		doGetData(GetDataType.LOADMORE);
	}

	@Override
	public int getLayoutResId() {
		return 0;
	}
}
