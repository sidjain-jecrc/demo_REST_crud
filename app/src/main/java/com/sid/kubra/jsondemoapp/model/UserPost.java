package com.sid.kubra.jsondemoapp.model;

/**
 * Created by Siddharth on 6/24/2017.
 */

public class UserPost {

    private Long postId;
    private String title;
    private String body;

    public UserPost(Long postId, String title, String body) {
        this.postId = postId;
        this.title = title;
        this.body = body;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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
