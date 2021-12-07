package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class GetUserPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private ProfilePageView view;
    private Context context;

    public GetUserPresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void getUserBanStatus(String username) {
        model.getUserRequest(username);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onError(VolleyError e) {
        Log.e("ERROR", "Error getting user ban status. Error was:");
        e.printStackTrace();
    }
}
