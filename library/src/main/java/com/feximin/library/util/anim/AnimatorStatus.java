package com.feximin.library.util.anim;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Neo on 16/2/1.
 */
public class AnimatorStatus {
    public static final int GONE = 0;
    public static final int VISIBLE = 1;
    //正在进行动画
    public static final int ACTIVE = 2;

    @IntDef({GONE, VISIBLE, ACTIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status{}
}
