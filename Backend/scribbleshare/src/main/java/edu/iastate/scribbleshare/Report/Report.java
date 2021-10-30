package edu.iastate.scribbleshare.Report;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    private String username;

    private String reason;

    private String comment;    
    
    public Report(String username, String reason, String comment){
        this.reason = reason;
        this.comment = comment;
        this.username = username;
    }
    
    public Report(){

    }

    public void setUser(String username){
        this.username = username;
    }

    public String getUser(){
        return this.username;
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