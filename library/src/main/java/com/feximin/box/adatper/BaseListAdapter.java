package com.feximin.box.adatper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.interfaces.ILifeCycle;
import com.feximin.box.util.LifeCycleHelper;
import com.feximin.box.util.ScreenUtil;
import com.feximin.box.util.Tool;
import com.feximin.box.util.ViewHolder;
import com.feximin.box.util.glide.GlideHelper;
import com.feximin.box.util.rx.RxBus;
import com.feximin.box.util.rx.RxBusHelper;

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
    protected ListView mListView;
    protected RxBusHelper mRxBusHelper;
    protected LifeCycleHelper mLifeCycleHelper;

    protected int RADIUS = ScreenUtil.dpToPx(6) - 1;
    public BaseListAdapter(BaseActivity activity){
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
        mData = new ArrayList<T>();
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

    public void showDialog(boolean cancelable){
        mActivity.showLoadingDialog(cancelable);
    }

    public void dismissDialog(){
        mActivity.dismissLoadingDialog();
    }

    public void showDialog(){
        mActivity.showLoadingDialog(true);
    }

    public boolean isEmpty(){
        return mData.size() == 0;
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
        GlideHelper.obtain(mActivity).load(img, url);
    }
    protected void loadSquareImage(ImageView img, String url){
        GlideHelper.obtain(mActivity).loadSquareImage(img, url);
    }

    protected void loadImage(ImageView img, String url, int placeHolder){
        GlideHelper.obtain(mActivity).load(img, url, placeHolder);
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
        loadRoundCornerImage(iv, url, corner, 0);
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

    public void delete(T t){
        if (mData.contains(t)){
            mData.remove(t);
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
            convertView = inflateView(position, parent);
        }
        fillUp(convertView, position);
        return convertView;
    }

    public View inflateView(int position, ViewGroup parent){
//        return mInflater.inflate(getItemLayoutId(position), null);
        //必须得用这个格式的, 否则item的高度就会成为WRAP_CONTENT的
        return mInflater.inflate(getItemLayoutId(position), parent, false);
    }

    public <T extends View> T getViewById(View convertView, int id){
        return ViewHolder.getViewById(convertView, id);
    }
    public <T extends View> T getViewById(View convertView, int id, View.OnClickListener listener){
        T t = ViewHolder.getViewById(convertView, id);
        t.setOnClickListener(listener);
        return t;
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

    protected abstract void fillUp(View convertView, int position);
    protected abstract int getItemLayoutId(int position);
}
