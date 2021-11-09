package com.example.scribbleshare;

/**
 * Handles the accounts of scribblers and relevant information
 */
public class User {
    private String username;
    private String permissionLevel;
    private boolean isMuted;
    private boolean isBanned;

    /**
     * Default constructor
     */
    public User(){}

    /**
     * Constructor for necessary information for a user
     * @param username Name of user
     * @param permissionLevel Permission level of user
     * @param isMuted Muted status of user
     * @param isBanned Banned status of user
     */
    public User(String username, String permissionLevel, boolean isMuted, boolean isBanned){
        this.username = username;
        this.permissionLevel = permissionLevel;
        this.isMuted = isMuted;
        this.isBanned = isBanned;
    }

    /**
     * This method returns the username of the user
     * @return Username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets the username of the user
     * @param username Username for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method returns the permission level of the user
     * @return Permission level of the user
     */
    public String getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * This method sets the permission level of the user
     * @param permissionLevel Permission level for the user
     */
    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    /**
     * This method returns the muted status for the user
     * @return User's muted status
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * This method sets the muted status for the user
     * @param muted User's muted status
     */
    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    /**
     * This method returns the banned status for the user
     * @return User's banned status
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * This method sets the banned status for the user
     * @param banned User's banned status
     */
    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    /**
     * This method displays the information of the user
     * @return
     */
    @Override
    public String toString(){
        return username + " permission: " + permissionLevel + " isMuted: " + isMuted + " isBanned: " + isBanned;
    }
}
