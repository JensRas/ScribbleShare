package edu.iastate.scribbleshare.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Follower {

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private int id;

private String username;

// @OneToMany(targetEntity = User.class)
// private User user;

private String following;


public void setUsername(String username){
    this.username = username;
}

public void setFollowing(String following){
    this.following = following;
}

public String getUsername(){
    return this.username;
}

public String getFollowing(){
    return this.following;
}

}
