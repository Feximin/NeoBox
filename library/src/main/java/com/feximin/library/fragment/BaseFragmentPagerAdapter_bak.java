package com.feximin.library.fragment;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mianmian.guild.util.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 15/11/26.
 */
public class BaseFragmentPagerAdapter_bak extends PagerAdapter {
    protected List<Entity> mFragments;
    public BaseFragmentPagerAdapter_bak(FragmentManager fm) {
        mFragmentManager = fm;
        this.mFragments = new ArrayList<>();
    }

//    public void addFragment(String title, Fragment fragment){
//        this.mFragments.add(new Entity(title, fragment));
//    }
    public void addFragment(String title, Class<? extends Fragment> clazz){
        this.mFragments.add(new Entity(title, clazz));
    }

    public void addFragment(Fragment fragment){
        this.mFragments.add(new Entity(fragment));
    }

    public Fragment getItem(int position) {
        Entity entity = mFragments.get(position);
        if(entity.fragment == null){
            entity.fragment = Tool.newInstance(entity.fragmentClazz);
        }
        return entity.fragment;
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
        public Fragment fragment;
        public Class<? extends Fragment> fragmentClazz;

        public Entity(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
        public Entity(String title, Class<? extends Fragment> clazz) {
            this.title = title;
            this.fragmentClazz = clazz;
        }
        public Entity(Fragment fragment) {
            this.fragment = fragment;
        }
    }



    /*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


    /**
     * Implementation of {@link PagerAdapter} that
     * represents each page as a {@link Fragment} that is persistently
     * kept in the fragment manager as long as the user can return to the page.
     *
     * <p>This version of the pager is best for use when there are a handful of
     * typically more static fragments to be paged through, such as a set of tabs.
     * The fragment of each page the user visits will be kept in memory, though its
     * view hierarchy may be destroyed when not visible.  This can result in using
     * a significant amount of memory since fragment instances can hold on to an
     * arbitrary amount of state.  For larger sets of pages, consider
     * {@link FragmentStatePagerAdapter}.
     *
     * <p>When using FragmentPagerAdapter the host ViewPager must have a
     * valid ID set.</p>
     *
     * <p>Subclasses only need to implement {@link #getItem(int)}
     * and {@link #getCount()} to have a working adapter.
     *
     * <p>Here is an example implementation of a pager containing fragments of
     * lists:
     *
     * {@sample development/samples/Support4Demos/src/com/example/android/supportv4/app/FragmentPagerSupport.java
     *      complete}
     *
     * <p>The <code>R.layout.fragment_pager</code> resource of the top-level fragment is:
     *
     * {@sample development/samples/Support4Demos/res/layout/fragment_pager.xml
     *      complete}
     *
     * <p>The <code>R.layout.fragment_pager_list</code> resource containing each
     * individual fragment's layout is:
     *
     * {@sample development/samples/Support4Demos/res/layout/fragment_pager_list.xml
     *      complete}
     */
        private static final String TAG = "FragmentPagerAdapter";
        private static final boolean DEBUG = false;

        private final FragmentManager mFragmentManager;
        private FragmentTransaction mCurTransaction = null;
        private Fragment mCurrentPrimaryItem = null;

        /**
         * Return the Fragment associated with a specified position.
         */

        @Override
        public void startUpdate(ViewGroup container) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }

            final long itemId = getItemId(position);

            // Do we already have this fragment?
            String name = makeFragmentName(container.hashCode(), itemId);
            Fragment fragment = mFragmentManager.findFragmentByTag(name);
            if (fragment != null) {
                if (DEBUG) Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
                mCurTransaction.attach(fragment);
            } else {
                fragment = getItem(position);
                if (DEBUG) Log.v(TAG, "Adding item #" + itemId + ": f=" + fragment);
                mCurTransaction.add(container.getId(), fragment,
                        makeFragmentName(container.hashCode(), itemId));
            }
            if (fragment != mCurrentPrimaryItem) {
                fragment.setMenuVisibility(false);
                fragment.setUserVisibleHint(false);
            }

            return fragment;
        }


        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            Fragment fragment = (Fragment)object;
            if (fragment != mCurrentPrimaryItem) {
                if (mCurrentPrimaryItem != null) {
                    mCurrentPrimaryItem.setMenuVisibility(false);
                    mCurrentPrimaryItem.setUserVisibleHint(false);
                }
                if (fragment != null) {
                    fragment.setMenuVisibility(true);
                    fragment.setUserVisibleHint(true);
                }
                mCurrentPrimaryItem = fragment;
            }
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            if (mCurTransaction != null) {
                mCurTransaction.commitAllowingStateLoss();
                mCurTransaction = null;
                mFragmentManager.executePendingTransactions();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return ((Fragment)object).getView() == view;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        /**
         * Return a unique identifier for the item at the given position.
         *
         * <p>The default implementation returns the given position.
         * Subclasses should override this method if the positions of items can change.</p>
         *
         * @param position Position within this adapter
         * @return Unique identifier for the item at position
         */
        public long getItemId(int position) {
            return position;
        }

        private static String makeFragmentName(int viewId, long id) {
            return "android:switcher:" + viewId + ":" + id;
        }

}