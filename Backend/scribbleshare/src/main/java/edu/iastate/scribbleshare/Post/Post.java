package edu.iastate.scribbleshare.Post;

import java.util.Date;  

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    private String username;

    private Date datePosted;

    @JsonIgnore
    private String path;

    private int likeCount;

    private int commentCount;

    public Post(){
    }

    public Post(String username) {
        this.username = username;
        this.datePosted = new Date();
        this.likeCount = 0;
        this.commentCount = 0;
    }

    public int getID()
    {
        return this.ID;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public Date getDatePosted(){
        return this.datePosted;
    }

    public void setDatePosted(Date date){
        this.datePosted = date;
    }

    public String getPath(){
        return this.path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public int getLikeCount(){
        return this.likeCount;
    }

    public void setLikeCount(int likes){
        this.likeCount = likes;
    }

    public int getCommentCount(){
        return this.commentCount;
    }

    public void setCommentCount(int comment){
        this.commentCount = comment;
    }
}
