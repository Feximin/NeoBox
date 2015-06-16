package com.neo.box.tools;

import com.neo.box.fragment.BaseFragment;

public class FragmentTool {

	public static <T> T newInstance(Class<? extends BaseFragment> clazz){
		if(clazz == null) return null;
		try {
			BaseFragment f = clazz.newInstance();
			return (T)f;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
