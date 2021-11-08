package edu.iastate.scribbleshare.Report;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import edu.iastate.scribbleshare.User.User;

@Entity
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @OneToOne
    private User username;

    @OneToOne
    private User userWhoReported;

    private String reason;

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