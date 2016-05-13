package com.feximin.library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.feximin.library.activity.BaseActivity;
import com.mianmian.guild.R;
import com.mianmian.guild.util.ViewSpanner;

/**
 * Created by Neo on 15/12/4.
 * 从底部弹出菜单的view
 */
public abstract class BaseBottomMenuView extends RelativeLayout {
    public int DURATION = 300;
    public BaseBottomMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    protected BaseActivity mActivity;
    protected LayoutInflater mInflater;
    protected View mMask;
    protected ViewGroup mContentContainer;

    protected void init(AttributeSet attrs){
        mActivity = (BaseActivity) getContext();
        mInflater = LayoutInflater.from(mActivity);
        inflate(mActivity, getRootLayoutResId(), this);
        mMask = ViewSpanner.getViewById(this, R.id.mask, "dismiss");
        mContentContainer = ViewSpanner.getViewById(this, R.id.container_content);
    }

    public enum Status{
        APPEAR, DISAPPEAR, ACTIVE               //显示，隐藏，正在显示或者正在隐藏
    }
    protected Status mCurStatus = Status.DISAPPEAR;
    public void show(){
        if (mCurStatus == Status.DISAPPEAR){
            mCurStatus = Status.ACTIVE;
            setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mMask, "alpha", 0f, 1f).setDuration(DURATION);
            int height = mContentContainer.getHeight();
            if(height == 0){
                Animation translationAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.anim_down_up_in);
                mContentContainer.startAnimation(translationAnimation);
                set.playTogether(alpha);
            }else{
                ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mContentContainer, "translationY", height, 0).setDuration(DURATION);
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
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mMask, "alpha", 1f, 0f).setDuration(DURATION);
            ObjectAnimator translation = ObjectAnimator.ofFloat(mContentContainer, "translationY", 0, height).setDuration(DURATION);
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

    protected abstract int getRootLayoutResId();
}
