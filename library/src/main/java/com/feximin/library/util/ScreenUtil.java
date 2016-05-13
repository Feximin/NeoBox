package com.feximin.library.util;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.mianmian.guild.App;

/**
 * Created by Neo on 15/11/18.
 */
public class ScreenUtil {
    private static int sScreenWidth;
    private static int sScreenHeight;
    private static float sScreenDensity;
    private static float sScreenScaleDensity;
    private static int sScreenStatusBarHeight;

    public static void initScreenParams(){
        if(sScreenWidth == 0){
            DisplayMetrics metrics = App.getApp().getResources().getDisplayMetrics();
            sScreenWidth = metrics.widthPixels;
            sScreenHeight = metrics.heightPixels;
            sScreenDensity = metrics.density;
            sScreenScaleDensity = metrics.scaledDensity;
        }
    }
    public static float getScreenDensity(){
        if(sScreenDensity == 0) initScreenParams();
        return sScreenDensity;
    }
    public static int getScreenHeight(){
        if(sScreenHeight == 0) initScreenParams();
        return sScreenHeight;
    }

    public static int getScreenWidth(){
        if(sScreenWidth == 0) initScreenParams();
        return sScreenWidth;
    }

    public static int getScreenWidthScaleValue(float scale){
        return (int) (getScreenWidth() * scale);
    }
    public static int getScreenHeightScaleValue(float scale){
        return (int) (getScreenHeight() * scale);
    }

    public static float getScreenScaleDensity(){
        if(sScreenScaleDensity == 0) initScreenParams();
        return sScreenScaleDensity;
    }
    public static int dpToPx(float dp){
        dp *= getScreenDensity();
        return (int) Math.ceil(dp);
    }
    public static int spToPx(float sp){
        sp *= getScreenScaleDensity();
        return (int) Math.ceil(sp);
    }


    private static final int DEFAULT_STATUS_BAR_HEIGHT = 25;
    public static int getStatusBarHeight(Activity activity){
        if(sScreenStatusBarHeight == 0){
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            sScreenStatusBarHeight = frame.top;
            if(frame.top == 0){
                sScreenStatusBarHeight = dpToPx(DEFAULT_STATUS_BAR_HEIGHT);
            }
        }
        return sScreenStatusBarHeight;
    }

}
