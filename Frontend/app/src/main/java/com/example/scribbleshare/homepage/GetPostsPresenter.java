package com.example.scribbleshare.homepage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.scribbleshare.drawingpage.DrawingPageView;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;

public class GetPostsPresenter implements IVolleyListener<JSONArray> {
    private EndpointCaller<JSONArray> model;
    private HomePageView view;
    private Context context;

    public GetPostsPresenter(HomePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONArray>(c, this);
    }

    public void populateHomeScreenPosts(String username){
        model.getHomeScreenPostsRequest(username);
    }

    @Override
    public void onSuccess(JSONArray array) {
        view.setHomePagePosts(array);
    }

    @Override
    public void onError(VolleyError e) {
        //TODO
    }
}
