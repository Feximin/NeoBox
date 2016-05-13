/**
 * 
 */
package com.feximin.library.util.recorder;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;

import com.mianmian.guild.App;

/**
 * @author Neo
 * 集中管理音频焦点   20150724,   主要执行的是失去焦点的处理，目前即使获得焦点后，所有的都不开始播放
 * 一次只有一个参与竞争audio    参考 http://blog.csdn.net/thl789/article/details/7422931
 */
public class AudioFocusManager {

	private AudioManager mAudioManager;
	private AudioFocusManager(){
		mAudioManager = (AudioManager) App.getApp().getSystemService(Context.AUDIO_SERVICE);
	}

	private static final AudioFocusManager INSTANCE = new AudioFocusManager();

	public static AudioFocusManager getInstance(){
		return INSTANCE;
	}
	
	public void requestFocus(OnAudioFocusChangeListener listener){
		mAudioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		
	}
	
	public void abandonFocus(OnAudioFocusChangeListener listener){		//音乐播放完成或者录音结束，也abandon掉
			mAudioManager.abandonAudioFocus(listener);
	}
	
	
}
