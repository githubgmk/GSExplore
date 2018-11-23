package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 7/28/17.
 */

public class mTodoList implements Serializable {
    private int RecId;
    private String Reference;
    private int CustId;
    private String CustName;
    private String Category;
    private String Title;
    private String DocDate;
    private String Detail;
    private  int StatusId;
    private String Status;
    private String StatusDetail;
    private String CreatedDate;
    private boolean StatusRead;

    public mTodoList(int recId,String reference, int custId, String custName, String category, String title, String docDate, String detail,int statusId, String status, String statusDetail, String createdDate,boolean statusRead) {
        RecId = recId;
        Reference=reference;
        CustId = custId;
        CustName = custName;
        Category = category;
        Title = title;
        DocDate = docDate;
        Detail = detail;
        StatusId=statusId;
        Status = status;
        StatusDetail = statusDetail;
        CreatedDate = createdDate;
        StatusRead=statusRead;
    }

    public int getRecId() {
        return RecId;
    }

    public void setRecId(int recId) {
        RecId = recId;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusDetail() {
        return StatusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        StatusDetail = statusDetail;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public boolean isStatusRead() {
        return StatusRead;
    }

    public void setStatusRead(boolean statusRead) {
        StatusRead = statusRead;
    }
}
