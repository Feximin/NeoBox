package com.feximin.library.fragment;

import android.os.Bundle;

import com.feximin.library.fragment.BaseFragmentWithPagerAndTitle;
import com.mianmian.guild.R;
import com.mianmian.guild.test.ScrollViewPagerHelper;
import com.mianmian.guild.view.NestViewPagerScrollView;

/**
 * Created by Neo on 16/2/29.
 */
public abstract class BaseScrollViewPagerFragmentWithTitle extends BaseFragmentWithPagerAndTitle {

    protected NestViewPagerScrollView mScrollView;

    //根布局id为R.id.root
    //scrollView 的id 为R.id.scroll_view
    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        this.mScrollView = getViewById(R.id.scroll_view);
        //包含了viewpager TabLayout的父布局
        ScrollViewPagerHelper.obtain().setup(mScrollView, mPager, mTabLayout);

    }

    protected abstract int getViewPagerId();

}
