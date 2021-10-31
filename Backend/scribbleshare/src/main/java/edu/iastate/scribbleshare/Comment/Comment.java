package edu.iastate.scribbleshare.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.iastate.scribbleshare.Frame.Frame;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Frame frame;

    private String username;

    @JsonIgnore
    private String path;

    private int likeCount;

    public Comment(){} //need default constructor

    public Comment(String username){
        this.username = username;
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

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getLikeCount(){
        return likeCount;
    }

    public void setLikeCount(int likeCount){
        this.likeCount = likeCount;
    }

}
