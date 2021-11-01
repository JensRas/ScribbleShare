package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfilePagePresenter implements IVolleyListener<JSONArray> {
    private EndpointCaller<JSONArray> model;
    private ProfilePageView view;
    private Context context;

    public ProfilePagePresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONArray>(c, this);
    }

    public void getFollowers(String username){
       // model.getFollowersRequest(username);
    }

    public void getUserPosts(String username){
        model.getUserPostRequest(username);
    }

    @Override
    public void onSuccess(JSONArray array) {
        Log.d("success", "get followers success!");
        view.setUserPosts(array);
    }

    @Override
    public void onError(VolleyError error) {
        Log.e("fail", "get followers FAILED. Response: " + error);
    }
}
