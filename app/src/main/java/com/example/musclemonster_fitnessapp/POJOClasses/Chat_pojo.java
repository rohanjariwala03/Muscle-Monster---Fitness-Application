package com.example.musclemonster_fitnessapp.POJOClasses;

public class Chat_pojo {
    private String Sender;
    private String Receiver;
    private String Msg;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    private String UserID;

    public Chat_pojo(String sender,String msg){
        this.Sender=sender;
        this.Msg=msg;
    }

    /*public Chat_pojo(String sender,String receiver,String msg){
        this.Sender=sender;
        this.Receiver=receiver;
        this.Msg=msg;
    }*/

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
