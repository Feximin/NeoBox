package com.feximin.library.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 16/5/4.
 */
public class BroadcastHelper extends BroadcastReceiver {

    private Context mContext;
    private List<Item> mItemList = new ArrayList<>();

    private BroadcastHelper(){}

    public static BroadcastHelper obtain(Context context){
        BroadcastHelper helper = new BroadcastHelper();
        helper.mContext = context;
        return helper;
    }

    public BroadcastHelper add(Item item){
        mItemList.add(item);
        return this;
    }

    public BroadcastHelper add(String action, OnReceiveListener listener){
        Item item = new Item();
        item.action = action;
        item.listener = listener;
        return add(item);
    }


    public BroadcastHelper register(){
        if (mItemList.size() <= 0) throw new IllegalArgumentException("item count should more than 1 !!");
        IntentFilter filter = new IntentFilter();
        for (Item item : mItemList){
            filter.addAction(item.action);
        }
        mContext.registerReceiver(this, filter);
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (Tool.isEmpty(action)) return;
        for (Item item : mItemList){
            if (Tool.isAllNotEmptyAndEquals(item.action, action)) {
                item.listener.onReceive(action, context, intent);
            }
        }
    }

    public void unRegister(){
        if (mContext != null) mContext.unregisterReceiver(this);
    }


    public static class Item{
        public String action;
        public OnReceiveListener listener;
    }

    public static interface OnReceiveListener{
        void onReceive(String action, Context context, Intent intent);
    }

}
