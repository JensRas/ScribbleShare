package com.example.scribbleshare.postpage;

/**
 * Holds data for a comment on a frame
 */
public class CommentModel {
    int id;
    String profileName;
    int likeCount;
    boolean isLiked;

    /**
     * Constructor to initialize data for a comment
     * @param id Id of a comment
     * @param profileName Name of user that is commenting
     * @param likeCount Like count for the comment
     */
    public CommentModel(int id, String profileName, int likeCount, boolean isLiked){
        this.id = id;
        this.profileName = profileName;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }

    /**
     * This method returns the id of the comment
     * @return Id of the comment
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the id of the comment
     * @param id Id of the comment
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the profile name of the user commenting
     * @return Profile name of the user commenting
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * This method sets the profile name of the user commenting
     * @param profileName Profile name of the user commenting
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * This method returns the like count of the comment
     * @return Like count of the comment
     */
    public int getLikeCount() {
        return likeCount;
    }

    /**
     * This method sets the like count of the comment
     * @param likeCount Like count of the comment
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean getIsLiked(){
        return isLiked;
    }

    public void setIsLiked(boolean isLiked){
        this.isLiked = isLiked;
    }
}
