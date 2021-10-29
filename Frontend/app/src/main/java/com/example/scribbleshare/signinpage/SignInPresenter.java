package com.example.scribbleshare.signinpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.homepage.HomeFragment;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;
import com.example.scribbleshare.User;
import com.example.scribbleshare.test_homescreen;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInPresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private SignInView view;
    private Context context;

    public SignInPresenter(SignInView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONObject>(c, this);
    }

    public void signInRequest(String username, String password){
        model.signInRequest(username, password);
    }

    @Override
    public void onSuccess(JSONObject response) {
        Log.e("test", "response: " + response);
        Log.d("signInSuccess", "Sign in successful");
        User user = new User();
        try {
            user.setUsername((String)response.get("username"));
//            user.setPermissionLevel((String)response.get("permissionLevel")); //TODO uncomment once implemented
            user.setMuted((boolean)response.get("isMuted"));
            user.setBanned((boolean)response.get("isBanned"));
        } catch (JSONException e) {
            //TODO handle bad parse?
            e.printStackTrace();
        }
        MySingleton.getInstance(context).setApplicationUser(user);
        view.switchView(HomePage.class);
    }

    @Override
    public void onError(VolleyError error) {
        //login invalid
        Log.e("login invalid", "invalid login: " + error.getMessage());
        view.makeToast("Username/Password Invalid");
    }

}
