package com.feximin.library.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.feximin.library.activity.BaseActivity;
import com.mianmian.guild.interfaces.ILifeCycle;
import com.mianmian.guild.util.LifeCycleHelper;
import com.mianmian.guild.util.ViewSpanner;
import com.mianmian.guild.util.glide.GlideHelper;
import com.mianmian.guild.util.rx.RxBus;
import com.mianmian.guild.util.rx.RxBusHelper;

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
		mThis = this;
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(activity);
		this.mRootView = inflateRootView();
		mRxBusHelper = RxBusHelper.obtain(this);
		mLifeCycleHelper = LifeCycleHelper.obtain();
		beforeInitViews();
		initViews();
		afterInitViews();
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

	public void addLifeCycle(ILifeCycle lc){
		mLifeCycleHelper.addLifeCycleComponent(lc);
	}

	public void registerEventBus(){
        mRxBusHelper.register();
	}

	protected void beforeInitViews(){}
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
		GlideHelper.obtain(mActivity).loadImage(iv, url);
	}

	public void loadCircleImage(ImageView iv, String url){
		GlideHelper.obtain(mActivity).loadCircleImage(iv, url);
	}

	public void loadCircleImage(ImageView iv, int resId){
		GlideHelper.obtain(mActivity).loadCircleImage(iv, resId);
	}

	public void loadImage(ImageView iv, int resId){
		GlideHelper.obtain(mActivity).loadImage(iv, resId);
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
