package com.feximin.box.util.recorder;

import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.CountDownTimer;
import android.util.Log;

import com.feximin.box.util.Tool;

import java.io.File;


public class RecorderManager {

	private final String TAG = "RecorderManager";

	private MediaRecorder mRecorder;

	private File mCurrentAudioFile;
	private CountDownTimer mAmplitudeTimer;

	private long mStartTime;

	private Config mConfig;
	private long mDuration;

	private OnAudioFocusChangeListener mOnAudioFocusChangeListener = new OnAudioFocusChangeListener() {

		@Override
		public void onAudioFocusChange(int focusChange) {
			switch (focusChange) {
				case AudioManager.AUDIOFOCUS_LOSS:
					//会长时间失去，
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
					//短暂失去焦点，先暂停。
					stop();
					break;
				case AudioManager.AUDIOFOCUS_GAIN:
					//重新获得焦点
					break;
			}
		}
	};

	private static enum Status {
		NONE, RECORDING
	}

	private Status mCurStatus = Status.NONE;

	public RecorderManager(Config config) {
		this.mConfig = config;
        if (mConfig.getMaxDuration() < mConfig.getMinDuration()){
            throw new IllegalArgumentException("max duration can not less than min duration !!");
        }
		initRecorder();
		initAmpTimer();
	}


	private void initRecorder() {
		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
		} else {
			mRecorder.reset();
		}
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setAudioSamplingRate(mConfig.getSampleRate());
		mRecorder.setOnErrorListener(new OnErrorListener() {

			@Override
			public void onError(MediaRecorder mr, int what, int extra) {
                mListener.onError();
				initRecorder();
			}
		});
	}

	private void initAmpTimer() {
		mAmplitudeTimer = new CountDownTimer(Integer.MAX_VALUE, mConfig.getUpdateInterval()) {

			@Override
			public void onTick(long millisUntilFinished) {
				if (mCurStatus == Status.NONE) return;
				int amp = getAmplitude();
                long duration = System.currentTimeMillis() - mStartTime;
                mListener.onUpdateAmpDisplay(amp, duration);
                long left = mConfig.getMaxDuration() - duration;
                if (left < 10 * 1000 && left > 0) {
                    mListener.onSecondsLeft((int) (left / 1000));
                }
                if (left < mConfig.getUpdateInterval()) {
                    stop();
//                    mListener.onMaxDuration(mConfig.getMaxDuration());
                }
			}

			@Override
			public void onFinish() {

			}
		};
	}

	public void start() {
		if (mCurStatus == Status.RECORDING) return;
        mDuration = 0;
		mCurStatus = Status.RECORDING;
		mCurrentAudioFile = mConfig.getGenerator().generateFile();
		mRecorder.setOutputFile(mCurrentAudioFile.getAbsolutePath());
		try {
			mRecorder.prepare();
			mRecorder.start();
			AudioFocusManager.getInstance().requestFocus(mOnAudioFocusChangeListener);
			mStartTime = System.currentTimeMillis();
			mAmplitudeTimer.start();
            mListener.onStart();
		} catch (Exception e) {        //
			e.printStackTrace();
            mListener.onError();
			initRecorder();
		}
	}

	public boolean isRecording() {
		return mCurStatus == Status.RECORDING;
	}

	public void stop() {
		if (mCurStatus != Status.RECORDING) return;
		try {
			mCurStatus = Status.NONE;
			mAmplitudeTimer.cancel();
            mDuration = System.currentTimeMillis() - mStartTime;
			mRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			initRecorder();
		}
		AudioFocusManager.getInstance().abandonFocus(mOnAudioFocusChangeListener);
		if (mDuration < mConfig.getMinDuration()) {
			mCurrentAudioFile.delete();
			mCurrentAudioFile = null;
			mListener.onShortDuration(mDuration);
		} else {
            if (Tool.isValidFile(mCurrentAudioFile)) {
                mListener.onRecordFinish(mDuration, mCurrentAudioFile.getAbsolutePath());
            } else {
                mListener.onError();
                mCurrentAudioFile = null;
            }
		}
	}

	public File getAudioFile() {
		return mCurrentAudioFile;
	}

    public long  getDuration(){
        return mDuration;
    }

	public void cancel() {
		mCurStatus = Status.NONE;
		try {
			mRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			initRecorder();
		}
		mAmplitudeTimer.cancel();
		mCurrentAudioFile.delete();
		mCurrentAudioFile = null;
        mListener.onCancel();
	}

	/**
	 * 释放资源
	 */
	public void release() {
		if (mRecorder != null) {
			try {
				mRecorder.stop();
				mRecorder.release();
			} catch (Exception e) {
			}
			mRecorder = null;
		}
		mListener = null;
		if (mAmplitudeTimer != null) {
			mAmplitudeTimer.cancel();
			mAmplitudeTimer = null;
		}
	}

	public int getAmplitude() {
		double ratio = (double) mRecorder.getMaxAmplitude();
		int db = 0;// 分贝
		if (ratio > 1) db = (int) (20 * Math.log10(ratio));
		Log.e(TAG, "录音分贝值--》" + db);
		return db;
	}


	private OnRecorderListener mListener = new SimpleOnRecorderListener();

	public void setOnRecorderListener(OnRecorderListener listener) {
		this.mListener = listener;
	}
}
