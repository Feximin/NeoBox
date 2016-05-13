package com.feximin.library.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.feximin.library.adatper.BaseListAdapter;
import com.feximin.library.fragment.BaseFragment;
import com.mianmian.guild.R;
import com.mianmian.guild.view.NListView;

/**
 * Created by Neo on 15/11/18.
 * 没有title的list
 */
public abstract class BaseListFragment<T> extends BaseFragment implements AdapterView.OnItemClickListener {

    protected NListView mListView;
    protected ImageView mImgEmptyHint;
    protected BaseListAdapter<T> mAdapter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mListView = getViewById(R.id.listview);
        mImgEmptyHint = getViewById(R.id.img_empty_hint);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void afterInitViews() {
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

    public void setDivider(int color, int height){
        mListView.setDividerHeight(height);
        mListView.setDivider(new ColorDrawable(color));
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
