package com.feximin.library.adatper;

import android.view.View;

import com.feximin.library.activity.BaseActivity;

/**
 * Created by Neo on 16/2/1.
 */
public class SimplePagerAdapter<T> extends BasePagerAdapter<T> {
    public SimplePagerAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void fillViewContent(View view, int position) {

    }

    @Override
    protected int getItemLayoutResId(int position) {
        return 0;
    }
}
