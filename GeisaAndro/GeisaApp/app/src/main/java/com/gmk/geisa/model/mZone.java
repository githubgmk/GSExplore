package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kenjinsan on 5/2/17.
 */

public class mZone implements Serializable {
    @SerializedName("CustZoneId")
    private  int CustZoneId;
    @SerializedName("CustZoneName")
    private  String CustZoneName;

    public mZone() {
    }

    public mZone(int custZoneId, String custZoneName) {
        CustZoneId = custZoneId;
        CustZoneName = custZoneName;
    }

    public int getCustZoneId() {
        return CustZoneId;
    }

    public void setCustZoneId(int custZoneId) {
        CustZoneId = custZoneId;
    }

    public String getCustZoneName() {
        return CustZoneName;
    }

    public void setCustZoneName(String custZoneName) {
        CustZoneName = custZoneName;
    }
}
