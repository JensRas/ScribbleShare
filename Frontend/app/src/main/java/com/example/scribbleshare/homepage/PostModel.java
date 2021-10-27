package com.example.scribbleshare.homepage;

public class PostModel {

    String id;
    String profileName;
    int likeCount;
    int commentCount;

    public PostModel() {}

    public PostModel(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getProfileName(){
        return profileName;
    }

    public void setProfileName(String profileName){
        this.profileName = profileName;
    }

    public int getLikeCount(){
        return likeCount;
    }

    public void setLikeCount(int likeCount){
        this.likeCount = likeCount;
    }

    public int getCommentCount(){
        return commentCount;
    }

    public void setCommentCount(int commentCount){
        this.commentCount = commentCount;
    }

}
