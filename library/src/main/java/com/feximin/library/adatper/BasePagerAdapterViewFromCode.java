package com.feximin.library.adatper;

import android.view.View;
import android.view.ViewGroup;

import com.feximin.library.activity.BaseActivity;

/**
 * Created by Neo on 16/3/10.
 */
public abstract class BasePagerAdapterViewFromCode<T> extends BasePagerAdapter<T> {
    public BasePagerAdapterViewFromCode(BaseActivity activity) {
        super(activity);
    }


    @Override
    public abstract Object instantiateItem(ViewGroup v, int position);

    @Override
    protected void fillViewContent(View view, int position) {

    }

    @Override
    protected int getItemLayoutResId(int position) {
        return 0;
    }
}
