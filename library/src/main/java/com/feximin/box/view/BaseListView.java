package com.feximin.box.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.adatper.BaseListAdapter;
import com.feximin.library.R;

/**
 * Created by Neo on 15/11/18.
 * 没有title的list
 */
public abstract class BaseListView<T> extends BaseView  implements AdapterView.OnItemClickListener {

    protected NListView mListView;
    protected ImageView mImgEmptyHint;
    protected BaseListAdapter<T> mAdapter;

    public BaseListView(BaseActivity activity) {
        super(activity);
    }

    public BaseListView(BaseActivity activity, Bundle bundle) {
        super(activity, bundle);
    }

    @Override
    protected void initViews() {
        mListView = getViewById(R.id.listview);
        mImgEmptyHint = getViewById(R.id.img_empty_hint);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void afterInitViews() {
        if (mActivity.isFinishing()) return;
        initHeaderView();
        initFooterView();
        mListView.setAdapter(mAdapter = getAdapter());
        addLifeCycleComponent(mAdapter);
    }

    protected void initHeaderView(){}

    protected void initFooterView(){}

    protected void setEmptyHint(int resId){
        mImgEmptyHint.setImageResource(resId);
    }

    protected void setEmptyHint(boolean show){
        mImgEmptyHint.setVisibility(show?View.VISIBLE:View.GONE);
    }


    public T getItem(long id){
        return mAdapter.getItem((int) id);
    }

    public void clearDivider(){
        mListView.setDividerHeight(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public ListView getListView(){
        return mListView;
    }

    protected abstract BaseListAdapter<T> getAdapter();

    @Override
    protected int getLayoutResId() {
        return R.layout.common_listview;
    }
}
