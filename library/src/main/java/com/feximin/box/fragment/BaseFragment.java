package com.feximin.box.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.interfaces.ILifeCycle;
import com.feximin.box.util.LeakHelper;
import com.feximin.box.util.LifeCycleHelper;
import com.feximin.box.util.Tool;
import com.feximin.box.util.ViewSpanner;
import com.feximin.box.util.rx.RxBus;
import com.feximin.box.util.rx.RxBusHelper;
import com.feximin.box.view.TitleBar;

import rx.Subscription;


/**
 * @author Neo
 */
public abstract class BaseFragment extends Fragment implements RxBus.RxBusManager {
	protected View mRootView;

	protected BaseActivity mActivity;
	protected LayoutInflater mInflater;
	protected BaseFragment mFragment;
	protected int mContainerId;
	protected RxBusHelper mRxBusHelper;
	protected LifeCycleHelper mLifeCycleHelper;
	protected TitleBar mTitleBar;
    private String HIDDEN_STATE = "hidden_state";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //http://www.jianshu.com/p/662c46cd3b5f
        if (savedInstanceState != null){
            boolean hidden = savedInstanceState.getBoolean(HIDDEN_STATE);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (hidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRxBusHelper = RxBusHelper.obtain(this).register();
		mLifeCycleHelper = LifeCycleHelper.obtain();
//		if(mOnCreateViewListener != null) mOnCreateViewListener.beforeCreateView();
		mFragment = this;
		mContainerId = initFragmentContainerId();
		mInflater = inflater;
		int layoutResId = getLayoutResId();
		if(layoutResId > 0) mRootView = inflater.inflate(layoutResId, container, false);
		setSoftInputMode();
		beforeInitViews();
		initViews(savedInstanceState);
        if (mOnCreateViewListener != null) mOnCreateViewListener.onCreateView();
        afterInitViews();
		LeakHelper.watch(this);
		return mRootView;
	}

	protected int initFragmentContainerId(){
		return 0;
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

	protected <T extends View> T getViewById(int id, View.OnClickListener listener){
		T t = getViewById(id);
		bindClick(t, listener);
		return t;
	}


	public <T extends View> void bindClick(T v, View.OnClickListener listener){
		if (v != null) v.setOnClickListener(listener);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(HIDDEN_STATE, isHidden());
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

	public void replace(BaseFragment fragment, String...tag){
        FragmentHelper.replace(getChildFragmentManager(), fragment, mContainerId, Tool.getFirstObject(tag));
	}

	protected abstract int getLayoutResId();
}
