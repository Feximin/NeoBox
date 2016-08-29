/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.feximin.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feximin.box.util.ViewSpanner;
import com.feximin.library.R;


public class NListViewFooter extends LinearLayout {

	private TextView mHintTxt;

	public NListViewFooter(Context context) {
		this(context, null);
	}

	public NListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		setGravity(Gravity.CENTER_HORIZONTAL);
		setBackgroundResource(R.drawable.selector_fff_eee);
		inflate(context, R.layout.footer_load_more, this);
		mHintTxt = ViewSpanner.getViewById(this, R.id.txt_load_more);
		setOnClickListener(v -> {if(mErrorClickListener != null && mCurStatus == Status.ERROR) mErrorClickListener.onErrorClick();});
		setStatus(Status.HIDE);			//默认是不会自动加载更多的
	}
	public enum Status{
		NONE, FETCHING, NO_MORE, ERROR, ENABLE, DISABLE, HIDE
	}

	private OnErrorClickListener mErrorClickListener;
	public void setOnErrorClickListener(OnErrorClickListener listener){
		mErrorClickListener = listener;
	}
	/**
	 * 出错的时候点击之后的操作
	 */
	public static interface OnErrorClickListener{
		void onErrorClick();
	}

	private Status mCurStatus = Status.NONE;

	public void setStatus(Status status) {
		if(mCurStatus != status){
			if(mCurStatus == Status.DISABLE){
                if (status != Status.ENABLE) return;
            }
			if (mCurStatus == Status.HIDE){
				enabledLoadMore(true);
			}
			switch (status){
				case FETCHING:
					mHintTxt.setText("正在加载");
					break;
				case NO_MORE:
					mHintTxt.setText("没有更多了");
					break;
				case ERROR:
					mHintTxt.setText("加载失败，点击重试");
					break;
				case ENABLE:
					enabledLoadMore(true);
					break;
				case DISABLE:
					enabledLoadMore(false);
					break;
				case HIDE:
					enabledLoadMore(false);
					break;
			}
			mCurStatus = status;
		}

	}

	public Status getCurStatus(){
		return mCurStatus;
	}

	public void enabledLoadMore(boolean b) {
        ViewGroup.LayoutParams lp = mHintTxt.getLayoutParams();
        if (lp != null) {
            lp.height = b ? LayoutParams.WRAP_CONTENT : 0;
            mHintTxt.requestLayout();
        }
	}
}
