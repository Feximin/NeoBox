package com.feximin.library.util;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by Neo on 15/11/23.
 */
public class TransformerZoomOut implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    private static final float SCALE_DELTA = 1 - MIN_SCALE;
    @Override
    public void transformPage(View view, float position) {
        if (position <= 1) {
            //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            // [-1,1]
            Log.e("TAG",  position + "");
            float scaleFactor = (1 - Math.abs(position)) * SCALE_DELTA + MIN_SCALE;
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.

        }
    }
}
