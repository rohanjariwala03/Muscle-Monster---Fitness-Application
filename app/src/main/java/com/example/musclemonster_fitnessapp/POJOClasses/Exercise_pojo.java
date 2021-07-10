package com.example.musclemonster_fitnessapp.POJOClasses;

public class Exercise_pojo {
    private String ExerciseName,ExerciseCat,ExerciseDesc,ImageUri,FKey,UserKey,steps;;

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Exercise_pojo(String exerciseName, String exerciseCat, String exerciseDesc, String imageUri, String userKey, String steps) {
        ExerciseName = exerciseName;
        ExerciseCat = exerciseCat;
        ExerciseDesc = exerciseDesc;
        ImageUri = imageUri;
        UserKey = userKey;
        this.steps=steps;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getFKey() {
        return FKey;
    }

    public void setFKey(String FKey) {
        this.FKey = FKey;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getImageUri() {
        return ImageUri;
    }
    public Exercise_pojo() {

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
