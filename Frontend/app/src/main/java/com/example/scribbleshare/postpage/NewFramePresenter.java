package com.example.scribbleshare.postpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewFramePresenter implements IVolleyListener<JSONObject> {

    private EndpointCaller<JSONObject> model;
    private PostView view;
    private Context context;

    public NewFramePresenter(PostView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    public void createNewFrame(String username, String postId, int index){
        model.createFrameRequest(username, postId, index);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        Log.d("debug", "new frame presenter success!");
        view.refreshFrames();
    }

    @Override
    public void onError(VolleyError e) {
        //TODO handle error
        Log.e("debug", "new frame presenter ERROR! " + e.getMessage());
    }
}
