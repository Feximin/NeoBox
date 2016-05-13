package com.feximin.library.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.feximin.library.adatper.BaseListAdapter;
import com.mianmian.guild.R;
import com.mianmian.guild.view.NListView;

/**
 * Created by Neo on 15/11/18.
 * 有title但是数据是从本地加载的listview
 */
public abstract class BaseListActivityWithTitle<T> extends BaseTitleActivity  implements AdapterView.OnItemClickListener {

    protected NListView mListView;
    protected BaseListAdapter<T> mAdapter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mListView = getViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
    }

    public void clearDivider(){
        mListView.setDividerHeight(0);
    }

    @Override
    protected void afterInitViews() {
        initHeaderView();
        initFooterView();
        mListView.setAdapter(mAdapter = getAdapter());
        mAdapter.setListView(mListView);
        addLifeCycleComponent(mAdapter);
    }

    protected void initHeaderView(){}

    protected void initFooterView(){}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) mAdapter.onDestroy();
    }

    protected abstract BaseListAdapter<T> getAdapter();

    @Override
    protected int getLayoutResId() {
        return R.layout.common_listview_with_title;
    }
}
