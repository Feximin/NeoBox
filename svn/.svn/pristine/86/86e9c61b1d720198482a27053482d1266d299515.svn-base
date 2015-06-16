package com.neo.box.task;

import com.neo.box.tools.Tool;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

public abstract class SimpleTask<T> extends AsyncTask<Void, Void, T> {

	private long mAtLeastDuration;		//至少需要执行的时间，如果少于，则sleep
	@TargetApi(11/*Build.VERSION_CODES.HONEYCOMB*/)
	public SimpleTask(long atLeastDuration){
		mAtLeastDuration = atLeastDuration;
		if(Build.VERSION.SDK_INT >= 11){
			executeOnExecutor(THREAD_POOL_EXECUTOR);
		}else{
			execute();
		}
	}
	public SimpleTask(){
		this(0);
	}
	@Override
	@Deprecated
	protected final T doInBackground(Void... params) {
		long start = System.currentTimeMillis();
		T t = executeInBackground();
		long duraton = System.currentTimeMillis() - start;
		
		if(mAtLeastDuration > 0 && duraton < mAtLeastDuration){
			Tool.sleep(mAtLeastDuration - duraton);
		}
		return t;
	}
	
	@Override
	@Deprecated
	protected final void onPostExecute(T result) {
		onMyPostExecute(result);
	}
	protected abstract T executeInBackground();
	
	protected void onMyPostExecute(T t){
	}

}
