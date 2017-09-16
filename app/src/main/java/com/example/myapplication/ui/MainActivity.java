package com.example.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.model.myUser;
import com.example.myapplication.ui.gallery.MyGalleryAdapter;
import com.example.myapplication.ui.gallery.MyGalleryFrafment;
import com.example.myapplication.ui.list.MyListFragment;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    RealmResults<myUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(MainActivity.this);
        realm = Realm.getDefaultInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.gallery_container, new MyGalleryFrafment())
                    .add(R.id.list_container, new MyListFragment())
                    .commit();
        }

        realm.executeTransaction(new Realm.Transaction() {

    public void execute(Realm realm) {
        realm.deleteAll();
        for (int i = 0; i < 5; i++) {
            myUser mu = realm.createObject(myUser.class);
            mu.setLogin("mojombo");
            mu.setAvatarUrl("https://avatars0.githubusercontent.com/u/1?v=4");
        }
    }

    });


}




}
