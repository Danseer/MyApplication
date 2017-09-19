package com.example.myapplication.model;

import io.realm.RealmObject;

/**
 * Created by Константин on 16.09.2017.
 */

public class myUser extends RealmObject {

    private String id,login,avatarUrl;
    private int  changesCount=0;

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

    public int getChangesCount() {
        return changesCount;
    }

    public void setChangesCount(int i) {
        this.changesCount +=1;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
