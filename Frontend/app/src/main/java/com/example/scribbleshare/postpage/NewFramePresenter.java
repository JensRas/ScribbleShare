package com.example.scribbleshare.postpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

/**
 * Handles the endpoint calls for the frames
 */
public class NewFramePresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private PostView view;
    private Context context;

    /**
     * Constructor to initialize necessary information for requests for the frame
     * @param view The current view
     * @param c The current context
     */
    public NewFramePresenter(PostView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    /**
     * This method handles the request to create a new frame
     * @param username Username of the frame's poster
     * @param postId Id of the post
     * @param index Index of the frame
     */
    public void createNewFrame(String username, String postId, int index){
        model.createFrameRequest(username, postId, index);
    }

    /**
     * This method handles the successful endpoint request
     * @param jsonObject Frame data from request
     */
    @Override
    public void onSuccess(JSONObject jsonObject) {
        view.refreshFrames();
    }

    /**
     * TODO implement
     */
    @Override
    public void onError(VolleyError e) {
        //TODO handle error
        Log.e("debug", "new frame presenter ERROR! " + e.getMessage());
    }
}
