package com.gmk.geisa.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjinsan on 6/7/17.
 */

public class mComplain implements Serializable {
    private String ComplainId;
    private boolean SafetyFood;
    private boolean QualityFood;
    private boolean QualityApplication;
    private boolean QuantityAll;
    private boolean PackagingAll;
    private String CallPlanId;
    private String CustId;
    private  String ProductId;
    private String ProductName;
    private int ComplainStatusId;//status draft 0 ,inreview 1, open 2,close 3
    private String ComplainStatusName;
    private String SampleSendDate;
    private String CustPic;
    private String CustPicJabatan;
    private String CustPicHp;
    private String ComplainNote;
    private String ComplainPriority;
    private String ComplainResponse;
    private String ComplainResponseDate;
    private String ComplainResponseBy;
    private String CreatedDate;
    private String CreatedBy;
    private String ModifiedDate;
    private String ModifiedBy;
    private int StatusSend;
    private ArrayList<mProdukComplain> ProducOfComplain;
    private String CustName="";
    private String CustAlias="";
    private  String CustAddress="";
    private  String ProductNameProduk;


    public mComplain() {
    }

    public mComplain(String complainId, boolean safetyFood, boolean qualityFood, boolean qualityApplication, boolean quantityAll, boolean packagingAll,
                     String callPlanId, String custId, String productId, String productName, int complainStatusId, String complainStatusName, String sampleSendDate,
                     String custPic, String custPicJabatan, String custPicHp,
                     String complainNote, String complainPriority, String complainResponse, String complainResponseDate, String complainResponseBy, String createdDate, String createdBy, String modifiedDate, String modifiedBy, int statusSend) {
        ComplainId = complainId;
        SafetyFood = safetyFood;
        QualityFood=qualityFood;
        QualityApplication=qualityApplication;
        QuantityAll=quantityAll;
        PackagingAll=packagingAll;
        CallPlanId = callPlanId;
        CustId = custId;
        ProductId = productId;
        ProductName = productName;
        ComplainStatusId = complainStatusId;
        ComplainStatusName = complainStatusName;
        SampleSendDate = sampleSendDate;
        CustPic = custPic;
        CustPicJabatan = custPicJabatan;
        CustPicHp = custPicHp;
        ComplainNote = complainNote;
        ComplainPriority = complainPriority;
        ComplainResponse = complainResponse;
        ComplainResponseDate = complainResponseDate;
        ComplainResponseBy = complainResponseBy;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
    }

    public mComplain(String complainId, boolean safetyFood, boolean qualityFood, boolean qualityApplication, boolean quantityAll, boolean packagingAll,
                     String callPlanId, String custId, String productId, String productName, int complainStatusId, String complainStatusName, String sampleSendDate,
                     String custPic, String custPicJabatan, String custPicHp,
                     String complainNote, String createdDate, String createdBy, int statusSend) {
        ComplainId = complainId;
        SafetyFood = safetyFood;
        QualityFood=qualityFood;
        QualityApplication=qualityApplication;
        QuantityAll=quantityAll;
        PackagingAll=packagingAll;
        CallPlanId = callPlanId;
        CustId = custId;
        ProductId = productId;
        ProductName = productName;
        ComplainStatusId = complainStatusId;
        ComplainStatusName = complainStatusName;
        SampleSendDate = sampleSendDate;
        CustPic = custPic;
        CustPicJabatan = custPicJabatan;
        CustPicHp = custPicHp;
        ComplainNote = complainNote;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = createdDate;
        ModifiedBy = createdBy;
        StatusSend = statusSend;
        ComplainPriority="-";
        ComplainResponse="-";
        ComplainResponseDate="1900-01-01";
        ComplainResponseBy="-";
    }

    public mComplain(String complainId, boolean safetyFood, boolean qualityFood, boolean qualityApplication, boolean quantityAll, boolean packagingAll,
                     String callPlanId, String custId, String productId, String productName, int complainStatusId, String complainStatusName, String sampleSendDate,
                     String custPic, String custPicJabatan, String custPicHp,
                     String complainNote, String createdDate, String createdBy, int statusSend, ArrayList<mProdukComplain> producOfComplain) {
        ComplainId = complainId;
        SafetyFood = safetyFood;
        QualityFood=qualityFood;
        QualityApplication=qualityApplication;
        QuantityAll=quantityAll;
        PackagingAll=packagingAll;
        CallPlanId = callPlanId;
        CustId = custId;
        ProductId = productId;
        ProductName = productName;
        ComplainStatusId = complainStatusId;
        ComplainStatusName = complainStatusName;
        SampleSendDate = sampleSendDate;
        CustPic = custPic;
        CustPicJabatan = custPicJabatan;
        CustPicHp = custPicHp;
        ComplainNote = complainNote;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = createdDate;
        ModifiedBy = createdBy;
        StatusSend = statusSend;
        ProducOfComplain = producOfComplain;
    }

