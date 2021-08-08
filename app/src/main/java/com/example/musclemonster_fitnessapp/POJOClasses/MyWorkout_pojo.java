package com.example.musclemonster_fitnessapp.POJOClasses;

public class MyWorkout_pojo {
    String userId, WorkoutName,flag,imgUrl,tCat,tDesc,tSteps;

    public MyWorkout_pojo(String userId, String workoutName,String flag) {
        this.userId = userId;
        WorkoutName = workoutName;
        this.flag=flag;
    }
    public  MyWorkout_pojo(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkoutName() {
        return WorkoutName;
    }

    public void setWorkoutName(String workoutName) {
        WorkoutName = workoutName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String gettCat() {
        return tCat;
    }

    public void settCat(String tCat) {
        this.tCat = tCat;
    }

    public String gettDesc() {
        return tDesc;
    }

    public void settDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    public String gettSteps() {
        return tSteps;
    }

    public void settSteps(String tSteps) {
        this.tSteps = tSteps;
    }
}
