package edu.iastate.scribbleshare.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.User.User;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Frame frame;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @JsonIgnore
    private String path;

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
