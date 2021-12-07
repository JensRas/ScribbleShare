package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.User;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class GetUserPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private DrawingPageView view;
    private Context context;

    public GetUserPresenter(DrawingPageView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    public void updateUserInSingleton(String username){
        model.getUserRequest(username);
    }

    @Override
    public void onSuccess(JSONObject response) {
        Log.d("Debug", "get user response: " + response);
        User user = new User();
        try {
            user.setUsername((String)response.get("username"));
            user.setPermissionLevel((String)response.get("permissionLevel"));
            user.setMuted((boolean)response.get("isMuted"));
            user.setBanned((boolean)response.get("isBanned"));
        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing user object. Object was: " + response);
            e.printStackTrace();
        }
        MySingleton.getInstance(context).setApplicationUser(user);
    }

    @Override
    public void onError(VolleyError e) {
        Log.e("Error", "Unable to find username. Error: " + e);
    }
}
