package com.example.scribbleshare.profilepage;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ProfilePageView {
    void setUserPosts(JSONArray array);
    void setUserFollowing(JSONObject object);
    void setUserBanned(JSONObject object);
    void setUserUnbanned(JSONObject object);
}
