package com.feximin.box.fragment;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.feximin.box.util.ScreenUtil;
import com.feximin.library.R;

/**
 * Created by Neo on 16/5/12.
 */
public class LoadingFragment extends BaseFullScreenDialogFragment {

    private ObjectAnimator[] mAnimators;

    @Override
    protected void initViews() {
        mAnimators = new ObjectAnimator[3];
        View iv0 = getViewById(0);
        View iv1 = getViewById(1);
        View iv2 = getViewById(2);

        int dp30 = ScreenUtil.dpToPx(30);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        mAnimators[0] = ObjectAnimator.ofFloat(iv0, "translationY", 0, -dp30, 0).setDuration(1500);
        mAnimators[1] = ObjectAnimator.ofFloat(iv1, "translationY", 0, -dp30, 0).setDuration(1500);
        mAnimators[2] = ObjectAnimator.ofFloat(iv2, "translationY", 0, -dp30, 0).setDuration(1500);
        for (int i =0 ;i < mAnimators.length; i++){
            mAnimators[i].setInterpolator(interpolator);
            mAnimators[i].setStartDelay(i * 300);
            mAnimators[i].setRepeatCount(Integer.MAX_VALUE);
            mAnimators[i].start();
        }
    }

    public static LoadingFragment obtain(boolean cancelable){
        LoadingFragment dialog = new LoadingFragment();
        dialog.setCancelable(cancelable);
        return dialog;
    }

    public static LoadingFragment obtain(){
        return obtain(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        for (int i = 0; i<mAnimators.length; i++){
            mAnimators[i].cancel();
        }
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.view_loading;
    }
}
