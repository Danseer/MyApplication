package com.example.myapplication.ui.list;

import android.util.Log;
import com.example.myapplication.model.userRepos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Константин on 17.09.2017.
 */

public class FetherList {

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


        } catch (IOException e) {}
        catch (JSONException e) {}

        return reposList;

    }

    private void parseItems(List<userRepos> items, String fromServer) throws IOException, JSONException {
        JSONArray usersJSONArray = new JSONArray(fromServer);

        for (int i = 0; i < usersJSONArray.length(); i++) {
            JSONObject userJSONObject = usersJSONArray.getJSONObject(i);
            userRepos item = new userRepos();
            item.setUserRepos(userJSONObject.getString("full_name"));
            items.add(item);
        }

    }

}
