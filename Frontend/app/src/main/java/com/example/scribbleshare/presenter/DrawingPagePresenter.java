package com.example.scribbleshare.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.model.EndpointCaller;
import com.example.scribbleshare.model.IVolleyListener;
import com.example.scribbleshare.view.DrawingPageView;

public class DrawingPagePresenter implements IVolleyListener {
    private EndpointCaller model;
    private DrawingPageView view;
    private Context context;

    public DrawingPagePresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller(c, this);
    }

    public void createPost(String username, Bitmap scribble){
        model.createPostRequest(username, scribble);
    }

    public void getPost(String postId) {
        //TODO get other post data as well. Right now it only gets the image here
        Log.d("debug", "presenter calling model for image request");
        model.getPostImageRequest(postId);
    }

    @Override
    public void onFileDownloadSuccess(byte[] data){
        Log.d("ree", "Download multipart file success");
        view.setDrawingImage(data);
    }

    @Override
    public void onFileDownloadFailure(VolleyError e) {
        Log.d("ree", "Download multipart file failure! Error was: " + e.toString());
    }

    @Override
    public void onSuccess(String response) {
        Log.d("ree", "Uploaded multipart file. Response: " + response);
    }

    @Override
    public void onError(VolleyError error) {
        Log.e("ree", "Uploaded multipart file FAILED. Response: " + error);
    }
}
