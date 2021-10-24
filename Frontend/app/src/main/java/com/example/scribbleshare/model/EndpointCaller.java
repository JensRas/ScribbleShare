package com.example.scribbleshare.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scribbleshare.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EndpointCaller {
//    private final String baseURL = "http://coms-309-010.cs.iastate.edu:8080";
    private String baseURL = "http://10.0.2.2:8080";

    private final Context context;
    private final IVolleyListener listener;

    public EndpointCaller(Context context, IVolleyListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void createAccountRequest(String username, String password) {
        String url = baseURL + "/users/new?username=" + username + "&password=" + password;
        sendStringRequest(url, Request.Method.PUT);
    }

    public void signInRequest(String username, String password) {
        String url = baseURL + "/users/login?username=" + username + "&password=" + password;
        sendStringRequest(url, Request.Method.GET);
    }

    public void createPostRequest(String username, Bitmap scribble){
        String url = baseURL + "/post?username=" + username;
        sendMultipartFileUpload(scribble, url, Request.Method.PUT);
    }

    public void getPostImageRequest(String postId){
        String url = baseURL + "/post/" + postId + "/image";
        Log.d("debug", "Model calling image endpoint: " + url);
        sendMultipartFileDownload(url, Request.Method.GET);
    }

    private void sendStringRequest(String url, int method) {
        StringRequest request = new StringRequest(
                method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("string request success", response);
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

    private void sendMultipartFileUpload(Bitmap bitmap, String url, int requestMethod) {
        MultipartRequestUpload request = new MultipartRequestUpload(
                requestMethod,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            listener.onSuccess(obj.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, MultipartRequestUpload.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new MultipartRequestUpload.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void sendMultipartFileDownload(String url, int requestMethod) {
        MultipartRequestDownload request = new MultipartRequestDownload(
                requestMethod,
                url,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        Log.d("debug", "MultipartFileDownload success! Calling presenter's listener");
                        listener.onFileDownloadSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("debug", "MultipartFileDownload FAILURE! Calling presenter's listener");
                        listener.onFileDownloadFailure(error);
                    }
                },
                null
        );

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

}
