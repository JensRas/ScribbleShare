package com.example.scribbleshare.model;

//not sure if this is needed yet...
public class User {

    private String username;

    private String permissionLevel;

    private boolean isMuted;

    private boolean isBanned;

    public User(){}

    public User(String username, String permissionLevel, boolean isMuted, boolean isBanned){
        this.username = username;
        this.permissionLevel = permissionLevel;
        this.isMuted = isMuted;
        this.isBanned = isBanned;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    @Override
    public String toString(){
        return username + " permission: " + permissionLevel + " isMuted: " + isMuted + " isBanned: " + isBanned;
    }
}
