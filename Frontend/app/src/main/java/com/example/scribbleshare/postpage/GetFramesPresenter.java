package com.example.scribbleshare.postpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;

/**
 *
 */
public class GetFramesPresenter implements IVolleyListener<JSONArray> {
    private EndpointCaller<JSONArray> model;
    private PostView view;
    private Context context;

    /**
     *
     * @param view
     * @param c
     */
    public GetFramesPresenter(PostView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<JSONArray>(c, this);
    }

    /**
     *
     * @param postId
     */
    public void getFrames(String postId){
        model.getPostFrames(postId);
    }

    /**
     *
     * @param jsonArray
     */
    @Override
    public void onSuccess(JSONArray jsonArray) {
        view.setFrames(jsonArray);
    }

    /**
     * TODO implement
     */
    @Override
    public void onError(VolleyError e) {
        //TODO handle error
        Log.e("debug", "get frame presenter ERROR! " + e.getMessage());
    }
}
