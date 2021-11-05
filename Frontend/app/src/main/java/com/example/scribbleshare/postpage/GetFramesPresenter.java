package com.example.scribbleshare.postpage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.scribbleshare.homepage.HomePageView;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;

public class GetFramesPresenter implements IVolleyListener<JSONArray> {
    private EndpointCaller<JSONArray> model;
    private PostView view;
    private Context context;

    public GetFramesPresenter(PostView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONArray>(c, this);
    }

    public void getFrames(String postId){
        model.getPostFrames(postId);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        view.setFrames(jsonArray);
    }

    @Override
    public void onError(VolleyError e) {
        //TODO handle error
    }
}
