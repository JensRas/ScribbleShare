package com.example.scribbleshare.homepage;

/**
 *
 */
public class PostModel {
    String id;
    String profileName;
    int likeCount;
    int commentCount;

    /**
     *
     */
    public PostModel() {}

    /**
     *
     * @param id
     * @param profileName
     * @param likeCount
     * @param commentCount
     */
    public PostModel(String id, String profileName, int likeCount, int commentCount){
        this.id = id;
        this.profileName = profileName;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getProfileName(){
        return profileName;
    }

    /**
     *
     * @param profileName
     */
    public void setProfileName(String profileName){
        this.profileName = profileName;
    }

    /**
     *
     * @return
     */
    public int getLikeCount(){
        return likeCount;
    }

    /**
     *
     * @param likeCount
     */
    public void setLikeCount(int likeCount){
        this.likeCount = likeCount;
    }

    /**
     *
     * @return
     */
    public int getCommentCount(){
        return commentCount;
    }

    /**
     *
     * @param commentCount
     */
    public void setCommentCount(int commentCount){
        this.commentCount = commentCount;
    }

}
