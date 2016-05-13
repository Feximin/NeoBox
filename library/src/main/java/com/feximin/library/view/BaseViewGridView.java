package com.feximin.library.view;

import android.view.ViewTreeObserver;

import com.feximin.library.activity.BaseActivity;
import com.feximin.library.adatper.BaseGridAdapter;

/**
 * Created by Neo on 16/4/19.
 */
public abstract class BaseViewGridView<T> extends BaseViewListView<T> {
    public BaseViewGridView(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void afterInitViews() {
        super.afterInitViews();
        if (mAdapter instanceof BaseGridAdapter){
            ViewTreeObserver vto = mListView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(() -> ((BaseGridAdapter) mAdapter).invalidateItemWidth(mListView.getWidth()));
        }else{
            throw new IllegalArgumentException("adapter should instance of BaseGridAdapter");
        }
    }
}
