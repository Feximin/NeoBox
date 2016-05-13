package com.feximin.library.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.feximin.library.activity.BaseActivity;
import com.mianmian.guild.Constant;
import com.mianmian.guild.R;
import com.mianmian.guild.util.Tool;

import java.util.UUID;

/**
 * Created by Neo on 16/4/22.
 */
public abstract class BaseFragmentContainerActivity extends BaseActivity {
    private String TAG;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.TAG = Tool.getStringFromBundleIfExist(savedInstanceState, Constant.TAG);
        this.randomTag();
        this.FRAGMENT_CONTAINER_ID = R.id.container_fragment;
    }

    private void randomTag(){
        if (Tool.isEmpty(TAG)){
            TAG = UUID.randomUUID().toString();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.TAG, TAG);
    }

    protected void replace(BaseFragment fragment){
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment oldF = manager.findFragmentByTag(TAG);
                if (oldF != null){
                    transaction.remove(oldF);
                }
                transaction.replace(getFragmentContainerId(), fragment, TAG);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment_container;
    }
}
