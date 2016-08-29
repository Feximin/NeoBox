package com.feximin.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feximin.box.util.ScreenUtil;
import com.feximin.box.util.Tool;
import com.feximin.box.util.ViewSpanner;
import com.feximin.library.R;

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
    }

    public MenuEntity CANCEL_ENTITY = new MenuEntity("取消", "dismiss");

    public void build(List<MenuEntity> list) {
        if (Tool.isEmpty(list)) throw new IllegalArgumentException("empty menu entity list !!");
        mEntities.clear();
        mContentContainer.removeAllViews();
        for (MenuEntity entity : list) {
            add(entity);
        }
        add(CANCEL_ENTITY);
    }

    public View getItemView(MenuEntity entity) {
        TextView txt = new TextView(getContext());

        txt.setTextSize(15);
        txt.setBackgroundResource(R.drawable.selector_fff_f6);
        int dp15 = ScreenUtil.dpToPx(15);
        txt.setPadding(0, dp15, 0, dp15);
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(0xFF111111);
        MarginLayoutParams lp = new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txt.setLayoutParams(lp);
        txt.setText(entity.name);
        if (Tool.isNotEmpty(entity.methodName)) {
            ViewSpanner.bindClick(txt, entity.methodName, this);
        } else if (entity.clickListener != null) {
            txt.setOnClickListener(v -> {
                entity.clickListener.onClick(v);
                if (entity.autoDismissAfterClose) dismiss();
            });
        } else if(entity.menuClickListener != null){
            txt.setOnClickListener(v -> {
                entity.menuClickListener.onMenuClick(entity.index);
                if (entity.autoDismissAfterClose) dismiss();
            });
        }
        return txt;
    }


    public void add(MenuEntity entity) {
        entity.index = mEntities.size();
        mEntities.add(entity);
        View v = getItemView(entity);
        mContentContainer.addView(v);
    }

    public void add(MenuEntity entity, int index){
        mEntities.add(index, entity);
        int N = mEntities.size();
        for (int i = 0; i<N; i++){
            mEntities.get(i).index = i;
        }
        View v = getItemView(entity);
        mContentContainer.addView(v, index);
    }

    public void addCancel(){
        add(CANCEL_ENTITY);
    }

    public void removeMenu(int index){
        mEntities.remove(index);
        mContentContainer.removeViewAt(index);
        int N = mEntities.size();
        for (int i = 0; i<N; i++){
            mEntities.get(i).index = i;
        }
    }

    public static class MenuEntity {
        public String name;
        public String methodName;
        public OnClickListener clickListener;
        public OnMenuClickListener menuClickListener;
        public boolean autoDismissAfterClose = true;
        int index;

        public MenuEntity(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
        }

        public MenuEntity(String name) {
            this.name = name;
        }

        public MenuEntity(String name, OnClickListener clickListener) {
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
