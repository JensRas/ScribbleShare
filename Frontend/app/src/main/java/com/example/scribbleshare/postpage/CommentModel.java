package com.example.scribbleshare.postpage;

/**
 *
 */
public class CommentModel {
    int id;
    String profileName;
    int likeCount;

    /**
     *
     * @param id
     * @param profileName
     * @param likeCount
     */
    public CommentModel(int id, String profileName, int likeCount){
        this.id = id;
        this.profileName = profileName;
        this.likeCount = likeCount;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     *
     * @param profileName
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     *
     * @return
     */
    public int getLikeCount() {
        return likeCount;
    }

    /**
     *
     * @param likeCount
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
