package com.example.user.ictakapp;

public class Message {
    String to;
    NotifyData data;
    String priority;

    public Message(String to, NotifyData data, String priority) {
        this.to = to;
        this.data = data;
        this.priority = priority;
    }
}