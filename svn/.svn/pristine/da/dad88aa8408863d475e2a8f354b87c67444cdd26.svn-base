package com.neo.box.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neo.box.R;

public class TitleBar extends BaseView {
	
	private ImageButton mButLeft, mButRight;
	private TextView mTxtTitle;
	private ProgressBar mProgress;

	public TitleBar(Activity activity) {
		super(activity);
	}
	
	public void initViews(){
		this.mButLeft = getViewById(R.id.but_title_bar_left);
		this.mButRight = getViewById(R.id.but_title_bar_right);
		this.mTxtTitle = getViewById(R.id.txt_title_bar);
		this.mProgress = getViewById(R.id.progress_title_bar);
		setLeftButDefault();
	}

	public void setTitle(String title){
		this.mTxtTitle.setVisibility(View.VISIBLE);
		this.mTxtTitle.setText(title);
	}
	
	public void setLeftBut(int resId, String methodName){
		
		this.mButLeft.setVisibility(View.VISIBLE);
		this.mButLeft.setImageResource(resId);
		setOnViewClickListener(mButLeft, methodName);
	}
	public void setLeftBut(String methodName){
		setLeftBut(R.drawable.img_but_back, methodName);
	}
	
	public void setLeftButDefault(){
		setLeftBut("finish");
	}
	
	public void setOnRightBut(int resId, String methodName){
		this.mButRight.setVisibility(View.VISIBLE);
		this.mButRight.setImageResource(resId);
		setOnViewClickListener(mButRight, methodName);
		
	}
	
	public void setVisibilityOfLoading(boolean b){
		this.mProgress.setVisibility(b?View.VISIBLE:View.GONE);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.view_title_bar;
	}
}
