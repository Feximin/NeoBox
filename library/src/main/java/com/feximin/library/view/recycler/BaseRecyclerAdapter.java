package com.feximin.library.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.feximin.library.activity.BaseActivity;
import com.feximin.library.view.recycler.BaseViewHolder;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 15/11/19.
 */
public abstract class BaseRecyclerAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mData;
    protected BaseActivity mActivity;
    protected LayoutInflater mInflater;
    public BaseRecyclerAdapter(BaseActivity activity){
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
        mData = new ArrayList<T>();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<T> data){
        addData(data, mData.size());
    }
    public void addData(List<T> data, int index){
        if(Tool.isNotEmpty(data) && index <= mData.size() && index >= 0){
            mData.addAll(index, data);
            notifyDataSetChanged();
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position){
        if(position >= 0 && position < mData.size()) return mData.get(position);
        return null;
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

    public T getFirst(){
        if(mData.size() > 0){
            return mData.get(0);
        }
        return null;
    }

    public T getLast(){
        if(Tool.isEmpty(mData))  return null;
        return mData.get(mData.size() - 1);
    }

    protected void loadImage(ImageView img, String url){
        GlideHelper.obtain(mActivity).loadImage(img, url);
    }

    protected void loadCircleImage(ImageView img, String url){
        GlideHelper.obtain(mActivity).loadCircleImage(img, url);
    }

    protected void loadImage(ImageView img, int resId){
        GlideHelper.obtain(mActivity).loadImage(img, resId);
    }

}
