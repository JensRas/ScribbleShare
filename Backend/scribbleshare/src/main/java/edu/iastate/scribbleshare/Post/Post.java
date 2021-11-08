package edu.iastate.scribbleshare.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.User.User;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Post {
        
    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private Date datePosted;

    @JsonIgnore
    private String path;

    private int likeCount;

    private int commentCount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Frame> frames;

    public Post(){
    }

    public Post(User user) {
        this.user = user;
        this.datePosted = new Date();
        this.likeCount = 0;
        this.commentCount = 0;
        frames = new ArrayList<>();
    }

    public int getID()
    {
        return this.ID;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
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

    public List<Frame> getFrames(){
        return frames;
    }

    public void setFrames(List<Frame> frames){
        this.frames = frames;
    }

    public void addFrame(Frame frame){
        frames.add(frame);
    }

    //TODO add test? move to a service??
    //TODO bad implementation... but it works lol
    public int getLastFrameIndex(){
        int r = -1;
        for(Frame f : frames){
            int index = f.getFrameIndex();
            if(index > r){
                r = index;
            }
        }
        return r;
    }
}  
