package com.feximin.box.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.feximin.library.R;

/**
 * Created by Neo on 16/5/12.
 */
public class LoadingFragment1 extends BaseFullScreenDialogFragment {

    private ObjectAnimator mAnimator;

    @Override
    protected void initViews() {
        View view = getViewById(R.id.iv_loading_0);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 1, 3, 1);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 1, 3, 1);
        mAnimator = ObjectAnimator.ofPropertyValuesHolder(view, x, y).setDuration(1500);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(Integer.MAX_VALUE);
        mAnimator.start();
    }

    public static LoadingFragment1 obtain(boolean cancelable){
        LoadingFragment1 dialog = new LoadingFragment1();
        dialog.setCancelable(cancelable);
        return dialog;
    }

    public static LoadingFragment1 obtain(){
        return obtain(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        mAnimator.cancel();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.view_loading_1;
    }
}
