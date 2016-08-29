package com.feximin.box.view;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.widget.AdapterView;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.api.ApiResult;
import com.feximin.box.exceptions.BaseException;
import com.feximin.box.util.Tool;
import com.feximin.box.util.rx.RxHelper;
import com.feximin.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 16/7/29.
 */

public abstract class BaseRefreshableListView<T> extends BaseListView<T> implements AdapterView.OnItemClickListener {

    protected SwipeRefreshLayoutCompat mSwipeRefreshLayout;
    protected boolean mIsFetchingData;          //是否存在获取数据
    public BaseRefreshableListView(BaseActivity activity) {
        super(activity);
    }

    public BaseRefreshableListView(BaseActivity activity, Bundle bundle){
        super(activity, bundle);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setTargetView(mListView);
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
                .subscribe(list -> onRetrieveLocalFinish(list), throwable -> onRetrieveLocalFinish(null));
        addSubscription(mSubscriptionRetrieveLocal);

        //view显示出来的时候，如果还在获取数据，则显示正在加载按钮
        mSwipeRefreshLayout.post(() -> refresh());
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

        Subscription subscription = RxHelper.emptyIoOb()
                .map(o -> doRetrieve(type))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> retrieveFinish(l, type), error -> onRetrieveError(type, error));
        addSubscription(subscription);
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

    protected void retrieveFinish(ApiResult result, @Type int type){
        boolean isOk = Tool.checkResultAndShowToastIfBad(result, R.string.get_data_fail_pls_retry);
        List<T> list = null;
        if (isOk) list = (List<T>) result.obj;
        int curN = 0;
        if (Tool.isNotEmpty(list)) curN = list.size();
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
        int N = mAdapter.getCount();
        if (curN < 10){
            if(N >= 5){
                mListView.setLoadMoreStatus(NListViewFooter.Status.NO_MORE);
            }else{
                mListView.setLoadMoreStatus(NListViewFooter.Status.DISABLE);           //小于一半的时候，不显示加载更多的view
            }
        }else{
            mListView.setLoadMoreStatus(NListViewFooter.Status.FETCHING);
        }
        mIsFetchingData = false;
    }

    protected void onRetrieveLocalFinish(List<T> list){
        //如果这个时候列表还是空的才加入数据
        if(Tool.isNotEmpty(list) && mAdapter.isEmpty()){
            mAdapter.addData(list);
        }
    }
    protected void onRetrieveRefreshFinish(List<T> list){
        if (list != null){
            RxHelper.unSubscribe(mSubscriptionRetrieveLocal);
            mAdapter.clearAndAddData(list);
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
