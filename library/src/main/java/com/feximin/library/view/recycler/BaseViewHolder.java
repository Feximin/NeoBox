package com.feximin.library.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mianmian.guild.util.ViewSpanner;

/**
 * Created by Neo on 15/11/19.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T getViewById(int id){
        return ViewSpanner.getViewById(itemView, id);
    }
}
