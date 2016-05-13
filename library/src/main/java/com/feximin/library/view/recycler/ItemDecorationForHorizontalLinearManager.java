package com.feximin.library.view.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mianmian.guild.util.Tool;

/**
 * Created by Neo on 16/3/22.
 *
 */
public class ItemDecorationForHorizontalLinearManager extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public ItemDecorationForHorizontalLinearManager(Context context, @DrawableRes int resId) {
        mDivider = Tool.getDrawable(context, resId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (!isLastPosition(parent, child)) {               //最后一个不需要divider
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    protected boolean isLastPosition(RecyclerView parent, View child){
        int position = parent.getChildAdapterPosition(child);
        int count = parent.getAdapter().getItemCount();
        return position + 1 == count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isLastPosition(parent, view)){
            outRect.set(0, 0, 0, 0);
        }else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
