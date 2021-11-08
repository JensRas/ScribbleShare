package edu.iastate.scribbleshare.Report;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import edu.iastate.scribbleshare.User.User;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @OneToOne
    @ApiModelProperty(value = "Username of the person who is being reported", required=true, example = "Username1")
    private User username;

    @OneToOne
    @ApiModelProperty(value = "Username of the person who reported", required=true, example = "Username2")
    private User userWhoReported;

    @ApiModelProperty(value = "Reason User reported", required=true, example = "Is was very bad")
    private String reason;

    @ApiModelProperty(value = "Any extra comments", required=true, example = "I dont like that it was bad")
    private String comment;    
    
    public Report(User username, User userWhoReported, String reason, String comment){
        this.reason = reason;
        this.comment = comment;
        this.username = username;
    }
    
    //Required by springboot otherwise throws errors
    public Report(){

    }

    public void setUser(User username){
        this.username = username;
    }

    public User getUser(){
        return this.username;
    }

    public void setUserWhoReported(User username){
        this.userWhoReported = username;
    }

    public User getUserWhoReported(){
        return this.userWhoReported;
    }
    
    public void setReason(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return this.reason;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getComment(){
        return this.comment;
    }
}