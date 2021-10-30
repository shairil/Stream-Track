package com.example.myapplication1.Modals;

public class User {
    private String name, password, email, uid;
    private boolean isWatching;

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatching(boolean watching) {
        isWatching = watching;
    }

    public User(String name, String password, String email, String uid) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.uid = uid;
    }

    public User(String password, String email, String uid) {
        this.password = password;
        this.email = email;
        this.uid = uid;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
