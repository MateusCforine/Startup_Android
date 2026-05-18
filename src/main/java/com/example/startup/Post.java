package com.example.startup;

public class Post {
    private final String author;
    private final String tag;
    private final String time;
    private final int likes;
    private final int comments;
    private final int imageResId;

    public Post(String author, String tag, String time, int likes, int comments, int imageResId) {
        this.author = author;
        this.tag = tag;
        this.time = time;
        this.likes = likes;
        this.comments = comments;
        this.imageResId = imageResId;
    }

    public String getAuthor() {
        return author;
    }

    public String getTag() {
        return tag;
    }

    public String getTime() {
        return time;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getImageResId() {
        return imageResId;
    }
}
