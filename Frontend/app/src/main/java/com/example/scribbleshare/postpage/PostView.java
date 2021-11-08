package com.example.scribbleshare.postpage;

import org.json.JSONArray;

/**
 *
 */
public interface PostView {
    /**
     *
     * @param array
     */
    void setFrames(JSONArray array);

    /**
     *
     */
    void refreshFrames();
}
