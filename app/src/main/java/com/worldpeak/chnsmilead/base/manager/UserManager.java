package com.worldpeak.chnsmilead.base.manager;

import com.worldpeak.chnsmilead.main.model.User;
import com.worldpeak.chnsmilead.main.model.UserInfo;

public class UserManager {

    private static UserManager instance;
    private UserManager(){}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public UserInfo getUserInfo() {
        return AccountManager.getUser();
    }

    public boolean isTeacher() {
        UserInfo user = getUserInfo();
        return user != null && user.getDefaultIdentity() == 2;
    }

    public boolean isFamily() {
        UserInfo user = getUserInfo();
        return user != null && user.getDefaultIdentity() == 1;
    }

}
