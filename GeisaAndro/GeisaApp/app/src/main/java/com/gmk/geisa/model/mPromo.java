package com.gmk.geisa.model;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by kenjinsan on 7/5/17.
 */

public class mPromo implements Serializable {
    private int PromoId;
    private String PromoName;
	private String StartDate;
    private String EndDate;
	private int DistId;
    private String DistName;
	private int CustRelation;
	private String Cust;
    private String CustName;
	private int ProductId;
    private  String ProductName;
	private String UnitId;
	private int MinQty;
	private int MinValue;
	private int MultiplyQty;
	private int ProductIdBonus;
    private String ProductBonusName;
	private String UnitIdBonus;
	private int QtyBonus;
	private String Notes;
	private String CreatedDate;
	private String CreatedBy;
	private String ModifiedDate;
	private String ModifiedBy;

    public mPromo(int promoId, String promoName, String startDate, String endDate, int distId, String distName, int custRelation, String cust, String custName, int productId, String productName, String unitId, int minQty, int minValue, int multiplyQty, int productIdBonus, String productBonusName, String unitIdBonus, int qtyBonus, String notes, String createdDate, String createdBy, String modifiedDate, String modifiedBy) {
        PromoId = promoId;
        PromoName = promoName;
        StartDate = startDate;
        EndDate = endDate;
        DistId = distId;
        DistName = distName;
        CustRelation = custRelation;
        Cust = cust;
        CustName = custName;
        ProductId = productId;
        ProductName = productName;
        UnitId = unitId;
        MinQty = minQty;
        MinValue = minValue;
        MultiplyQty = multiplyQty;
        ProductIdBonus = productIdBonus;
        ProductBonusName = productBonusName;
        UnitIdBonus = unitIdBonus;
        QtyBonus = qtyBonus;
        Notes = notes;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
    }

    public mPromo(int promoId, String promoName, String startDate, String endDate, int distId, int custRelation, String cust, int productId, String unitId, int minQty, int minValue, int multiplyQty, int productIdBonus, String unitIdBonus, int qtyBonus, String notes, String createdDate, String createdBy, String modifiedDate, String modifiedBy) {
        PromoId = promoId;
        PromoName = promoName;
        StartDate = startDate;
        EndDate = endDate;
        DistId = distId;
        CustRelation = custRelation;
        Cust = cust;
        ProductId = productId;
        UnitId = unitId;
        MinQty = minQty;
        MinValue = minValue;
        MultiplyQty = multiplyQty;
        ProductIdBonus = productIdBonus;
        UnitIdBonus = unitIdBonus;
        QtyBonus = qtyBonus;
        Notes = notes;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
    }

    public mPromo(int promoId, String promoName, String startDate, String endDate, int distId, int custRelation, String cust, int productId, String unitId, int minQty, int minValue, int multiplyQty, int productIdBonus, String unitIdBonus, int qtyBonus, String notes,String createdDate,String createdBy) {
        PromoId = promoId;
        PromoName = promoName;
        StartDate = startDate;
        EndDate = endDate;
        DistId = distId;
        CustRelation = custRelation;
        Cust = cust;
        ProductId = productId;
        UnitId = unitId;
        MinQty = minQty;
        MinValue = minValue;
        MultiplyQty = multiplyQty;
        ProductIdBonus = productIdBonus;
        UnitIdBonus = unitIdBonus;
        QtyBonus = qtyBonus;
        Notes = notes;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = createdDate;
        ModifiedBy = createdBy;
    }

    public int getPromoId() {
        return PromoId;
    }

    public void setPromoId(int promoId) {
        PromoId = promoId;
    }

    public String getPromoName() {
        return PromoName;
    }

    public void setPromoName(String promoName) {
        PromoName = promoName;
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

    public int getDistId() {
        return DistId;
    }

    public void setDistId(int distId) {
        DistId = distId;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public int getCustRelation() {
        return CustRelation;
    }

    public void setCustRelation(int custRelation) {
        CustRelation = custRelation;
    }

    public String getCust() {
        return Cust;
    }

    public void setCust(String cust) {
        Cust = cust;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
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

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public int getMinQty() {
        return MinQty;
    }

    public void setMinQty(int minQty) {
        MinQty = minQty;
    }

    public int getMinValue() {
        return MinValue;
    }

    public void setMinValue(int minValue) {
        MinValue = minValue;
    }

    public int getMultiplyQty() {
        return MultiplyQty;
    }

    public void setMultiplyQty(int multiplyQty) {
        MultiplyQty = multiplyQty;
    }

    public int getProductIdBonus() {
        return ProductIdBonus;
    }

    public void setProductIdBonus(int productIdBonus) {
        ProductIdBonus = productIdBonus;
    }

    public String getProductBonusName() {
        return ProductBonusName;
    }

    public void setProductBonusName(String productBonusName) {
        ProductBonusName = productBonusName;
    }

    public String getUnitIdBonus() {
        return UnitIdBonus;
    }

    public void setUnitIdBonus(String unitIdBonus) {
        UnitIdBonus = unitIdBonus;
    }

    public int getQtyBonus() {
        return QtyBonus;
    }

    public void setQtyBonus(int qtyBonus) {
        QtyBonus = qtyBonus;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
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
