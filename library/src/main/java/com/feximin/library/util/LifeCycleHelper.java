package com.feximin.library.util;

import com.mianmian.guild.interfaces.ILifeCycle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neo on 16/4/11.
 */
public class LifeCycleHelper {
    private Set<ILifeCycle> mSet = new HashSet<>();

    public static LifeCycleHelper obtain(){
        return new LifeCycleHelper();
    }
    public void addLifeCycleComponent(ILifeCycle lifeCycle){
        if (mSet == null) mSet = new HashSet<>();
        mSet.add(lifeCycle);
    }

    public void destroy(){
        if (mSet == null) return;
        for (ILifeCycle c : mSet){
            if (c != null) c.onDestroy();
        }
    }
}
