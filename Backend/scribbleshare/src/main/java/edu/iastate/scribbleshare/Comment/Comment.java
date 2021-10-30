package edu.iastate.scribbleshare.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.Post.Post;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Frame frame;
    
    public Comment(){} //need default constructor

    private String username;

    private String path;

    private int likeCount;

}
