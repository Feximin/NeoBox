package com.feximin.box.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.feximin.box.api.ApiResult;
import com.feximin.box.exceptions.BaseException;
import com.feximin.box.util.Tool;
import com.feximin.box.util.rx.RxHelper;
import com.feximin.box.view.NListViewFooter;
import com.feximin.library.R;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 15/11/18.
 * 可以下拉刷新的list
 * 泛型数据类型
 */
public abstract class BaseRefreshableListActivity<T> extends BaseListActivity<T>{

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected int RETRIEVE_SIZE = 10;           //每次加载10条数据
    protected boolean mIsFetchingData;          //是否存在获取数据
    protected ImageView mImgEmptyHint;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mImgEmptyHint = getViewById(R.id.img_empty_hint);
        mSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> doGetData(RetrieveType.REFRESH));
        mListView.setOnLoadMoreListener(() -> doGetData(RetrieveType.MORE));
        mListView.setOnLoadMoreErrorClickListener(() -> doGetData(RetrieveType.MORE));
    }

    private Subscription mSubscriptionRetrieveLocal;

    @Override
    protected void afterInitViews() {
        if (isFinishing()) return;
        super.afterInitViews();
        mSubscriptionRetrieveLocal = RxHelper.emptyIoOb()
                .map(o -> doFetchLocal())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> onRetrieveLocalFinish(list), throwable -> onRetrieveLocalFinish(null));
        addSubscription(mSubscriptionRetrieveLocal);

        //view显示出来的时候，如果还在获取数据，则显示正在加载按钮
        mSwipeRefreshLayout.post(() ->doGetData(RetrieveType.REFRESH));
    }


    protected void setEmptyHint(int resId){
        mImgEmptyHint.setImageResource(resId);
    }

    protected void setEmptyHint(boolean show){
        mImgEmptyHint.setVisibility(show? View.VISIBLE:View.GONE);
    }
    public enum RetrieveType{
        REFRESH, MORE
    }

    protected void doGetData(RetrieveType type){
        if(mIsFetchingData)return;
        mIsFetchingData = true;
        if(type == RetrieveType.REFRESH){                   //正在刷新的时候不能去加载更多 ，加载更多的时候不能去刷新
            mListView.setLoadMoreStatus(NListViewFooter.Status.HIDE);
            mSwipeRefreshLayout.setRefreshing(true);
        }else if(type == RetrieveType.MORE){
            mSwipeRefreshLayout.setEnabled(false);
            mListView.setLoadMoreStatus(NListViewFooter.Status.ENABLE);
        }

        Subscription subscription = RxHelper.emptyIoOb()
                .map(o -> doRetrieve(type))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> retrieveFinish(l, type), error -> onRetrieveError(type, error));
        addSubscription(subscription);
    }

    protected void onRetrieveError(RetrieveType type, Throwable t){
        switch (type){
            case REFRESH:
                onRetrieveRefreshError(t);
                break;
            case MORE:
                onRetrieveLoadMoreError(t);
                break;
        }
        mIsFetchingData = false;
    }

    protected void onRetrieveRefreshError(Throwable t){
        mSwipeRefreshLayout.setRefreshing(false);

    }
    protected void onRetrieveLoadMoreError(Throwable t){
        mListView.setLoadMoreStatus(NListViewFooter.Status.ERROR);
        mSwipeRefreshLayout.setEnabled(true);
    }

    protected void retrieveFinish(ApiResult result, RetrieveType type){
        RxHelper.unSubscribe(mSubscriptionRetrieveLocal);
        boolean isOk = Tool.checkResultAndShowToastIfBad(result, R.string.get_data_fail_pls_retry);
        List<T> list = null;
        if (isOk) list = (List<T>) result.obj;
        switch (type) {
            case REFRESH:
                onRetrieveRefreshFinish(list);
                mSwipeRefreshLayout.setRefreshing(false);
                onRefreshEmpty(result);
                break;
            case MORE:
                onRetrieveMoreFinish(list);
                break;
        }
        mIsFetchingData = false;
    }

    protected void onRetrieveLocalFinish(List<T> list){
        //如果这个时候列表还是空的才加入数据
        if(Tool.isNotEmpty(list) && Tool.isEmpty(mAdapter.getData())){
            mAdapter.addData(list);
        }
    }
    protected void onRetrieveRefreshFinish(List<T> list){
        if (list != null){
            mAdapter.clearAndAddData(list);
            int count = list.size();
//            mListView.setLoadMoreStatus(NListViewFooter.Status.ENABLE);
            if(count >= RETRIEVE_SIZE){
                mListView.setLoadMoreStatus(NListViewFooter.Status.FETCHING);
            }else if(count >= RETRIEVE_SIZE / 2){
                mListView.setLoadMoreStatus(NListViewFooter.Status.NO_MORE);
            }else{
                mListView.setLoadMoreStatus(NListViewFooter.Status.DISABLE);           //小于一半的时候，不显示加载更多的view
            }
        }
    }

    protected void onRefreshEmpty(ApiResult result){

        boolean empty = mAdapter.isEmpty();
        setEmptyHint(empty);
        //只有在为空的时候才需要去设置图片
        if (empty){
            setEmptyHint(result.code == ApiResult.CODE_INVALID_NETWORK?R.mipmap.img_bad_network:R.mipmap.img_empty);
        }
    }
    protected void onRetrieveMoreFinish(List<T> list){
        if (list != null){
            mAdapter.addData(list);
            if (list.size() < RETRIEVE_SIZE)
                mListView.setLoadMoreStatus(NListViewFooter.Status.NO_MORE);
        }
        mSwipeRefreshLayout.setEnabled(true);
    }

    protected ApiResult doRetrieve(RetrieveType type){
        switch (type){
            case REFRESH:
                return doRefresh();
            case MORE:
                return doLoadMore();
            default: throw new BaseException("no this kind of RetrieveType");
        }
    }


    protected abstract ApiResult doRefresh();

    protected abstract ApiResult doLoadMore();

    protected List<T> doFetchLocal(){ return null; }

    @Override
    protected int getLayoutResId() {
        return R.layout.common_listview_refreshable;
    }

}
