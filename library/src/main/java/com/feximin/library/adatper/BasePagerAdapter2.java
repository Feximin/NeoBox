package com.feximin.library.adatper;

import android.view.View;
import android.view.ViewGroup;

import com.feximin.library.activity.BaseActivity;
import com.mianmian.guild.R;
import com.mianmian.guild.util.Tool;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePagerAdapter2<T> extends BasePagerAdapter<T> {

	protected List<View> mViews;

	public BasePagerAdapter2(BaseActivity activity) {
		super(activity);
		mViews = new ArrayList<>();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	// 销毁position位置的界面
	@Override
	public void destroyItem(ViewGroup v, int position, Object arg2) {
		v.removeView(destroyView(getRealPosition(position)));
	}


	// 初始化position位置的界面
	@Override
	public Object instantiateItem(ViewGroup v, int position) {
		View child = getView(getRealPosition(position));
		v.addView(child);
		return child;
	}

	protected int getRealPosition(int position){
		return position;
	}


	protected View getView(int position){
		View child = null;
		int viewType = getViewType(position);
		for(View v: mViews){
			int tag = Tool.getInt(v.getTag(TAG_POSITION), -1);
			int type = Tool.getInt(v.getTag(TAG_TYPE), -1);
			if(tag == position && type == viewType){
				child = v;
				break;
			}
		}

		if(child == null){
			for(View v: mViews){
				int type = Tool.getInt(v.getTag(TAG_TYPE), -1);
				if(v.getTag(TAG_POSITION) == null && type == viewType){
					child = v;
					break;
				}
			}
			if(child == null){
//				child = mInflater.inflate(getItemLayoutResId(position), null);
				child = inflateView(position);
				mViews.add(child);
			}
		}
//		Tool.showToast(mViews.size() + "");
		child.setTag(TAG_POSITION, position);
		child.setTag(TAG_TYPE, viewType);
		fillViewContent(child, position);
		return child;
	}

	protected static final int TAG_POSITION = R.id.tag_position;
	protected static final int TAG_TYPE = R.id.tag_type;

	protected View destroyView(int position){
		for(View v: mViews){
			int i = Tool.getInt(v.getTag(TAG_POSITION), -1);
			if(i == position){
				v.setTag(TAG_POSITION, null);
				return v;
			}
		}
		return null;
	}
}
