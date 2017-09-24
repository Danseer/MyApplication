package com.example.myapplication.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.myUser;
import com.example.myapplication.ui.gallery.Fetcher;
import com.example.myapplication.ui.gallery.MyGalleryFrafment;
import com.example.myapplication.ui.list.MyListFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity
{

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        FirebaseApp.initializeApp(this);
        //String token = FirebaseInstanceId.getInstance().getToken();
       //Log.d("myToken", token);
       // Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        FirebaseMessaging.getInstance().subscribeToTopic("news");
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
                        mu.setId(mItems.get(i).getId());
                    }
                }
            });
        }
    }

}
