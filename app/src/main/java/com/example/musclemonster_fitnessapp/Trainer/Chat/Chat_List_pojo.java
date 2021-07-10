package com.example.musclemonster_fitnessapp.Trainer.Chat;

public class Chat_List_pojo {

    String UserID;
    String TrainerID;
    String TrainerName;
    String TimgUrl;
    String FKey;
    String TEmail;

    public Chat_List_pojo(String trainerID, String trainerName, String timgUrl, String FKey, String TEmail) {
        TrainerID = trainerID;
        TrainerName = trainerName;
        TimgUrl = timgUrl;
        this.FKey = FKey;
        this.TEmail = TEmail;
    }

    public Chat_List_pojo(){

    }

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
}
