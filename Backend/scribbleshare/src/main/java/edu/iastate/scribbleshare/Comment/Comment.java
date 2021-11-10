package edu.iastate.scribbleshare.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.User.User;
import io.swagger.annotations.ApiModelProperty;

/**
 * A comment by a single user for a frame. Comments are very similar to posts, as they need to store the image files under them.
 * A comment must keep track of a uniquely generated ID, the frame with which it exists under, the user who made the comment, its like count, and the path 
 * where the image is stored on the server's filesystem. 
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ApiModelProperty(value = "Frame for the comment", required=true, example = "")
    private Frame frame;

    @ManyToOne(cascade = CascadeType.ALL)
    @ApiModelProperty(value = "User that made the comment", required=true, example = "User")
    private User user;

    @JsonIgnore
    @ApiModelProperty(value = "Path where the comment is stored", required=true, example = "")
    private String path;

    @ApiModelProperty(value = "How many likes the comment has", required=true, example = "")
    private int likeCount;

    public Comment(){} //need default constructor

    public Comment(User user){
        this.user = user;
        this.likeCount = 0;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public int getID(){
        return ID;
    }

    public Frame getFrame(){
        return frame;
    }

    public void setFrame(Frame frame){
        this.frame = frame;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public int getLikeCount(){
        return likeCount;
    }

    public void setLikeCount(int likeCount){
        this.likeCount = likeCount;
    }

}
