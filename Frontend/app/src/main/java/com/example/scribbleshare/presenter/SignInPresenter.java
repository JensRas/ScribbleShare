package com.example.scribbleshare.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.scribbleshare.model.EndpointCaller;
import com.example.scribbleshare.model.IVolleyListener;
import com.example.scribbleshare.test_homescreen;
import com.example.scribbleshare.view.SignInView;

public class SignInPresenter implements IVolleyListener {
    private EndpointCaller model;
    private SignInView view;
    private Context context;

    public SignInPresenter(SignInView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller(c, this);
    }

    public void signInRequest(String username, String password){
        model.signInRequest(username, password);
    }

    @Override
    public void onSuccess(String response) {
        if(response.equals("true")){
            Log.d("signInSuccess", "Sign in successful");
            view.switchView(test_homescreen.class);
            view.makeToast("Signed In");
        }else{
            view.makeToast("Username/Password Invalid");
        }
    }

    @Override
    public void onError(String s) {

    }
}
