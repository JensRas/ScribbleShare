package com.example.scribbleshare.profilepage;

import org.json.JSONArray;

public interface ProfilePageView {
    //add methods here that the profile page view needs to update things in the UI
    //then override them in the ProfilePage class and actually implement them
    public void setUserPosts(JSONArray array);
}
