package com.feximin.library.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mianmian.guild.util.Tool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Neo on 15/11/26.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    protected List<Entity> mFragments;
    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragments = new ArrayList<>();
    }
    public BaseFragmentPagerAdapter(FragmentManager fm, List<Entity> list) {
        super(fm);
        this.mFragments = list;
    }

//    public void addFragment(String title, Fragment fragment){
//        this.mFragments.add(new Entity(title, fragment));
//    }
    public void addFragment(String title, Class<? extends BaseFragment> clazz){
        this.mFragments.add(new Entity(title, clazz));
    }

    public void addFragment(Class<? extends BaseFragment> clazz){
        this.mFragments.add(new Entity(clazz));
    }

    @Override
    public Fragment getItem(int position) {
        Entity entity = mFragments.get(position);
        if(entity.fragment == null){
            if (entity.param != null){
                entity.fragment = Tool.newInstance(entity.fragmentClazz, entity.param);
            }else {
                entity.fragment = Tool.newInstance(entity.fragmentClazz);
            }
            for (OnNewFragmentListener listener : mOnNewFragmentListenerSet){
                listener.onNew(entity.fragment, position);
            }
        }
        return entity.fragment;
    }

    public Entity getEntity(int position){
        if (position >=0 && position < getCount()){
            return mFragments.get(position);
        }
        return null;
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //默认不销毁
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).title;
    }

    public static class Entity{
        public String title;
        public BaseFragment fragment;
        public Class<? extends BaseFragment> fragmentClazz;
        public Object param;

        public Entity(String title, BaseFragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
        public Entity(String title, Class<? extends BaseFragment> clazz) {
            this.title = title;
            this.fragmentClazz = clazz;
        }

        public Entity(String title, Class<? extends BaseFragment> clazz, Object param){
            this(title, clazz);
            this.param = param;
        }

        public Entity(Class<? extends BaseFragment> clazz, Object param){
            this.fragmentClazz = clazz;
            this.param = param;
        }

        public Entity(BaseFragment fragment) {
            this.fragment = fragment;
        }

        public Entity(Class<? extends BaseFragment> clazz){
            this.fragmentClazz = clazz;
        }
    }

    private Set<OnNewFragmentListener> mOnNewFragmentListenerSet = new HashSet<>(1);
    public void addOnNewFragmentListener(OnNewFragmentListener listener){
        if (listener == null) return;
        mOnNewFragmentListenerSet.add(listener);
    }

    public static interface OnNewFragmentListener{
        void onNew(BaseFragment fragment, int index);
    }
}
