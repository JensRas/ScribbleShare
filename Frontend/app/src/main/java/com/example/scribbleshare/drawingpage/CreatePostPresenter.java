package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

public class CreatePostPresenter implements IVolleyListener<byte[]> {
    private EndpointCaller<byte[]> model;
    private DrawingPageView view;
    private Context context;

    public CreatePostPresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<byte[]>(c, this);
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
    public void onSuccess(byte[] data) {
        Log.d("ree", "Upload multipart file success! Created new post");
        //TODO call view function(s) to do stuff when a new post is created
    }

    @Override
    public void onError(VolleyError error) {
        Log.e("ree", "Uploaded multipart file FAILED. Response: " + error);
    }
}
