package com.example.musclemonster_fitnessapp.POJOClasses;

public class UsersPojo {


    String email;
    String firstName;
    String imageUrl;
    String lastName;
    String phoneNumber;
    String sflag;


    public UsersPojo(){

    }

    public UsersPojo(String email, String firstName, String imageUrl, String lastName, String phoneNumber, String sflag) {
        this.email = email;
        this.firstName = firstName;
        this.imageUrl = imageUrl;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.sflag = sflag;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSflag() {
        return sflag;
    }

    public void setSflag(String sflag) {
        this.sflag = sflag;
    }


}
