package com.feximin.box.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.feximin.box.api.ApiResult;
import com.feximin.box.util.rx.RxHelper;
import com.feximin.library.R;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 16/4/18.
 */
public abstract class BaseRefreshableActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.mRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        this.mRefreshLayout.setOnRefreshListener(this);
    }

    private Subscription mSubscriptionFetchLocal;
    @Override
    protected void afterInitViews() {
        if (isFinishing()) return;
        mSubscriptionFetchLocal = RxHelper.emptyIoOb()
                .map(o -> doFetchDataFromLocal())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o1 -> onFetchFromLocalFinish(o1), throwable -> onFetchFromLocalFinish(null));
        addSubscription(mSubscriptionFetchLocal);
        this.mRefreshLayout.post(() -> onRefresh());
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        Subscription subscription = RxHelper.emptyIoOb()
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
        RxHelper.unSubscribe(mSubscriptionFetchLocal);
        onRefreshFinish(result);
        mRefreshLayout.setRefreshing(false);
    }

    protected abstract void onRefreshFinish(ApiResult result);

}
