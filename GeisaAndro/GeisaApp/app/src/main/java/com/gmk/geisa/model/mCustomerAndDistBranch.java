package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kenjinsan on 4/23/17.
 */

public class mCustomerAndDistBranch implements Serializable {

    private int CustomerDistBranchId;
    private int DistBranchId;
    private int CustId;
    private String CustCode;
    private String PriceGroupId;
    private String PriceGroupName;
    private String DiscGroupId;
    private String DiscGroupName;
    private String CustIdAndro;

    mDistributorBranch distributorBranch;
    private String DistBranchName;
    private String DistBranchAreaCode;
    private String DistBranchAreaName;
    private String DistBranchAddress;
    private String DistBranchPic;
    private String DistBranchTelp;
    private String DistBranchEmail;
    private int DistId;
    private int DistBranchAreaId;
    private int DistBranchStatusId;

    private String DistDistName;
    private String DistAddress;
    private String DistPic;
    private String DistTelp;
    private String DistEmail;


    private  int StatusSend;

    public mCustomerAndDistBranch() {
    }

    public mCustomerAndDistBranch(int customerDistBranchId, int distBranchId, int custId, String custCode, String priceGroupId, String priceGroupName, String discGroupId, String discGroupName, String custIdAndro, mDistributorBranch distributor) {
        CustomerDistBranchId = customerDistBranchId;
        DistBranchId = distBranchId;
        CustId = custId;
        CustCode = custCode;
        PriceGroupId = priceGroupId;
        PriceGroupName = priceGroupName;
        DiscGroupId = discGroupId;
        DiscGroupName = discGroupName;
        CustIdAndro = custIdAndro;
        this.distributorBranch = distributor;
    }

    public mCustomerAndDistBranch(int customerDistBranchId, int distBranchId, int custId, String custCode, String priceGroupId, String priceGroupName, String discGroupId, String discGroupName, String custIdAndro) {
        CustomerDistBranchId = customerDistBranchId;
        DistBranchId = distBranchId;
        CustId = custId;
        CustCode = custCode;
        PriceGroupId = priceGroupId;
        PriceGroupName = priceGroupName;
        DiscGroupId = discGroupId;
        DiscGroupName = discGroupName;
        CustIdAndro = custIdAndro;
    }

    public mCustomerAndDistBranch(int customerDistBranchId, int distBranchId, int custId, String priceGroupId, String discGroupId) {
        CustomerDistBranchId = customerDistBranchId;
        DistBranchId = distBranchId;
        CustId = custId;
        PriceGroupId = priceGroupId;
        DiscGroupId = discGroupId;
    }

    public mCustomerAndDistBranch(int customerDistBranchId, int distBranchId, int custId, String custCode,
                                  String priceGroupId, String priceGroupName, String discGroupId, String discGroupName,
                                  String custIdAndro, String distBranchName, String areaCode, String areaName,
                                  String address, String pic, String telp, String email, int distId, int areaId,
                                  int statusId, String distName, String distAddress, String distPic, String distTelp, String distEmail) {
        CustomerDistBranchId = customerDistBranchId;
        DistBranchId = distBranchId;
        CustId = custId;
        CustCode = custCode;
        PriceGroupId = priceGroupId;
        PriceGroupName = priceGroupName;
        DiscGroupId = discGroupId;
        DiscGroupName = discGroupName;
        CustIdAndro = custIdAndro;
        DistBranchName = distBranchName;
        DistBranchAreaCode = areaCode;
        DistBranchAreaName = areaName;
        DistBranchAddress = address;
        DistBranchPic = pic;
        DistBranchTelp = telp;
        DistBranchEmail = email;
        DistId = distId;
        DistBranchAreaId = areaId;
        DistBranchStatusId = statusId;
        DistDistName=distName;
        DistAddress=distAddress;
        DistPic=distPic;
        DistTelp=distTelp;
        DistEmail=distEmail;
    }

    public int getCustomerDistBranchId() {
        return CustomerDistBranchId;
    }

    public void setCustomerDistBranchId(int customerDistBranchId) {
        CustomerDistBranchId = customerDistBranchId;
    }

