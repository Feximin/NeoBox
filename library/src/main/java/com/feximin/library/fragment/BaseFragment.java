package com.feximin.library.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.feximin.library.activity.BaseActivity;
import com.mianmian.guild.interfaces.ILifeCycle;
import com.mianmian.guild.util.LeakHelper;
import com.mianmian.guild.util.LifeCycleHelper;
import com.mianmian.guild.util.ViewSpanner;
import com.mianmian.guild.util.rx.RxBus;
import com.mianmian.guild.util.rx.RxBusHelper;

import rx.Subscription;


/**
 * @author Neo
 */
public abstract class BaseFragment extends Fragment implements RxBus.RxBusManager {
	protected View mRootView;

	protected BaseActivity mActivity;
	protected LayoutInflater mInflater;
	protected BaseFragment mFragment;
	protected int CONTAINER_ID;

	protected RxBusHelper mRxBusHelper;
	protected LifeCycleHelper mLifeCycleHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRxBusHelper = RxBusHelper.obtain(this);
		mLifeCycleHelper = LifeCycleHelper.obtain();
//		if(mOnCreateViewListener != null) mOnCreateViewListener.beforeCreateView();
		mFragment = this;
		CONTAINER_ID = mActivity.getFragmentContainerId();
		mInflater = inflater;
		int layoutResId = getLayoutResId();
		if(layoutResId > 0) mRootView = inflater.inflate(layoutResId, container, false);
		setSoftInputMode();
		beforeInitViews();
		initViews(savedInstanceState);
		afterInitViews();
		if (mOnCreateViewListener != null) mOnCreateViewListener.onCreateView();
		LeakHelper.watch(this);
		return mRootView;
	}

	@Override
    public void addSubscription(Subscription subscription){
		mRxBusHelper.addSubscription(subscription);
    }

	protected void addLifeCycleComponent(ILifeCycle lc){
		mLifeCycleHelper.addLifeCycleComponent(lc);
	}
	
	public View getRootView(){
		return mRootView;
	}
	protected void setSoftInputMode() {
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}
	protected void setSoftInputMode(int mode) {
		getActivity().getWindow().setSoftInputMode(mode);
	}

	protected <T extends View> T getViewById(int id) {
		return ViewSpanner.getViewById(this, id);
	}
	protected <T extends View> T getViewById(int id, String methodName, Object... params) {
		return ViewSpanner.getViewById(this, id, methodName, params);
	}
	
	protected void bindClick(View v, String methodName) {
		ViewSpanner.bindClick(v, methodName, this);
	}
	protected abstract void initViews(Bundle savedInstanceState);
	
	protected void afterInitViews() {
		
	}

	protected void beforeInitViews(){

	}

	protected void startActivity(Class<? extends Activity> clazz){
		startActivity(new Intent(mActivity, clazz));
	}

	@Override
	public void onEvent(Object ev) {

	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
        mRxBusHelper.unRegister();
        mLifeCycleHelper.destroy();
	}

	public static interface OnCreateViewListener{
		void onCreateView();
	}

	private OnCreateViewListener mOnCreateViewListener;
	public void setOnCreateViewListener(OnCreateViewListener listener){
		this.mOnCreateViewListener = listener;
	}

	protected abstract int getLayoutResId();
}
