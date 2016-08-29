package com.feximin.box.util.recorder;

import com.feximin.box.Constant;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleAudioFileGenerator implements IAudioFileGenerator {


		private String parentDirectory = Constant.f_audio;


		private static SimpleDateFormat sFormat= new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

		public File generateFile(){
			File f = new File(parentDirectory, sFormat.format(new Date()) + File.separator +".amr");
			if (!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			return f;
		}
	}