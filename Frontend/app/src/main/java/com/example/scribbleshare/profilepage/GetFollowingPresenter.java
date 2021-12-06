package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class GetFollowingPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private ProfilePageView view;
    private Context context;

    public GetFollowingPresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void setIsFollowing(String username, String secondUsername){
        model.createIsUserFollowing(username, secondUsername);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        view.setUserFollowing(jsonObject);
    }

    @Override
    public void onError(VolleyError e) {
        //TODO
    }
}
