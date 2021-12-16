package com.example.simplemovieapp;

public class Slide{
    private String link;
    private String image;
    private String title;

    public Slide(String link, String image, String title) {
        this.link = link;
        this.image = image;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

