package com.gmk.geisa.model;

/**
 * Created by kenjinsan on 9/5/17.
 */

public class mStock {
    private String WarehouseId ;
    private String WarehouseName ;
    private String BranchId ;
    private String BranchName ;
    private String AreaCode ;
    private String Packing ;
    private String ProductId ;
    private String ProductName ;
    private String ProductNameDist ;
    private String ProductCode ;
    private double UnitConverter ;
    private double NetWeight ;
    private double Qty ;
    private String PrintDate ;

    public mStock(String warehouseId, String warehouseName, String branchId, String branchName, String areaCode, String packing, String productId, String productName, String productNameDist, String productCode, double unitConverter, double netWeight, double qty, String printDate) {
        WarehouseId = warehouseId;
        WarehouseName = warehouseName;
        BranchId = branchId;
        BranchName = branchName;
        AreaCode = areaCode;
        Packing = packing;
        ProductId = productId;
        ProductName = productName;
        ProductNameDist = productNameDist;
        ProductCode = productCode;
        UnitConverter = unitConverter;
        NetWeight = netWeight;
        Qty = qty;
        PrintDate = printDate;
    }

    public String getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        WarehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return WarehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        WarehouseName = warehouseName;
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

    public String getPacking() {
        return Packing;
    }

    public void setPacking(String packing) {
        Packing = packing;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public double getUnitConverter() {
        return UnitConverter;
    }

    public void setUnitConverter(double unitConverter) {
        UnitConverter = unitConverter;
    }

    public double getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(double netWeight) {
        NetWeight = netWeight;
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
}
