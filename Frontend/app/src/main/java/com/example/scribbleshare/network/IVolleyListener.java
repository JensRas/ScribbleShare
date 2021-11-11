package com.example.scribbleshare.network;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * A listener that presenters should implement
 * @param <T> The object that the request will return (JSONObject, JSONArray, etc)
 */
public interface IVolleyListener <T>{
    /**
     * Called when a request successfully completes
     * @param t The object that the request will return (JSONObject, JSONArray, etc)
     */
    void onSuccess(T t);

    //void onSuccess(JSONArray array);

    /**
     * Called when a request fails
     * @param e The error from the request
     */
    void onError(VolleyError e);
}
