package com.feximin.box.util.setting;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Neo on 16/6/15.
 */

public class Setting {
    private String title;
    private String desc;
    private boolean select;
    private int type;
    public static final int NONE = 0;
    public static final int ARROW = 2;
    public static final int TOGGLE = 1;
    public static final int DOT = 3;

    @IntDef({NONE, ARROW, TOGGLE, DOT})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface Type{}

    public Setting(String title, String desc, boolean select, int type) {
        this.title = title;
        this.desc = desc;
        this.select = select;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
