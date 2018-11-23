package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 4/22/17.
 */

public class mChannel implements Serializable {
    private int ChannelId;
    private String ChannelName;
    private String Email;
    private String Signature;
    private String Pic;
    private int StatusId;

    public mChannel() {
    }

    public mChannel(int channelId, String channelName, String email, String signature, String pic, int statusId) {
        ChannelId = channelId;
        ChannelName = channelName;
        Email = email;
        Signature = signature;
        Pic = pic;
        StatusId = statusId;
    }

    public mChannel(int channelId, String channelName) {
        ChannelId = channelId;
        ChannelName = channelName;
    }

    public int getChannelId() {
        return ChannelId;
    }

    public void setChannelId(int channelId) {
        ChannelId = channelId;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }
}
