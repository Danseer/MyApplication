package com.example.myapplication.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;
import com.example.myapplication.model.myUser;
import com.example.myapplication.ui.gallery.Fetcher;
import com.example.myapplication.ui.gallery.MyGalleryFrafment;
import com.example.myapplication.ui.list.MyListFragment;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;


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

            realm.executeTransaction(new Realm.Transaction() {

                public void execute(Realm realm) {
                    realm.deleteAll();

                    for (int i = 0; i < mItems.size(); i++) {
                        myUser mu = realm.createObject(myUser.class);
                        mu.setLogin(mItems.get(i).getLogin());
                        mu.setAvatarUrl(mItems.get(i).getAvatarUrl());
                    }
                }
            });
        }
    }

}
