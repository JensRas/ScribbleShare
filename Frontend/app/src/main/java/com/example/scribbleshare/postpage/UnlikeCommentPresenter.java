package com.example.scribbleshare.postpage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.scribbleshare.homepage.HomePageView;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

public class UnlikeCommentPresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private PostView view;
    private Context context;

    public UnlikeCommentPresenter(Context c) {
//        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(context, this);
    }

    public void unlikeComment(String username, String commentId){
        model.createUnlikeCommentRequest(username, commentId);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onError(VolleyError e) {

    }
}
