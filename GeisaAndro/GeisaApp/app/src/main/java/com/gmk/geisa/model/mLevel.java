package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kenjinsan on 5/2/17.
 */

public class mLevel implements Serializable {
    @SerializedName("CustLevelId")
    private int CustLevelId;
    @SerializedName("CustLevelName")
    private String CustLevelName;

    public mLevel() {
    }

    public mLevel(int custLevelId, String custLevelName) {
        CustLevelId = custLevelId;
        CustLevelName = custLevelName;
    }

    public int getCustLevelId() {
        return CustLevelId;
    }

    public void setCustLevelId(int custLevelId) {
        CustLevelId = custLevelId;
    }

    public String getCustLevelName() {
        return CustLevelName;
    }

    public void setCustLevelName(String custLevelName) {
        CustLevelName = custLevelName;
    }
}
