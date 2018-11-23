package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kenjinsan on 5/2/17.
 */

public class mCustStatus implements Serializable {
    @SerializedName("CustStatusId")
    private int CustStatusId;
    @SerializedName("CustStatusName")
    private String CustStatusName;

    public mCustStatus(int custStatusId, String custStatusName) {
        CustStatusId = custStatusId;
        CustStatusName = custStatusName;
    }

    public mCustStatus() {
    }

    public int getCustStatusId() {
        return CustStatusId;
    }

    public void setCustStatusId(int custStatusId) {
        CustStatusId = custStatusId;
    }

    public String getCustStatusName() {
        return CustStatusName;
    }

    public void setCustStatusName(String custStatusName) {
        CustStatusName = custStatusName;
    }
}

