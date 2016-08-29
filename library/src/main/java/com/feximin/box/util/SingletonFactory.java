package com.feximin.box.util;


import com.feximin.box.interfaces.ILifeCycle;

import java.util.Map;
import java.util.WeakHashMap;

public class SingletonFactory implements ILifeCycle {

	private static final Map<Class<?>, Object> sInstanceMap = new WeakHashMap<>();

    public static final SingletonFactory INSTANCE = getInstance(SingletonFactory.class);

    private SingletonFactory(){}
	/**
	 * 必须有一个无参的构造函数
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getInstance(Class<E> clazz){
		if(sInstanceMap.containsKey(clazz)){
			return (E) sInstanceMap.get(clazz);
		}else{
			E instance;
			synchronized(SingletonFactory.class) {
				instance = (E) sInstanceMap.get(clazz);
				if(instance == null) {
					instance = Tool.newInstance(clazz);
					if (instance != null) sInstanceMap.put(clazz, instance);
				}
			}
			return instance;
		}
	}
	public static  <E> E getInstance(Class<E> clazz, Object param){
		if(sInstanceMap.containsKey(clazz)){
			return (E) sInstanceMap.get(clazz);
		}else{
			E instance;
			synchronized(SingletonFactory.class) {
				instance = (E) sInstanceMap.get(clazz);
				if(instance == null) {
					instance = Tool.newInstance(clazz, param);
					if (instance != null) sInstanceMap.put(clazz, instance);
				}
			}
			return instance;
		}
	}

    public synchronized static void remove(Class<?> clazz){
        sInstanceMap.remove(clazz);
    }

    public synchronized static void removeAll(){
//        Iterator<Map.Entry<Class<?>, Object>> iterator = sInstanceMap.entrySet().iterator();
//        while (iterator.hasNext()){
//            iterator.remove();
//        }
		sInstanceMap.clear();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        removeAll();
    }
}
