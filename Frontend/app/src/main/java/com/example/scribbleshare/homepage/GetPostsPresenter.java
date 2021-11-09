package com.example.scribbleshare.homepage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;

/**
 * Handles the endpoint calls for the homepage posts
 */
public class GetPostsPresenter implements IVolleyListener<JSONArray> {
    private EndpointCaller<JSONArray> model;
    private HomePageView view;
    private Context context;

    /**
     * Constructor to initialize necessary information for requests for the homepage
     * @param view The current view
     * @param c The current context
     */
    public GetPostsPresenter(HomePageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONArray>(c, this);
    }

    /**
     * Handles the requests for homepage posts based on username
     * @param username Name of current user
     */
    public void populateHomeScreenPosts(String username){
        model.getHomeScreenPostsRequest(username);
    }

    /**
     * This method handles the successful endpoint request
     * @param array Homepage post data from request
     */
    @Override
    public void onSuccess(JSONArray array) {
        view.setHomePagePosts(array);
    }

    /**
     * This method handles the errors from the endpoint request
     * @param e Volley error from endpoint request
     */
    @Override
    public void onError(VolleyError e) {
        //TODO
    }
}
