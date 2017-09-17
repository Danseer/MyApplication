package com.example.myapplication.ui.list;

import android.util.Log;

import com.example.myapplication.model.myUser;
import com.example.myapplication.model.userRepos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Константин on 17.09.2017.
 */

public class FetherList {
    private static final String TAG = "FetcherList";
    Realm realm;

    public String getJSONString(String URLSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URLSpec)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        return result;
    }

    public List<userRepos> fetchItems(String login) {
        final List<userRepos> reposList = new ArrayList<>();
        try {
            String url = "https://api.github.com/users/" + login + "/repos";


            String jsonString = getJSONString(url);
            Log.e("url", url);
            Log.e("jsonString", jsonString);


            parseItems(reposList, jsonString);

            // realm.executeTransaction(new Realm.Transaction() {

            //   public void execute(Realm realm) {
            //    for (int i = 0; i < reposList.size(); i++) {
            //      userRepos ur = realm.createObject(userRepos.class);
            // Log.e("getLogin", reposList.get(i).getLogin());
            //       ur.setUserRepos(reposList.get(i).getUserRepos());

            //  }

            //  }
            // });

        } catch (IOException e) {
            Log.e(TAG, "Ощибка загрузки данных", e);
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка парсинга JSON", e);
        }

        return reposList;

    }

    private void parseItems(List<userRepos> items, String fromServer) throws IOException, JSONException {
        JSONArray usersJSONArray = new JSONArray(fromServer);

        for (int i = 0; i < usersJSONArray.length(); i++) {
            JSONObject userJSONObject = usersJSONArray.getJSONObject(i);
            userRepos item = new userRepos();
Log.e("repositories",userJSONObject.getString("name"));
            item.setUserRepos(userJSONObject.getString("name"));


            items.add(item);
        }

    }

}