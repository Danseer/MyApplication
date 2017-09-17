package com.example.myapplication.model;

import io.realm.RealmObject;

/**
 * Created by Константин on 17.09.2017.
 */

public class userRepos  extends RealmObject {
    private String userId;
    private String userLogin;
    private String userRepos;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserRepos() {
        return userRepos;
    }

    public void setUserRepos(String userRepos) {
        this.userRepos = userRepos;
    }


}
