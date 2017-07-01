package com.sid.kubra.jsondemoapp.model;

/**
 * Created by Siddharth on 6/24/2017.
 */

public class UserPost {

    private long id;
    private String title;
    private String body;
    private long userId;

    public UserPost() {

    }

    public UserPost(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
