package com.feximin.box.entity;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Neo on 16/2/18.
 */
public class SettingBean {
    public int imgLeftResId;
    public String title;
    public String desc;
    public int type;
    public boolean selected;        //如果是dot则表示是否有新消息，如果是toggle则表示是否是打开状态

    public static final int TEXT = 0;
    public static final int DOT =1;
    public static final int TOGGLE = 2;

    @IntDef({TEXT, DOT, TOGGLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type{}

    public SettingBean(int imgLeft, String title, String desc){
        this.imgLeftResId = imgLeft;
        this.title = title;
        this.desc = desc;
        this.type = TEXT;
    }

    public SettingBean(int imgLeft, String title, @Type int type){
        this.imgLeftResId = imgLeft;
        this.title = title;
        this.type = type;
    }
    public SettingBean(int imgLeft, String title, @Type int type, boolean selected){
        this(imgLeft, title, type);
        this.selected = selected;
    }

    public SettingBean(String title, String desc){
        this.title = title;
        this.desc = desc;
        this.type = TEXT;
    }

    public SettingBean(String title, boolean selected){
        this.title = title;
        this.selected = selected;
        this.type = TOGGLE;
    }


    public SettingBean(int imgLeft, String title){
        this(imgLeft, title, "");
    }
}
