package com.feximin.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.feximin.box.Constant;
import com.feximin.box.util.ScreenUtil;
import com.feximin.box.util.Tool;
import com.feximin.box.util.ViewSpanner;
import com.feximin.box.util.recorder.Config;
import com.feximin.box.util.recorder.RecorderManager;
import com.feximin.box.util.recorder.SimpleOnRecorderListener;
import com.feximin.library.R;

import java.io.File;

/**
 * Created by Neo on 16/6/10.
 */

public class RecordView extends BaseBottomMenuView {
    private TextView mTvTime;
    private ImageView mIvController;
    private TextView mTvOk;
    private TextView mTvCancel;
    private boolean mIsRecording;
    private RecorderManager mRecorderManager;

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTvTime = ViewSpanner.getViewById(this, R.id.tv_time);
        this.mTvOk = ViewSpanner.getViewById(this, R.id.tv_ok, v -> doOk());
        this.mTvCancel = ViewSpanner.getViewById(this, R.id.tv_cancel, v -> doCancel());
        this.mIvController = ViewSpanner.getViewById(this, R.id.iv_controller, v -> doOp());
    }

    private void doOp(){
        ensureRecorderManager();
        if (mIsRecording){
            mIsRecording = false;
            int dp20 = ScreenUtil.dpToPx(20);
            mIvController.setPadding(dp20, dp20, dp20, dp20);
            mIvController.setImageResource(R.drawable.selector_circle_red);
            mRecorderManager.stop();
        }else{
            mIsRecording = true;
            int dp20 = ScreenUtil.dpToPx(30);
            mIvController.setPadding(dp20, dp20, dp20, dp20);
            mIvController.setImageResource(R.drawable.selector_corner_10dp_primary);
            mRecorderManager.start();
        }
    }

    private void doOk(){
        if (mIsRecording) {
            doOp();
        }
        if (mOnAudioSelectListener != null){
            File f = mRecorderManager.getAudioFile();
            long duration = mRecorderManager.getDuration();
            if (f != null && duration > 0) {
                dismiss();
                mTvTime.setText(R.string.click_record);
                mOnAudioSelectListener.onAudioSelect(f, duration);
            }
        }
    }

    private void doCancel(){
        if (mIsRecording) {
            mIsRecording = false;
            mRecorderManager.cancel();
            int dp20 = ScreenUtil.dpToPx(20);
            mTvTime.setText(R.string.click_record);
            mIvController.setPadding(dp20, dp20, dp20, dp20);
            mIvController.setImageResource(R.drawable.selector_circle_red);
        }
        dismiss();
    }

    private void ensureRecorderManager(){
        if (mRecorderManager == null){
            Config config = new Config();
            config.setMaxDuration(10 * 60 * 1000);      //最长10分钟
            config.setMinDuration(5 * 1000);            //最短5秒
            config.setGenerator(() -> new File(Constant.f_audio, Tool.randomName("amr")));
            mRecorderManager = new RecorderManager(config);
            mRecorderManager.setOnRecorderListener(new SimpleOnRecorderListener(){
                @Override
                public void onUpdateAmpDisplay(int amp, long duration) {
                    mTvTime.setText(Tool.getHourMinuteSecond(duration));
                }

                @Override
                public void onShortDuration(long duration) {
                    Tool.showToast(R.string.short_duration);
                }
            });
        }
    }

    @Override
    protected int getRootLayoutResId() {
        return R.layout.view_record_audio;
    }

    private OnAudioSelectListener mOnAudioSelectListener;
    public void setOnAudioSelectListener(OnAudioSelectListener listener){
        this.mOnAudioSelectListener = listener;
    }

    public static interface OnAudioSelectListener{
        void onAudioSelect(File file, long duration);
    }
}
