package com.feximin.library.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.mianmian.guild.R;
import com.mianmian.guild.api.ApiResult;
import com.mianmian.guild.util.rx.RxHelper;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 16/4/18.
 */
public abstract class BaseRefreshLayoutActivityWithTitle extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout mRefreshLayout;
    protected boolean mIsDoingRefresh;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        this.mRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        this.mRefreshLayout.setOnRefreshListener(this);
    }


    private Subscription mSubscriptionFetchLocal;
    @Override
    protected void afterInitViews() {
        if (isFinishing()) return;
        mSubscriptionFetchLocal = RxHelper.emptyNetOb()
                .map(o -> doFetchDataFromLocal())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o1 -> onFetchFromLocalFinish(o1), throwable -> onFetchFromLocalFinish(null));
        addSubscription(mSubscriptionFetchLocal);
        this.mRefreshLayout.post(() -> onRefresh());
    }

    @Override
    public void onRefresh() {
        if (mIsDoingRefresh) return;
        mIsDoingRefresh = true;
        mRefreshLayout.setRefreshing(true);
        Subscription subscription = RxHelper.emptyNetOb()
                .map(o -> doFetchDataFromNet())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> onFetchFromNetFinish(result), throwable -> onFetchFromNetFinish(null));
        addSubscription(subscription);
    }

    protected Object doFetchDataFromLocal(){
        return null;
    }


    protected void onFetchFromLocalFinish(Object data){

    }

    protected abstract ApiResult doFetchDataFromNet();


    protected void onFetchFromNetFinish(ApiResult result){
        if (mSubscriptionFetchLocal != null && mSubscriptionFetchLocal.isUnsubscribed()){
            mSubscriptionFetchLocal.unsubscribe();
        }
        onRefreshFinish(result);
        mIsDoingRefresh = false;
        mRefreshLayout.setRefreshing(false);
    }

    protected abstract void onRefreshFinish(ApiResult result);

}
