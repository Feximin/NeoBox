package com.feximin.box.view;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.fragment.BaseFragment;
import com.feximin.box.util.Tool;


public abstract class BaseTabItem extends BaseView {

    protected Class<? extends BaseFragment> mFragmentClass;

    protected BaseFragment mFragmentInstance;

    public BaseTabItem(BaseActivity activity, Class<? extends  BaseFragment> clazz) {
        super(activity);
        this.mFragmentClass = clazz;
    }

    public abstract void setSelected(boolean b);

    public BaseFragment getFragment() {
        return this.mFragmentInstance;
    }

    public BaseFragment instance() {
        if (mFragmentInstance == null) {
            mFragmentInstance = Tool.newInstance(mFragmentClass);
        }
        return this.mFragmentInstance;
    }


}