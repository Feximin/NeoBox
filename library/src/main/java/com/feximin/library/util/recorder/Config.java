package com.feximin.library.util.recorder;

public class Config{
	private int sampleRate = 8000;
	private int minDuration = 1000;
	private int maxDuration = 60 * 1000;
	private int updateInterval = 100;			//更新图片显示
	private int reminderLeft = 10 * 1000;		//还剩多少的时候需要提示
	private IAudioFileGenerator generator = new SimpleAudioFileGenerator();

	public int getReminderLeft() {
		return reminderLeft;
	}

	public void setReminderLeft(int reminderLeft) {
		this.reminderLeft = reminderLeft;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public int getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(int minDuration) {
		this.minDuration = minDuration;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}

	public int getUpdateInterval() {
		return updateInterval;
	}

	public void setUpdateInterval(int updateDuration) {
		this.updateInterval = updateDuration;
	}

	public IAudioFileGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(IAudioFileGenerator generator) {
			this.generator = generator;
		}
}