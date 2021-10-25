package com.example.scribbleshare.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.model.EndpointCaller;
import com.example.scribbleshare.model.IVolleyListener;
import com.example.scribbleshare.model.User;
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

    @Override
    public void onFileDownloadSuccess(byte[] b) {
        Log.e("ERROR", "Signing in shouldn't require this method");
    }

    @Override
    public void onFileDownloadFailure(VolleyError e) {
        Log.e("ERROR", "Signing in shouldn't require this method");
    }
}
