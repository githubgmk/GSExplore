package com.gmk.geisa.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjinsan on 6/8/17.
 */

public class mSample implements Serializable{
    private String SampleId;
    private String CallPlanId;
    private int CustId;
    private String SampleFor;
    private String SampleDate;
    private int SampleStatusId;
    private String SampleStatus;
    private String CreatedDate;
    private String CreatedBy;
    //untuk realisasi sample
    private String SampleReceivedDate;
    private String CustPic;
    private String CustPicJabatan;
    private String CustPicHp;
    private String Note;
    private String ModifiedDate;
    private String ModifiedBy;
    private int StatusSend;
    // untuk followup sample
    private String SampleResponseDate;
    private String SampleResponseNote;
    //gobal
    private ArrayList<mProductSample> ProductOfRequest;
    private ArrayList<mProductSample> ProductOfRealisasi;
    private mProductSample productSample;
    private int StepSample;
    private mCustomer Customer;

    public mSample() {
    }

    public mSample(String sampleId, String callPlanId, int custId, String sampleFor, String sampleDate,
                   int sampleStatusId, String sampleStatus, String createdDate, String createdBy) {
        SampleId = sampleId;
        CallPlanId = callPlanId;
        CustId = custId;
        SampleFor = sampleFor;
        SampleDate = sampleDate;
        SampleStatusId = sampleStatusId;
        SampleStatus = sampleStatus;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = createdDate;
        ModifiedBy = createdBy;
        SampleReceivedDate = "1900-01-01";
        Note = "";
        CustPic = "";
        CustPicJabatan = "";
        CustPicHp = "";
        SampleResponseDate = "1900-01-01";
        SampleResponseNote = "";
        StepSample = 0;

    }

    public mSample(String sampleId, String callPlanId, int custId, String sampleFor, String sampleDate,
                   int sampleStatusId, String sampleStatus, ArrayList<mProductSample> productOfRequest,ArrayList<mProductSample> productOfRealisasi,
                   String createdDate, String createdBy, int statusSend) {
        SampleId = sampleId;
        CallPlanId = callPlanId;
        CustId = custId;
        SampleFor = sampleFor;
        SampleDate = sampleDate;
        SampleStatusId = sampleStatusId;
        SampleStatus = sampleStatus;
        ProductOfRequest = productOfRequest;
        ProductOfRealisasi=productOfRealisasi;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = createdDate;
        ModifiedBy = createdBy;
        StatusSend = statusSend;
        SampleReceivedDate = "1900-01-01";
        Note = "";
        CustPic = "";
        CustPicJabatan = "";
        CustPicHp = "";
        SampleResponseDate = "1900-01-01";
        SampleResponseNote = "";
        StepSample = 0;

    }

    public mSample(String sampleId, String callPlanId, int custId, String sampleFor, String note, String sampleDate,
                   String sampleReceivedDate, int sampleStatusId, String sampleStatus, String sampleResponseDate, String sampleResponseNote, ArrayList<mProductSample> productOfRequest, String createdDate, String createdBy, String modifiedDate, String modifiedBy, int statusSend) {
        SampleId = sampleId;
        CallPlanId = callPlanId;
        CustId = custId;
        SampleFor = sampleFor;
        Note = note;
        SampleDate = sampleDate;
        SampleReceivedDate = sampleReceivedDate;
        SampleStatusId = sampleStatusId;
        SampleStatus = sampleStatus;
        SampleResponseDate = sampleResponseDate;
        SampleResponseNote = sampleResponseNote;
        ProductOfRequest = productOfRequest;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        StatusSend = statusSend;
    }

    public mSample(String sampleId, String callPlanId, int custId, String sampleFor, String sampleDate, int sampleStatusId,
                   String sampleStatus, String sampleReceivedDate, String custPic, String custPicJabatan,
                   String custPicHp, String note, String sampleResponseDate, String sampleResponseNote,
                   String createdDate, String createdBy, String modifiedDate, String modifiedBy,
                   ArrayList<mProductSample> productOfRequest,ArrayList<mProductSample> productOfRealisasi, int statusSend) {
        SampleId = sampleId;
        CallPlanId = callPlanId;
        CustId = custId;
        SampleFor = sampleFor;
        SampleDate = sampleDate;
        SampleStatusId = sampleStatusId;
        SampleStatus = sampleStatus;
        SampleReceivedDate = sampleReceivedDate;
        CustPic = custPic;
        CustPicJabatan = custPicJabatan;
        CustPicHp = custPicHp;
        Note = note;
        SampleResponseDate = sampleResponseDate;
        SampleResponseNote = sampleResponseNote;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        ProductOfRequest = productOfRequest;
        ProductOfRealisasi=productOfRealisasi;
        StatusSend = statusSend;
    }

