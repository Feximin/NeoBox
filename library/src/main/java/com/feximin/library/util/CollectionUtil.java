package com.feximin.library.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 16/4/20.
 */
public class CollectionUtil {
    public static final List<Object> EMPTY_LIST = new ArrayList<>();
    public static  List emptyList(boolean isNew){
        if (isNew){
            return new ArrayList<>(0);
        }else{
            return EMPTY_LIST;
        }
    }

    public static List emptyList(){
        return emptyList(false);
    }
}
