package com.feximin.box.util.sr;

import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Neo
 * @time 2015年8月15日-下午5:03:43
 * @param <K>
 * @param <V>
 */
public abstract class SoftReferenceUtil<K, V> {
	private Map<K, SoftReference<V>> mReferenceCache = new HashMap<>();
//	private ReferenceQueue<V> mQueue = new ReferenceQueue<>();    //不需要这个queue 用 mReferenceCache 去管理已经回收的软引用
	
	
//	public void put(K k, V v){
//		mReferenceCache.put(k, new SoftReference<>(v, mQueue));
////		v = null;   //不需要显式的置为空, 这个v其实是一个局部变量, 在方法结束之后 会自动被清理
///	}


	public V get(K k){
		return get(k, true);
	}

	/**
	 *
	 * @param k
	 * @param produce  如果为空的话，是否需要去产生新的
     * @return
     */
	public V get(K k, boolean produce){
		SoftReference<V> soft = mReferenceCache.get(k);
        V result;
		if(soft == null || (result = soft.get()) == null){
            if (soft != null){
                soft = null;
                Log.e("SoftReferenceUtil-->", "clear");
            }
            mReferenceCache.remove(k);
            if (produce){
                V v = generate(k);
                SoftReference<V> softReference = new SoftReference<>(v);
                mReferenceCache.put(k, softReference);
                return softReference.get();
            }
            return null;
        }else{
            return result;
        }
	}

	abstract public V generate(K k);
}
