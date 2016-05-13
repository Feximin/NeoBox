package com.feximin.library.util.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mianmian.guild.App;
import com.mianmian.guild.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Neo on 16/2/26.
 */
public class TranslationToggleAnimation extends ToggleAnimation {
    public static final int LEFT_IN = 0;
    public static final int RIGHT_IN = 1;

    @IntDef({LEFT_IN, RIGHT_IN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type{}


    private int DURATION = 300;

    @Type
    private int mType;

    private View mTinyCrumb;

    public TranslationToggleAnimation(View view, @Type int type, @Status int status) {
        super(view);
        this.mType = type;
        this.mTinyCrumb = new View(view.getContext());
        this.mCurStatus = status;
    }
    public TranslationToggleAnimation(View view, @Type int type) {
        this(view, type, OFF);
    }

    @Override
    public Animator getOnAnimator() {
        AnimatorSet set = new AnimatorSet();
        int width = mHostView.getWidth();
        if(width == 0){
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mTinyCrumb, "alpha", 1f, 1f).setDuration(DURATION);
            //http://blog.csdn.net/bawuju/article/details/47834451
            Animation translationAnimation;
            if (mType == LEFT_IN){
                translationAnimation = AnimationUtils.loadAnimation(App.getApp(), R.anim.anim_left_right_in);
            }else{
                translationAnimation = AnimationUtils.loadAnimation(App.getApp(), R.anim.anim_right_left_in);
            }
            mHostView.startAnimation(translationAnimation);
            set.playTogether(alpha);
        }else{
            int start = mType == LEFT_IN?-width:width;
            ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mHostView, "translationX", start, 0).setDuration(DURATION);
            set.playTogether(translationAnimator);
        }
        return set;
    }

    @Override
    public Animator getOffAnimator() {
        int width = mHostView.getWidth();
        int end = mType == LEFT_IN?-width:width;
        ObjectAnimator translation = ObjectAnimator.ofFloat(mHostView, "translationX", 0, end).setDuration(DURATION);
        return translation;
    }
}
