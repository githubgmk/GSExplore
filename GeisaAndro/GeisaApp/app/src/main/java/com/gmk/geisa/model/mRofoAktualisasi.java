package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/28/17.
 */

public class mRofoAktualisasi implements Serializable{
    private String RofoAktualisasiId;
    private int Year;
    private int Month;
    private String MonthName;
    private int SalesmanId;
    private double ValueRofo;
    private double ValueSales;
    private  double ValueJualan;
    private double ValueTarget;
    private double ValueRofoDraft;
    private String UpdatedDate;

    public mRofoAktualisasi(String rofoAktualisasiId, int year, int month, String monthName, int salesmanId, double valueRofo, double valueRofoDraft, double valueSales, double valueTarget, String updatedDate) {
        RofoAktualisasiId = rofoAktualisasiId;
        Year = year;
        Month = month;
        MonthName = monthName;
        SalesmanId = salesmanId;
        ValueRofo = valueRofo;
        ValueRofoDraft=valueRofoDraft;
        ValueSales = valueSales;
        ValueTarget = valueTarget;
        UpdatedDate = updatedDate;
    }

    public double getValueJualan() {
        return ValueJualan;
    }

    public void setValueJualan(double valueJualan) {
        ValueJualan = valueJualan;
    }

    public String getRofoAktualisasiId() {
        return RofoAktualisasiId;
    }

    public void setRofoAktualisasiId(String rofoAktualisasiId) {
        RofoAktualisasiId = rofoAktualisasiId;
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

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String monthName) {
        MonthName = monthName;
    }

    public int getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        SalesmanId = salesmanId;
    }

    public double getValueRofo() {
        return ValueRofo;
    }

    public void setValueRofo(double valueRofo) {
        ValueRofo = valueRofo;
    }

    public double getValueRofoDraft() {
        return ValueRofoDraft;
    }

    public void setValueRofoDraft(double valueRofoDraft) {
        ValueRofoDraft = valueRofoDraft;
    }

    public double getValueSales() {
        return ValueSales;
    }

    public void setValueSales(double valueSales) {
        ValueSales = valueSales;
    }

    public double getValueTarget() {
        return ValueTarget;
    }

    public void setValueTarget(double valueTarget) {
        ValueTarget = valueTarget;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }
}
