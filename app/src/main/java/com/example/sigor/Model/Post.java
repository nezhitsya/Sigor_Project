package com.example.sigor.Model;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    private String R;
    private String G;
    private String B;
    private String Ratio;

    public Post(String postid, String postimage, String description, String publisher, String R, String G, String B, String Ratio) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
        this.R = R;
        this.G = G;
        this.B = B;
        this.Ratio = Ratio;
    }

    public Post() {

    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }

    public String getG() {
        return G;
    }

    public void setG(String g) {
        G = g;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getRatio() {
        return Ratio;
    }

    public void setRatio(String ratio) {
        Ratio = ratio;
    }
}
