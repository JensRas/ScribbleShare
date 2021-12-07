package com.example.scribbleshare.profilepage;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ProfilePageView {
    void setUserPosts(JSONArray array);

    void setUserFollowing(JSONObject object);

     void setUserStats(JSONObject object);
}
