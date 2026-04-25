package com.example.android3081;

public class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getName(){
        return this.username;
    }
    public String getEmail(){
        return this.email;
    }

    public void clear() {
        this.username = "";
        this.email = "";
    }
}
