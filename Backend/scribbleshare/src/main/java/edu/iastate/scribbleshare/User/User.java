package edu.iastate.scribbleshare.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Comment.Comment;
import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Report.Report;
import edu.iastate.scribbleshare.exceptions.BadHashException;
import edu.iastate.scribbleshare.helpers.Security;
import io.swagger.annotations.ApiModelProperty;


/**
 * A user is an account required to create posts/comments/etc and is created for each user.
 * Users will be used to keep track of several things we use throughout the app. 
 * There will be two different kinds of users, Mod which can ban and mute users, and just
 * normal users who will use the app as normal. User is storing Username, which is the Id, 
 * password which is hashed, persmissionLevel, isMuted and isBanned which mods can change.
 * 
 * 
 * Followers are also implemented here as a many to many relation both ways. Following is who the 
 * user follows, and followers is who follows the user.
 */

@Entity //make a table with this class
public class User {

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @Id
    @ApiModelProperty(value = "Username of user",name="username", required=true,example = "Corbeno")
    private String username;

    @JsonIgnore
    @ApiModelProperty(value = "Password of the user, is hashed", required=true, example = "password123")
    private String password; //hashed

    @ApiModelProperty(value = "Permission level of user, either Mod or user", required=true, example = "Mod")
    private String permissionLevel;

    @ApiModelProperty(value = "If user is muted, they wont be able to post", required=true, example = "False")
    private boolean isMuted;

    @ApiModelProperty(value = "If user is banned, they wont be able to log in to their account", required=true, example = "False")
    private boolean isBanned;

    public User(String username, String password){
        this.username = username;
        String hash = Security.generateHash(password);
        if(hash == null){
            throw new BadHashException();
        }
        this.password = hash;
    }

    //need a default constructor or springboot will throw a tantrum
    public User(){
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "following",
        joinColumns = @JoinColumn(name = "follower_username", referencedColumnName = "username"),
        inverseJoinColumns = @JoinColumn(name = "followee_username", referencedColumnName = "username"))
    private Set<User> following;

    @ManyToMany(mappedBy="following")
	@JsonIgnore
	private Set<User> followers = new HashSet<User>();

    @OneToMany
    @JsonIgnore
    private List<Report> reports;

    @OneToMany
    @JsonIgnore
    private List<Post> posts;

    @OneToMany
    @JsonIgnore
    private List<Comment> comments;

    public List<Post> getPosts(){
        return this.posts;
    }

    public void setPosts(List<Post> posts){
        this.posts = posts;
    }

    public List<Comment> getComments(){
        return this.comments;
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    public List<Report> getReports(){
        return this.reports;
    }

    public void setReports(List<Report> reports){
        this.reports = reports;
    }

    public void addReport(Report report){
        this.reports.add(report);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String name){
        this.username = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPermissionLevel(){
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel){
        this.permissionLevel = permissionLevel;
    }

    public boolean getIsMuted(){
        return isMuted;
    }

    public void setIsMuted(boolean isMuted){
        this.isMuted = isMuted;
    }

    public boolean getIsBanned(){
        return isBanned;
    }

    public void setIsBanned(boolean isBanned){
        this.isBanned = isBanned;
    }

    public void setFollowing(Set<User> following){
        this.following = following;
    }

    public Set<User> getFollowing(){
        return this.following;
    }

    public void setFollowers(Set<User> followers){
        this.followers = followers;
    }

    public Set<User> getFollowers(){
        return followers;
    }

}
