package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/25/17.
 */

public class mRofoTarget implements Serializable {
    private int SalesTargetId;
    private int Year;
    private int Month;
    private int SalesmanId;
    private double Value;
    private String CreatedDate;
	private String CreatedBy;
    private String ModifiedDate;
    private String ModifiedBy;

    public mRofoTarget(int salesTargetId, int year, int month, int salesmanId, double value, String createdDate, String createdBy, String modifiedDate, String modifiedBy) {
        SalesTargetId = salesTargetId;
        Year = year;
        Month = month;
        SalesmanId = salesmanId;
        Value = value;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
    }

    public int getSalesTargetId() {
        return SalesTargetId;
    }

    public void setSalesTargetId(int salesTargetId) {
        SalesTargetId = salesTargetId;
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

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
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
}
