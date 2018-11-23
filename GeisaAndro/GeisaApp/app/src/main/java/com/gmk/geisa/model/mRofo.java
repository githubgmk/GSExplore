package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/25/17.
 */

public class mRofo implements Serializable {
    private String RofoId;
    private int Year;
    private int Month;
    private  int SalesmanId;
    private int CustId;
    private  String CustName;
    private int DistBranchId;
    private int ProductId;
    private String ProductName;
    private String ProductCode;
    private int  Qty;
    private String  UnitId;
    private int PriceId;
    private double  Value;
    private int  StatusId;
    private String StatusName;
    private String   CreatedDate;
    private String CreatedBy;
    private String  ModifiedDate;
    private String ModifiedBy;
    private int StatusSend;
    private  boolean Selected;

    public mRofo(String rofoId, int year, int month, int salesmanId, int custId, int distBranchId, int productId, String productName, String productCode, int qty, double value, String unitId, int priceId,
                 int statusId, String statusName, String createdDate, String createdBy, String modifiedDate, String modifiedBy, int statusSend) {
        RofoId = rofoId;
        Year = year;
        Month = month;
        SalesmanId=salesmanId;
        CustId = custId;
        DistBranchId = distBranchId;
        ProductId = productId;
        ProductName=productName;
        ProductCode = productCode;
        Qty = qty;
        Value = value;
        UnitId = unitId;
        PriceId=priceId;
        StatusId = statusId;
        StatusName=statusName;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
    }

    public mRofo(String rofoId, int year, int month, int salesmanId, int custId, String custName, int distBranchId, int productId, String productName, String productCode, int qty, double value, String unitId, int priceId,
                 int statusId, String statusName, String createdDate, String createdBy, String modifiedDate, String modifiedBy, int statusSend) {
        RofoId = rofoId;
        Year = year;
        Month = month;
        SalesmanId=salesmanId;
        CustId = custId;
        CustName=custName;
        DistBranchId = distBranchId;
        ProductId = productId;
        ProductName=productName;
        ProductCode = productCode;
        Qty = qty;
        Value = value;
        UnitId = unitId;
        PriceId=priceId;
        StatusId = statusId;
        StatusName=statusName;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
    }


    public String getRofoId() {
        return RofoId;
    }

    public void setRofoId(String rofoId) {
        RofoId = rofoId;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
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

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public int getDistBranchId() {
        return DistBranchId;
    }

    public void setDistBranchId(int distBranchId) {
        DistBranchId = distBranchId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
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

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public int getPriceId() {
        return PriceId;
    }

    public void setPriceId(int priceId) {
        PriceId = priceId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}
