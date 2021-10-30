package com.example.scribbleshare.postpage;

public class CommentModel {
    int id;
    String profileName;
    int likeCount;

    public CommentModel(int id, String profileName, int likeCount){
        this.id = id;
        this.profileName = profileName;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
