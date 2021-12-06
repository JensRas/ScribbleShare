package com.example.scribbleshare.postpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.homepage.HomePageView;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class GetCommentIsLikedPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private HomePageView view;
    private Context context;

    public GetCommentIsLikedPresenter (HomePageView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void setIsCommentLiked(String username, String postId){
        model.createPostIsLikedRequest(username, postId);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        view.setHomePageIsLiked(jsonObject);
    }

    @Override
    public void onError(VolleyError e) {
        //TODO
    }
}