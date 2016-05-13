package com.feximin.library.adatper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feximin.library.activity.BaseActivity;
import com.feximin.library.presenter.BasePresenter;
import com.mianmian.guild.R;
import com.mianmian.guild.interfaces.ILifeCycle;
import com.mianmian.guild.util.LifeCycleHelper;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.ViewHolder;
import com.mianmian.guild.util.glide.GlideHelper;
import com.mianmian.guild.util.rx.RxBus;
import com.mianmian.guild.util.rx.RxBusHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by Neo on 15/11/18.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter implements ILifeCycle, RxBus.RxBusManager {
    protected List<T> mData;
    protected BaseActivity mActivity;
    protected LayoutInflater mInflater;
    protected BasePresenter<T> mPresenter;
    protected ListView mListView;
    protected RxBusHelper mRxBusHelper;
    protected LifeCycleHelper mLifeCycleHelper;
    public BaseListAdapter(BaseActivity activity){
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
        mData = new ArrayList<T>();
        mPresenter = getPresenter();
        mRxBusHelper = RxBusHelper.obtain(this);
        mLifeCycleHelper = LifeCycleHelper.obtain();
    }

    public void registerEvent(){
        mRxBusHelper.register();
    }

    @Override
    public void onEvent(Object ev) {

    }

    @Override
    public void addSubscription(Subscription subscription) {
        mRxBusHelper.addSubscription(subscription);
    }

    public BaseListAdapter(BaseActivity activity, List<T> list){
        this(activity);
        this.mData = list;
    }

    public void setListView(ListView view){
        this.mListView = view;
    }

    protected BasePresenter<T> getPresenter(){
        return null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position){
        if(position >= 0 && position < mData.size()) return mData.get(position);
        return null;
    }

    public List<T> getData(){
        return mData;
    }
    public void addData(List<T> data, int index){
        if(Tool.isNotEmpty(data) && index <= mData.size() && index >= 0){
            mData.addAll(index, data);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> data){
        addData(data, mData.size());
    }


    public void addData(T t, int index){
        if(t != null && index <= mData.size() && index >= 0){
            mData.add(index, t);
            notifyDataSetChanged();
        }
    }
    public void addData(T t){
        addData(t, mData.size());
    }

    public void removeData(T t){
        if(t != null && mData.contains(t)){
            mData.remove(t);
            notifyDataSetChanged();
        }
    }
    public void removeData(List<T> t){
        if(Tool.isNotEmpty(t)){
            mData.removeAll(t);
            notifyDataSetChanged();
        }
    }
    protected void loadImage(ImageView img, String url){
        GlideHelper.obtain(mActivity).loadImage(img, url);
    }

    protected void loadImage(ImageView img, String url, int placeHolder){
        GlideHelper.obtain(mActivity).loadImage(img, url, placeHolder);
    }

    protected void loadCircleImage(ImageView img, String url){
        GlideHelper.obtain(mActivity).loadCircleImage(img, url);
    }

    protected void loadCircleImage(ImageView img, String url, int placeHolder){
        GlideHelper.obtain(mActivity).loadCircleImage(img, url, placeHolder);
    }

    public void loadRoundCornerImage(ImageView iv, String url, int corner, int placeHolder){
        GlideHelper.obtain(mActivity).loadRoundCornerImage(iv, url, corner, placeHolder);
    }

    public void loadRoundCornerImage(ImageView iv, String url, int corner){
        loadRoundCornerImage(iv, url, corner, R.mipmap.img_default_gift);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy(){
        mRxBusHelper.unRegister();
    }

    public void clearAndAddData(List<T> data){
        boolean needNotify = false;
        if(Tool.isNotEmpty(mData)){
            mData.clear();
            needNotify = true;
        }
        if(Tool.isNotEmpty(data)){
            mData.addAll(data);
            needNotify = true;
        }
        if(needNotify) notifyDataSetChanged();
    }
    public void clearAll(){
        if(Tool.isNotEmpty(mData)){
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void deleteAt(int position){
        if(position < mData.size() && position >= 0){
            mData.remove(position);
            notifyDataSetChanged();
        }
    }

    public boolean contains(T t){
        return mData.contains(t);
    }

    public T getLast(){
        if(Tool.isEmpty(mData))  return null;
        return mData.get(mData.size() - 1);
    }

    public T getFirst(){
        if (Tool.isEmpty(mData)) return null;
        return mData.get(0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            int layoutResId = getItemLayoutId(position);
            if (layoutResId > 0) {
                convertView = mInflater.inflate(layoutResId, parent, false);
            }else{
                convertView = generateView(position);
            }
        }
        inflateView(convertView, position);
        return convertView;
    }

    public View generateView(int position){
        return null;
    }


    public <T extends View> T getViewById(View convertView, int id){
        return ViewHolder.getViewById(convertView, id);
    }

    public TextView getTextViewById(View convertView, int id, String text){
        TextView txt = getViewById(convertView, id);
        txt.setText(text);
        return txt;
    }

    public ImageView getImageView(View convertView, int id, String path){
        ImageView iv = getViewById(convertView, id);
        if (iv != null){
            loadImage(iv, path);
        }
        return iv;
    }

    public ImageView getImageViewWithCircle(View convertView, int id, String path){
        ImageView iv = getViewById(convertView, id);
        if (iv != null){
            loadCircleImage(iv, path);
        }
        return iv;
    }

    public ImageView getImageViewWithRoundCorner(View convertView, int id, String path, int corner){
        ImageView iv = getViewById(convertView, id);
        if (iv != null){
            GlideHelper.obtain(mActivity).loadRoundCornerImage(iv, path, corner);
        }
        return iv;
    }

    protected abstract void inflateView(View convertView, int position);
    protected int getItemLayoutId(int position){
        return 0;
    }
}
