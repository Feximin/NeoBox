package com.feximin.library.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.feximin.library.activity.BaseActivity;
import com.feximin.library.adatper.BaseListAdapter;
import com.feximin.library.view.BaseView;
import com.mianmian.guild.R;
import com.mianmian.guild.view.NListView;

/**
 * Created by Neo on 16/4/19.
 */
public abstract class BaseViewListView<T> extends BaseView implements AdapterView.OnItemClickListener{

    public BaseViewListView(BaseActivity activity) {
        super(activity);
    }


    protected NListView mListView;
    protected BaseListAdapter<T> mAdapter;
    protected ImageView mIvEmptyHint;
    @Override
    protected void initViews() {
        mListView = getViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
        mIvEmptyHint = getViewById(R.id.img_empty_hint);
    }

    @Override
    protected void afterInitViews() {
        initHeaderView();
        initFooterView();
        mListView.setAdapter(mAdapter = getAdapter());
    }

    protected void initHeaderView(){}

    protected void initFooterView(){}

    public void clearDivider(){
        mListView.setDividerHeight(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    protected void setEmptyHint(int resId){
        mIvEmptyHint.setImageResource(resId);
    }

    protected void setEmptyHint(boolean show){
        mIvEmptyHint.setVisibility(show? View.VISIBLE:View.GONE);
    }
    protected abstract BaseListAdapter<T> getAdapter();

    @Override
    protected int getLayoutResId() {
        return R.layout.common_listview;
    }
}
