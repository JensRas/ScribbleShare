package com.example.scribbleshare.postpage;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * View for the post page
 */
public interface PostView {
    /**
     * Sets the frames of a post
     * @param array Array of frames for the post
     */
    void setFrames(JSONArray array, boolean scrollToBottom);

    /**
     * Refreshes the frames of the post for debugging
     */
    void refreshFrames(boolean shouldScrollToBottom);

    void scrollViewToBottom();

    void setCommentIsLiked(JSONObject o);
}
