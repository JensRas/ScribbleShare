package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class AddFollowerPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private ProfilePageView view;
    private Context context;

    public AddFollowerPresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void addFollower(String username, String secondUsername){
        model.addFollowerRequest(username, secondUsername);
    }

    @Override
    public void onSuccess(JSONObject obj) {
    }

    @Override
    public void onError(VolleyError e) {

    }
}
