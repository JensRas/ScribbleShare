package com.example.scribbleshare.homepage;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * View for the homepage
 */
public interface HomePageView {
    /**
     * This method sets the posts for the scrollable homepage
     * @param array Array of posts data
     */
    void setHomePagePosts(JSONArray array);

    /**
     * Creates a toast
     * @param message message to be displayed in toast
     */
    void makeToast(String message);

    void setHomePageIsLiked(JSONObject o);
}
