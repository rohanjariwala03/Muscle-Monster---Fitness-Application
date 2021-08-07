package com.example.musclemonster_fitnessapp.POJOClasses;

public class RatingPojo {

    public RatingPojo(String email, String name, String msg, String date, String traineremail, String firstname, String rating) {
        this.email = email;
        Name = name;
        this.msg = msg;
        this.date = date;
        this.traineremail = traineremail;
        this.firstname = firstname;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getTraineremail() {
        return traineremail;
    }

    public void setTraineremail(String traineremail) {
        this.traineremail = traineremail;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String email;
    String Name;
    String msg;
    String date;
    String traineremail;
    String firstname;
    String rating;
    public RatingPojo(){

    }
}
