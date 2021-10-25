package com.example.scribbleshare.model;

import com.android.volley.NetworkError;
import com.android.volley.VolleyError;

public interface IVolleyListener {
    public void onSuccess(String s);
    public void onError(VolleyError e);
    public void onFileDownloadSuccess(byte[] b);
    public void onFileDownloadFailure(VolleyError e);
}
