package com.example.scribbleshare.postpage;

import android.graphics.Bitmap;

import org.json.JSONArray;

public interface PostView {
    void setFrames(JSONArray array);
    void refreshFrames();
}
