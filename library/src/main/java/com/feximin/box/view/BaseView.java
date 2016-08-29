package com.feximin.box.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.interfaces.ILifeCycle;
import com.feximin.box.util.LifeCycleHelper;
import com.feximin.box.util.ViewSpanner;
import com.feximin.box.util.glide.GlideHelper;
import com.feximin.box.util.rx.RxBus;
import com.feximin.box.util.rx.RxBusHelper;

import rx.Subscription;

/**
 * @author Neo
 */
public abstract class BaseView implements OnClickListener, ILifeCycle, RxBus.RxBusManager {
	
	protected View mRootView;
	protected LayoutInflater mInflater;
	protected BaseActivity mActivity;
	protected BaseView mThis;
	protected RxBusHelper mRxBusHelper;
	protected LifeCycleHelper mLifeCycleHelper;
	public BaseView(BaseActivity activity) {
		this(activity, null);
	}

	public BaseView(BaseActivity activity, Bundle bundle){
		mThis = this;
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(activity);
		this.mRootView = inflateRootView();
		mRxBusHelper = RxBusHelper.obtain(this);
		mLifeCycleHelper = LifeCycleHelper.obtain();
		beforeInitViews();
		beforeInitViews(bundle);
		initViews();
		if (!mActivity.isFinishing()) afterInitViews();
	}

	protected View inflateRootView(){
        return mInflater.inflate(getLayoutResId(), null);
    }

	@Override
	public void onEvent(Object ev) {

	}

	@Override
	public void addSubscription(Subscription subscription) {
		mRxBusHelper.addSubscription(subscription);
	}

	public void addLifeCycleComponent(ILifeCycle lc){
		mLifeCycleHelper.addLifeCycleComponent(lc);
	}


	public void registerEventBus(){
        mRxBusHelper.register();
	}

	protected void beforeInitViews(){}
	protected void beforeInitViews(Bundle bundle){}
	protected abstract void initViews();
	protected void afterInitViews(){}

	protected abstract int getLayoutResId();
	
	@Override
	public void onClick(View arg0) {
		
	}
	
	public <T extends View> T getViewById(int id){
		return ViewSpanner.getViewById(this, id);
	}

	public <T extends View> T getViewById(int id, OnClickListener listener){
        T t = ViewSpanner.getViewById(this, id);
        t.setOnClickListener(listener);
        return t;
    }

	
	protected <T extends View> T  getViewById( int id, final String methodName, Object... params) {
		return ViewSpanner.getViewById(this, id, methodName, params);
	}

	protected TextView getTextViewById(int id, String text){
		TextView tv = ViewSpanner.getViewById(getView(), id);
		tv.setText(text);
		return tv;
	}

	protected void bindClick(View v, String methodName, Object...params) {
		ViewSpanner.bindClick(v, methodName, this, params);
	}
	@Override
	public void onCreate() {

	}


	public void loadImage(ImageView iv, String url){
		GlideHelper.obtain(mActivity).load(iv, url);
	}

	public void loadCircleImage(ImageView iv, String url){
		GlideHelper.obtain(mActivity).loadCircleImage(iv, url);
	}

	public void loadCircleImage(ImageView iv, int resId){
		GlideHelper.obtain(mActivity).loadCircleImage(iv, resId);
	}

	public void loadImage(ImageView iv, int resId){
		GlideHelper.obtain(mActivity).load(iv, resId);
	}
	@Override
	public void onDestroy() {
        mRxBusHelper.unRegister();
        mLifeCycleHelper.destroy();
	}

	public View getView(){
		return this.mRootView;
	}
	
	
}
