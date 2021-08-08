package com.example.musclemonster_fitnessapp.POJOClasses;

public class Pojo_Activity_My_Appointments {
    String Name,Date,Flag,FKey,userID;

    public Pojo_Activity_My_Appointments(String Name,String date,String Flag) {
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

    public Pojo_Activity_My_Appointments(){}

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
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
