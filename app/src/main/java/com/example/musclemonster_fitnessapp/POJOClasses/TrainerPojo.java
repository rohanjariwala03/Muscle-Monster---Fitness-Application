package com.example.musclemonster_fitnessapp.POJOClasses;

public class TrainerPojo {

    String TrainerID;
    String Firstname;
    String LastName;
    String Email;
    String Password;
    String Experience;
    String Weight;
    String Height;
    String Age;
    String Contact;
    String ImgUri;

    public TrainerPojo(){

    }

    public TrainerPojo(String trainerID, String firstname, String lastName, String email, String password, String contact,String experience, String weight, String height, String age, String imgUri) {
        TrainerID = trainerID;
        Firstname = firstname;
        LastName = lastName;
        Email = email;
        Password = password;
        Experience = experience;
        Weight = weight;
        Height = height;
        Age = age;
        Contact = contact;
        ImgUri = imgUri;
    }

    public String getTrainerID() {
        return TrainerID;
    }

    public void setTrainerID(String trainerID) {
        TrainerID = trainerID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getImgUri() {
        return ImgUri;
    }

    public void setImgUri(String imgUri) {
        ImgUri = imgUri;
    }


}
