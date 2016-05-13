package com.feximin.library.adatper;

import android.widget.LinearLayout;

import com.feximin.library.activity.BaseActivity;

/**
 * Created by Neo on 16/1/26.
 * GridView也使用ListView的形式
 */
public abstract class BaseSquareGridAdapter<T> extends BaseGridAdapter<T> {

    public BaseSquareGridAdapter(BaseActivity activity, int column) {
        super(activity, column);
    }

    @Override
    protected boolean refreshLayoutParams(LinearLayout.LayoutParams lp) {
        if (lp != null){
            if (lp.height != mItemWidth) {
                lp.height = mItemWidth;
                return true;
            }
        }
        return false;
    }
}
