package com.feximin.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mianmian.guild.Constant;
import com.mianmian.guild.R;
import com.mianmian.guild.impl.TextWatcherAdapter;
import com.mianmian.guild.util.Tool;

/**
 * Created by Neo on 16/1/25.
 */
public class BaseEditableActivity extends BaseTitleActivity {

    private EditText mEdit;
    private TextView mTxtMaxCount;
    private Options mOptions;
    private View mLine;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        this.mOptions = getIntent().getParcelableExtra(Constant.OPTIONS);
        if(this.mOptions == null) throw new IllegalArgumentException("options can not be null !!");
        setTitleText(mOptions.title);
        setLeftButDefaultListener();
        this.mEdit = getViewById(R.id.edit_content);
        if (mOptions.number) mEdit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        int maxCount = mOptions.maxCount;
        if(maxCount > 0){
            mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
            mEdit.addTextChangedListener(new TextWatcherAdapter() {

                @Override
                public void afterTextChanged(Editable edit) {
                    int start = mEdit.getSelectionStart();
                    String content = edit.toString();
                    mEdit.setSelection(start > maxCount ? maxCount :start);
                    mTxtMaxCount.setText(maxCount - content.length() + "");

                }
            });
            this.mLine = getViewById(R.id.line);
            mLine.setVisibility(View.VISIBLE);
            this.mTxtMaxCount = getViewById(R.id.txt_count);
            mTxtMaxCount.setVisibility(View.VISIBLE);
            mTxtMaxCount.setText(maxCount + "");
        }
        mEdit.setHint(mOptions.hint);
        String content = mOptions.content;
        if(Tool.isNotEmpty(content)){
            mEdit.setText(content);
            mEdit.setSelection(content.length());
        }
        String okText = mOptions.okText;
        if(Tool.isNotEmpty(okText)) mTitleBar.setRightText(okText, v -> doOk());

    }

    void doOk(){
        setResult();
        finish();
    }

    private void setResult(){
        Intent intent = new Intent();
        intent.putExtra(Constant.DATA, Tool.getContent(mEdit));
        setResult(Activity.RESULT_OK,intent);
    }


    @Override
    protected void onFinishing() {
        if(Tool.isEmpty(mOptions.okText)) setResult();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_editable;
    }

    public static void startActivity(Activity activity, Options options){
        Intent intent = new Intent(activity, BaseEditableActivity.class);
        intent.putExtra(Constant.OPTIONS, options);
        activity.startActivityForResult(intent, options.requestCode);
    }

    public static class Options implements Parcelable {
        public int maxCount;
        public String hint;
        public String title;
        public String okText;           //不为空就表示，点击了确定才会保存，否则就是finish就保存
        public String content;
        public int requestCode;
        public boolean number;              //是否是数字
//        public static final int SAVE_ON_OK = 0;
//        public static final int SAVE_ON_FINISH = 1;
//        public int saveMode = SAVE_ON_OK;        //默认是点击了确定才会保存

        public Options(){}

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.maxCount);
            dest.writeString(this.hint);
            dest.writeString(this.title);
            dest.writeString(this.okText);
            dest.writeString(this.content);
            dest.writeInt(this.requestCode);
            dest.writeByte(number ? (byte) 1 : (byte) 0);
        }

        protected Options(Parcel in) {
            this.maxCount = in.readInt();
            this.hint = in.readString();
            this.title = in.readString();
            this.okText = in.readString();
            this.content = in.readString();
            this.requestCode = in.readInt();
            this.number = in.readByte() != 0;
        }

        public static final Creator<Options> CREATOR = new Creator<Options>() {
            @Override
            public Options createFromParcel(Parcel source) {
                return new Options(source);
            }

            @Override
            public Options[] newArray(int size) {
                return new Options[size];
            }
        };
    }
}
