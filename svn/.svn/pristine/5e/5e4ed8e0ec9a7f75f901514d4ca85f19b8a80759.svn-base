package com.neo.box.tools;

import java.lang.reflect.Field;

import com.neo.box.NeoApp;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenTool {


	public static int mScreenWidth;
	public static int mScreenHeight;
	public static int mScreenStatusBarHeight;
	public static float mScreenDensity;
	public static float mScreenScaleDensity;
	public static int mScreenDensityDpi;
	
	private static void checkScreen(){
		if(mScreenWidth == 0){
			WindowManager windowManager = (WindowManager) NeoApp.getApp().getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics metrics = new DisplayMetrics(); 
			windowManager.getDefaultDisplay().getMetrics(metrics);
			mScreenWidth = metrics.widthPixels;
			mScreenHeight = metrics.heightPixels;
			mScreenScaleDensity = metrics.scaledDensity;
			mScreenDensity = metrics.density;
			mScreenDensityDpi = metrics.densityDpi;
		}
	}

	public static int getScreenWidth() {
		checkScreen();
		return mScreenWidth;
	}

	public static int getScreenHeight() {
		checkScreen();
		return mScreenHeight;
	}

	public static int getScreenStatusBarHeight() {
		checkScreen();
		return mScreenStatusBarHeight;
	}

	public static float getScreenDensity() {
		checkScreen();
		return mScreenDensity;
	}

	public static float getScreenScaleDensity() {
		checkScreen();
		return mScreenScaleDensity;
	}

	public static int getScreenDensityDpi() {
		checkScreen();
		return mScreenDensityDpi;
	}
	
	private static final int DEFAULT_STATUS_BAR_HEIGHT = 25;
	public static int getStatusBarHeight(){ 
		if(mScreenStatusBarHeight > 0) return mScreenStatusBarHeight;
        Class<?> c = null; 
        Object obj = null; 
        Field field = null; 
        int x = 0, statusBarHeight = 0; 
        try { 
            c = Class.forName("com.android.internal.R$dimen"); 
            obj = c.newInstance(); 
            field = c.getField("status_bar_height"); 
            x = Integer.parseInt(field.get(obj).toString()); 
            statusBarHeight = NeoApp.getApp().getResources().getDimensionPixelSize(x);  
        } catch (Exception e1) { 
            e1.printStackTrace(); 
        }  
        if(statusBarHeight == 0){
        	statusBarHeight = (int) (DEFAULT_STATUS_BAR_HEIGHT * getScreenDensity());
        }
        return statusBarHeight; 
    }
	private static Resources mResources;
	public static int getPxFromDimenRes(int dimenResId){
		if(mResources == null) mResources = NeoApp.getApp().getResources();
		return (int) mResources.getDimension(dimenResId);
	}
}
