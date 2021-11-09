package com.example.scribbleshare.postpage;

import org.json.JSONArray;

/**
 * View for the post page
 */
public interface PostView {
    /**
     * Sets the frames of a post
     * @param array Array of frames for the post
     */
    void setFrames(JSONArray array);

    /**
     * Refreshes the frames of the post for debugging
     */
    void refreshFrames();
}
