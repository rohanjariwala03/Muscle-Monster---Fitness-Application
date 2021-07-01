package com.example.musclemonster_fitnessapp.POJOClasses;

public class Chat_pojo {
    String message;
    String senderId;


    public Chat_pojo() {
    }


    public Chat_pojo(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


}