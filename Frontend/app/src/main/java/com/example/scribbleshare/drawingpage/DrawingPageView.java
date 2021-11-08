package com.example.scribbleshare.drawingpage;

import org.json.JSONObject;

/**
 * View for drawing page
 */
public interface DrawingPageView {
    /**
     * Sets bitmap for drawing
     * @param data bitmap data from the drawing
     */
    public void setDrawingImage(byte[] data);

    /**
     * Creates comment if successful
     * @param o returned post-JSON
     */
    public void onCreateCommentSuccess(JSONObject o);

    /**
     * TODO implement
     */
    public void onCreatePostSuccess(JSONObject o);

    /**
     * Creates a toast with an inputted message
     * @param message Toast message to be shown
     */
    public void makeToast(String message);

    /**
     * Switches View
     * @param c class of new view
     */
    void switchView(Class c);
}
