package com.feximin.box.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.feximin.box.adatper.BaseListAdapter;
import com.feximin.box.view.NListView;
import com.feximin.library.R;


/**
 * Created by Neo on 15/11/18.
 * 没有title的并且数据是从本地加载的list
 */
public abstract class BaseListActivity<T> extends BaseActivity  implements AdapterView.OnItemClickListener {

    protected NListView mListView;
    protected BaseListAdapter<T> mAdapter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mListView = getViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
        this.mTitleBar = getViewById(R.id.title_bar);
    }


    @Override
    protected void afterInitViews() {
        if (isFinishing()) return;
        initHeaderView();
        initFooterView();
        mListView.setAdapter(mAdapter = getAdapter());
        mAdapter.setListView(mListView);
        addLifeCycleComponent(mAdapter);
    }

    protected void initHeaderView(){}

    protected void initFooterView(){}

    public void clearDivider(){
        mListView.setDividerHeight(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    protected abstract BaseListAdapter<T> getAdapter();

    @Override
    protected int getLayoutResId() {
        return R.layout.common_listview_with_title;
    }
}
