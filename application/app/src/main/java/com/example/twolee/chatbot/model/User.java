package com.example.twolee.chatbot.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    /*
        V 1.2

        비밀번호 변수 제거

        
     */

    private String username;
    private String state;

    public User(String username) {
        setUsername(username);
        setState("");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
