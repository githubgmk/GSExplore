package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/15/17.
 */

public class mPoLine implements Serializable {
    private String RecIdTab;
    private String PoId;
    private int ProductId;
    private double Qty;
    private double UnitPrice;
    private String UnitId;
    private int PriceId;
    private double PriceList;

    private int DiscId;
    private double Disc1;
    private double Disc2;
    private double Disc3;
    private double DiscRp;
    private double Point;
    private boolean IncludePPN;
    private String CreatedDate;
    private String ConfirmDate;

    private int StatusSend;
    private boolean Selected;
    private String ProductName;
    private String ProductCode;
    private int PromoId;
    private String RefRecIdTab;

    private boolean isDraft;
    //private int QtyBonus;
    private mPoLine poLineBonus;


    public mPoLine(String recIdTab, String poId, int productId, String productName, String productCode, double qty, double unitPrice,
                   String unitId, int priceId, double priceList, int discId, double disc1, double disc2, double disc3, double discRp, int promoId, String refRecIdTab,
                   double point, boolean includePPN, String createdDate, String confirmDate, int statusSend) {
        RecIdTab = recIdTab;
        PoId = poId;
        ProductId = productId;
        ProductName = productName;
        ProductCode = productCode;
        Qty = qty;
        UnitPrice = unitPrice;
        UnitId = unitId;
        PriceId = priceId;
        PriceList = priceList;
        DiscId = discId;
        Disc1 = disc1;
        Disc2 = disc2;
        Disc3 = disc3;
        DiscRp = discRp;
        PromoId = promoId;
        RefRecIdTab = refRecIdTab;
        Point = point;
        IncludePPN = includePPN;
        CreatedDate = createdDate;
        ConfirmDate = confirmDate;
        StatusSend = statusSend;
    }

    public mPoLine(String recIdTab, String poId, int productId, String productName, String productCode, double qty, double unitPrice,
                   String unitId, int priceId, double priceList, int discId, double disc1, double disc2, double disc3, double discRp, int promoId, String refRecIdTab,
                   double point, boolean includePPN, String createdDate, String confirmDate, int statusSend, mPoLine poBonus, boolean isdraft) {
        RecIdTab = recIdTab;
        PoId = poId;
        ProductId = productId;
        ProductName = productName;
        ProductCode = productCode;
        Qty = qty;
        UnitPrice = unitPrice;
        UnitId = unitId;
        PriceId = priceId;
        PriceList = priceList;
        DiscId = discId;
        Disc1 = disc1;
        Disc2 = disc2;
        Disc3 = disc3;
        DiscRp = discRp;
        PromoId = promoId;
        RefRecIdTab = refRecIdTab;
        Point = point;
        IncludePPN = includePPN;
        CreatedDate = createdDate;
        ConfirmDate = confirmDate;
        StatusSend = statusSend;
        poLineBonus = poBonus;
        isDraft = isdraft;
    }

    public mPoLine(String recIdTab, String poId, int productId, double qty, double unitPrice, String unitId, int priceId,
                   int discId, double disc1, double disc2, double disc3, double discRp, int point, boolean includePPN,
                   String createdDate, String confirmDate, int statusSend) {
        RecIdTab = recIdTab;
        PoId = poId;
        ProductId = productId;
        Qty = qty;
        UnitPrice = unitPrice;
        UnitId = unitId;
        PriceId = priceId;

        DiscId = discId;
        Disc1 = disc1;
        Disc2 = disc2;
        Disc3 = disc3;
        DiscRp = discRp;
        Point = point;
        IncludePPN = includePPN;
        CreatedDate = createdDate;
        ConfirmDate = confirmDate;
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

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public int getPriceId() {
        return PriceId;
    }

    public void setPriceId(int priceId) {
        PriceId = priceId;
    }

    public int getDiscId() {
        return DiscId;
    }

    public void setDiscId(int discId) {
        DiscId = discId;
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

    public double getDiscRp() {
        return DiscRp;
    }

    public void setDiscRp(double discRp) {
        DiscRp = discRp;
    }

    public double getPoint() {
        return Point;
    }

    public void setPoint(double point) {
        Point = point;
    }

    public boolean isIncludePPN() {
        return IncludePPN;
    }

    public void setIncludePPN(boolean includePPN) {
        IncludePPN = includePPN;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getConfirmDate() {
        return ConfirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        ConfirmDate = confirmDate;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        StatusSend = statusSend;
    }

    public double getPriceList() {
        return PriceList;
    }

    public void setPriceList(double priceList) {
        PriceList = priceList;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public int getPromoId() {
        return PromoId;
    }

    public void setPromoId(int promoId) {
        PromoId = promoId;
    }

    public String getRefRecIdTab() {
        return RefRecIdTab;
    }

    public void setRefRecIdTab(String refRecIdTab) {
        RefRecIdTab = refRecIdTab;
    }

    public mPoLine getPoLineBonus() {
        return poLineBonus;
    }

    public void setPoLineBonus(mPoLine poLineBonus) {
        this.poLineBonus = poLineBonus;
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
