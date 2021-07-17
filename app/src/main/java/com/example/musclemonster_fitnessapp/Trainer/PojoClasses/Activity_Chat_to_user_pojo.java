package com.example.musclemonster_fitnessapp.Trainer.PojoClasses;

public class Activity_Chat_to_user_pojo {

    String message;
    String senderId;

    public Activity_Chat_to_user_pojo() {
    }

    public Activity_Chat_to_user_pojo(String message, String senderId) {
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
