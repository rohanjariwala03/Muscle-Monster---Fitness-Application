package com.example.musclemonster_fitnessapp.POJOClasses;

public class FeedbackPojo {

    public FeedbackPojo(String email, String name, String msg, String date) {
        this.email = email;
        this.name = name;
        this.msg = msg;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String email;
    String name;
    String msg;
    String date;
    public FeedbackPojo(){

    }
}
