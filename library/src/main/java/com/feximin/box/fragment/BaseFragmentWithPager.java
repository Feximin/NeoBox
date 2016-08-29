package com.feximin.box.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.feximin.box.adatper.BaseFragmentPagerAdapter;
import com.feximin.library.R;

import java.util.List;

/**
 * Created by Neo on 16/1/14.
 */
public abstract class BaseFragmentWithPager extends BaseFragment implements ViewPager.OnPageChangeListener {
    protected ViewPager mPager;
    protected BaseFragmentPagerAdapter mAdapter;
    protected TabLayout mTabLayout;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPager = getViewById(getViewPagerId());
        mPager.addOnPageChangeListener(this);
        mPager.setAdapter(mAdapter = getAdapter());
        onGetAdapter(mAdapter);
        mTabLayout = getViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);
    }

    protected BaseFragmentPagerAdapter getAdapter(){
        return new BaseFragmentPagerAdapter(getFragmentManager(), getEntities());
    }

    protected void onGetAdapter(BaseFragmentPagerAdapter adapter){}

    //viewpager的id不能相同，这算是FragmentPagerAdapter的一个坑
    protected int getViewPagerId(){
        return R.id.pager;
    }

    protected abstract List<BaseFragmentPagerAdapter.Entity> getEntities();

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.common_framgment_with_pager;
    }
}
