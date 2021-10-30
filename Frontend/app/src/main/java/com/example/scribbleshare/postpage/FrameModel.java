package com.example.scribbleshare.postpage;

import android.media.Image;

import java.util.ArrayList;

public class FrameModel {
    int frameNumber;
    ArrayList<CommentModel> comments;

    public FrameModel(int frameNumber, ArrayList<CommentModel> comments){
        this.frameNumber = frameNumber;
        this.comments = comments;
    }

    public int getFrameNumber(){
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber){
        this.frameNumber = frameNumber;
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
