package com.example.sigor.Model;

public class User {

    private String email;
    private String nickname;
    private String imageurl;
    private String bio;

    public User(String email, String nickname, String imageurl, String bio) {
        this.email = email;
        this.nickname = nickname;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
