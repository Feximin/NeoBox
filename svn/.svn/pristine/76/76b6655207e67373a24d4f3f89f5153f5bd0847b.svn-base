package com.neo.box.view;

import android.app.Activity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neo.box.fragment.BaseFragment;
import com.neo.box.tools.Tool;

public class TabItem extends BaseTabItem{
		
		private ImageView mImg;
		private TextView mTxt;
		
		public TabItem(Activity activity, String title, int imgResId, Class<? extends BaseFragment> clazz) {
			super(activity);
			this.mImg.setImageResource(imgResId);
			this.mTxt.setText(title);
			this.mFragmentClass = clazz;
		}

		@Override
		public void inflateViewFromCode() {
			mRootView = new RelativeLayout(mActivity);
			int padding = Tool.dpToPx(2);
			mRootView.setPadding(padding, padding, padding, padding);
			mTxt = new TextView(mActivity);
			mTxt.setTextSize(11);
			mTxt.setSingleLine();
			mTxt.setGravity(Gravity.CENTER);
			mImg = new ImageView(mActivity);
			mImg.setPadding(0, 0, 0, padding);
			
			int txtId = Tool.generateViewId();
			int imgId = Tool.generateViewId();
			
			mTxt.setId(txtId);
			mImg.setId(imgId);
			
			RelativeLayout.LayoutParams lpTxt = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lpTxt.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lpTxt.addRule(RelativeLayout.CENTER_IN_PARENT);
			mRootView.addView(mTxt, lpTxt);
			RelativeLayout.LayoutParams lpImg = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			lpImg.addRule(RelativeLayout.CENTER_IN_PARENT);
			lpImg.addRule(RelativeLayout.ABOVE, txtId);
			mRootView.addView(mImg, lpImg);
		}
		public void setSelected(boolean b){
			this.mImg.setSelected(b);
			this.mRootView.setSelected(b);
			this.mTxt.setSelected(b);
		}
	}