package com.feximin.library.activity;

import android.view.ViewTreeObserver;

import com.feximin.library.adatper.BaseGridAdapter;

/**
 * Created by Neo on 15/11/18.
 * 可以下拉刷新的list
 * 泛型数据类型
 */
public abstract class BaseRefreshableGridActivityWithTitle<T> extends BaseRefreshableListActivity<T>{

    @Override
    protected void afterInitViews() {
        if (mAdapter instanceof BaseGridAdapter){
            ViewTreeObserver vto = mListView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(() -> ((BaseGridAdapter) mAdapter).invalidateItemWidth(mListView.getWidth()));
        }else{
            throw new IllegalArgumentException("adapter should instance of BaseGridAdapter");
        }
    }
}
