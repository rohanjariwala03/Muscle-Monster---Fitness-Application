package com.example.musclemonster_fitnessapp.POJOClasses;

public class Exercise_History_pojo {
    String exName,exDate,exTime,exImageUri,time;

    public Exercise_History_pojo(String exName,String exDate, String exTime,String exImageUri,String time) {
        this.exName = exName;
        this.exDate=exDate;
        this.exTime = exTime;
        this.exImageUri=exImageUri;
        this.time=time;
    }

    public Exercise_History_pojo(){

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getExTime() {
        return exTime;
    }

    public void setExTime(String exTime) {
        this.exTime = exTime;
    }

    public String getExImageUri() {
        return exImageUri;
    }

    public void setExImageUri(String exImageUri) {
        this.exImageUri = exImageUri;
    }
}
