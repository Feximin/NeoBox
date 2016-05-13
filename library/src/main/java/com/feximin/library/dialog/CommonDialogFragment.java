package com.feximin.library.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mianmian.guild.R;
import com.mianmian.guild.util.ScreenUtil;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.ViewSpanner;

public class CommonDialogFragment extends BaseDialogFragment {
	private LinearLayout mContainerContent;
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
    private boolean mHideCloseBtn;

	
	protected void initViews() {
		mContainerContent = ViewSpanner.getViewById(mRootView, R.id.container);
		View view = initContentView();
		if(view != null){
			mContainerContent.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		}
		mTxtOk = ViewSpanner.getViewById(mRootView, R.id.txt_ok);
		if(mHideOkBut) mTxtOk.setVisibility(View.GONE);
		mTxtCancel = ViewSpanner.getViewById(mRootView, R.id.but_close);
        if (mHideCloseBtn) mTxtCancel.setVisibility(View.GONE);

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

	public View initContentView(){
		mTxtContent = new TextView(getActivity());
		int dp5 = ScreenUtil.dpToPx(5);
		int dp10 = ScreenUtil.dpToPx(10);
		int dp40 = ScreenUtil.dpToPx(40);
		int dp100 = ScreenUtil.dpToPx(100);
		mTxtContent.setPadding(dp10, dp5, dp10, dp40);
		mTxtContent.setTextColor(0xFF555555);
		mTxtContent.setTextSize(16);
		mTxtContent.setText(mContent);
		mTxtContent.setMinHeight(dp100);
		mTxtContent.setGravity(Gravity.CENTER);
		return mTxtContent;
	}

	protected int getLayoutResId(){
		return R.layout.dialog_root;
	}

	public static class Builder{
		private String okText;
		private View.OnClickListener okListener;
		private View.OnClickListener cancelListener;
		private String contentText;
		private boolean hideOkBut;
		private String titleText;
		private boolean hideCloseBut;
		private boolean cancelAble = true;

		public Builder content(String text){
			this.contentText = text;
			return this;
		}

        public Builder content(int textResId){
            return (content(Tool.getString(textResId)));
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

		public Builder hideClose(){
			hideCloseBut = true;
			return this;
		}

		public Builder title(String title){
			this.titleText = title;
			return this;
		}

		public Builder notCancelable(){
			this.cancelAble = false;
			return this;
		}


		public CommonDialogFragment build(){
			CommonDialogFragment dialog = new CommonDialogFragment();
			dialog.mOkText = okText;
			dialog.mOkListener = okListener;
			dialog.mCancelListener = cancelListener;
			dialog.mContent = contentText;
			dialog.mHideOkBut = hideOkBut;
			dialog.mTitleText = titleText;
			dialog.mHideCloseBtn = hideCloseBut;
			dialog.setCancelable(cancelAble);
			return dialog;
		}
	}
	
}
