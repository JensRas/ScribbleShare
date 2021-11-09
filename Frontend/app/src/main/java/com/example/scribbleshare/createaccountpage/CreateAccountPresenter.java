package com.example.scribbleshare.createaccountpage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

/**
 * Presenter for create account
 */
public class CreateAccountPresenter implements IVolleyListener<String> {
    private EndpointCaller<String> model;
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
        this.model = new EndpointCaller<String>(c, this);
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
    public void onSuccess(String response) {
        if(response.equals("new user created")){
            view.switchView(HomePage.class);
            view.makeToast("Account Created");
        } else if(response.equals("username already exists")) {
            view.makeToast("username already exists");
        }
    }

    /**
     * Handles volley error
     * @param error error message from volley
     */
    @Override
    public void onError(VolleyError error){
        view.makeToast("Unexpected error: " + error);
    }

}
