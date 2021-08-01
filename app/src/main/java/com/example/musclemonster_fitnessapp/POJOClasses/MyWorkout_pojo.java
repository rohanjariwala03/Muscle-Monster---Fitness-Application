package com.example.musclemonster_fitnessapp.POJOClasses;

public class MyWorkout_pojo {
    String userId, WorkoutName,flag;

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
}
