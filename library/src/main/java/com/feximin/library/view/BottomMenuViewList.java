package com.feximin.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.feximin.library.view.BaseBottomMenuView;
import com.mianmian.guild.R;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.ViewSpanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 16/2/17.
 * 从底部弹出的列表形式的菜单
 */
public class BottomMenuViewList extends BaseBottomMenuView {

    private List<MenuEntity> mEntities = new ArrayList<>(2);

    public BottomMenuViewList(Context context, AttributeSet attrs) {
        super(context, attrs);
        add(CANCEL_ENTITY);
    }

    public MenuEntity CANCEL_ENTITY = new MenuEntity("取消", "dismiss");

    public void build(List<MenuEntity> list) {
        if (Tool.isEmpty(list)) throw new IllegalArgumentException("empty menu entity list !!");;
        mEntities.clear();
        mEntities.addAll(list);
        mEntities.add(CANCEL_ENTITY);
        mContentContainer.removeAllViews();
        for (int i = 0; i < mEntities.size(); i++) {
            MenuEntity entity = mEntities.get(i);
            entity.index = i;
            View view = getItemView(entity);
            mContentContainer.addView(view);
        }
    }

    public View getItemView(MenuEntity entity) {
        TextView txt = (TextView) mInflater.inflate(R.layout.item_bottom_menu, null);
        txt.setText(entity.name);
        if (Tool.isNotEmpty(entity.methodName)) {
            ViewSpanner.bindClick(txt, entity.methodName, this);
        } else if (entity.clickListener != null) {
            txt.setOnClickListener(entity.clickListener);
        } else if(entity.menuClickListener != null){
            txt.setOnClickListener(v -> entity.menuClickListener.onMenuClick(entity.index));
        }
        return txt;
    }

    public void add(MenuEntity entity, int index) {
        mEntities.add(index, entity);
        for (int i = index + 1; i < mEntities.size(); i++) {
            MenuEntity en = mEntities.get(i);
            en.index = en.index + 1;
        }
        View v = getItemView(entity);
        mContentContainer.addView(v, index);
    }

    public void add(MenuEntity entity) {
        int N = mEntities.size() - 1;
        if (N < 0) N = 0;
        add(entity, N);
    }

    public static class MenuEntity {
        public String name;
        public String methodName;
        public View.OnClickListener clickListener;
        public OnMenuClickListener menuClickListener;
        int index;

        public MenuEntity(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
        }

        public MenuEntity(String name) {
            this.name = name;
        }

        public MenuEntity(String name, View.OnClickListener clickListener) {
            this.name = name;
            this.clickListener = clickListener;
        }
        public MenuEntity(String name, OnMenuClickListener clickListener) {
            this.name = name;
            this.menuClickListener = clickListener;
        }
    }


    public static interface OnMenuClickListener {
        void onMenuClick(int index);
    }


    @Override
    protected int getRootLayoutResId() {
        return R.layout.view_bottom_menu_view_list;
    }
}
