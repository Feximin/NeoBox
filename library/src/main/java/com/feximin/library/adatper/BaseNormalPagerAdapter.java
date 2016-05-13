package com.feximin.library.adatper;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.feximin.library.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 15/11/26.
 * 每个View都是固定的，不需要根据数据去生成
 */
public class BaseNormalPagerAdapter extends PagerAdapter {

    protected List<View> mViews;
    protected LayoutInflater mInflater;
    protected BaseActivity mActivity;

    public BaseNormalPagerAdapter(BaseActivity activity){
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
        this.mViews = new ArrayList<View>();
    }
    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
