package com.feximin.library.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mianmian.guild.R;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.ViewSpanner;

public class BaseTextDialog extends BaseDialogFragment {
	private TextView mTxtOk;
	private ImageButton mTxtCancel;

	private String mOkText;
	private View.OnClickListener mOkListener;
	private View.OnClickListener mCancelListener;

	private TextView mTxtTitle;

	private TextView mTxtContent;
	private String mContent;
	private boolean mHideOkBut;

	private String mTitleText;

	
	protected void initViews() {
		mTxtContent = ViewSpanner.getViewById(mRootView, R.id.txt_content);
		mTxtContent.setText(mContent);
		mTxtOk = ViewSpanner.getViewById(mRootView, R.id.txt_ok);
		if(mHideOkBut) mTxtOk.setVisibility(View.GONE);
		mTxtCancel = ViewSpanner.getViewById(mRootView, R.id.but_close);

		mTxtTitle = ViewSpanner.getViewById(mRootView, R.id.txt_title);
		if(Tool.isNotEmpty(mTitleText)) mTxtTitle.setText(mTitleText);

		if(Tool.isNotEmpty(mOkText)) mTxtOk.setText(mOkText);

		ViewSpanner.bindClick(mTxtOk, "onOkButtonClick", this);
		ViewSpanner.bindClick(mTxtCancel, "onCancelButtonClick", this);
	}

	protected void onOkButtonClick(){
		dismiss();
		if(mOkListener != null) mOkListener.onClick(mTxtOk);
	}

	protected void onCancelButtonClick(){
		dismiss();
		if(mCancelListener != null) mOkListener.onClick(mTxtCancel);
	}

	protected int getLayoutResId(){
		return R.layout.dialog_text;
	}

	public static class Builder{
		private String okText;
		private View.OnClickListener okListener;
		private View.OnClickListener cancelListener;
		private String contentText;
		private boolean hideOkBut;
		private String titleText;

		public Builder content(String text){
			this.contentText = text;
			return this;
		}
		public Builder ok(String text){
			this.okText = text;
			return this;
		}

		public Builder okListener(View.OnClickListener listener){
			okListener = listener;
			return this;
		}

		public Builder cancelListener(View.OnClickListener listener){
			cancelListener = listener;
			return this;
		}

		public Builder hideOk(){
			hideOkBut = true;
			return this;
		}

		public Builder title(String title){
			this.titleText = title;
			return this;
		}


		public BaseTextDialog build(){
			BaseTextDialog dialog = new BaseTextDialog();
			dialog.mOkText = okText;
			dialog.mOkListener = okListener;
			dialog.mCancelListener = cancelListener;
			dialog.mContent = contentText;
			dialog.mHideOkBut = hideOkBut;
			dialog.mTitleText = titleText;
			return dialog;
		}
	}
	
}
