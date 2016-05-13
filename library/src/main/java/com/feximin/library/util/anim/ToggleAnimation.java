package com.feximin.library.util.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.animation.Animator.AnimatorListener;

/**
 * Created by Neo on 16/2/25.
 */
public abstract class ToggleAnimation {


    public static final int ON = 0;
    public static final int OFF = 2;
    public static final int ACTIVE = 4;
    @IntDef({ON, OFF, ACTIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status{}

    @Status
    protected int mCurStatus = OFF;

    protected View mHostView;
    private AnimatorListener mOnAnimatorListener, mOffAnimatorListener;

    public ToggleAnimation(View view){
        this.mHostView = view;
    }

    private ToggleAnimationListener mToggleAnimationListener;
    public void setToggleAnimationListener(ToggleAnimationListener listener){
        this.mToggleAnimationListener = listener;
    }

    public interface ToggleAnimationListener{
        void onOnAnimationEnd();
        void onOffAnimationEnd();
    }

    public void toggle(){
        if(mCurStatus == ON){
            off();
        }else if (mCurStatus == OFF){
            on();
        }
    }

    public void on(){
        if (mCurStatus != OFF) return;
        mCurStatus = ACTIVE;
        mHostView.setVisibility(View.VISIBLE);
        Animator animator = getOnAnimator();
        if (mToggleAnimationListener != null){
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mToggleAnimationListener.onOnAnimationEnd();
                }
            });
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurStatus = ON;
            }
        });
        animator.start();
    }

    public void off(){
        if (mCurStatus != ON) return;
        mCurStatus = ACTIVE;
        Animator animator = getOffAnimator();
        if (mToggleAnimationListener != null){
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mToggleAnimationListener.onOffAnimationEnd();
                }
            });
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurStatus = OFF;
                mHostView.setVisibility(View.GONE);
                mHostView.clearAnimation();
            }
        });
        animator.start();

    }


    public abstract Animator getOnAnimator();
    public abstract Animator getOffAnimator();

}
