package edu.iastate.scribbleshare;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity //make a table with this class
public class User {

    @Id
    private String username;

    private String email;

    private String password; //hashed

    private String permissionLevel;

    private boolean isMuted;

    private boolean isBanned;

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
}
