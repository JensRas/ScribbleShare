package com.example.scribbleshare.homepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class GetPostIsLikedPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private HomePageView view;
    private Context context;

    public GetPostIsLikedPresenter(HomePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void setIsPostLiked(String username, int postId){
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
