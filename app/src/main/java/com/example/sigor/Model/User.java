package com.example.sigor.Model;

public class User {

    private String username;
    private String id;
    private String imageurl;
    private String bio;
    private String status;

    public User(String username, String id, String imageurl, String bio, String status) {
        this.username = username;
        this.id = id;
        this.imageurl = imageurl;
        this.bio = bio;
        this.status = status;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
