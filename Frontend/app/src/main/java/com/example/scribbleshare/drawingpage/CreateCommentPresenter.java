package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

/**
 * Presenter Class for creating a new comment
 */
public class CreateCommentPresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private DrawingPageView view;
    private Context context;

    /**
     * Sets view and context to this
     * @param view drawing page view
     * @param c context
     */
    public CreateCommentPresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    /**
     * Gets information for the comment
     * @param username username of the person commenting
     * @param frameId id of frame they're drawing their comment in
     * @param scribble bitmap data from the drawing comment
     */
    public void createComment(String username, int frameId, Bitmap scribble){
        model.createCommentRequest(username, frameId, scribble);
    }

    /**
     * Returns JSON data for successful comment
     * @param o JSONObject for successful comment
     */
    @Override
    public void onSuccess(JSONObject o) {
        view.onCreateCommentSuccess(o);
    }

    @Override
    public void onError(VolleyError e) {
        Log.e("Error", "Error creating comment. Printing stacktrace: ");
        e.printStackTrace();
    }
}
