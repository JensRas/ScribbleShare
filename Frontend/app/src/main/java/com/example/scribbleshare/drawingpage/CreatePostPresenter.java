package com.example.scribbleshare.drawingpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONObject;

/**
 * Presenter class for creating a new post
 */
public class CreatePostPresenter implements IVolleyListener<JSONObject> {
    private EndpointCaller<JSONObject> model;
    private DrawingPageView view;
    private Context context;

    /**
     * Sets view and context to this
     * @param view drawing page view
     * @param c context
     */
    public CreatePostPresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    /**
     * Creates post
     * @param username username of the poster
     * @param scribble data from the drawing post
     */
    public void createPost(String username, Bitmap scribble){
        model.createPostRequest(username, scribble);
    }

    /**
     * Creates the post on success
     * @param o JSON data for the created post
     */
    @Override
    public void onSuccess(JSONObject o) {
        Log.d("ree", "Upload multipart file success! Created new post");
        view.onCreatePostSuccess(o);
        view.switchView(HomePage.class);
        view.makeToast("Post made / saved!");
    }

    /**
     * Displays a toast if the post had an error
     * @param error Error message from Volley
     */
    @Override
    public void onError(VolleyError error) {
        Log.e("ree", "Uploaded multipart file FAILED. Response: " + error);
        view.makeToast("Bad save");
    }
}
