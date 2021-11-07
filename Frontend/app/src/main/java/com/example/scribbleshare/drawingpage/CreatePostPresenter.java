package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class CreatePostPresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private DrawingPageView view;
    private Context context;

    public CreatePostPresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
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
    public void onSuccess(JSONObject o) {
        Log.d("ree", "Upload multipart file success! Created new post");
        view.onCreatePostSuccess(o);
        view.switchView(HomePage.class);
        view.makeToast("Post made / saved!");
    }

    @Override
    public void onError(VolleyError error) {
        Log.e("ree", "Uploaded multipart file FAILED. Response: " + error);
        view.makeToast("Bad save");
    }
}
