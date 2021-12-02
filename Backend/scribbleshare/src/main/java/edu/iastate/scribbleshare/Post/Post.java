package edu.iastate.scribbleshare.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.User.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.OneToMany;

/**
 * A post created by a user. It stores the path to an image stored in the filesystem. 
 * This image is used as the starter for a set of frames, which a Post also stores as a list (one to many relationship)
 * Posts also must store the user who created the post, the date it was posted, the like count, the comment count, and have a unique generated ID.   
 */
@Transactional
@Entity
public class Post {
        
    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_username")
    @ApiModelProperty(value = "User who made the post", required=true, example = "User")
    private User user;

    @ApiModelProperty(value = "Date the post was made", required=true, example = "12:32")
    private Date datePosted;

    @JsonIgnore
    @ApiModelProperty(value = "Path the post is stored", required=true, example = "")
    private String path;

    @ApiModelProperty(value = "Number of likes the post has", required=true, example = "21")
    private int likeCount;

    @ApiModelProperty(value = "Number of comments the post has", required=true, example = "23")
    private int commentCount;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    @ApiModelProperty(value = "Frames that belong to the post", required=true, example = "")
    private List<Frame> frames;

    @ManyToMany(mappedBy="liked_posts")
	@JsonIgnore
	private Set<User> liked_users = new HashSet<User>();

    public Post(){
    }

    public Post(User user) {
        this.user = user;
        this.datePosted = new Date();
        this.likeCount = 0;
        this.commentCount = 0;
        frames = new ArrayList<>();
    }

    /**
     * Get the last frame in a post. Is useful for creating new frames in a post.
     * @return the index of the last frame a post has
     */
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

    @JsonIgnore
    public Set<User> getLikedUsers(){
        return this.liked_users;
    }

    public void setLikedUsers(Set<User> liked_users){
        this.liked_users = liked_users;
    }

    public int getID(){
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

}  
