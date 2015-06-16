package com.neo.box.tools;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.neo.box.R;

public class Tool {

	
	//获取可变参数的第一个元素
	public static <T> T get(T...t){
		if(t == null || t.length < 1){
			return null;
		}
		return t[0];
	}
	public static <T> T get(T def, T...t){
		if(t == null || t.length < 1){
			return def;
		}
		return t[0];
	}
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
	
	public static void sleep(long time){
		if(time < 0) return;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> boolean isListEmpty(List<T> list){
		if(list == null || list.size() == 0) return true;
		return false;
	}
	
	public static int dpToPx(int dp){
		return (int) (dp * ScreenTool.getScreenDensity() + 0.5);
	}
}
