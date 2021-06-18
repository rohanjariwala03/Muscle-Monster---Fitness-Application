package com.example.musclemonster_fitnessapp;

public class Exercise_pojo {
    public String Exercise;

    public Exercise_pojo(){

    }
    public Exercise_pojo(String Exercise){
        this.Exercise=Exercise;
    }

    public String getExercise(){
        return  Exercise;
    }

    public void setExercise(String Exercise){
        this.Exercise=Exercise;
    }
}
