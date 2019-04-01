package com.example.androidlabs;

public class Message {

    private long id;
    private String msg;
    private boolean isSend;

    public Message(long id,String msg, boolean isSend) {
        this.id=id;
        this.msg = msg;
        this.isSend = isSend;
    }

    public Message(String msg, boolean isSend) {
        this.msg = msg;
        this.isSend = isSend;
    }


    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
