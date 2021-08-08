package com.example.musclemonster_fitnessapp.Trainer.PojoClasses;

public class Pojo_Activity_My_Appointments_With_Users {

    String Name,Date,Flag,FKey,UserID;

    public Pojo_Activity_My_Appointments_With_Users(String Name,String date,String Flag) {
        Date = date;
        this.Name=Name;
        this.Flag=Flag;
    }

    public String getFKey() {
        return FKey;
    }

    public void setFKey(String FKey) {
        this.FKey = FKey;
    }

    public Pojo_Activity_My_Appointments_With_Users(){}

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
