package com.feximin.box.fragment;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.ImageView;

import com.feximin.box.api.ApiResult;
import com.feximin.box.exceptions.BaseException;
import com.feximin.box.util.Tool;
import com.feximin.box.util.rx.RxHelper;
import com.feximin.box.view.NListViewFooter;
import com.feximin.box.view.SwipeRefreshLayoutCompat;
import com.feximin.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 15/11/18.
 * 可以下拉刷新的list
 * 泛型数据类型
 */
public abstract class BaseRefreshableListFragment<T> extends BaseListFragment<T>{

    protected SwipeRefreshLayoutCompat mSwipeRefreshLayout;
    protected int RETRIEVE_SIZE = 10;           //每次加载10条数据
    protected boolean mIsFetchingData;          //是否存在获取数据
    protected ImageView mImgEmptyHint;
    protected boolean mIsSwipeRefreshLayoutEnabled = true;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mImgEmptyHint = getViewById(R.id.img_empty_hint);
        mSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(mIsSwipeRefreshLayoutEnabled);
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh());
        mListView.setOnLoadMoreListener(() -> loadMore());
        mListView.setOnLoadMoreErrorClickListener(() -> loadMore());
    }

    private Subscription mSubscriptionRetrieveLocal;

    @Override
    protected void afterInitViews() {
        super.afterInitViews();
        mSubscriptionRetrieveLocal = RxHelper.emptyIoOb()
                .map(o -> doFetchLocal())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> onRetrieveLocalFinish(list), throwable -> {});
        addSubscription(mSubscriptionRetrieveLocal);

        //view显示出来的时候，如果还在获取数据，则显示正在加载按钮
        mSwipeRefreshLayout.post(() -> refresh());
    }


    protected void setEmptyHint(int resId){
        mImgEmptyHint.setImageResource(resId);
    }

    protected void setEmptyHint(boolean show){
        mImgEmptyHint.setVisibility(show? View.VISIBLE:View.GONE);
    }

    public void refresh(){
        doGetData(REFRESH);
    }

    public void loadMore(){
        doGetData(MORE);
    }
    public static final int REFRESH = 0;
    public static final int MORE = 1;

    @IntDef({REFRESH, MORE})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface Type{}

    protected void doGetData(@Type int type){
        if(mIsFetchingData)return;
        mIsFetchingData = true;
        if(type == REFRESH){                   //正在刷新的时候不能去加载更多 ，加载更多的时候不能去刷新
            mListView.setLoadMoreStatus(NListViewFooter.Status.HIDE);
            mSwipeRefreshLayout.setRefreshing(true);
        }else if(type == MORE){
            mSwipeRefreshLayout.setEnabled(false);
            mListView.setLoadMoreStatus(NListViewFooter.Status.ENABLE);
        }

        Subscription subscription = RxHelper.ioOb(type).map(retrieveType -> doRetrieve(type))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> onRetrieveFinish(l, type), error -> onRetrieveError(type, error));
        addSubscription(subscription);
    }


    public void setSwipeRefreshLayoutEnabled(boolean enabled){
        mIsSwipeRefreshLayoutEnabled = enabled;
        if (mSwipeRefreshLayout != null){
            mSwipeRefreshLayout.setEnabled(enabled);
        }
    }

    protected void onRetrieveError(@Type int type, Throwable t){
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

    protected void onRetrieveFinish(ApiResult result, @Type int type){
        boolean isOk = Tool.checkResultAndShowToastIfBad(result, R.string.get_data_fail_pls_retry);
        List<T> list = null;
        if (isOk) list = (List<T>) result.obj;
        switch (type) {
            case REFRESH:
                onRetrieveRefreshFinish(list);
                mSwipeRefreshLayout.setRefreshing(false);
                boolean empty = mAdapter.isEmpty();
                setEmptyHint(empty);
                if (empty){
                    setEmptyHint(result.code == ApiResult.CODE_INVALID_NETWORK?R.mipmap.img_bad_network:R.mipmap.img_empty);
                }
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
            RxHelper.unSubscribe(mSubscriptionRetrieveLocal);
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
    protected void onRetrieveMoreFinish(List<T> list){
        if (list != null){
            mAdapter.addData(list);
            if (list.size() < RETRIEVE_SIZE) {
                mListView.setLoadMoreStatus(NListViewFooter.Status.NO_MORE);
            }else{
                mListView.setLoadMoreStatus(NListViewFooter.Status.FETCHING);
            }
        }
        mSwipeRefreshLayout.setEnabled(true);
    }

    protected ApiResult doRetrieve(@Type int type){
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
