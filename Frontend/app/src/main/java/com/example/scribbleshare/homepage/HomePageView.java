package com.example.scribbleshare.homepage;

import org.json.JSONArray;

/**
 * View for the homepage
 */
public interface HomePageView {
    /**
     * This method sets the posts for the scrollable homepage
     * @param array Array of posts data
     */
    public void setHomePagePosts(JSONArray array);
}
