package com.example.scribbleshare.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.view.MainActivity;

public class EndpointCaller {
    private String baseURL = "http://coms-309-010.cs.iastate.edu:8080";
//    private String baseURL = "http://10.0.2.2:8080";

    private final Context context;
    private final IVolleyListener listener;

    public EndpointCaller(Context context, IVolleyListener listener){
        this.context = context;
        this.listener = listener;
    }

    public void createAccountRequest(String username, String password){
        String url = baseURL + "/users/new?username=" + username + "&password=" + password;
        sendStringRequest(url, Request.Method.PUT);
    }

    public void signInRequest(String username, String password){
        String url = baseURL + "/users/login?username=" + username + "&password=" + password;
        sendStringRequest(url, Request.Method.GET);
    }

    private void sendStringRequest(String url, int method){
        StringRequest request = new StringRequest(
                method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("string request success", response.toString());
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("string request error", error.toString());
                        listener.onError(error.getMessage());
                    }
                }
        );

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

}
