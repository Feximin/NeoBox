package com.feximin.library.util.notification;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Neo on 16/3/24.
 */
public abstract class Not {

    protected int mNid;
    protected Object mTargetId;
    protected Context mContext;

    private static final Map<Integer, Map<Object, Integer>> sTargetNidMap = new HashMap<>();
    private static final AtomicInteger sNidSeed = new AtomicInteger(2 << 10);		//如果nid不变的话需要小于这个初始值
    private int generateNewNid() {			//产生通知栏的id
        int nid = sNidSeed.getAndAdd(1);
        Map<Object, Integer> map = sTargetNidMap.get(Integer.valueOf(getType()));
        if (map == null){
            map = new HashMap<>();
            sTargetNidMap.put(Integer.valueOf(getType()), map);
        }
        map.put(mTargetId, nid);
        return nid;
    }

    public Not(Context context){
        this.mContext = context;
    }

    public void clear(Object targetId){
        clear(fetchNid(targetId));
    }

    public void clear(int nid){

    }

    public void clear(){

    }

    //有多类Not, 每种not可发布多个，需要以type来区分
    public abstract int getType();

    //需要根据
    protected int generateNid(){
        int id = fetchNid(mTargetId);
        if (id <= 0) id = generateNewNid();
        return id;
    }

    public int getNid(){
        return mNid;
    }


    private int fetchNid(Object targetId){
        Map<Object, Integer> map = sTargetNidMap.get(Integer.valueOf(getType()));
        if (map != null && map.containsKey(targetId)){
            int id = map.get(targetId);
            return id;
        }
        return -1;
    }

    public abstract NotMeta generateNotMeta();

}
