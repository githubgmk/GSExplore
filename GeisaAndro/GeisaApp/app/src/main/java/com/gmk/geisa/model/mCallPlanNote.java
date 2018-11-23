package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/5/17.
 */

public class mCallPlanNote implements Serializable {
    private String CallPlanNoteId;
    private int BiCsTypeId;
    private  String BiCsTypeName;
    private String CallPlanId;
    private int CustId;
    private String Notes1;
    private String Notes2;
    private String Notes3;
    private String CreatedDate;
    private String CreatedBy;
    private String ModifiedDate;
    private String ModifiedBy;
    private int StatusSend;

    private String CustName="";
    private String CustAlias="";
    private String CustAddress="";
    private String BiTypeName="";

    public mCallPlanNote() {
    }

    public mCallPlanNote(String callPlanNoteId, int biCsTypeId,String biCsTypeName, String callPlanId,int custId, String notes1, String notes2, String notes3, String createdDate, String createdBy, int statusSend) {
        CallPlanNoteId = callPlanNoteId;
        BiCsTypeId = biCsTypeId;
        BiCsTypeName=biCsTypeName;
        CallPlanId = callPlanId;
        CustId=custId;
        Notes1 = notes1;
        Notes2 = notes2;
        Notes3 = notes3;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        StatusSend = statusSend;
        ModifiedDate=createdDate;
        ModifiedBy=createdBy;
    }

    public mCallPlanNote(String callPlanNoteId, int biCsTypeId,String biCsTypeName, String callPlanId,int custId, String notes1, String notes2, String notes3, String createdDate, String createdBy, String modifiedDate, String modifiedBy, int statusSend) {
        CallPlanNoteId = callPlanNoteId;
        BiCsTypeId = biCsTypeId;
        BiCsTypeName=biCsTypeName;
        CallPlanId = callPlanId;
        CustId=custId;
        Notes1 = notes1;
        Notes2 = notes2;
        Notes3 = notes3;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
    }

    public mCallPlanNote(String callPlanNoteId, int biCsTypeId,String biCsTypeName, String callPlanId,int custId, String notes1, String notes2, String notes3, String createdDate, String createdBy, String modifiedDate, String modifiedBy, int statusSend,
                         String custName,String custAlias,String custAddress,String biTypeName) {
        CallPlanNoteId = callPlanNoteId;
        BiCsTypeId = biCsTypeId;
        BiCsTypeName=biCsTypeName;
        CallPlanId = callPlanId;
        CustId=custId;
        Notes1 = notes1;
        Notes2 = notes2;
        Notes3 = notes3;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
        CustName=custName;
        CustAlias=custAlias;
        CustAddress=custAddress;
        BiTypeName=biTypeName;
    }

    public String getCallPlanNoteId() {
        return CallPlanNoteId;
    }

    public void setCallPlanNoteId(String callPlanNoteId) {
        CallPlanNoteId = callPlanNoteId;
    }

    public int getBiCsTypeId() {
        return BiCsTypeId;
    }

    public void setBiCsTypeId(int biCsTypeId) {
        BiCsTypeId = biCsTypeId;
    }

    public String getBiCsTypeName() {
        return BiCsTypeName;
    }

    public void setBiCsTypeName(String biCsTypeName) {
        BiCsTypeName = biCsTypeName;
    }

    public String getCallPlanId() {
        return CallPlanId;
    }

    public void setCallPlanId(String callPlanId) {
        CallPlanId = callPlanId;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public String getNotes1() {
        return Notes1;
    }

    public void setNotes1(String notes1) {
        Notes1 = notes1;
    }

    public String getNotes2() {
        return Notes2;
    }

    public void setNotes2(String notes2) {
        Notes2 = notes2;
    }

    public String getNotes3() {
        return Notes3;
    }

    public void setNotes3(String notes3) {
        Notes3 = notes3;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        StatusSend = statusSend;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustAlias() {
        return CustAlias;
    }

    public void setCustAlias(String custAlias) {
        CustAlias = custAlias;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }

    public String getBiTypeName() {
        return BiTypeName;
    }

    public void setBiTypeName(String biTypeName) {
        BiTypeName = biTypeName;
    }
}
