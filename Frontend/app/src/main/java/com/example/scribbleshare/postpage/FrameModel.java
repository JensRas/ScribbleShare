package com.example.scribbleshare.postpage;

import java.util.ArrayList;

/**
 *
 */
public class FrameModel {
    int frameId;
    ArrayList<CommentModel> comments;

    /**
     *
     * @param frameNumber
     * @param comments
     */
    public FrameModel(int frameNumber, ArrayList<CommentModel> comments){
        this.frameId = frameNumber;
        this.comments = comments;
    }

    /**
     *
     * @return
     */
    public int getFrameId(){
        return frameId;
    }

    /**
     *
     * @param frameId
     */
    public void setFrameId(int frameId){
        this.frameId = frameId;
    }

    /**
     *
     * @return
     */
    public ArrayList<CommentModel> getComments(){
        return comments;
    }

    /**
     *
     * @param comments
     */
    public void setComments(ArrayList<CommentModel> comments){
        this.comments = comments;
    }

    /**
     *
     * @param comment
     */
    public void addComment(CommentModel comment){
        this.comments.add(comment);
    }
}
