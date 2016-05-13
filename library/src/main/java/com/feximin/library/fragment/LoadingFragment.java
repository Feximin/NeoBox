package com.feximin.library.fragment;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.feximin.library.dialog.BaseDialogFragment;
import com.mianmian.guild.R;
import com.mianmian.guild.util.ScreenUtil;

/**
 * Created by Neo on 16/5/12.
 */
public class LoadingFragment extends BaseDialogFragment {

    private ObjectAnimator[] mAnimators;

    @Override
    protected void initViews() {
        mAnimators = new ObjectAnimator[3];
        View iv0 = getViewById(R.id.iv_loading_0);
        View iv1 = getViewById(R.id.iv_loading_1);
        View iv2 = getViewById(R.id.iv_loading_2);

        int dp30 = ScreenUtil.dpToPx(30);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        mAnimators[0] = ObjectAnimator.ofFloat(iv0, "translationY", 0, -dp30, 0).setDuration(1500);
        mAnimators[1] = ObjectAnimator.ofFloat(iv1, "translationY", 0, -dp30, 0).setDuration(1500);
        mAnimators[2] = ObjectAnimator.ofFloat(iv2, "translationY", 0, -dp30, 0).setDuration(1500);
        for (int i =0 ;i < mAnimators.length; i++){
            mAnimators[i].setInterpolator(interpolator);
            mAnimators[i].setStartDelay(i * 200);
            mAnimators[i].setRepeatCount(Integer.MAX_VALUE);
            mAnimators[i].start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        for (int i = 0; i<mAnimators.length; i++){
            mAnimators[i].cancel();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Translucent);
        dialog.getWindow().setWindowAnimations(R.style.style_dialog_anim_fade);
        return dialog;
    }

    protected void setDialogSize(Dialog dialog){
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.view_loading;
    }
}
