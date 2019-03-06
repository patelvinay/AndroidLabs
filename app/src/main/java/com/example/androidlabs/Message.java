package com.example.androidlabs;

public class Message {

    public long id;
    public String msg;
    public boolean isSent;
    public boolean isReceived;


    public Message(String msg, boolean isSent, boolean isReceived) {
        this.msg = msg;
        this.isSent = isSent;
        this.isReceived = isReceived;
    }

    public Message() {
    }

    public long getId () {
        return id;
    }

    public void setId (long id) { this.id = id; }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getisSent() { return isSent; }

    public void setSent(boolean send) { this.isSent = send; }

    public boolean getisReceived() { return isReceived; }

    public void setIsReceived(boolean isReceived) { this.isReceived = isReceived; }
}