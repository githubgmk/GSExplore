package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/15/17.
 */

public class mPoLineOther implements Serializable {
    private String RecIdTab;
    private String PoId;
    private String ProductCode;
    private String ProductName;
    private double Qty;
    private String Unit;
    private int StatusSend;
    private boolean Selected;

    public mPoLineOther() {
    }

    public mPoLineOther(String recIdTab, String poId, String productCode, String productName, double qty, String unit, int statusSend) {
        RecIdTab = recIdTab;
        PoId = poId;
        ProductCode = productCode;
        ProductName = productName;
        Qty = qty;
        Unit = unit;
        StatusSend = statusSend;
    }

    public String getRecIdTab() {
        return RecIdTab;
    }

    public void setRecIdTab(String recIdTab) {
        RecIdTab = recIdTab;
    }

    public String getPoId() {
        return PoId;
    }

    public void setPoId(String poId) {
        PoId = poId;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
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
}
