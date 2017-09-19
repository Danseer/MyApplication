package com.example.myapplication.ui;

import android.util.Log;
import com.example.myapplication.model.myUser;
import io.realm.Realm;

/**
 * Created by Константин on 19.09.2017.
 */
public class Mess {

    Realm realm;

    public void Output(String message) {
        realm = Realm.getDefaultInstance();
        Log.e("id Пользователя", message);

        myUser mu = realm.where(myUser.class)
                .equalTo("id", message)
                .findFirst();

        realm.beginTransaction();

        int old = mu.getChangesCount();
        mu.setChangesCount(old + 1);

        realm.commitTransaction();


    }
}