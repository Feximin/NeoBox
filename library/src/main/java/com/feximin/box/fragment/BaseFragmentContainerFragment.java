package com.feximin.box.fragment;

import com.feximin.library.R;

/**
 * Created by Neo on 16/4/22.
 */
public abstract class BaseFragmentContainerFragment extends BaseFragment {

    @Override
    public int initFragmentContainerId() {
        return R.id.container;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.common_empty_framelayout;
    }
}
