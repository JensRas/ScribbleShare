package com.example.scribbleshare.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.scribbleshare.model.EndpointCaller;
import com.example.scribbleshare.model.IVolleyListener;
import com.example.scribbleshare.view.CreateAccountView;
import com.example.scribbleshare.view.MainActivity;

public class CreateAccountPresenter implements IVolleyListener {

    private EndpointCaller model;
    private CreateAccountView view;
    private Context context;

    public CreateAccountPresenter(CreateAccountView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller(c, this);
    }

    public void createAccountRequest(String username, String password){
        //put things here that would occur in the UI whenever the user clicks the "create account" button
        //before the request is sent
        model.createAccountRequest(username, password);
    }

    @Override
    public void onSuccess(String response) {
        if(response.equals("new user created")){
            view.switchView(MainActivity.class);
            view.makeToast("Account Created");
        } else if(response.equals("username already exists")) {
            view.makeToast("username already exists");
        }
    }

    @Override
    public void onError(VolleyError error){
        view.makeToast("Unexpected error: " + error);
    }

    @Override
    public void onFileDownloadSuccess(byte[] b) {
        Log.e("ERROR", "Creating account shouldn't require this method");
    }

    @Override
    public void onFileDownloadFailure(VolleyError e) {
        Log.e("ERROR", "Creating account shouldn't require this method");
    }


}
