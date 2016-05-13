package com.feximin.library.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author Neo
 * ͨ�õ���Ϣ������   20141208
 *
 */
public class BaseBroadcastReceiver extends BroadcastReceiver {

	protected Context mContext;
	protected List<String> mListActions;
	public BaseBroadcastReceiver(Context context,String action) {
		this(context, Arrays.asList(action));
	}
	public BaseBroadcastReceiver(Context context,String action, String methodName) {
		this(context, Arrays.asList(action), methodName);
	}
	public BaseBroadcastReceiver(Context context,List<String> actions) {
		this.mContext = context;
		if(actions != null && actions.size() > 0){
			this.mListActions = actions;
			IntentFilter filter = new IntentFilter();
			for(String action : actions){
				filter.addAction(action);
			}
			this.mContext.registerReceiver(this, filter);
		}
	}
	private String mMethodName;
	public BaseBroadcastReceiver(Context context,List<String> actions, String methodName) {
		this(context, actions);
		this.mMethodName = methodName;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if(!TextUtils.isEmpty(mMethodName)){
			Class<?> clazz = context.getClass();
			try {
				Method method = null;
				do{
					try{
						method = clazz.getDeclaredMethod(mMethodName);
						break;
					}catch(NoSuchMethodException e){
						clazz = clazz.getSuperclass();
					}
				}while(clazz != Object.class);
				if(method != null){
					method.setAccessible(true);  
					method.invoke(context);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}     
		}
	}
	
	
	public void unregister(){
		try {
			if(mListActions != null){
				this.mContext.unregisterReceiver(this);
			}
		} catch (Exception e) {
			
		}
	}

}
