package com.feximin.library.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feximin.library.R;
import com.feximin.library.adatper.SimplePagerAdapter;
import com.feximin.library.util.ScreenUtil;
import com.feximin.library.util.Tool;
import com.feximin.library.util.anim.AnimatorStatus;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Neo on 16/2/1.
 */
public class ActivityImageShower extends BaseActivity{
    protected ViewPager mPager;
    protected ImageButton mButBack;
    protected TextView mTxtTitle;
    protected RelativeLayout mRlTitle;
    protected AdapterShower mAdapter;
    private int mTitleHeight = ScreenUtil.dpToPx(48);

    @AnimatorStatus.Status
    private int mCurStatus = AnimatorStatus.VISIBLE;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.mPager = getViewById(R.id.pager);
        this.mButBack = getViewById(R.id.but_back, "finish");
        this.mTxtTitle = getViewById(R.id.txt_title);
        this.mRlTitle = getViewById(R.id.rl_top);
        this.mAdapter = new AdapterShower(this);
        this.mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setTitlePageIndex(position);
            }
        });

        ArrayList<String> data = getIntent().getStringArrayListExtra(Constant.DATA);
        mAdapter.add(data);
        int index = getIntent().getIntExtra(Constant.INDEX, 0);
        setTitlePageIndex(index);
        if(index != 0) mPager.setCurrentItem(index);
    }

    private void setTitlePageIndex(int position){
        mTxtTitle.setText(String.format("%s/%s", position + 1, mAdapter.getCount()));
    }

    public static final void startActivity(Context activity, ArrayList<String> data, int index){
        if(Tool.isEmpty(data)) return;
        Intent intent = new Intent(activity, ActivityImageShower.class);
        intent.putStringArrayListExtra(Constant.DATA, data);
        if(index < 0) index = 0;
        if(index > data.size() -1) index = data.size() - 1;
        intent.putExtra(Constant.INDEX, index);
        activity.startActivity(intent);
    }
    public static final void startActivity(Context activity, ArrayList<String> data){
        startActivity(activity, data, 0);
    }

    public static final void startActivity(Context activity, String imgUrl){
        ArrayList<String> list = new ArrayList<>(1);
        list.add(imgUrl);
        startActivity(activity, list);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_image_shower;
    }

    private void toggleTitle(){
        if(mCurStatus == AnimatorStatus.ACTIVE) return;
        ObjectAnimator animator;
        boolean isToShow = true;
        if(mCurStatus == AnimatorStatus.VISIBLE){
            isToShow = false;
            animator = ObjectAnimator.ofFloat(mRlTitle, "translationY", 0, -mTitleHeight).setDuration(200);
        }else{
            mRlTitle.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(mRlTitle, "translationY", -mTitleHeight, 0).setDuration(200);
        }
        mCurStatus = AnimatorStatus.ACTIVE;
        final boolean finalIsToShow = isToShow;
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(finalIsToShow){
                    mCurStatus = AnimatorStatus.VISIBLE;
                }else{
                    mRlTitle.setVisibility(View.GONE);
                    mCurStatus = AnimatorStatus.GONE;
                }
            }
        });
        animator.start();
    }

    class AdapterShower extends SimplePagerAdapter<String> {

        public AdapterShower(BaseActivity activity) {
            super(activity);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(mActivity);
            loadImage(photoView, getItem(position));
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setOnViewTapListener((v, x, y) -> toggleTitle());
            return photoView;
        }
    }
}
