package com.example.scribbleshare.searchpage;

public class SearchModel {
    String username;
    int followerCount;

    public SearchModel(String username, int followerCount) {
        this.username = username;
        this.followerCount = followerCount;
    }

    public SearchModel() {}

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }
}
