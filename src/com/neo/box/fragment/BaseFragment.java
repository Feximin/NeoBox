package com.neo.box.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neo.box.activity.BaseActivity;
import com.neo.box.activity.BaseFragmentContainerActivity;
import com.neo.box.interfaces.InflateViewFromRes;

public abstract class BaseFragment extends Fragment implements InflateViewFromRes{

	protected BaseActivity mParentActivity;
	protected Bundle mBundleArgument;
	protected LayoutInflater mInflater;
	protected View mRootView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mParentActivity = (BaseActivity) getActivity();
		mBundleArgument = getArguments();
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mInflater = inflater;
		createFragmentView();
		initViews();
		return mRootView;
	}
	
	protected void createFragmentView() {
		this.mRootView = mInflater.inflate(getLayoutResId(), null);
	}
	
	public void startFragment(Class<? extends BaseFragment> clazz){
		startFragment(clazz, false);
	}
	public void startFragment(Class<? extends BaseFragment> clazz, Bundle bundle){
		startFragment(clazz, false, bundle);
	}
	
	public void startFragment(Class<? extends BaseFragment> clazz, boolean finish){
		startFragment(clazz, finish, null);
	}
	public void startFragment(Class<? extends BaseFragment> clazz, boolean finish, Bundle bundle){
//		mParentActivity.startFragment(clazz, finish, bundle);
	}
}
