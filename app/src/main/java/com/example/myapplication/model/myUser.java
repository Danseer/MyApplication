package com.example.myapplication.model;

import io.realm.RealmObject;

/**
 * Created by Константин on 16.09.2017.
 */

public class myUser extends RealmObject {

    private String login,avatarUrl;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


}
