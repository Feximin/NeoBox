package com.feximin.library.util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Neo on 15/11/23.
 */
public class TransformerDepth implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1){
         // [-Infinity,-1)
            // This page is way off-screen to the left.
            // view.setAlpha(0);
            view.setAlpha(0);
        } else if (position <= 0){
            // a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            // [-1,0]
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1){
            // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);
            view.setTranslationX(pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else{ // (1,+Infinity]
            // This page is way off-screen to the right.
             view.setAlpha(0);
//            ViewHelper.setAlpha(view, 1);
        }
    }
}
