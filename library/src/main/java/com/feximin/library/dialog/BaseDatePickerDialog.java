
package com.feximin.library.dialog;

import android.widget.DatePicker;
import android.widget.TextView;

import com.mianmian.guild.R;
import com.mianmian.guild.util.Tool;
import com.mianmian.guild.util.ViewSpanner;

public class BaseDatePickerDialog extends BaseDialogFragment {
    private DatePicker mDatePicker;
    private TextView mTxtOk;
    private TextView mTxtCancel;

    private String mOkText;
    private String mCancelText;
    private int mCurYear;
    private int mCurMonth;
    private int mCurDay;

    public BaseDatePickerDialog(){}

    protected void initViews() {
        mTxtOk = ViewSpanner.getViewById(mRootView, R.id.txt_ok);
        mTxtCancel = ViewSpanner.getViewById(mRootView, R.id.txt_cancel);
        mDatePicker = ViewSpanner.getViewById(mRootView, R.id.date_picker);
        mDatePicker.init(mCurYear, mCurMonth, mCurDay, null);
        if (Tool.isNotEmpty(mOkText)) mTxtOk.setText(mOkText);
        if (Tool.isNotEmpty(mCancelText)) mTxtCancel.setText(mCancelText);

        ViewSpanner.bindClick(mTxtOk, "onOkButtonClick", this);
        ViewSpanner.bindClick(mTxtCancel, "onCancelButtonClick", this);
    }

    protected void onOkButtonClick() {
        dismiss();
        if (mOnDateSelectListener != null) mOnDateSelectListener.onDateSelected(mDatePicker.getYear(), mDatePicker.getMonth()+1, mDatePicker.getDayOfMonth());
    }

    public void init(int year, int month, int day){
        this.mCurDay = year;
        this.mCurMonth = month;
        this.mCurDay = day;
    }

    protected void onCancelButtonClick() {
        dismiss();
    }

    public interface OnDateSelectListener{
        void onDateSelected(int year, int month, int day);
    }

    private OnDateSelectListener mOnDateSelectListener;
    public void setOnDateSelecteListener(OnDateSelectListener listener){
        this.mOnDateSelectListener = listener;
    }


    protected int getLayoutResId() {
        return R.layout.dialog_datepicker;
    }


}

