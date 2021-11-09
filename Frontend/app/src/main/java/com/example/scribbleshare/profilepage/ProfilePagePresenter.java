package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

/**
 * TODO implement
 */
public class ProfilePagePresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private ProfilePageView view;
    private Context context;

    /**
     * TODO implement
     * @param view
     * @param c
     */
    public ProfilePagePresenter(ProfilePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONObject>(c, this);
    }

    /**
     * TODO implement
     * @param username
     */
    public void getFollowers(String username){
        // model.getFollowersRequest(username);
    }

    /**
     * TODO implement
     * @param data
     */
    @Override
    public void onSuccess(JSONObject data) {
        Log.d("success", "get followers success!");
    }

    /**
     * TODO implement
     * @param error
     */
    @Override
    public void onError(VolleyError error) {
        Log.e("fail", "get followers FAILED. Response: " + error);
    }
}
