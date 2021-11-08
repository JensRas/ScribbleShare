package com.example.scribbleshare.signinpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;
import com.example.scribbleshare.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A presenter/listener for the sign in page
 */
public class SignInPresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private SignInView view;
    private Context context;

    /**
     * Create a new sign in presenter for a given view and context
     * @param view The view of the presenter
     * @param c The context of the presenter
     */
    public SignInPresenter(SignInView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONObject>(c, this);
    }

    /**
     * Create a new sign in request with the given user credentials
     * @param username The username for the sign in request
     * @param password The password for the sign in request
     */
    public void signInRequest(String username, String password){
        model.signInRequest(username, password);
    }

    /**
     * When the request succeeds
     * @param response The object of the response
     */
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

    /**
     * When the request fails
     * @param error the error of the request to process
     */
    @Override
    public void onError(VolleyError error) {
        //login invalid
        Log.e("login invalid", "invalid login: " + error.getMessage());
        view.makeToast("Username/Password Invalid");
    }

}
