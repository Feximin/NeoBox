package com.feximin.box.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.feximin.box.fragment.BaseFragment;
import com.feximin.box.view.BaseView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*

 */
public class ViewSpanner {

	private ViewSpanner(){}

	public static TextView getTextViewById(View view, int id, String text){
		TextView tv = getViewById(view, id);
		tv.setText(text);
		return tv;
	}

	public static TextView getTextViewById(View view, int id, String text, View.OnClickListener listener){
		TextView tv = getTextViewById(view, id, text);
		tv.setOnClickListener(listener);
		return tv;
	}


	public static <T extends View> T getViewById(Activity target, int id){
		View v = target.findViewById(id);
		return getView(v);
	}
	public static <T extends View> T getViewById(Activity target, int id, String methodName, Object...params){
		T v = getViewById(target, id);
		return bindClick(v, methodName, target, params);
	}
	
	public static <T extends View> T getViewById(View target, int id){
		View v = target.findViewById(id);
		return getView(v);
	}

	public static <T extends View> T getViewById(View view, int id, View.OnClickListener listener){
		T t = getViewById(view, id);
		t.setOnClickListener(listener);
		return t;
	}

	public static <T extends View> T getViewById(View target, int id, String methodName){
		T v = getViewById(target, id);
		return bindClick(v, methodName, target);
	}
	public static <T extends View> T getViewById(View target, int id, String methodName, Object...params){
		T v = getViewById(target, id);
		return bindClick(v, methodName, target, params);
	}

	public static <T extends View> T bindClick(T v, final String methodName, final Object target, final Object...params){
		v.setOnClickListener(TextUtils.isEmpty(methodName)?null:view -> invoke(methodName, target, params));
		return v;
	}
	public static <T extends View> T getViewById(BaseView b, int id){
		return getViewById(b.getView(), id);
	}
	public static <T extends View> T getViewById(BaseView b, int id, String methodName, Object...params){
		T t = getViewById(b.getView(), id);
		bindClick(t, methodName, b, params);
		return t;
	}

	public static <T extends View> T getViewById(BaseFragment target, int id, boolean...throwIfNotExsit){
		View root = target.getRootView();
		View v = root.findViewById(id);
		return getView(v);
	}
	public static <T extends View> T getViewById(BaseFragment target, int id, String methodName, Object...params){
		T v = getViewById(target, id);
		bindClick(v, methodName, target, params);
		return v;
	}
	private static void invoke(final String methodName, final Object target, final Object...params){
		try {
			Method method = null;
			Class<?> clazz = target.getClass();
			Class<?>[] paramsClass = getParamsClass(params);
			do{
				try{
					method = clazz.getDeclaredMethod(methodName, paramsClass);
					break;
				}catch(NoSuchMethodException e){
					clazz = clazz.getSuperclass();
				}
			}while(clazz != Object.class);
			
			if(method != null && clazz != target.getClass()){
				//如果本类中没有对应的方法，但是父类中有，只有当父类方法不为private的时候才能使用

				//太坑了，不能这么搞，如果调用的方法本来就是父类的话
				//比如说，A继承了B，B中有一个控件绑定了方法C，C是一个private的方法，那么在A中点击了控件C，如果按上面说的话，就不能调用了    .。。。20160311
//				int modifiers = method.getModifiers();
//				if(!Modifier.isAbstract(modifiers) && (!Modifier.isPrivate(modifiers))){
//
//				}else{
//					method = null;
//				}
			}
			
			if(method != null){
				method.setAccessible(true);  
				method.invoke(target, params);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}  
	}
	
	private static Class<?>[] getParamsClass(Object...params){
		int length = 0;
		if(params == null || (length = params.length) == 0) return null;
		Class<?>[] clazz = new Class<?>[length];
		for(int i = 0 ; i<length; i++){
			Object pa = params[i];
			if(pa == null) throw new IllegalArgumentException("params can not contains null");
			Class<?> c = pa.getClass();
			if(c == Integer.class) c = int.class;
			if(c == Long.class) c = long.class;
			if(c == Double.class) c = double.class;
			if(c == Float.class) c = float.class;
			if(c == Character.class) c = char.class;
			if(c == Byte.class) c = byte.class;
			if(c == Short.class) c = short.class;
			if(c == Boolean.class) c = boolean.class;
			clazz[i] = c;
			
		}
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends View> T getView(View from){
		if(from == null){
			throw new IllegalArgumentException("no view find with this id");
		}
		return (T) from;
	}

}
