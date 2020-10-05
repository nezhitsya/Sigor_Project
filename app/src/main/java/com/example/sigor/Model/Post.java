package com.example.sigor.Model;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String publisher;
//    private int R;
//    private int G;
//    private int B;
//    private int Ratio;

    public Post(String postid, String postimage, String description, String publisher) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
//        this.R = R;
//        this.G = G;
//        this.B = B;
//        this.Ratio = Ratio;
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

//    public int getR() {
//        return R;
//    }

//    public void setR(int r) {
//        R = r;
//    }
//
//    public int getG() {
//        return G;
//    }
//
//    public void setG(int g) {
//        G = g;
//    }
//
//    public int getB() {
//        return B;
//    }
//
//    public void setB(int b) {
//        B = b;
//    }
//
//    public int getRatio() {
//        return Ratio;
//    }
//
//    public void setRatio(int ratio) {
//        Ratio = ratio;
//    }
}
