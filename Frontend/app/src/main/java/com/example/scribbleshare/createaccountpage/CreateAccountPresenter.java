package com.example.scribbleshare.createaccountpage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.User;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Presenter for create account
 */
public class CreateAccountPresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private CreateAccountView view;
    private Context context;

    /**
     * Sets view and context to this
     * @param view drawing page view
     * @param c context
     */
    public CreateAccountPresenter(CreateAccountView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    /**
     * Creates an account request with username/password
     * @param username username that the user wants
     * @param password password that the user wants
     */
    public void createAccountRequest(String username, String password){
        //put things here that would occur in the UI whenever the user clicks the "create account" button
        //before the request is sent
        model.createAccountRequest(username, password);
    }

    /**
     * Checks if username is valid and if so goes to homescreen
     * @param response Is either user created or username already exists
     */
    @Override
    public void onSuccess(JSONObject response) {
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
        view.makeToast("Account Created");
    }

    /**
     * Handles volley error
     * @param error error message from volley
     */
    @Override
    public void onError(VolleyError error){
        view.makeToast("USERNAME ERROR TODO");
    }

}
