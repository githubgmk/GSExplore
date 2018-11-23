package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/12/17.
 */

public class mProductPriceDiskon implements Serializable{
    private  int RecId;
    private  int Relation;
    private int DistId;
    private  int PriceType;
    private String PriceDiscGroupId;
    private String ProductCode;
    private String UnitId;
    private double Price;
    private double Disc1;
    private double Disc2;
    private double Disc3;
    private double DiscRp;
    private String StartDate;
    private String EndDate;


    //untuk join dengan produk
    private int ProductId;
    private  String ProductName;
    private String Foto;
    private String ProductSimpleDescription;
    private  int RecIdProductMap;
    private String ProductNameDist;
    private int StatusId;

    private boolean Selected;
    private  boolean isDraft;

    public mProductPriceDiskon(int recId, int relation, int distId, int priceType, String priceDiscGroupId, String productCode,String ProductDistName, String unitId,
                               double price, double disc1, double disc2,double disc3, String startDate, String endDate) {
        RecId = recId;
        Relation = relation;
        DistId = distId;
        PriceType=priceType;
        PriceDiscGroupId = priceDiscGroupId;
        ProductCode = productCode;
        ProductNameDist=ProductDistName;
        UnitId = unitId;
        Price = price;
        Disc1 = disc1;
        Disc2 = disc2;
        Disc3=disc3;
        StartDate = startDate;
        EndDate = endDate;
    }

    public mProductPriceDiskon(int recId, int relation, int distId, int priceType, String priceDiscGroupId, String productCode, String unitId,
                               double price, double disc1, double disc2,double disc3, String startDate, String endDate,
                               int productId, String productName, String foto, String productSimpleDescription,
                               int recIdProductMap, String productNameDist, int statusId) {
        RecId = recId;
        Relation = relation;
        DistId = distId;
        PriceType = priceType;
        PriceDiscGroupId = priceDiscGroupId;
        ProductCode = productCode;
        UnitId = unitId;
        Price = price;
        Disc1 = disc1;
        Disc2 = disc2;
        Disc3=disc3;
        StartDate = startDate;
        EndDate = endDate;
        ProductId = productId;
        ProductName = productName;
        Foto = foto;
        ProductSimpleDescription = productSimpleDescription;
        RecIdProductMap = recIdProductMap;
        ProductNameDist = productNameDist;
        StatusId = statusId;
    }

    public int getRecId() {
        return RecId;
    }

    public void setRecId(int recId) {
        RecId = recId;
    }

    public int getRelation() {
        return Relation;
    }

    public void setRelation(int relation) {
        Relation = relation;
    }

    public int getDistId() {
        return DistId;
    }

    public void setDistId(int distId) {
        DistId = distId;
    }

    public int getPriceType() {
        return PriceType;
    }

    public void setPriceType(int priceType) {
        PriceType = priceType;
    }

    public String getPriceDiscGroupId() {
        return PriceDiscGroupId;
    }

    public void setPriceDiscGroupId(String priceDiscGroupId) {
        PriceDiscGroupId = priceDiscGroupId;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getDisc1() {
        return Disc1;
    }

    public void setDisc1(double disc1) {
        Disc1 = disc1;
    }

    public double getDisc2() {
        return Disc2;
    }

    public void setDisc2(double disc2) {
        Disc2 = disc2;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    //untuk join

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

    public double getDiscRp() {
        return DiscRp;
    }

    public void setDiscRp(double discRp) {
        DiscRp = discRp;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public double getDisc3() {
        return Disc3;
    }

    public void setDisc3(double disc3) {
        Disc3 = disc3;
    }
}
