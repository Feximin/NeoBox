package com.feximin.box.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.feximin.box.util.Tool;

/**
 * Created by Neo on 16/8/5.
 */

public class FragmentHelper {


    public static void replace(FragmentManager manager, BaseFragment fragment, int containerId, String...tag){
        if (manager != null){
            String realTag = Tool.getFirstObject(tag);
            FragmentTransaction transaction = manager.beginTransaction();
            if (Tool.isEmpty(realTag)){
                transaction.replace(containerId, fragment);
            }else{
                Fragment oldF = manager.findFragmentByTag(realTag);
                if (oldF != null) transaction.remove(oldF);
                transaction.replace(containerId, fragment, realTag);
            }
            transaction.commitAllowingStateLoss();
        }
    }

}
