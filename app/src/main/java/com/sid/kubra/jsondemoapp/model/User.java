package com.sid.kubra.jsondemoapp.model;

/**
 * Created by Siddharth on 6/24/2017.
 */

public class User {

    private Long id;
    private String username;
    private UserAddress address;

    public User(Long id, String username, UserAddress address) {
        this.id = id;
        this.username = username;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }
}
