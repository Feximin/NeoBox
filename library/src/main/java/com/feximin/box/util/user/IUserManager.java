package com.feximin.box.util.user;

/**
 * Created by Neo on 16/6/20.
 */

public interface IUserManager {
    boolean isLogin();
    void logout();
    AbsUser getUser();
    AbsUser getUserFromLocal();
    AbsUser getUserFromServer();
}
