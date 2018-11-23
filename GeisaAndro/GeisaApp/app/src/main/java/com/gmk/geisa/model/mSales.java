package com.gmk.geisa.model;

import java.io.Serializable;

public class mSales implements Serializable {
    private String UserName;
    private String UserPass;
    private int SalesmanId;
    private String FcmId;
    private String Imei;
    private int StatusId;
    private String SalesmanName;
    private String Email;
    private int SpvId;
    private String SPV;
    private int SalesmanLevelId;
    private int AreaId;
    private int statusId;
    private int recId;

    public mSales(String userName, String userPass, int salesmanId, String fcmId, String imei, int statusId, String salesmanName, String email, int spvId, String SPV, int salesmanLevelId, int areaId, int statusId1, int recId) {
        UserName = userName;
        UserPass = userPass;
        SalesmanId = salesmanId;
        FcmId = fcmId;
        Imei = imei;
        StatusId = statusId;
        SalesmanName = salesmanName;
        Email = email;
        SpvId = spvId;
        this.SPV = SPV;
        SalesmanLevelId = salesmanLevelId;
        AreaId = areaId;
        this.statusId = statusId1;
        this.recId = recId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public int getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        SalesmanId = salesmanId;
    }

    public String getFcmId() {
        return FcmId;
    }

    public void setFcmId(String fcmId) {
        FcmId = fcmId;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public String getSalesmanName() {
        return SalesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        SalesmanName = salesmanName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getSpvId() {
        return SpvId;
    }

    public void setSpvId(int spvId) {
        SpvId = spvId;
    }

    public String getSPV() {
        return SPV;
    }

    public void setSPV(String SPV) {
        this.SPV = SPV;
    }

    public int getSalesmanLevelId() {
        return SalesmanLevelId;
    }

    public void setSalesmanLevelId(int salesmanLevelId) {
        SalesmanLevelId = salesmanLevelId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }
}