package edu.iastate.scribbleshare.Frame;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.iastate.scribbleshare.Comment.Comment;
import edu.iastate.scribbleshare.Post.Post;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A single frame of a post which holds user comments for that frame. 
 * A frame must have a unique, generated ID, a one to many list of Comments posted under it, a backwards pointing, 
 * one to one relationship with the post it is under (for easier data manipulation), and an index value. The index must represent the ordering of the frame for a given post
 */
@Entity
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Post post;

    private int frameIndex;

    public Frame(Post post, int index){
        this.post = post;
        this.frameIndex = index;
    }

    public Frame(){}

    public int getID(){
        return ID;
    }

    public List<Comment> getComments(){
        return comments;
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    public Post getPost(){
        return post;
    }

    public void setPost(Post post){
        this.post = post;
    }

    public int getFrameIndex(){
        return frameIndex;
    }

    public void setFrameIndex(int index){
        this.frameIndex = index;
    }
}
