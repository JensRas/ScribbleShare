package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class ProfilePagePresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private ProfilePageView view;
    private Context context;

    public ProfilePagePresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONObject>(c, this);
    }

    public void getFollowers(String username){
        model.getFollowersRequest(username);
    }

    public void getFollowersNum(String username){
        model.getNumFollowersRequest(username);
    }

    @Override
    public void onSuccess(JSONObject data) {
        Log.d("success", "get followers success!");
    }

    @Override
    public void onError(VolleyError error) {
        Log.e("fail", "get followers FAILED. Response: " + error);
    }
}
