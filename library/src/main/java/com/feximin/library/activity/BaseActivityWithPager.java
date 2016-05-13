package com.feximin.library.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.feximin.library.fragment.BaseFragmentPagerAdapter;
import com.mianmian.guild.R;

import java.util.List;

/**
 * Created by Neo on 16/1/14.
 */
public abstract  class BaseActivityWithPager extends BaseActivity implements ViewPager.OnPageChangeListener {
    protected ViewPager mPager;
    protected TabLayout mTabLayout;
    protected BaseFragmentPagerAdapter mFragmentPagerAdapter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPager = getViewById(getViewPagerId());
        mPager.setAdapter(mFragmentPagerAdapter = getAdapter());
        onGetPagerAdapter(mFragmentPagerAdapter);
        mPager.addOnPageChangeListener(this);
        mTabLayout = getViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);
    }

    protected BaseFragmentPagerAdapter getAdapter(){
        return new BaseFragmentPagerAdapter(getSupportFragmentManager(), getEntities());
    }

    protected void onGetPagerAdapter(BaseFragmentPagerAdapter adapter){

    }



    protected abstract List<BaseFragmentPagerAdapter.Entity> getEntities();
    //viewpager的id不能相同，这算是FragmentPagerAdapter的一个坑
    protected abstract int getViewPagerId();

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
