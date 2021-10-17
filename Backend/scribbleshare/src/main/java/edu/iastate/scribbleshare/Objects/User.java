package edu.iastate.scribbleshare.Objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.exceptions.BadHashException;
import edu.iastate.scribbleshare.helpers.Security;

@Entity //make a table with this class
public class User {

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @Id
    private String username;

    private String email;

    private String password; //hashed

    private String permissionLevel;

    private boolean isMuted;

    private boolean isBanned;

    public User(String username, String password){
        this.username = username;
        String hash = Security.generateHash(password);
        if(hash == null){
            throw new BadHashException();
        }
        this.password = hash;
        this.following = new ArrayList<User>();
    }

    //need a default constructor or springboot will throw a tantrum
    public User(){
        this.following = new ArrayList<User>();
    }

    //users the current user is following
    @OneToMany
    private List<User> following;

    //users that are following this user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User follow;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name){
        this.username = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
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

    public void setFollowing(List<User> following){
        this.following = following;
    }

    public List<User> getFollowing(){
        return this.following;
    }

    public void addFollower(User user){
        logger.info("following before: " + following.toString());
        this.following.add(user);
        logger.info("following after: " + following.toString());
    }
}
