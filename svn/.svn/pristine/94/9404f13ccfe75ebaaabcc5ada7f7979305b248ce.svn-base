package com.neo.box.view;

import android.app.Activity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.neo.box.R;
import com.neo.box.fragment.BaseFragment;
import com.neo.box.tools.FragmentTool;
import com.neo.box.tools.ScreenTool;

public abstract class BaseTabItem extends BaseViewFromCode{
		
		protected Class<? extends BaseFragment> mFragmentClass;
		
		protected BaseFragment mBaseFragment;
		
		public BaseTabItem(Activity activity) {
			super(activity);
		}

		public abstract void setSelected(boolean b);
		
		public BaseFragment getFragment(){
			return this.mBaseFragment;
		}
		
		public BaseFragment instance(){
			if(mBaseFragment == null){
				mBaseFragment = FragmentTool.newInstance(mFragmentClass);
			}
			return this.mBaseFragment;
		}


	}