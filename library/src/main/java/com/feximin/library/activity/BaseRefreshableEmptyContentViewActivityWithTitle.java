package com.feximin.library.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.feximin.library.fragment.BaseFragment;
import com.feximin.library.view.BaseView;
import com.mianmian.guild.R;

/**
 * Created by Neo on 16/4/19.
 */
public abstract class BaseRefreshableEmptyContentViewActivityWithTitle extends BaseRefreshLayoutActivityWithTitle {

    private final String TAG = getClass().getName();

    private FrameLayout mContentViewContainer;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        this.mContentViewContainer = getViewById(R.id.content_view_container);
    }

    protected void replace(BaseFragment fragment){
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment oldF = manager.findFragmentByTag(TAG);
                if (oldF != null){
                    transaction.remove(oldF);
                }
                transaction.replace(R.id.container_fragment, fragment, TAG);
                transaction.commitAllowingStateLoss();
            }
        }
    }
    private BaseView mOldBaseView;
    protected void replace(BaseView baseView){
        if (mOldBaseView != null) mOldBaseView.onDestroy();
        mContentViewContainer.removeAllViews();
        mContentViewContainer.addView(baseView.getView(), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mOldBaseView = baseView;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment_container_refresh_able_and_title;
    }
}
