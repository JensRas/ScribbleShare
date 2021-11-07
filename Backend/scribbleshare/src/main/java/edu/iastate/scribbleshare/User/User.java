package edu.iastate.scribbleshare.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Report.Report;
import edu.iastate.scribbleshare.exceptions.BadHashException;
import edu.iastate.scribbleshare.helpers.Security;

@Entity //make a table with this class
public class User {

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @Id
    private String username;

    @JsonIgnore
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
    private List<Report> reports;

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
