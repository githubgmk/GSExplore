package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kenjinsan on 5/24/17.
 */

public class mCallPlan implements Serializable{

    @SerializedName("CallPlanId")
    private String CallPlanId;
    @SerializedName("CallPlanDate")
    private String CallPlanDate;
    @SerializedName("CallPlanTypeId")
    private String CallPlanTypeId;
    @SerializedName("CallPlanTypeName")
    private String CallPlanTypeName;
    @SerializedName("SalesmanId")//unplan/plan
    private int SalesmanId;
    @SerializedName("CustId")
    private int CustId;
    @SerializedName("CallPlanStatusId")
    private int CallPlanStatusId;//status draft 0 ,inreview 1, open 2,close 3
    @SerializedName("CallPlanStatusName")
    private String CallPlanStatusName;//status draft 0 ,inreview 1, open 2,close 3
    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("CreatedBy")
    private String CreatedBy;//sales id
    @SerializedName("ModifiedDate")
    private String ModifiedDate;
    @SerializedName("ModifiedBy")
    private String ModifiedBy;// yg terakhir update siapa
    @SerializedName("Notes")
    private  String Notes;
    private int jenisplan;
    private  int StatusSend;
    private  boolean Selected;

    private  String CustomerName;
    private  String CustomerAlias;
    private  String CustomerAddress;

    public mCallPlan() {
    }

    public mCallPlan(String callPlanId, String callPlanDate, String callPlanTypeId, int salesmanId, int custId, int callPlanStatusId,String notes, String createdDate, String createdBy, int statusSend) {
        CallPlanId = callPlanId;
        CallPlanDate = callPlanDate;
        CallPlanTypeId = callPlanTypeId;
        SalesmanId = salesmanId;
        CustId = custId;
        CallPlanStatusId = callPlanStatusId;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        StatusSend = statusSend;
        ModifiedDate=createdDate;
        Notes=notes;
        ModifiedBy=createdBy;
    }
    public mCallPlan(String callPlanId, String callPlanDate, String callPlanTypeId, int salesmanId, int custId,
                     int callPlanStatusId,String notes, String createdDate) {
        CallPlanId = callPlanId;
        CallPlanDate = callPlanDate;
        CallPlanTypeId = callPlanTypeId;
        SalesmanId = salesmanId;
        CustId = custId;
        CallPlanStatusId = callPlanStatusId;
        Notes=notes;
        CreatedDate = createdDate;
        CreatedBy = String.valueOf(salesmanId);
        ModifiedDate=createdDate;
        ModifiedBy=String.valueOf(salesmanId);;
    }

    public mCallPlan(String callPlanId, String callPlanDate, String callPlanTypeId, int salesmanId, int custId,
                     int callPlanStatusId, String notes,String createdDate,int statusSend) {
        CallPlanId = callPlanId;
        CallPlanDate = callPlanDate;
        CallPlanTypeId = callPlanTypeId;
        SalesmanId = salesmanId;
        CustId = custId;
        CallPlanStatusId = callPlanStatusId;
        Notes=notes;
        CreatedDate = createdDate;
        CreatedBy = String.valueOf(salesmanId);
        ModifiedDate=createdDate;
        ModifiedBy=String.valueOf(salesmanId);
        StatusSend=statusSend;
    }

    public mCallPlan(String callPlanId, String callPlanDate, String callPlanTypeId, int salesmanId, int custId, int callPlanStatusId,String notes,
                     String createdDate, String createdBy, int statusSend, String custName, String custAliasName, String custAddress) {
        CallPlanId = callPlanId;
        CallPlanDate = callPlanDate;
        CallPlanTypeId = callPlanTypeId;
        SalesmanId = salesmanId;
        CustId = custId;
        CallPlanStatusId = callPlanStatusId;
        Notes=notes;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        StatusSend = statusSend;
        CustomerName=custName;
        CustomerAlias=custAliasName;
        CustomerAddress=custAddress;
        ModifiedDate=createdDate;
        ModifiedBy=String.valueOf(salesmanId);

    }
    public mCallPlan(String callPlanId, String callPlanDate, String callPlanTypeId,String callPlanTypeName, int salesmanId, int custId,
                     int callPlanStatusId,String callPlanStatusName,String notes, String createdDate, String createdBy, int statusSend, String custName,
                     String custAliasName, String custAddress) {
        CallPlanId = callPlanId;
        CallPlanDate = callPlanDate;
        CallPlanTypeId = callPlanTypeId;
        CallPlanTypeName=callPlanTypeName;
        SalesmanId = salesmanId;
        CustId = custId;
        CallPlanStatusId = callPlanStatusId;
        CallPlanStatusName=callPlanStatusName;
        Notes=notes;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        StatusSend = statusSend;
        CustomerName=custName;
        CustomerAlias=custAliasName;
        CustomerAddress=custAddress;
        ModifiedDate=createdDate;
        ModifiedBy=String.valueOf(salesmanId);

    }

    public String getCallPlanId() {
        return CallPlanId;
    }

    public void setCallPlanId(String callPlanId) {
        CallPlanId = callPlanId;
    }

    public String getCallPlanDate() {
        return CallPlanDate;
    }

    public void setCallPlanDate(String callPlanDate) {
        CallPlanDate = callPlanDate;
    }

    public String getCallPlanTypeId() {
        return CallPlanTypeId;
    }

    public void setCallPlanTypeId(String callPlanTypeId) {
        CallPlanTypeId = callPlanTypeId;
    }

    public int getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        SalesmanId = salesmanId;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public int getCallPlanStatusId() {
        return CallPlanStatusId;
    }

    public void setCallPlanStatusId(int callPlanStatusId) {
        CallPlanStatusId = callPlanStatusId;
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

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        StatusSend = statusSend;
    }

    public int getJenisplan() {
        return jenisplan;
    }

    public void setJenisplan(int jenisplan) {
        this.jenisplan = jenisplan;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAlias() {
        return CustomerAlias;
    }

    public void setCustomerAlias(String customerAlias) {
        CustomerAlias = customerAlias;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public String getCallPlanTypeName() {
        return CallPlanTypeName;
    }

    public void setCallPlanTypeName(String callPlanTypeName) {
        CallPlanTypeName = callPlanTypeName;
    }

    public String getCallPlanStatusName() {
        return CallPlanStatusName;
    }

    public void setCallPlanStatusName(String callPlanStatusName) {
        CallPlanStatusName = callPlanStatusName;
    }
}
