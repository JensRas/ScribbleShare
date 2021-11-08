package com.example.scribbleshare.network;

import com.android.volley.VolleyError;

/**
 *
 * @param <T>
 */
public interface IVolleyListener <T>{
    /**
     *
     * @param t
     */
    void onSuccess(T t);

    /**
     *
     * @param e
     */
    void onError(VolleyError e);
}
