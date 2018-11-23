package com.gmk.geisa.model;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjinsan on 6/15/17.
 */

public class mPO implements Serializable {
    private String PoId;
    private String PoCustNumberRef;
    private String PoDate;
    private int DistBranchId;
    private int CustId;
    private int SalesmanId;
    private String CallPlanId;
    private String PoById;
    private String PoViaId;
    private String ShipDate;
    private String EndPeriodeDate;
    private String Mechanisme;
    private String ShipAddress;
    private double Disc1;
    private double Disc2;
    private double CashDisc;
    private boolean isPP;
    private String PicDist;
    private String PicCust;
    private String Notes;
    private String Signature;
    private int PoStatusId;
    private String PoStatusName;
    private String SoNo;
    private String SoDate;
    private String DoNo;
    private String DoDate;
    private String CreatedDate;
    private String CreatedBy;
    private String ConfirmDate;
    private String ModifiedDate;
    private String ModifiedBy;
    private boolean isSellOut;
    private int StatusSend;

    private boolean isSelected;
    private String KeteranganDetail="";

    private mCustomerAndDistBranch distBranch;
    private mCustomer customer;

    ArrayList<mPoLine> poLines;
    ArrayList<mPoLineOther> poLineOthers;

    public mPO(String poId, String poCustNumberRef, String poDate, int custId, int salesmanId,
               String callPlanId, String poById, String poViaId, String shipDate,
               String endPeriodeDate,String mechanisme, String shipAddress, double disc1, double disc2,double cashDisc,
               boolean isPP, String picDist, String picCust, String notes, String signature,
               int poStatusId, String poStatusName, String createdDate,String createdBy, int statusSend) {
        PoId = poId;
        PoCustNumberRef = poCustNumberRef;
        PoDate = poDate;
        CustId = custId;
        SalesmanId = salesmanId;
        CallPlanId = callPlanId;
        PoById = poById;
        PoViaId = poViaId;
        ShipDate = shipDate;
        EndPeriodeDate = endPeriodeDate;
        Mechanisme=mechanisme;
        ShipAddress = shipAddress;
        Disc1 = disc1;
        Disc2 = disc2;
        CashDisc=cashDisc;
        this.isPP = isPP;
        PicDist = picDist;
        PicCust = picCust;
        Notes = notes;
        Signature = signature;
        PoStatusId = poStatusId;
        PoStatusName = poStatusName;
        CreatedDate = createdDate;
        CreatedBy=createdBy;
        ModifiedDate = createdDate;
        ModifiedBy=createdBy;
        StatusSend = statusSend;
    }
    

    public mPO(String poId, String poCustNumberRef, String poDate,int distBranchId, int custId,
               int salesmanId, String callPlanId, String poById, String poViaId, String shipDate,
               String endPeriodeDate,String mechanisme, String shipAddress, double disc1, double disc2,double cashDisc, boolean isPP,
               String picDist, String picCust, String notes, String signature, int poStatusId,
               String poStatusName, String createdDate,String createdBy, int statusSend, String soNo,
               String soDate, String doNo, String doDate, String confirmDate, String modifiedDate, String modifiedBy) {
        PoId = poId;
        PoCustNumberRef = poCustNumberRef;
        PoDate = poDate;
        DistBranchId=distBranchId;
        CustId = custId;
        SalesmanId = salesmanId;
        CallPlanId = callPlanId;
        PoById = poById;
        PoViaId = poViaId;
        ShipDate = shipDate;
        EndPeriodeDate = endPeriodeDate;
        Mechanisme=mechanisme;
        ShipAddress = shipAddress;
        Disc1 = disc1;
        Disc2 = disc2;
        CashDisc=cashDisc;
        this.isPP = isPP;
        PicDist = picDist;
        PicCust = picCust;
        Notes = notes;
        Signature = signature;
        PoStatusId = poStatusId;
        PoStatusName = poStatusName;
        CreatedDate = createdDate;
        CreatedBy=createdBy;
        StatusSend = statusSend;
        SoNo = soNo;
        SoDate = soDate;
        DoNo = doNo;
        DoDate = doDate;
        ConfirmDate = confirmDate;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
    }


