package com.feximin.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neo on 15/11/20.
 * 滑动到底部自动刷新
 */
public class NListView extends ListView{

    private static final String TAG = NListView.class.getSimpleName();

    private NListViewFooter mFooterLoadMore;

    public NListView(Context context) {
        this(context, null);
    }

    public NListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){
        mFooterLoadMore = new NListViewFooter(getContext());
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mScrollListener != null) mScrollListener.onScrollStateChanged(view, scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mScrollListener != null) mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                NListViewFooter.Status status = mFooterLoadMore.getCurStatus();
                if (onLoadMoreListener != null && status == NListViewFooter.Status.FETCHING) {
                    int headerCount = getHeaderViewsCount();
                    int footerCount = getFooterViewsCount();
                    int validCount = totalItemCount - footerCount;
                    int lastIndex = firstVisibleItem + visibleItemCount;
                    if (lastIndex > headerCount && lastIndex >= validCount) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        for (com.feximin.box.interfaces.OnScrollChangeListener listener : mOnScrollChangeListenerSet){
            listener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    public void setLoadMoreStatus(NListViewFooter.Status status){
        mFooterLoadMore.setStatus(status);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        addFooterView(mFooterLoadMore);
        super.setAdapter(adapter);
    }

    private Set<com.feximin.box.interfaces.OnScrollChangeListener> mOnScrollChangeListenerSet = new HashSet<>(0);
    public void addOnScrollChangeListener(com.feximin.box.interfaces.OnScrollChangeListener listener){
        mOnScrollChangeListenerSet.add(listener);
    }

    private OnLoadMoreListener onLoadMoreListener;
    private OnScrollListener mScrollListener;
    public void addOnScrollListener(OnScrollListener l) {
        this.mScrollListener = l;
    }



    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.onLoadMoreListener = listener;
    }


    //
    public void setOnLoadMoreErrorClickListener(NListViewFooter.OnErrorClickListener l){
        this.mFooterLoadMore.setOnErrorClickListener(l);
    }

    public static interface OnLoadMoreListener{
        void onLoadMore();
    }

}
