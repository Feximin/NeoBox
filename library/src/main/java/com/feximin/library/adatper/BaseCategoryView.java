package com.feximin.library.adatper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mianmian.guild.R;
import com.mianmian.guild.util.ScreenUtil;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.ViewSpanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 15/11/27.
 */
public class BaseCategoryView extends GridLayout {
    public BaseCategoryView(Context context) {
        super(context);
    }

    private List<Entry> mEntryList;

    public void build(List<Category> data, int columnCount){
        if(Tool.isEmpty(data)) return;
        setBackgroundColor(0xFFFFFFFF);
        setPadding(0, 0, 0, ScreenUtil.dpToPx(15));
        setColumnCount(columnCount);
        if(getChildCount() > 0) removeAllViews();
        int width = ScreenUtil.getScreenWidth() / columnCount;
        int count = data.size();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mEntryList = new ArrayList<>(count);
        for(int i = 0; i<count; i++){
            Category entity = data.get(i);
            View child = inflater.inflate(getItemLayoutResId(), this, false);
            child.setOnClickListener(v -> {if(mOnCategoryClickListener != null) mOnCategoryClickListener.onClassifyClick(child, entity);});
            Entry entry = new Entry();
            entry.view = child;
            entry.bean = entity;
            mEntryList.add(entry);
            fillItemContent(child, entity);
            //指定该组件所在的行数
            Spec rowSpec = spec(i/columnCount);
            //指定给组件所在的列数
            Spec columnSpec = spec(i%columnCount);
            LayoutParams lp = new LayoutParams(rowSpec, columnSpec);
            lp.setGravity(Gravity.CENTER);
            lp.width = width;
            lp.height = LayoutParams.WRAP_CONTENT;
            addView(child, lp);
        }
    }

    public static class Entry{
        public View view;
        public Category bean;
    }

    public List<Entry> getEntryList(){
        return mEntryList;
    }


    protected void fillItemContent(View view, Category entity){
        ImageView img = ViewSpanner.getViewById(view, R.id.img);
        TextView txt = ViewSpanner.getViewById(view, R.id.txt);
        Tool.loadImage(getContext(), img, entity.imgResId);
        txt.setText(entity.text);
    }

    protected int getItemLayoutResId(){
        return R.layout.item_classify;
    }

    public static class Category implements Parcelable {
        public Category(String id, int resId, String text){
            this.imgResId = resId;
            this.text = text;
            this.id = id;
        }
        public int imgResId;
        public String text;
        public String iconUrl;
        public String id;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.imgResId);
            dest.writeString(this.text);
            dest.writeString(this.iconUrl);
            dest.writeString(this.id);
        }

        protected Category(Parcel in) {
            this.imgResId = in.readInt();
            this.text = in.readString();
            this.iconUrl = in.readString();
            this.id = in.readString();
        }

        public static final Creator<Category> CREATOR = new Creator<Category>() {
            public Category createFromParcel(Parcel source) {
                return new Category(source);
            }

            public Category[] newArray(int size) {
                return new Category[size];
            }
        };
    }

    private OnCategoryClickListener mOnCategoryClickListener;
    public void setOnCategoryClickListener(OnCategoryClickListener listener){
        this.mOnCategoryClickListener = listener;
    }

    public static interface OnCategoryClickListener{
        void onClassifyClick(View view, Category entity);
    }
}
