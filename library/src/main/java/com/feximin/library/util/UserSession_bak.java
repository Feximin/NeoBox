package com.feximin.library.util;

import com.mianmian.guild.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neo on 15/12/8.
 */
public class UserSession_bak {
    private static final String CUR_USER = "current_login_user";


    private UserSession_bak(){}
    public static UserSession_bak getInstance(){
        return SingletonFactory.getInstance(UserSession_bak.class);
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
        String id = data.optString(User.UID, "");
        String token = data.optString(User.TOKEN, "");
        String ryToken = data.optString(User.RY_TOKEN, "");
        String userName = data.optString(User.NAME, "");
        String userAvatar = data.optString(User.AVATAR, "");
        int gender = data.optInt(User.GENDER, 0);
        String birthday = data.optString(User.BIRTHDAY, "2000-01-01");
        String guildId = data.optString(User.GUILD_ID,  "");
        String legionId = data.optString(User.LEGION_ID,  "");
        int roleLevel = data.optInt(User.SYS_ROLE);

        mCurUser.setId(id);
        mCurUser.setToken(token);
        mCurUser.setRyToken(ryToken);
        mCurUser.setName(userName);
        mCurUser.setAvatar(userAvatar);
        mCurUser.setGender(gender);
        mCurUser.setBirthday(birthday);
        mCurUser.setGuildId(guildId);
        mCurUser.setLegionId(legionId);


        Map<String, Object> map = new HashMap<>();
        map.put(User.UID, id);
        map.put(User.NAME, userName);
        map.put(User.TOKEN, token);
        map.put(User.AVATAR, userAvatar);
        map.put(User.RY_TOKEN, ryToken);
        map.put(User.SYS_ROLE, roleLevel);
        map.put(User.GENDER, gender);
        map.put(User.BIRTHDAY, birthday);
        map.put(User.GUILD_ID, guildId);
        map.put(User.LEGION_ID, legionId);
        mUserData = new JSONObject(map);


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

    void updateRoleLevel(int level){
        update(User.SYS_ROLE, level);
//        mCurUser.setUserRoleLevel(level);
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

                mCurUser = new User();
                mCurUser.setId(data.optString(User.UID, ""));
                mCurUser.setToken(data.optString(User.TOKEN, ""));
                mCurUser.setRyToken(data.optString(User.RY_TOKEN, ""));
                mCurUser.setAvatar(data.optString(User.AVATAR, ""));
                mCurUser.setName(data.optString(User.NAME, ""));
                mCurUser.setGender(data.optInt(User.GENDER, 0));
                mCurUser.setBirthday(data.optString(User.BIRTHDAY, "2000-01-01"));
//                mCurUser.setUserRoleLevel(data.optInt(User.SYS_ROLE, User.ROLE_NONE));
                mCurUser.setGuildId(data.optString(User.GUILD_ID, ""));
                mCurUser.setLegionId(data.optString(User.LEGION_ID, ""));
            }
        }
        return mCurUser;
    }

    synchronized JSONObject getData(){
        return mUserData;
    }

}
