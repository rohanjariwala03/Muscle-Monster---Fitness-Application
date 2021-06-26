package com.example.musclemonster_fitnessapp.LoginSignUp;

public class Model {
    String firstName,lastName,email,phoneNumber;
    int Sflag;


    public Model(String firstName, String lastName, String email, String phoneNumber, int sflag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.Sflag=sflag;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSflag() {
        return Sflag;
    }

    public void setSflag(int sflag) {
        Sflag = sflag;
    }

}
