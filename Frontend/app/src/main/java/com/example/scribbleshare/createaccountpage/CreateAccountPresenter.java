package com.example.scribbleshare.createaccountpage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;
import com.example.scribbleshare.MainActivity;

/**
 *
 */
public class CreateAccountPresenter implements IVolleyListener<String> {
    private EndpointCaller<String> model;
    private CreateAccountView view;
    private Context context;

    /**
     *
     * @param view
     * @param c
     */
    public CreateAccountPresenter(CreateAccountView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<String>(c, this);
    }

    /**
     *
     * @param username
     * @param password
     */
    public void createAccountRequest(String username, String password){
        //put things here that would occur in the UI whenever the user clicks the "create account" button
        //before the request is sent
        model.createAccountRequest(username, password);
    }

    /**
     *
     * @param response
     */
    @Override
    public void onSuccess(String response) {
        if(response.equals("new user created")){
            view.switchView(MainActivity.class);
            view.makeToast("Account Created");
        } else if(response.equals("username already exists")) {
            view.makeToast("username already exists");
        }
    }

    /**
     *
     * @param error
     */
    @Override
    public void onError(VolleyError error){
        view.makeToast("Unexpected error: " + error);
    }

}
