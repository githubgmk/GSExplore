package com.gmk.geisa.model;

/**
 * Created by kenjinsan on 9/5/17.
 */

public class mStockBranch {
    private String ProductId ;
    private String ProductCode ;
    private String ProductName ;
    private String ProductNameDist ;
    private String Packing ;
    private String BranchId;
    private String BranchName ;
    private String AreaCode ;
    private double Qty;
    private String PrintDate ;
    private boolean Selected;

    public mStockBranch(String productId, String productCode, String productName, String productNameDist, String packing, String branchId, String branchName, String areaCode, double qty, String printDate) {
        ProductId = productId;
        ProductCode = productCode;
        ProductName = productName;
        ProductNameDist = productNameDist;
        Packing = packing;
        BranchId = branchId;
        BranchName = branchName;
        AreaCode = areaCode;
        Qty = qty;
        PrintDate = printDate;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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

    public String getProductNameDist() {
        return ProductNameDist;
    }

    public void setProductNameDist(String productNameDist) {
        ProductNameDist = productNameDist;
    }

    public String getPacking() {
        return Packing;
    }

    public void setPacking(String packing) {
        Packing = packing;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public String getPrintDate() {
        return PrintDate;
    }

    public void setPrintDate(String printDate) {
        PrintDate = printDate;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
