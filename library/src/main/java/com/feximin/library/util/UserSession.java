package com.feximin.library.util;

import com.mianmian.guild.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Neo on 15/12/8.
 */
public class UserSession {
    private static final String CUR_USER = "current_login_user";

    private UserSession(){}
    public static UserSession getInstance(){
        return SingletonFactory.getInstance(UserSession.class);
    }

    synchronized void logout(){
        Tool.setStringValue(CUR_USER, "");
        if (mCurUser != null) mCurUser = null;
        if (mUserData != null) mUserData = null;
    }

    private User mCurUser;

    private JSONObject mUserData;
    synchronized void login(JSONObject data){

        mCurUser = new User(data);
        mUserData = data;

        Tool.setStringValue(CUR_USER, mUserData.toString());

    }

    void updateUserInfo(User user){
        mCurUser.setName(user.getName());
        mCurUser.setAvatar(user.getAvatar());
        mCurUser.setGender(user.getGender());
        mCurUser.setBirthday(user.getBirthday());
        try {
            mUserData.put(User.NAME, user.getName());
            mUserData.put(User.AVATAR, user.getAvatar());
            mUserData.put(User.GENDER, user.getGender());
            mUserData.put(User.BIRTHDAY, user.getBirthday());
            Tool.setStringValue(CUR_USER, mUserData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void updateLegion(String legionId){
        update(User.LEGION_ID, legionId);
        mCurUser.setLegionId(legionId);
    }

    void updateGuild(String guildId){
        update(User.GUILD_ID, guildId);
        mCurUser.setGuildId(guildId);
    }

    void updateUserLevel(int level){
        update(User.LEVEL, level);
        mCurUser.setLevel(level);
    }

    void updateUserCoinCount(int count){
        update(User.USER_COIN, count);
        mCurUser.setCoinCount(count);
    }

    void updateUserLegionLevel(int level){
        update(User.LEGION_LEVEL, level);
        mCurUser.setLegionLevel(level);
    }



    void updateUserSysRole(int level){
        update(User.SYS_ROLE, level);
        mCurUser.getUserSysRole();
    }

    void updateUseLegionRole(int role){

    }




    private void update(String key, Object value){
        try {
            mUserData.put(key, value);
            Tool.setStringValue(CUR_USER, mUserData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    synchronized User getUser(){
        if (mCurUser == null){
            String json = Tool.getStringValue(CUR_USER);
            if (Tool.isNotEmpty(json)) {
                JSONObject data = Tool.getJSONObject(json);
                mUserData = data;

                mCurUser = new User(data);
            }
        }
        return mCurUser;
    }

    synchronized JSONObject getData(){
        return mUserData;
    }

}
