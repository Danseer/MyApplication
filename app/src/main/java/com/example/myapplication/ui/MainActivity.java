package com.example.myapplication.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.model.myUser;
import com.example.myapplication.model.userRepos;
import com.example.myapplication.ui.gallery.Fetcher;
import com.example.myapplication.ui.gallery.MyGalleryAdapter;
import com.example.myapplication.ui.gallery.MyGalleryFrafment;
import com.example.myapplication.ui.list.MyListFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    private List<myUser> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(MainActivity.this);
        new FetchItemTask().execute();
        realm = Realm.getDefaultInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.gallery_container, new MyGalleryFrafment())
                    .add(R.id.list_container, new MyListFragment())
                    .commit();
        }

    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<myUser>> {
        @Override
        protected void onPreExecute() {


        }

        @Override
        protected List<myUser> doInBackground(Void... voids) {
            return new Fetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<myUser> users) {
            mItems = users;
            Log.e("sizeUsers", String.valueOf(mItems.size()));

            realm.executeTransaction(new Realm.Transaction() {

                public void execute(Realm realm) {
                    realm.deleteAll();

                    for (int i = 0; i < mItems.size(); i++) {
                        myUser mu = realm.createObject(myUser.class);
                       // Log.e("getLogin", mItems.get(i).getLogin());
                        mu.setLogin(mItems.get(i).getLogin());
                       // Log.e("getUrl", mItems.get(i).getAvatarUrl());
                        mu.setAvatarUrl(mItems.get(i).getAvatarUrl());
                    }

                   //userRepos ur=realm.createObject(userRepos.class);
                  //  ur.setUserRepos("repos");
                   // userRepos ur1=realm.createObject(userRepos.class);
                   // ur1.setUserRepos("repos-repos");
                }

            });
        }
    }


}