    public int getDistBranchId() {
        return DistBranchId;
    }

    public void setDistBranchId(int distBranchId) {
        DistBranchId = distBranchId;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public String getCustCode() {
        return CustCode;
    }

    public void setCustCode(String custCode) {
        CustCode = custCode;
    }

    public String getPriceGroupId() {
        return PriceGroupId;
    }

    public void setPriceGroupId(String priceGroupId) {
        PriceGroupId = priceGroupId;
    }

    public String getPriceGroupName() {
        return PriceGroupName;
    }

    public void setPriceGroupName(String priceGroupName) {
        PriceGroupName = priceGroupName;
    }

    public String getDiscGroupId() {
        return DiscGroupId;
    }

    public void setDiscGroupId(String discGroupId) {
        DiscGroupId = discGroupId;
    }

    public String getDiscGroupName() {
        return DiscGroupName;
    }

    public void setDiscGroupName(String discGroupName) {
        DiscGroupName = discGroupName;
    }

    public String getCustIdAndro() {
        return CustIdAndro;
    }

    public void setCustIdAndro(String custIdAndro) {
        CustIdAndro = custIdAndro;
    }

    public mDistributorBranch getDistributor() {
        return distributorBranch;
    }

    public void setDistributor(mDistributorBranch distributor) {
        this.distributorBranch = distributor;
    }

    public mDistributorBranch getDistributorBranch() {
        return distributorBranch;
    }

    public void setDistributorBranch(mDistributorBranch distributorBranch) {
        this.distributorBranch = distributorBranch;
    }

    public String getDistBranchName() {
        return DistBranchName;
    }

    public void setDistBranchName(String distBranchName) {
        DistBranchName = distBranchName;
    }

    public String getDistBranchAreaCode() {
        return DistBranchAreaCode;
    }

    public void setDistBranchAreaCode(String distBranchAreaCode) {
        DistBranchAreaCode = distBranchAreaCode;
    }

    public String getDistBranchAreaName() {
        return DistBranchAreaName;
    }

    public void setDistBranchAreaName(String distBranchAreaName) {
        DistBranchAreaName = distBranchAreaName;
    }

    public String getDistBranchAddress() {
        return DistBranchAddress;
    }

    public void setDistBranchAddress(String distBranchAddress) {
        DistBranchAddress = distBranchAddress;
    }

    public String getDistBranchPic() {
        return DistBranchPic;
    }

    public void setDistBranchPic(String distBranchPic) {
        DistBranchPic = distBranchPic;
    }

    public String getDistBranchTelp() {
        return DistBranchTelp;
    }

    public void setDistBranchTelp(String distBranchTelp) {
        DistBranchTelp = distBranchTelp;
    }

    public String getDistBranchEmail() {
        return DistBranchEmail;
    }

    public void setDistBranchEmail(String distBranchEmail) {
        DistBranchEmail = distBranchEmail;
    }



    public int getDistBranchAreaId() {
        return DistBranchAreaId;
    }

    public void setDistBranchAreaId(int distBranchAreaId) {
        DistBranchAreaId = distBranchAreaId;
    }

    public int getDistBranchStatusId() {
        return DistBranchStatusId;
    }

    public void setDistBranchStatusId(int distBranchStatusId) {
        DistBranchStatusId = distBranchStatusId;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        StatusSend = statusSend;
    }

    public int getDistId() {
        return DistId;
    }

    public void setDistId(int distId) {
        DistId = distId;
    }

    public String getDistDistName() {
        return DistDistName;
    }

    public void setDistDistName(String distDistName) {
        DistDistName = distDistName;
    }

    public String getDistAddress() {
        return DistAddress;
    }

    public void setDistAddress(String distAddress) {
        DistAddress = distAddress;
    }

    public String getDistPic() {
        return DistPic;
    }

    public void setDistPic(String distPic) {
        DistPic = distPic;
    }

    public String getDistTelp() {
        return DistTelp;
    }

    public void setDistTelp(String distTelp) {
        DistTelp = distTelp;
    }

    public String getDistEmail() {
        return DistEmail;
    }

    public void setDistEmail(String distEmail) {
        DistEmail = distEmail;
    }
}