    public mPO(String poId, String poCustNumberRef, String poDate,int distBranchId, int custId, int salesmanId,
               String callPlanId, String poById, String poViaId, String shipDate,
               String endPeriodeDate,String mechanisme, String shipAddress, double disc1, double disc2,double cashDisc,
               boolean isPP, String picDist, String picCust, String notes, String signature,
               int poStatusId, String poStatusName, String createdDate,String createdBy, int statusSend,
               String soNo, String soDate, String doNo, String doDate, String confirmDate,
               String modifiedDate, String modifiedBy,
               ArrayList<mPoLine> poLines, ArrayList<mPoLineOther> poLineOthers) {
        PoId = poId;
        PoCustNumberRef = poCustNumberRef;
        PoDate = poDate;
        DistBranchId=distBranchId;
        CustId = custId;
        SalesmanId = salesmanId;
        CallPlanId = callPlanId;
        PoById = poById;
        PoViaId = poViaId;
        ShipDate = shipDate;
        EndPeriodeDate = endPeriodeDate;
        Mechanisme=mechanisme;
        ShipAddress = shipAddress;
        Disc1 = disc1;
        Disc2 = disc2;
        CashDisc=cashDisc;
        this.isPP = isPP;
        PicDist = picDist;
        PicCust = picCust;
        Notes = notes;
        Signature = signature;
        PoStatusId = poStatusId;
        PoStatusName = poStatusName;
        CreatedDate = createdDate;
        CreatedBy=createdBy;
        StatusSend = statusSend;
        SoNo = soNo;
        SoDate = soDate;
        DoNo = doNo;
        DoDate = doDate;
        ConfirmDate = confirmDate;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        this.poLines = poLines;
        this.poLineOthers = poLineOthers;
    }
    public mPO(String poId, String poCustNumberRef, String poDate,int distBranchId, int custId, int salesmanId,
               String callPlanId, String poById, String poViaId, String shipDate,
               String endPeriodeDate,String mechanisme, String shipAddress, double disc1, double disc2,double cashDisc,
               boolean isPP, String picDist, String picCust, String notes, String signature,
               int poStatusId, String poStatusName, String createdDate,String createdBy, int statusSend,
               String soNo, String soDate, String doNo, String doDate, String confirmDate,
               String modifiedDate, String modifiedBy,
               ArrayList<mPoLine> poLines, ArrayList<mPoLineOther> poLineOthers,boolean isSellOut) {
        PoId = poId;
        PoCustNumberRef = poCustNumberRef;
        PoDate = poDate;
        DistBranchId=distBranchId;
        CustId = custId;
        SalesmanId = salesmanId;
        CallPlanId = callPlanId;
        PoById = poById;
        PoViaId = poViaId;
        ShipDate = shipDate;
        EndPeriodeDate = endPeriodeDate;
        Mechanisme=mechanisme;
        ShipAddress = shipAddress;
        Disc1 = disc1;
        Disc2 = disc2;
        CashDisc=cashDisc;
        this.isPP = isPP;
        PicDist = picDist;
        PicCust = picCust;
        Notes = notes;
        Signature = signature;
        PoStatusId = poStatusId;
        PoStatusName = poStatusName;
        CreatedDate = createdDate;
        CreatedBy=createdBy;
        StatusSend = statusSend;
        SoNo = soNo;
        SoDate = soDate;
        DoNo = doNo;
        DoDate = doDate;
        ConfirmDate = confirmDate;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        this.poLines = poLines;
        this.poLineOthers = poLineOthers;
        this.isSellOut=isSellOut;
    }
    public mPO(String poId, String poCustNumberRef, String poDate,int distBranchId, int custId, int salesmanId,
               String callPlanId, String poById, String poViaId, String shipDate,
               String endPeriodeDate,String mechanisme, String shipAddress, double disc1, double disc2,double cashDisc,
               boolean isPP, String picDist, String picCust, String notes, String signature,
               int poStatusId, String poStatusName, String createdDate,String createdBy, int statusSend,
               String soNo, String soDate, String doNo, String doDate, String confirmDate,
               String modifiedDate, String modifiedBy,
               ArrayList<mPoLine> poLines, ArrayList<mPoLineOther> poLineOthers,mCustomer cust,mCustomerAndDistBranch dist) {
        PoId = poId;
        PoCustNumberRef = poCustNumberRef;
        PoDate = poDate;
        DistBranchId=distBranchId;
        CustId = custId;
        SalesmanId = salesmanId;
        CallPlanId = callPlanId;
        PoById = poById;
        PoViaId = poViaId;
        ShipDate = shipDate;
        EndPeriodeDate = endPeriodeDate;
        Mechanisme=mechanisme;
        ShipAddress = shipAddress;
        Disc1 = disc1;
        Disc2 = disc2;
        CashDisc=cashDisc;
        this.isPP = isPP;
        PicDist = picDist;
        PicCust = picCust;
        Notes = notes;
        Signature = signature;
        PoStatusId = poStatusId;
        PoStatusName = poStatusName;
        CreatedDate = createdDate;
        CreatedBy=createdBy;
        StatusSend = statusSend;
        SoNo = soNo;
        SoDate = soDate;
        DoNo = doNo;
        DoDate = doDate;
        ConfirmDate = confirmDate;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        this.poLines = poLines;
        this.poLineOthers = poLineOthers;
        customer=cust;
        distBranch=dist;
    }

