package com.example.musclemonster_fitnessapp.POJOClasses;

public class Find_Trainer_pojo {

    String Tid,TFName,TLName,TEmail,TimgUrl;
    private String FKey;

    public Find_Trainer_pojo(){

    }

    public Find_Trainer_pojo(String tid, String TFName, String TLName, String temail, String timgUrl) {
        this.Tid = tid;
        this.TFName = TFName;
        this.TLName = TLName;
        this.TEmail = temail;
        this.TimgUrl = timgUrl;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getTFName() {
        return TFName;
    }

    public void setTFName(String TFName) {
        this.TFName = TFName;
    }

    public String getTLName() {
        return TLName;
    }

    public void setTLName(String TLName) {
        this.TLName = TLName;
    }

    public String getTEmail() {
        return TEmail;
    }

    public void setTEmail(String TEmail) {
        this.TEmail = TEmail;
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

}
