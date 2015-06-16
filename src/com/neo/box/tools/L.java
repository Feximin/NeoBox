package com.neo.box.tools;


import com.neo.box.NeoApp;

import android.util.Log;

public class L{
	
	public static void d(String msg, String...tag){
		if(NeoApp.mIsTest){
			Log.d(getTag(tag), msg);
		}
	}
	public static void i(String msg, String...tag){
		if(NeoApp.mIsTest){
			Log.i(getTag(tag), msg);
		}
	}
	public static void e(String msg, String...tag){
		if(NeoApp.mIsTest){
			Log.d(getTag(tag), msg);
		}
	}
	public static void v(String msg, String...tag){
		if(NeoApp.mIsTest){
			Log.v(getTag(tag), msg);
		}
	}
	public static void w(String msg, String...tag){
		if(NeoApp.mIsTest){
			Log.w(getTag(tag), msg);
		}
	}
	
	private static String getTag(String...tag){
		String myTag = Tool.get(tag);
		if(myTag == null){
			myTag = NeoApp.getApp().getPackageName();
		}
		return myTag;
	}

}
