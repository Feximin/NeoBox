package com.feximin.library.util;

import com.mianmian.guild.Constant;
import com.mianmian.guild.entity.User;

import org.json.JSONObject;

/**
 * Created by Neo on 16/3/15.
 */
public class UserUtil {



    public static void login(JSONObject data){
        UserSession.getInstance().login(data);
    }

    public static void updateUserInfo(User user){
        UserSession.getInstance().updateUserInfo(user);
    }

    public static void logout(){
        UserSession.getInstance().logout();
    }

    public static boolean isLogin(){
        return UserSession.getInstance().getUser() != null ;
    }

    public static boolean isGuildManager(){
        return getUserSysRole() == User.SYS_ROLE_GUILD_MANAGER;
    }

    public static boolean isLegionManager(){
        return getUserLegionRole() == User.LEGION_ROLE_MANAGER;
    }

    public static JSONObject getSessionData(){
        return UserSession.getInstance().getData();
    }

    public static User getUser(){
        return UserSession.getInstance().getUser();
    }


    public static int getUserSysRole(){
        return isLogin()?getUser().getUserSysRole():User.SYS_ROLE_NORMAL;
    }

    public static int getUserLegionRole(){
        return isLogin()?getUser().getUserLegionRole():User.LEGION_ROLE_NORMAL;
    }

    public static String getUserAvatar(){
        return isLogin()?getUser().getAvatar():"";
    }

    public static String getBirthday(){
        return isLogin()?getUser().getBirthday():"";
    }

    public static String getUserId(){
        return isLogin()?getUser().getId():"";
    }

    public static boolean isMy(String targetId){
        return Tool.isAllNotEmptyAndEquals(targetId, getUserId());
    }

    public static String getUserName(){
        return  isLogin()?getUser().getName():"";
    }

    public static int getUserGender(){
        return isLogin()?getUser().getGender():-1;
    }

    public static String getUserLocal(){
        return "";
    }

    public static String getUserToken(){
        return isLogin()?getUser().getToken():"";
    }

    public static String getUserRyToken(){
        return isLogin()?getUser().getRyToken():"";
    }

    public static int getCoinCount(){
        return isLogin()?getUser().getCoinCount():0;
    }

    public static void updateCoinCount(){

    }

    public static String getGuildId(){
        return isLogin()?getUser().getGuildId():"";
    }

    public static String getLegionId(){
        return isLogin()?getUser().getLegionId():"";
    }

    public static int getUserRoleLevel(){
        return isLogin()?getUser().getLevel():0;
    }

    public static void updateRoleLevel(int level){
        UserSession.getInstance().updateUserLevel(level);
    }

    public static void updateLegion(String legionId){
        UserSession.getInstance().updateLegion(legionId);
    }

    public static void updateGuildId(String guildId){
        UserSession.getInstance().updateGuild(guildId);
    }

    public static void init() {
        UserSession.getInstance().getUser();
    }

    public static String getLastLoginUserPhone(){
        return Tool.getStringValue(Constant.LAST_LOGIN_USER_PHONE);
    }
}
