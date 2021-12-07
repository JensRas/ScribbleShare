package com.example.scribbleshare.postpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.postpage.PostView;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class GetCommentIsLikedPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private PostView view;
    private Context context;

    public GetCommentIsLikedPresenter (PostView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void setIsCommentLiked(String username, String commentId){
        model.createCommentIsLikedRequest(username, commentId);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        view.setCommentIsLiked(jsonObject);
    }

    @Override
    public void onError(VolleyError e) {
        //TODO
    }
}