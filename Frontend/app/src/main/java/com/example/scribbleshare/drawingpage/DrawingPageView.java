package com.example.scribbleshare.drawingpage;

import org.json.JSONObject;

public interface DrawingPageView {
    public void setDrawingImage(byte[] data);
    public void onCreateCommentSuccess(JSONObject o);
    public void onCreatePostSuccess(JSONObject o);
    public void makeToast(String message);
    void switchView(Class c);
}
