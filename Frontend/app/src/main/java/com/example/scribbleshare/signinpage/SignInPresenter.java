package com.example.scribbleshare.signinpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;
import com.example.scribbleshare.User;
import com.example.scribbleshare.test_homescreen;

public class SignInPresenter implements IVolleyListener<String> {
    private EndpointCaller<String> model;
    private SignInView view;
    private Context context;

    public SignInPresenter(SignInView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<String>(c, this);
    }

    public void signInRequest(String username, String password){
        model.signInRequest(username, password);
    }

    @Override
    public void onSuccess(String response) {
        if(response.equals("true")){
            Log.d("signInSuccess", "Sign in successful");
            User user = new User();
            MySingleton.getInstance(context).setApplicationUser(user);
            view.switchView(test_homescreen.class);
            view.makeToast("Signed In");
        }else{
            view.makeToast("Username/Password Invalid");
        }
    }

    @Override
    public void onError(VolleyError error) {

    }

}
