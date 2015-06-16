package com.neo.box.tools;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewHolder {  
  
    @SuppressWarnings("unchecked")  
    public static <T extends View> T get(View view, int id){  
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();  
        if(viewHolder == null){  
            viewHolder = new SparseArray<View>();  
            view.setTag(viewHolder);  
        }  
        View childView = viewHolder.get(id);  
        if(childView == null){  
            childView = view.findViewById(id);  
            viewHolder.put(id, childView);  
        }  
        return (T) childView;  
    }  
    
    public static TextView getTextView(View view, int id, String text){
    	TextView textView = get(view, id);
    	textView.setText(text);
    	return textView;
    }
} 