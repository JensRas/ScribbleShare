package com.example.scribbleshare.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.scribbleshare.model.EndpointCaller;
import com.example.scribbleshare.model.IVolleyListener;
import com.example.scribbleshare.view.DrawingPageView;

public class DrawingPagePresenter implements IVolleyListener {
    private EndpointCaller model;
    private DrawingPageView view;
    private Context context;

    public DrawingPagePresenter(DrawingPageView view, Context c){
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller(c, this);
    }

    public void uploadBitmap(String username, Bitmap bitmap){
        model.uploadBitmap(username, bitmap);
    }

    @Override
    public void onSuccess(String response) {
        Log.d("ree", "Uploaded multipart file. Response: " + response);
    }

    @Override
    public void onError(String response) {
        Log.e("ree", "Uploaded multipart file FAILED. Response: " + response);
    }
}
