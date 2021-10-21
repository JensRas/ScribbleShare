package edu.iastate.scribbleshare.Objects;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Post {
    
@Id
private int ID;

private String username;

private Date datePosted;

private String path;

private int likeCount;

private int commentCount;



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
