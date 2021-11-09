package com.example.scribbleshare.postpage;

import java.util.ArrayList;

/**
 * Holds the data of a frame of a post
 */
public class FrameModel {
    int frameId;
    ArrayList<CommentModel> comments;

    /**
     * Constructor to initialize necessary data of a frame of a post
     * @param frameNumber Number of this frame in the order of frames
     * @param comments List of comments on the frame
     */
    public FrameModel(int frameNumber, ArrayList<CommentModel> comments){
        this.frameId = frameNumber;
        this.comments = comments;
    }

    /**
     * This method returns the frame id
     * @return Frame id
     */
    public int getFrameId(){
        return frameId;
    }

    /**
     * This method sets the frame id
     * @param frameId Frame id
     */
    public void setFrameId(int frameId){
        this.frameId = frameId;
    }

    /**
     * This method gets the list of comments on the frame
     * @return
     */
    public ArrayList<CommentModel> getComments(){
        return comments;
    }

    /**
     * This method sets the list of comments on the frame
     * @param comments
     */
    public void setComments(ArrayList<CommentModel> comments){
        this.comments = comments;
    }

    /**
     * This method adds a comment to the list of comments on the frame
     * @param comment
     */
    public void addComment(CommentModel comment){
        this.comments.add(comment);
    }
}
