package com.feximin.library.util.recorder;

import com.mianmian.guild.App;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleAudioFileGenerator implements IAudioFileGenerator {


		private File parentDirectory;

		public SimpleAudioFileGenerator(){
			this.parentDirectory = App.getApp().getCacheDir();
		}

		private static SimpleDateFormat sFormat= new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

		public File generateFile(){
			return new File(parentDirectory, sFormat.format(new Date()) + File.separator +".amr");
		}
	}