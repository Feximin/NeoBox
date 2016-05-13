package com.feximin.library.util.recorder;

public interface OnRecorderListener {
	//		void onPrepare();					//准备
		void onStart();

		void onCancel();

		//		void onRecording();
		void onError();                        //出错

		void onShortDuration(long duration);                //时间太短

		//剩下的秒数
		void onSecondsLeft(int left);                //小于10秒的时候会有提示

		void onRecordFinish(long duration, String path);

//		void onMaxDuration(long duration);

		/**
		 * @param amp      当前的分贝
		 * @param duration 当前已经录了多长时间，毫秒
		 */
		void onUpdateAmpDisplay(int amp, long duration);//录音动画
	}