    public mSample(String sampleId, String callPlanId, int custId, String sampleFor, String sampleDate, int sampleStatusId,
                   String sampleStatus, String sampleReceivedDate, String custPic, String custPicJabatan,
                   String custPicHp, String note, String sampleResponseDate, String sampleResponseNote,
                   String createdDate, String createdBy, String modifiedDate, String modifiedBy,
                   ArrayList<mProductSample> productOfRequest,ArrayList<mProductSample> productOfRealisasi,mCustomer customer, int statusSend) {
        SampleId = sampleId;
        CallPlanId = callPlanId;
        CustId = custId;
        SampleFor = sampleFor;
        SampleDate = sampleDate;
        SampleStatusId = sampleStatusId;
        SampleStatus = sampleStatus;
        SampleReceivedDate = sampleReceivedDate;
        CustPic = custPic;
        CustPicJabatan = custPicJabatan;
        CustPicHp = custPicHp;
        Note = note;
        SampleResponseDate = sampleResponseDate;
        SampleResponseNote = sampleResponseNote;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        ProductOfRequest = productOfRequest;
        ProductOfRealisasi=productOfRealisasi;
        Customer=customer;
        StatusSend = statusSend;
    }


    public static class mProductSample implements Serializable {
        private String SampleProdukId;
        private String SampleId;
        private int ProductId;
        private String Kemasan;
        private double Qty;
        private String Note;
        private String TypeRequest;
        private boolean Selected;
        private String ProductName;
        private String ProductCode;
        private String CreatedDate;
        public mProductSample() {
        }

        public mProductSample(String sampleProdukId, String sampleId, int productId,String productName,String productCode, String kemasan, double qty, String note, String typeRequest,String createdDate) {
            SampleProdukId = sampleProdukId;
            SampleId = sampleId;
            ProductId = productId;
            ProductName=productName;
            ProductCode=productCode;
            Kemasan = kemasan;
            Qty = qty;
            Note = note;
            TypeRequest = typeRequest;
            CreatedDate=createdDate;

        }

        public String getSampleProdukId() {
            return SampleProdukId;
        }

        public void setSampleProdukId(String sampleProdukId) {
            SampleProdukId = sampleProdukId;
        }

        public String getSampleId() {
            return SampleId;
        }

        public void setSampleId(String sampleId) {
            SampleId = sampleId;
        }

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int productId) {
            ProductId = productId;
        }

        public String getKemasan() {
            return Kemasan;
        }

        public void setKemasan(String kemasan) {
            Kemasan = kemasan;
        }

        public double getQty() {
            return Qty;
        }

        public void setQty(double qty) {
            Qty = qty;
        }

        public String getNote() {
            return Note;
        }

        public void setNote(String note) {
            Note = note;
        }

        public String getTypeRequest() {
            return TypeRequest;
        }

        public void setTypeRequest(String typeRequest) {
            TypeRequest = typeRequest;
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

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String createdDate) {
            CreatedDate = createdDate;
        }
    }

    public int getStepSample() {
        return StepSample;
    }

    public void setStepSample(int stepSample) {
        this.StepSample = stepSample;
    }

    public mProductSample getProductSample() {
        return productSample;
    }

    public void setProductSample(mProductSample productSample) {
        this.productSample = productSample;
    }

    public String getSampleId() {
        return SampleId;
    }

    public void setSampleId(String sampleId) {
        SampleId = sampleId;
    }

    public String getCallPlanId() {
        return CallPlanId;
    }

    public void setCallPlanId(String callPlanId) {
        CallPlanId = callPlanId;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public String getSampleFor() {
        return SampleFor;
    }

    public void setSampleFor(String sampleFor) {
        SampleFor = sampleFor;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getSampleDate() {
        return SampleDate;
    }

    public void setSampleDate(String sampleDate) {
        SampleDate = sampleDate;
    }

    public String getSampleReceivedDate() {
        return SampleReceivedDate;
    }

    public void setSampleReceivedDate(String sampleReceivedDate) {
        SampleReceivedDate = sampleReceivedDate;
    }

    public int getSampleStatusId() {
        return SampleStatusId;
    }

    public void setSampleStatusId(int sampleStatusId) {
        SampleStatusId = sampleStatusId;
    }

    public String getSampleResponseDate() {
        return SampleResponseDate;
    }

    public void setSampleResponseDate(String sampleResponseDate) {
        SampleResponseDate = sampleResponseDate;
    }

    public String getSampleResponseNote() {
        return SampleResponseNote;
    }

    public void setSampleResponseNote(String sampleResponseNote) {
        SampleResponseNote = sampleResponseNote;
    }

    public ArrayList<mProductSample> getProductOfRequest() {
        return ProductOfRequest;
    }

    public void setProductOfRequest(ArrayList<mProductSample> productOfRequest) {
        ProductOfRequest = productOfRequest;
    }

    public ArrayList<mProductSample> getProductOfRealisasi() {
        return ProductOfRealisasi;
    }

    public void setProductOfRealisasi(ArrayList<mProductSample> productOfRealisasi) {
        ProductOfRealisasi = productOfRealisasi;
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

    public String getSampleStatus() {
        return SampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        SampleStatus = sampleStatus;
    }

    public mCustomer getCustomer() {
        return Customer;
    }

    public void setCustomer(mCustomer customer) {
        this.Customer = customer;
    }
}

