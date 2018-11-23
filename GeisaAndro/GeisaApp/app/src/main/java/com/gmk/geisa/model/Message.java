package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by Belal on 5/29/2016.
 */
public class Message implements Serializable {
    private int usersId;
    private long idmessage;
    private String message;
    private String sentAt;
    private String name;
    private boolean send;

    public Message(long idmessage,int usersId, String message, String sentAt, String name) {
        this.idmessage=idmessage;
        this.usersId = usersId;
        this.message = message;
        this.sentAt = sentAt;
        this.name = name;
    }
    public Message(long idmessage,int usersId, String message, String sentAt, String name,boolean send) {
        this.idmessage=idmessage;
        this.usersId = usersId;
        this.message = message;
        this.sentAt = sentAt;
        this.name = name;
        this.send=send;
    }

    public long getIdmessage(){return  idmessage;}

    public int getUsersId() {
        return usersId;
    }

    public String getMessage() {
        return message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public String getName() {
        return name;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
