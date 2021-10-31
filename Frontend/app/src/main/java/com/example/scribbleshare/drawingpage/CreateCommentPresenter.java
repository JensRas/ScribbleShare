package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

public class CreateCommentPresenter implements IVolleyListener<String> {
    private EndpointCaller<String> model;
    private DrawingPageView view;
    private Context context;

    public CreateCommentPresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    public void createPost(String username, Bitmap scribble){

    }

    @Override
    public void onSuccess(String s) {

    }

    @Override
    public void onError(VolleyError e) {

    }
}