    public mComplain(String complainId, boolean safetyFood, boolean qualityFood, boolean qualityApplication, boolean quantityAll, boolean packagingAll,
                     String callPlanId, String custId, String productId,String productNameProduk, String productName, int complainStatusId, String complainStatusName, String sampleSendDate,
                     String custPic, String custPicJabatan, String custPicHp,
                     String complainNote, String complainPriority, String complainResponse, String complainResponseDate, String complainResponseBy, String createdDate, String createdBy, String modifiedDate, String modifiedBy, int statusSend,
                     String custName,String custAlias,String custAddress) {
        ComplainId = complainId;
        SafetyFood = safetyFood;
        QualityFood=qualityFood;
        QualityApplication=qualityApplication;
        QuantityAll=quantityAll;
        PackagingAll=packagingAll;
        CallPlanId = callPlanId;
        CustId = custId;
        ProductId = productId;
        ProductName = productName;
        ComplainStatusId = complainStatusId;
        ComplainStatusName = complainStatusName;
        SampleSendDate = sampleSendDate;
        CustPic = custPic;
        CustPicJabatan = custPicJabatan;
        CustPicHp = custPicHp;
        ComplainNote = complainNote;
        ComplainPriority = complainPriority;
        ComplainResponse = complainResponse;
        ComplainResponseDate = complainResponseDate;
        ComplainResponseBy = complainResponseBy;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
        CustName= custName;
        CustAlias=custAlias;
        CustAddress=custAddress;
        ProductNameProduk=productNameProduk;
    }
    public String getComplainId() {
        return ComplainId;
    }

    public void setComplainId(String complainId) {
        ComplainId = complainId;
    }

    public boolean isSafetyFood() {
        return SafetyFood;
    }

    public void setSafetyFood(boolean safetyFood) {
        SafetyFood = safetyFood;
    }

    public boolean isQualityFood() {
        return QualityFood;
    }

    public void setQualityFood(boolean qualityFood) {
        QualityFood = qualityFood;
    }

    public boolean isQualityApplication() {
        return QualityApplication;
    }

    public void setQualityApplication(boolean qualityApplication) {
        QualityApplication = qualityApplication;
    }

    public boolean isQuantityAll() {
        return QuantityAll;
    }

    public void setQuantityAll(boolean quantityAll) {
        QuantityAll = quantityAll;
    }

    public boolean isPackagingAll() {
        return PackagingAll;
    }

    public void setPackagingAll(boolean packagingAll) {
        PackagingAll = packagingAll;
    }

    public String getCallPlanId() {
        return CallPlanId;
    }

    public void setCallPlanId(String callPlanId) {
        CallPlanId = callPlanId;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
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

    public int getComplainStatusId() {
        return ComplainStatusId;
    }

    public void setComplainStatusId(int complainStatusId) {
        ComplainStatusId = complainStatusId;
    }

    public String getComplainStatusName() {
        return ComplainStatusName;
    }

    public void setComplainStatusName(String complainStatusName) {
        ComplainStatusName = complainStatusName;
    }

    public String getSampleSendDate() {
        return SampleSendDate;
    }

    public void setSampleSendDate(String sampleSendDate) {
        SampleSendDate = sampleSendDate;
    }

    public String getCustPic() {
        return CustPic;
    }

    public void setCustPic(String custPic) {
        CustPic = custPic;
    }

    public String getCustPicJabatan() {
        return CustPicJabatan;
    }

    public void setCustPicJabatan(String custPicJabatan) {
        CustPicJabatan = custPicJabatan;
    }

    public String getCustPicHp() {
        return CustPicHp;
    }

    public void setCustPicHp(String custPicHp) {
        CustPicHp = custPicHp;
    }

    public String getComplainNote() {
        return ComplainNote;
    }

    public void setComplainNote(String complainNote) {
        ComplainNote = complainNote;
    }

    public String getComplainPriority() {
        return ComplainPriority;
    }

    public void setComplainPriority(String complainPriority) {
        ComplainPriority = complainPriority;
    }

    public String getComplainResponse() {
        return ComplainResponse;
    }

    public void setComplainResponse(String complainResponse) {
        ComplainResponse = complainResponse;
    }

    public String getComplainResponseDate() {
        return ComplainResponseDate;
    }

    public void setComplainResponseDate(String complainResponseDate) {
        ComplainResponseDate = complainResponseDate;
    }

    public String getComplainResponseBy() {
        return ComplainResponseBy;
    }

    public void setComplainResponseBy(String complainResponseBy) {
        ComplainResponseBy = complainResponseBy;
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

    public ArrayList<mProdukComplain> getProducOfComplain() {
        return ProducOfComplain;
    }

    public void setProducOfComplain(ArrayList<mProdukComplain> producOfComplain) {
        ProducOfComplain = producOfComplain;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustAlias() {
        return CustAlias;
    }

    public void setCustAlias(String custAlias) {
        CustAlias = custAlias;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }

    public String getProductNameProduk() {
        return ProductNameProduk;
    }

    public void setProductNameProduk(String productNameProduk) {
        ProductNameProduk = productNameProduk;
    }
}
