package com.example.scribbleshare.postpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;

/**
 * Handles the endpoint calls for the frames of a post
 */
public class GetFramesPresenter implements IVolleyListener<JSONArray> {
    private EndpointCaller<JSONArray> model;
    private PostView view;
    private Context context;
    boolean shouldScrollToBottom;

    /**
     * Constructor to initialize necessary information for requests for the frames of a post
     * @param view The current view
     * @param c The current context
     */
    public GetFramesPresenter(PostView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONArray>(c, this);
    }

    /**
     * This method returns the frames of the given post
     * @param postId Id of post
     */
    public void getFrames(int postId, boolean shouldScrollToBottom){
        this.shouldScrollToBottom = shouldScrollToBottom;
        model.getPostFrames(postId);
    }


    /**
     * This method handles the successful endpoint request
     * @param jsonArray Frame data from request
     */
    @Override
    public void onSuccess(JSONArray jsonArray) {
        view.setFrames(jsonArray, shouldScrollToBottom);
    }

    /**
     * TODO implement
     */
    @Override
    public void onError(VolleyError e) {
        //TODO handle error
        Log.e("ERROR", "get frame presenter ERROR! " + e.getMessage());
    }
}
