package com.example.musclemonster_fitnessapp.Trainer.Chat;

public class Chat_List_pojo {
    String UserFName,UserLName,UserID;
    private String FKey;

    public Chat_List_pojo(){

    }
    public Chat_List_pojo(String userFName, String userLName, String userEmail) {
        UserFName = userFName;
        UserLName = userLName;
        UserID = userEmail;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getFKey() {
        return FKey;
    }

    public void setFKey(String FKey) {
        this.FKey = FKey;
    }

    public String getUserFName() {
        return UserFName;
    }

    public void setUserFName(String userFName) {
        UserFName = userFName;
    }

    public String getUserLName() {
        return UserLName;
    }

    public void setUserLName(String userLName) {
        UserLName = userLName;
    }

    public String getUserEmail() {
        return UserID;
    }

    public void setUserEmail(String userEmail) {
        UserID = userEmail;
    }
}