    public mPO(String poId, String poCustNumberRef, String poDate,int distBranchId, int custId, int salesmanId,
               String callPlanId, String poById, String poViaId, String shipDate,
               String endPeriodeDate,String mechanisme, String shipAddress, double disc1, double disc2,double cashDisc,
               boolean isPP, String picDist, String picCust, String notes, String signature,
               int poStatusId, String poStatusName, String createdDate,String createdBy, int statusSend,
               String soNo, String soDate, String doNo, String doDate, String confirmDate,
               String modifiedDate, String modifiedBy,
               ArrayList<mPoLine> poLines, ArrayList<mPoLineOther> poLineOthers,mCustomer cust,mCustomerAndDistBranch dist,boolean isSellOut) {
        PoId = poId;
        PoCustNumberRef = poCustNumberRef;
        PoDate = poDate;
        DistBranchId=distBranchId;
        CustId = custId;
        SalesmanId = salesmanId;
        CallPlanId = callPlanId;
        PoById = poById;
        PoViaId = poViaId;
        ShipDate = shipDate;
        EndPeriodeDate = endPeriodeDate;
        Mechanisme=mechanisme;
        ShipAddress = shipAddress;
        Disc1 = disc1;
        Disc2 = disc2;
        CashDisc=cashDisc;
        this.isPP = isPP;
        PicDist = picDist;
        PicCust = picCust;
        Notes = notes;
        Signature = signature;
        PoStatusId = poStatusId;
        PoStatusName = poStatusName;
        CreatedDate = createdDate;
        CreatedBy=createdBy;
        StatusSend = statusSend;
        SoNo = soNo;
        SoDate = soDate;
        DoNo = doNo;
        DoDate = doDate;
        ConfirmDate = confirmDate;
        ModifiedDate = modifiedDate;
        ModifiedBy = modifiedBy;
        this.poLines = poLines;
        this.poLineOthers = poLineOthers;
        customer=cust;
        distBranch=dist;
        this.isSellOut=isSellOut;
    }

    public String getPoId() {
        return PoId;
    }

    public void setPoId(String poId) {
        PoId = poId;
    }

