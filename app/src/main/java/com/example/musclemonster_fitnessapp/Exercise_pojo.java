package com.example.musclemonster_fitnessapp;

public class Exercise_pojo {
    private String ExerciseName,ExerciseCat,ExerciseDesc;

    public Exercise_pojo(String exerciseName, String exerciseCat, String exerciseDesc) {
        ExerciseName = exerciseName;
        ExerciseCat = exerciseCat;
        ExerciseDesc = exerciseDesc;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public String getExerciseCat() {
        return ExerciseCat;
    }

    public void setExerciseCat(String exerciseCat) {
        ExerciseCat = exerciseCat;
    }

    public String getExerciseDesc() {
        return ExerciseDesc;
    }

    public void setExerciseDesc(String exerciseDesc) {
        ExerciseDesc = exerciseDesc;
    }
}
