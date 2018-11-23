package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/12/17.
 */

public class mProduct implements Serializable {
    private int ProductId;
    private  String ProductName;
    private String Foto;
    private String ProductSimpleDescription;
    @SerializedName("RecIdTab")
    private  int RecIdProductMap;
    private String ProductCode;
    private  int DistId;
    private String ProductNameDist;
    private int StatusId;

    private boolean Selected;

    public mProduct() {
    }

    public mProduct(int productId, String productName, String foto,String productSimpleDescription, int recIdProductMap, String productCode, int distId, String productNameDist, int statusId) {
        ProductId = productId;
        ProductName = productName;
        Foto = foto;
        ProductSimpleDescription=productSimpleDescription;
        RecIdProductMap = recIdProductMap;
        ProductCode = productCode;
        DistId = distId;
        ProductNameDist = productNameDist;
        StatusId = statusId;
    }

    public mProduct(int productId, String productName,String productCode, String foto,String productSimpleDescription, int statusId) {
        ProductId = productId;
        ProductName = productName;
        ProductCode=productCode;
        Foto = foto;
        ProductSimpleDescription=productSimpleDescription;
        StatusId = statusId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getProductSimpleDescription() {
        return ProductSimpleDescription;
    }

    public void setProductSimpleDescription(String productSimpleDescription) {
        ProductSimpleDescription = productSimpleDescription;
    }

    public int getRecIdProductMap() {
        return RecIdProductMap;
    }

    public void setRecIdProductMap(int recIdProductMap) {
        RecIdProductMap = recIdProductMap;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public int getDistId() {
        return DistId;
    }

    public void setDistId(int distId) {
        DistId = distId;
    }

    public String getProductNameDist() {
        return ProductNameDist;
    }

    public void setProductNameDist(String productNameDist) {
        ProductNameDist = productNameDist;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
