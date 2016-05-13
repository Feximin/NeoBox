package com.feximin.library.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mianmian.guild.R;
import com.mianmian.guild.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 16/4/21.
 */
public class BaseMenuDialogFragment extends BaseDialogFragment {
    private List<Menu> mMenuList = new ArrayList<>(1);
    private LinearLayout mMenuContainer;
    int dp12 = ScreenUtil.dpToPx(12);
    int dp15 = ScreenUtil.dpToPx(15);
    @Override
    protected void initViews() {
        this.mMenuContainer = getViewById(R.id.root);
        int N = mMenuList.size();
        for (int i = 0; i<N; i++){
            Menu menu = mMenuList.get(i);
            addItem(menu,i);
        }
    }

    public void remove(int index){
        int N = mMenuContainer.getChildCount();
        if (N == 0) return;
        if (index < 0 || index > N - 1) return;
        mMenuContainer.removeViewAt(index);
        N = mMenuContainer.getChildCount();
        if (N == 0) return;

        for (int i = 0; i<N; i++){
            View tv = mMenuContainer.getChildAt(i);
            setMenuBg(tv, i, N);
        }
    }

    private void setMenuBg(View tv, int i, int N){
        if (N == 1){
            tv.setBackgroundResource(R.drawable.selector_fff_f6_corner_3dp);
        }else  if (i == 0) {
            tv.setBackgroundResource(R.drawable.selector_fff_f6_corner_3_3_0_0);
        }else if (i == N - 1){
            tv.setBackgroundResource(R.drawable.selector_fff_f6_corner_0_0_3_3);
        }else{
            tv.setBackgroundResource(R.drawable.selector_fff_f6);
        }
    }

    public void addItem(Menu menu, int i){
        TextView tv = new TextView(getContext());
        tv.setGravity(Gravity.LEFT);
        tv.setTextSize(15);
        tv.setTextColor(0xFF333333);
        tv.setText(menu.content);
        tv.setOnClickListener(v -> {
            dismiss();
            menu.listener.onClick(v);
        });
        tv.setPadding(dp15, dp12, dp15, dp12);
        setMenuBg(tv, i, mMenuList.size());
        mMenuContainer.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_menu_list;
    }
    public static class Menu{
        public String content;
        public View.OnClickListener listener;
        public Menu(){}
        public Menu(String content, View.OnClickListener listener){
            this.content = content;
            this.listener = listener;
        }
    }



    public static class Builder{
        List<Menu> menuList = new ArrayList<>(1);
        public  Builder item(Menu menu){
            menuList.add(menu);
            return this;
        }


        public BaseMenuDialogFragment build(){
            BaseMenuDialogFragment dialog = new BaseMenuDialogFragment();
            dialog.mMenuList.addAll(menuList);
            return dialog;
        }
    }
}
