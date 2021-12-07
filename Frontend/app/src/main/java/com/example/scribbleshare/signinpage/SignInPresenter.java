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
     * Creates a sign in request with given username/password
     * @param username username entered by user
     * @param password password entered by user
     */
    public void signInRequest(String username, String password){
        model.signInRequest(username, password);
    }

    /**
     * Handles if username/password is valid
     * @param response The object of the response
     */
    @Override
    public void onSuccess(JSONObject response) {
        Log.d("SignInPresenter", "response: " + response);
        User user = new User();
        try {
            user.setUsername((String)response.get("username"));
//            user.setPermissionLevel((String)response.get("permissionLevel")); //TODO uncomment once implemented
            user.setMuted((boolean)response.get("isMuted"));
            user.setBanned((boolean)response.get("isBanned"));
        } catch (JSONException e) {
            Log.e("Error", "error parsing sign in User object. Error was: ");
            e.printStackTrace();
        }
        MySingleton.getInstance(context).setApplicationUser(user);
        view.switchView(HomePage.class);
    }

    /**
     * Handles if username/password is invalid
     * @param error Volley error
     */
    @Override
    public void onError(VolleyError error) {
        if(error.getMessage() == null){
            view.makeToast("Unable to connect");
        }else{
            view.makeToast("Username/Password Invalid");
        }
    }

}
