package com.example.scribbleshare.network;

import com.android.volley.VolleyError;

public interface IVolleyListener <T>{
    void onSuccess(T t);
    void onError(VolleyError e);
}
