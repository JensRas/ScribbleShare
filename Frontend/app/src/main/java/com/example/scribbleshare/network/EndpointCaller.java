package com.example.scribbleshare.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.scribbleshare.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A model type object which communicates with the server. It is assumed that the specified parameter is the return type of the requests
 * @param <T> The type of the request
 */
public class EndpointCaller<T> {
    /**
     * The URL of the endpoint. (The local one is also present below for easier testing
     */
//    public static final String baseURL = "http://coms-309-010.cs.iastate.edu:8080";
//    public static final String baseURL = "http://10.0.2.2:8080"; //for debugging with emulated phone
    public static final String baseURL = "http://localhost:8080"; //for debugging with tethered phone (must use chrome reverse port forwarding)

    /**
     * The context of the request when instantiated
     */
    private final Context context;

    /**
     * The listener each requet will send to. Most presenters are these listeners as they all implement IVolleyListener<T>
     */
    private final IVolleyListener<T> listener;

    /**
     * Create an endpoint caller with given context and listener objects
     * @param context the context which the caller resides
     * @param listener the listener with which the requests will be sent to one received
     */
    public EndpointCaller(Context context, IVolleyListener<T> listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Create a new account
     * @param username Account username
     * @param password Account password (plaintext)
     */
    public void createAccountRequest(String username, String password) {
        String url = baseURL + "/users/new?username=" + username + "&password=" + password;
        sendJsonObjectRequest(url, Request.Method.PUT);
    }

    /**
     * Attempt a sign in
     * @param username attempted username sign in
     * @param password attempted password sign in
     */
    public void signInRequest(String username, String password) {
        String url = baseURL + "/users/login?username=" + username + "&password=" + password;
        sendJsonObjectRequest(url, Request.Method.GET);
    }

    /**
     * Create a new post and save the specified bitmap
     * @param username Username of the account to create the post
     * @param scribble Bitmap of the post's image to be saved on the database
     */
    public void createPostRequest(String username, Bitmap scribble){
        String url = baseURL + "/post?username=" + username;
        sendMultipartFileUpload(scribble, url, Request.Method.PUT);
    }

    /**
     * Get the home screen posts for a specified user
     * @param username The username of the user to get the recommended home screen posts
     */
    public void getHomeScreenPostsRequest(String username) {
        String url = baseURL + "/post/getHomeScreenPosts/" + username;
//        Log.d("debug", "Model calling json array endpoint: " + url);
        sendJsonArrayRequest(url);
    }

    /**
     * Get the frames of a specified post
     * @param postId the id of the post
     */
    public void getPostFrames(String postId){
        String url = baseURL + "/frames/" + postId;
//        Log.d("debug", "Model calling json array endpoint: " + url);
        sendJsonArrayRequest(url);
    }

    /**
     * Create a new comment
     * @param username Username of the user who made the comment
     * @param frameId Frame id with which the comment was made
     * @param scribble Bitmap of the image the comment was made under
     */
    public void createCommentRequest(String username, int frameId, Bitmap scribble){
        String url = baseURL + "/comment?username=" + username + "&frameId=" + frameId;
//        Log.d("debug", "creating comment request with url: " + url);
        sendMultipartFileUpload(scribble, url, Request.Method.PUT);
    }

    /**
     * Create a new frame for the specified post
     * @param username Username of the user creating the frame
     * @param postId Id of the post to create the new frame
     * @param index Index of the new frame for the post (for concurrency)
     */
    public void createFrameRequest(String username, String postId, int index){
        String url = baseURL + "/frames?username=" + username + "&postId=" + postId + "&index=" + index;
//        Log.d("debug", "creating new frame request with url: " + url);
        sendJsonObjectRequest(url, Request.Method.POST);
    }

    public void createSearchRequest(String search) {
        String url = baseURL + "/users/search/" + search;
//        Log.d("debug", "performing search with url: " + url);
        sendJsonArrayRequest(url);
    }

    /**
     * Send a request where the response is a string
     * @param url full endpoint url
     * @param method HTTP method for the request
     */
    private void sendStringRequest(String url, int method) {
        StringRequest request = new StringRequest(
                method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onSuccess((T)response); //TODO check cast?
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("string request error", error.toString());
                        listener.onError(error);
                    }
                }
        );

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    /**
     * Send a request where the response is a JSON object
     * @param url full endpoint url
     * @param method HTTP method for the request
     */
    private void sendJsonObjectRequest(String url, int method) {
        JsonObjectRequest request = new JsonObjectRequest(
                method,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess((T) response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    /**
     * Send a request where the response is a JSON array
     * @param url full endpoint url
     */
    private void sendJsonArrayRequest(String url) {
        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.e("response", "JsonarrayRequest response: " + response.toString());
                        listener.onSuccess((T)response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    /**
     * Send a multipart file upload
     * @param bitmap bitmap file to upload
     * @param url full endpoint url
     * @param requestMethod HTTP method for the request
     */
    private void sendMultipartFileUpload(Bitmap bitmap, String url, int requestMethod) {
        MultipartRequestUpload request = new MultipartRequestUpload(
                requestMethod,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            //TODO change this to reflect the acutal endpoints response structure
                            JSONObject obj = new JSONObject(new String(response.data));
                            listener.onSuccess((T)obj); //TODO check cast?
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("multipart upload error", error.toString());
                        listener.onError(error);
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

    /**
     * Convert a bitmap to a byte array
     * @param bitmap bitmap
     * @return the byte array of the bitmap
     */
    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
