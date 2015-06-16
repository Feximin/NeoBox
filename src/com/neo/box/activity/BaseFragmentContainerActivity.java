package com.neo.box.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.neo.box.fragment.BaseFragment;
import com.neo.box.tools.FragmentTool;
import com.neo.box.tools.L;
import com.neo.box.tools.Tool;

public final class BaseFragmentContainerActivity extends BaseActivity{
	
	public static final String FRAGMENT_NAME = "FRAGMENT_NAME";
	public static final String BUNDLE = "BUNDLE";
	protected FrameLayout mFragmentContainer;
	protected int mFragmentContainerId;
	@Override
	protected final void createActivityView() {
		mFragmentContainer = new FrameLayout(mActivityThis);
		mFragmentContainerId = Tool.generateViewId();
		mFragmentContainer.setId(mFragmentContainerId);
		setContentView(mFragmentContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	@Override
	public void initViews() {
		String name = getIntent().getStringExtra(FRAGMENT_NAME);
		if(TextUtils.isEmpty(name)){
			finish();
			return;
		}
		try {
			Class<?> clazz = Class.forName(name);
			BaseFragment f = (BaseFragment) clazz.newInstance();
			Bundle bundle = getIntent().getBundleExtra(BUNDLE);
			startFragment(f, false, bundle);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	

	public  <T extends BaseFragment> void startFragment(T f, boolean finish, Bundle...bundles) {
		if(f == null) {
			L.e("Fragment is null");
			return ;
		}
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		Bundle b = Tool.get(bundles);
		f.setArguments(b);
		if(finish && mFragmentManager.getBackStackEntryCount() > 0){
			mFragmentManager.popBackStack();
		}
		transaction.add(mFragmentContainerId, f);
		transaction.addToBackStack(f.getClass().getSimpleName() + f.toString());
		transaction.commit();
	}
	public void startFragment(Class<? extends BaseFragment> clazz, boolean finish, Bundle...bundles) {
		BaseFragment f = null;
		try {
			f = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(f == null) {
			L.e("Fragment newInstance failed");
			return ;
		}
		startFragment(f, finish, bundles);
	}

	@Override
	public final int getLayoutResId() {
		return 0;
	}

}
