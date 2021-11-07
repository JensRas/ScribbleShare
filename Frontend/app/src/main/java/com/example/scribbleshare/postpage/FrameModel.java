package com.example.scribbleshare.postpage;

import java.util.ArrayList;

public class FrameModel {
    int frameId;
    ArrayList<CommentModel> comments;

    public FrameModel(int frameNumber, ArrayList<CommentModel> comments){
        this.frameId = frameNumber;
        this.comments = comments;
    }

    public int getFrameId(){
        return frameId;
    }

    public void setFrameId(int frameId){
        this.frameId = frameId;
    }

    public ArrayList<CommentModel> getComments(){
        return comments;
    }

    public void setComments(ArrayList<CommentModel> comments){
        this.comments = comments;
    }

    public void addComment(CommentModel comment){
        this.comments.add(comment);
    }
}
