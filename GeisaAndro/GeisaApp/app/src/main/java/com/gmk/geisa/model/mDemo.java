package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/7/17.
 */

public class mDemo implements Serializable {
    private String DemoId;
    private String CallPlanId;
    private String CustId;
    private String DemoTitle;
    private String DemoDescription;
    private int DemoPeserta;
    private int DemoStatusId;//status draft 0 ,inreview 1, open 2,close 3
    private String DemoStatusName;
    private String DemoDate;
    private String DemoResponse;
    private String CreatedDate;
    private String CreatedBy;
    private String ResponseDate;
    private String ResponseBy;
    private String ModifiedDate;
    private String ModifiedBy;
    private int StatusSend;
    private String CustName="";
    private String CustAlias="";
    private  String CustAddress="";
    public mDemo() {
    }

    public mDemo(String demoId, String callPlanId, String custId, String demoTitle, String demoDescription, int demoPeserta, int demoStatusId,String demoStatusName, String demoDate,
                 String demoResponse, String createdDate, String createdBy, String responseDate, String responseBy, String modifiedDate, String modifiedBy, int statusSend) {
        DemoId = demoId;
        CallPlanId = callPlanId;
        CustId = custId;
        DemoTitle = demoTitle;
        DemoDescription = demoDescription;
        DemoPeserta = demoPeserta;
        DemoStatusId = demoStatusId;
        DemoStatusName=demoStatusName;
        DemoResponse = demoResponse;
        DemoDate=demoDate;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ResponseDate = responseDate;
        ResponseBy = responseBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
    }
    public mDemo(String demoId, String callPlanId, String custId, String demoTitle, String demoDescription, int demoPeserta, int demoStatusId,String demoStatusName, String demoDate,
                 String demoResponse, String createdDate, String createdBy, String responseDate, String responseBy, int statusSend) {
        DemoId = demoId;
        CallPlanId = callPlanId;
        CustId = custId;
        DemoTitle = demoTitle;
        DemoDescription = demoDescription;
        DemoPeserta = demoPeserta;
        DemoStatusId = demoStatusId;
        DemoStatusName=demoStatusName;
        DemoResponse = demoResponse;
        DemoDate=demoDate;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ResponseDate = responseDate;
        ResponseBy = responseBy;
        ModifiedDate = createdDate;
        ModifiedBy = createdBy;
        StatusSend = statusSend;
    }

    public mDemo(String demoId, String callPlanId, String custId, String demoTitle, String demoDescription, int demoPeserta,
                 int demoStatusId,String demoStatusName, String demoDate, String demoResponse, String createdDate, String createdBy, int statusSend) {
        DemoId = demoId;
        CallPlanId = callPlanId;
        CustId = custId;
        DemoTitle = demoTitle;
        DemoDescription = demoDescription;
        DemoPeserta = demoPeserta;
        DemoStatusId = demoStatusId;
        DemoStatusName=demoStatusName;
        DemoDate=demoDate;
        DemoResponse = demoResponse;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        StatusSend = statusSend;
        ModifiedDate = createdDate;
        ModifiedBy = createdBy;
    }

    public mDemo(String demoId, String callPlanId, String custId, String demoTitle, String demoDescription, int demoPeserta, int demoStatusId,String demoStatusName, String demoDate,
                 String demoResponse, String createdDate, String createdBy, String responseDate, String responseBy, String modifiedDate, String modifiedBy, int statusSend,
                 String custName,String custAlias,String custAddress) {
        DemoId = demoId;
        CallPlanId = callPlanId;
        CustId = custId;
        DemoTitle = demoTitle;
        DemoDescription = demoDescription;
        DemoPeserta = demoPeserta;
        DemoStatusId = demoStatusId;
        DemoStatusName=demoStatusName;
        DemoResponse = demoResponse;
        DemoDate=demoDate;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ResponseDate = responseDate;
        ResponseBy = responseBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
        CustName= custName;
        CustAlias=custAlias;
        CustAddress=custAddress;
    }
    public String getDemoId() {
        return DemoId;
    }

    public void setDemoId(String demoId) {
        DemoId = demoId;
    }

    public String getCallPlanId() {
        return CallPlanId;
    }

    public void setCallPlanId(String callPlanId) {
        CallPlanId = callPlanId;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }

    public String getDemoTitle() {
        return DemoTitle;
    }

    public void setDemoTitle(String demoTitle) {
        DemoTitle = demoTitle;
    }

    public String getDemoDescription() {
        return DemoDescription;
    }

    public void setDemoDescription(String demoDescription) {
        DemoDescription = demoDescription;
    }

    public int getDemoPeserta() {
        return DemoPeserta;
    }

    public void setDemoPeserta(int demoPeserta) {
        DemoPeserta = demoPeserta;
    }

    public int getDemoStatusId() {
        return DemoStatusId;
    }

    public void setDemoStatusId(int demoStatusId) {
        DemoStatusId = demoStatusId;
    }

    public String getDemoStatusName() {
        return DemoStatusName;
    }

    public void setDemoStatusName(String demoStatusName) {
        DemoStatusName = demoStatusName;
    }

    public String getDemoDate() {
        return DemoDate;
    }

    public void setDemoDate(String demoDate) {
        DemoDate = demoDate;
    }

    public String getDemoResponse() {
        return DemoResponse;
    }

    public void setDemoResponse(String demoResponse) {
        DemoResponse = demoResponse;
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

    public String getResponseDate() {
        return ResponseDate;
    }

    public void setResponseDate(String responseDate) {
        ResponseDate = responseDate;
    }

    public String getResponseBy() {
        return ResponseBy;
    }

    public void setResponseBy(String responseBy) {
        ResponseBy = responseBy;
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
}
