package com.feximin.library.util;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Neo
 * 使用adapter时候的辅助类，20141125 //http://www.eoeandroid.com/thread-321547-1-1.html
 */
public class ViewHolder {
    /**
     * @param view
     * @param id
     * @param notThrow	为false时表示如果获取到的view为空则抛出异常，默认为false
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T getViewById(View view, int id, boolean notThrow) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            if(childView == null){
            	if(!notThrow){
            		throw new IllegalArgumentException("can not find a view with this id");
            	}
            	return null;
            }else{
            	viewHolder.put(id, childView);
            	return (T) childView;
            }
        }else{
        	return (T) childView;
        }
    }
    
    public static <T extends View> T getViewById(View view, int id){
    	return getViewById(view, id, false);
    }
    public static TextView getTextViewById(View view, int id, String text) {
    	TextView txt = getViewById(view, id);
		txt.setText(text);
    	return txt;
    }
    public static TextView getTextViewById(View view, int id, int textResId) {
    	return getTextViewById(view, id, Tool.getString(textResId));
    }
    public static TextView getTextViewById(View view, int id) {
    	return getTextViewById(view, id, null);
    }
    
    public static ImageView getImageViewById(View view, int id, int imgResId){
    	ImageView img = getViewById(view, id);
		img.setImageResource(imgResId);
    	return img;
    }

    public static ImageView getImageViewById(View view, int id){
        return getViewById(view, id);
    }
    
}