package com.feximin.box.interfaces;

import org.json.JSONObject;

/*
	根据json串提取里面的变量
 * 20150507   NEO
 */
public interface JSONExtractor<T> {
	T create(JSONObject json);
}
