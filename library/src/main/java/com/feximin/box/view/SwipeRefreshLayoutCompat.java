package com.feximin.box.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Neo on 16/6/16.
 * 原生的SwipeRefreshLayout 直接子view 需要是 可以滑动的控件才有效 ,如果是不能滑动的,则效果非常不好
 * 主要是canChildScrollUp 这个函数在起作用
 */

public class SwipeRefreshLayoutCompat extends SwipeRefreshLayout {
    public SwipeRefreshLayoutCompat(Context context) {
        super(context);
    }

    public SwipeRefreshLayoutCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mRealTargetView != null){
            if (android.os.Build.VERSION.SDK_INT < 14) {
                if (mRealTargetView instanceof AbsListView) {
                    final AbsListView absListView = (AbsListView) mRealTargetView;
                    return absListView.getChildCount() > 0
                            && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                            .getTop() < absListView.getPaddingTop());
                } else {
                    return ViewCompat.canScrollVertically(mRealTargetView, -1) || mRealTargetView.getScrollY() > 0;
                }
            } else {
                return ViewCompat.canScrollVertically(mRealTargetView, -1);
            }
        }else{
            return super.canChildScrollUp();
        }
    }

    private View mRealTargetView;

    public void setTargetView(View view){
        this.mRealTargetView = view;
    }


}
