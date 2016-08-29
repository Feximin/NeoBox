package com.feximin.box.activity;

import android.content.Intent;
import android.os.Bundle;

import com.feximin.box.fragment.BaseFragment;
import com.feximin.box.util.Tool;
import com.feximin.library.R;

/**
 * Created by Neo on 16/4/22.
 */
public class BaseFragmentContainerActivity extends BaseActivity {


    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle;
            if (intent != null && (bundle = intent.getExtras()) != null) {
                Class<? extends BaseFragment> clazz = (Class<? extends BaseFragment>) bundle.getSerializable("clazz");
                if (clazz != null) {
                    BaseFragment fragment = Tool.newInstance(clazz);
                    fragment.setArguments(bundle);
                    replace(fragment);
                }
            }
        }
    }

    @Override
    public int initFragmentContainerId() {
        return R.id.container;
    }

    public static void startActivity(BaseActivity activity, Class<? extends BaseFragment> clazz, Bundle...bundle){
        Intent intent = new Intent();
        intent.putExtra("clazz", clazz);
        Bundle realBundle = Tool.getFirstObject(bundle);
        if (realBundle != null) intent.putExtras(realBundle);
        intent.setClass(activity, BaseFragmentContainerActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.common_empty_framelayout;
    }
}
