package com.example.musclemonster_fitnessapp.POJOClasses;

public class TrainerList_Chat_user_pojo {

    String UserID;
    String TrainerID;
    String TrainerName;
    String TimgUrl;

    public String getTimgUrl() {
        return TimgUrl;
    }

    public void setTimgUrl(String timgUrl) {
        TimgUrl = timgUrl;
    }

    public String getFKey() {
        return FKey;
    }

    public void setFKey(String FKey) {
        this.FKey = FKey;
    }

    public String getTEmail() {
        return TEmail;
    }

    public void setTEmail(String TEmail) {
        this.TEmail = TEmail;
    }

    String FKey;
    String TEmail;

    public TrainerList_Chat_user_pojo(String trainerID, String trainerName, String timgUrl, String FKey, String TEmail) {
        TrainerID = trainerID;
        TrainerName = trainerName;
        TimgUrl = timgUrl;
        this.FKey = FKey;
        this.TEmail = TEmail;
    }

    public TrainerList_Chat_user_pojo(){

    }

    public TrainerList_Chat_user_pojo(String trainerName) {
        TrainerName = trainerName;
    }

    /*public TrainerList_Chat_user_pojo(String userID, String trainerID, String trainerName) {
        UserID = userID;
        TrainerID = trainerID;
        TrainerName = trainerName;
    }*/

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getTrainerID() {
        return TrainerID;
    }

    public void setTrainerID(String trainerID) {
        TrainerID = trainerID;
    }

    public String getTrainerName() {
        return TrainerName;
    }

    public void setTrainerName(String trainerName) {
        TrainerName = trainerName;
    }
}