    public String getPoCustNumberRef() {
        return PoCustNumberRef;
    }

    public void setPoCustNumberRef(String poCustNumberRef) {
        PoCustNumberRef = poCustNumberRef;
    }

    public String getPoDate() {
        return PoDate;
    }

    public void setPoDate(String poDate) {
        PoDate = poDate;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public int getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        SalesmanId = salesmanId;
    }

    public String getCallPlanId() {
        return CallPlanId;
    }

    public void setCallPlanId(String callPlanId) {
        CallPlanId = callPlanId;
    }

    public String getPoById() {
        return PoById;
    }

    public void setPoById(String poById) {
        PoById = poById;
    }

    public String getPoViaId() {
        return PoViaId;
    }

    public void setPoViaId(String poViaId) {
        PoViaId = poViaId;
    }

    public String getShipDate() {
        return ShipDate;
    }

    public void setShipDate(String shipDate) {
        ShipDate = shipDate;
    }

    public String getEndPeriodeDate() {
        return EndPeriodeDate;
    }

    public void setEndPeriodeDate(String endPeriodeDate) {
        EndPeriodeDate = endPeriodeDate;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public void setShipAddress(String shipAddress) {
        ShipAddress = shipAddress;
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

    public double getCashDisc() {
        return CashDisc;
    }

    public void setCashDisc(double cashDisc) {
        CashDisc = cashDisc;
    }

    public String getPicDist() {
        return PicDist;
    }

    public void setPicDist(String picDist) {
        PicDist = picDist;
    }

    public String getPicCust() {
        return PicCust;
    }

    public void setPicCust(String picCust) {
        PicCust = picCust;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public int getPoStatusId() {
        return PoStatusId;
    }

    public void setPoStatusId(int poStatusId) {
        PoStatusId = poStatusId;
    }

    public String getSoNo() {
        return SoNo;
    }

    public void setSoNo(String soNo) {
        SoNo = soNo;
    }

    public String getSoDate() {
        return SoDate;
    }

    public void setSoDate(String soDate) {
        SoDate = soDate;
    }

    public String getDoNo() {
        return DoNo;
    }

    public void setDoNo(String doNo) {
        DoNo = doNo;
    }

    public String getDoDate() {
        return DoDate;
    }

    public void setDoDate(String doDate) {
        DoDate = doDate;
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

    public boolean isPP() {
        return isPP;
    }

    public void setPP(boolean PP) {
        isPP = PP;
    }

    public ArrayList<mPoLine> getPoLines() {
        return poLines;
    }

    public void setPoLines(ArrayList<mPoLine> poLines) {
        this.poLines = poLines;
    }

    public ArrayList<mPoLineOther> getPoLineOthers() {
        return poLineOthers;
    }

    public void setPoLineOthers(ArrayList<mPoLineOther> poLineOthers) {
        this.poLineOthers = poLineOthers;
    }

    public String getPoStatusName() {
        return PoStatusName;
    }

    public void setPoStatusName(String poStatusName) {
        PoStatusName = poStatusName;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public int getDistBranchId() {
        return DistBranchId;
    }

    public void setDistBranchId(int distBranchId) {
        DistBranchId = distBranchId;
    }


    public mCustomerAndDistBranch getDistBranch() {
        return distBranch;
    }

    public void setDistBranch(mCustomerAndDistBranch distBranch) {
        this.distBranch = distBranch;
    }

    public mCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(mCustomer customer) {
        this.customer = customer;
    }

    public String getMechanisme() {
        return Mechanisme;
    }

    public void setMechanisme(String mechanisme) {
        Mechanisme = mechanisme;
    }

    public String getKeteranganDetail() {
        return KeteranganDetail;
    }

    public void setKeteranganDetail(String keteranganDetail) {
        KeteranganDetail = keteranganDetail;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSellOut() {
        return isSellOut;
    }

    public void setSellOut(boolean sellOut) {
        isSellOut = sellOut;
    }
}
