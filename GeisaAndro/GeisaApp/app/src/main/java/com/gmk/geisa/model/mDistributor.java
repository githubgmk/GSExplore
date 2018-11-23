package com.gmk.geisa.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjinsan on 4/23/17.
 */

public class mDistributor implements Serializable {
    private int DistId;
    private String DistName;
    private String Address;
    private String Pic;
    private String Telp;
    private String Email;
    private int StatusId;

   ArrayList<mDistributorBranch> distributorBranch;

    public mDistributor() {
    }

    public mDistributor(int distId, String distName, String address, String pic, String telp, String email,int statusId) {
        DistId = distId;
        DistName = distName;
        Address = address;
        Pic = pic;
        Telp = telp;
        Email = email;
        StatusId = statusId;
    }

    public int getDistId() {
        return DistId;
    }

    public void setDistId(int distId) {
        DistId = distId;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getTelp() {
        return Telp;
    }

    public void setTelp(String telp) {
        Telp = telp;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }
}
