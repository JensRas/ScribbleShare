package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class BanUserPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private ProfilePageView view;
    private Context context;

    public BanUserPresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void banUser(String username) {
        model.banUserRequest(username);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        view.setUserBanned(jsonObject);
    }

    @Override
    public void onError(VolleyError e) {
        Log.e("ERROR", "Error banning user. Error was:");
        e.printStackTrace();
    }
}
