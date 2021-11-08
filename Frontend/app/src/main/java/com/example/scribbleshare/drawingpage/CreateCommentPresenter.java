package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

/**
 *
 */
public class CreateCommentPresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private DrawingPageView view;
    private Context context;

    /**
     *
     * @param view
     * @param c
     */
    public CreateCommentPresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    /**
     *
     * @param username
     * @param frameId
     * @param scribble
     */
    public void createComment(String username, int frameId, Bitmap scribble){
        model.createCommentRequest(username, frameId, scribble);
    }

    /**
     *
     * @param o
     */
    @Override
    public void onSuccess(JSONObject o) {
        view.onCreateCommentSuccess(o);
    }

    /**
     *
     * @param e
     */
    @Override
    public void onError(VolleyError e) {
        //TODO handle error
    }
}
