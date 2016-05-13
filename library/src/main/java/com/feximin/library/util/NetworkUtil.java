package com.feximin.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import com.mianmian.guild.App;
import com.mianmian.guild.R;

public class NetworkUtil {

	/**
	 * 网络是否连接，无论是wifi还是手机
	 * @return
	 */
	public static boolean isNetworkConnected(){
		NetworkInfo info = getNetworkInfo();
		return info != null && info.isAvailable();
	}

	public static boolean isNetworkConnectedAndToast(){
		boolean b = isNetworkConnected();
		if (!b && Thread.currentThread() == Looper.getMainLooper().getThread()) Tool.showToast(R.string.net_work_unavailable);
		return b;
	}

	public static boolean isNetworkUnavailableAndToast(){
		return isNetworkUnavailableAndToast(R.string.net_work_unavailable);
	}

	public static boolean isNetworkUnavailableAndToast(String hintStr){
		boolean b = isNetworkConnected();
		if (!b && Thread.currentThread() == Looper.getMainLooper().getThread()) Tool.showToast(hintStr);
		return !b;
	}

	public static boolean isNetworkUnavailableAndToast(int strResId){
		return isNetworkUnavailableAndToast(Tool.getString(strResId));
	}

	/**
	 * 是否是wifi网络
	 * @return
	 */
	public static boolean isWifi(){
		NetworkInfo info = getNetworkInfo();
		return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
	}

	/**
	 * 是否是手机网络
	 * @return
	 */
	public static boolean isPhoneNetwork(){
		NetworkInfo info = getNetworkInfo();
		return info != null && info.getType() == ConnectivityManager.TYPE_MOBILE;
	}

	private static NetworkInfo getNetworkInfo(){
		ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
		return info;
	}
	
}
