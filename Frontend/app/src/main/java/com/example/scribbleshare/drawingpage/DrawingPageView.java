package com.example.scribbleshare.drawingpage;

import org.json.JSONObject;

/**
 *
 */
public interface DrawingPageView {
    /**
     *
     * @param data
     */
    public void setDrawingImage(byte[] data);

    /**
     *
     * @param o
     */
    public void onCreateCommentSuccess(JSONObject o);

    /**
     *
     * @param o
     */
    public void onCreatePostSuccess(JSONObject o);

    /**
     *
     * @param message
     */
    public void makeToast(String message);

    /**
     *
     * @param c
     */
    void switchView(Class c);
}
