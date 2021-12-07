package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class UnfollowUserPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private ProfilePageView view;
    private Context context;

    public UnfollowUserPresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void unfollowUser(String username, String secondUsername){
        Log.e("debug", "unfollow in presenter");
        model.unfollowUser(username, secondUsername);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onError(VolleyError e) {

    }
}
