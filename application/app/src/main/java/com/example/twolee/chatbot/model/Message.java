package com.example.twolee.chatbot.model;


import java.io.Serializable;

public class Message implements Serializable {
    String id, message;

    // 생성자
    public Message() {
    }

    // 메시지 앱
    public Message(String id, String message, String createdAt) {
        this.id = id;
        this.message = message;
    }

    // 아이디 얻기
    public String getId() {
        return id;
    }

    // 아이디 설정
    public void setId(String id) {
        this.id = id;
    }

    // 메시지 얻기
    public String getMessage() {
        return message;
    }

    // 메시지 설정
    public void setMessage(String message) {
        this.message = message;
    }
}

