package com.neo.box.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.neo.box.view.BaseView;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewTool {

	public static <T extends View> T getViewById(Activity activity, int id, String...methodName){
		View view = activity.findViewById(id);
		setOnViewClickListener(view, activity, methodName);
		return (T) view;
	}
	
	public static TextView getTextViewById(Activity activity, int id, String text, String...methodName){
		TextView txt = getViewById(activity, id, methodName);
		txt.setText(text);
		return txt;
	}
	
	public static <T extends View> T getViewById(Fragment fragment, int id, String...methodName){
		View view = fragment.getActivity().findViewById(id);
		setOnViewClickListener(view, fragment, methodName);
		return (T) view;
	}

	public static <T extends View> T getViewById(View parent, int id, String...methodName){
		View view = parent.findViewById(id);
		setOnViewClickListener(view, parent, methodName);
		return (T) view;
	}
	
	public static <T extends View> T getViewById(BaseView parent, int id, String...methodName){
		View view = parent.getView().findViewById(id);
		setOnViewClickListener(view, parent, methodName);
		return (T) view;
	}
	
	

	public static ImageView getImageView(int id, int resId, Activity parent, String... methodName){
		ImageView img = getViewById(parent, resId, methodName);
		img.setImageResource(resId);
		img.setVisibility(View.VISIBLE);
		setOnViewClickListener(img, parent, methodName);
		return img;
	}
	
	public static void setOnViewClickListener(final View view, final Object parent, final String... methodNames){
		if(view == null) return;
		if(methodNames == null || methodNames.length == 0 || TextUtils.isEmpty(methodNames[0])){
			view.setOnClickListener(null);
			return;
		}
		if(parent == null) return;
		final String methodName = methodNames[0];
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Class<?> clazz = parent.getClass();
				Method method = null;
				do{
					try {
						method = clazz.getDeclaredMethod(methodName);
						method.setAccessible(true);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
						clazz = clazz.getSuperclass();
					}
				}while(method == null && clazz != Object.class);
				if(method == null){
					if(parent instanceof View){
						setOnViewClickListener(view, ((View) parent).getContext(), methodNames);
					}else if(parent instanceof Fragment){
						setOnViewClickListener(view, ((Fragment) parent).getActivity(), methodNames);
					}
				}else{
					try {
						method.invoke(parent);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
