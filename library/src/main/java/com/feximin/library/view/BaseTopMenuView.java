package com.feximin.library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.feximin.library.view.BaseBottomMenuView;
import com.mianmian.guild.R;

/**
 * Created by Neo on 15/12/4.
 * 从顶部部弹出菜单的view
 */
public abstract class BaseTopMenuView extends BaseBottomMenuView {
    public BaseTopMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void show(){
        if (mCurStatus == Status.DISAPPEAR){
            mCurStatus = Status.ACTIVE;
            setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mMask, "alpha", 0f, 1f).setDuration(300);
            int height = mContentContainer.getHeight();
            if(height == 0){
                Animation translationAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.anim_up_down_in);
                mContentContainer.startAnimation(translationAnimation);
                set.playTogether(alpha);
            }else{
                ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mContentContainer, "translationY", -height, 0).setDuration(300);
                set.playTogether(alpha, translationAnimator);
            }
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCurStatus = Status.APPEAR;
                    mContentContainer.clearAnimation();
                }
            });
            set.start();
        }
    }


    public void dismiss() {
        if(mCurStatus == Status.APPEAR){
            mCurStatus = Status.ACTIVE;
            AnimatorSet set = new AnimatorSet();
            int height = mContentContainer.getHeight();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mMask, "alpha", 1f, 0f);
            ObjectAnimator translation = ObjectAnimator.ofFloat(mContentContainer, "translationY", 0, -height);
            set.playTogether(alpha, translation);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(View.GONE);
                    mCurStatus = Status.DISAPPEAR;
                }
            });
            set.start();
        }
    }
}
