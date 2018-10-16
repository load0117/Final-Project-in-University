package com.example.twolee.chatbot.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {


    public String username;
    public String password;
    public String state;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.state = "";
    }

}
