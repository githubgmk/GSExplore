package com.gmk.geisa.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjinsan on 4/22/17.
 */

public class mDistributorBranch implements Serializable {
    private int DistBranchId;
    private String DistBranchName;
    private String AreaCode;
    private String AreaName;
    private String Address;
    private String Pic;
    private String Telp;
    private String Email;
    private int DistId;
    private int AreaId;
    private int StatusId;
    private mDistributor distributor;
    private ArrayList<mCustomerAndDistBranch> customerAndDistributorBranches;

    public mDistributorBranch() {
    }

    public mDistributorBranch(int distBranchId, String distBranchName, String areaCode, String areaName, String address, String pic, String telp, String email, int distId, int areaId, int statusId) {
        DistBranchId = distBranchId;
        DistBranchName = distBranchName;
        AreaCode = areaCode;
        AreaName = areaName;
        Address = address;
        Pic = pic;
        Telp = telp;
        Email = email;
        DistId = distId;
        AreaId = areaId;
        StatusId = statusId;
    }

    public mDistributorBranch(int distBranchId, String distBranchName, String areaCode, String areaName, String address, String pic, String telp, String email, int distId, int areaId, int statusId, mDistributor distributor, ArrayList<mCustomerAndDistBranch> customerAndDistributorBranches) {
        DistBranchId = distBranchId;
        DistBranchName = distBranchName;
        AreaCode = areaCode;
        AreaName = areaName;
        Address = address;
        Pic = pic;
        Telp = telp;
        Email = email;
        DistId = distId;
        AreaId = areaId;
        StatusId = statusId;
        this.distributor = distributor;
        this.customerAndDistributorBranches = customerAndDistributorBranches;
    }

    public int getDistBranchId() {
        return DistBranchId;
    }

    public void setDistBranchId(int distBranchId) {
        DistBranchId = distBranchId;
    }

    public String getDistBranchName() {
        return DistBranchName;
    }

    public void setDistBranchName(String distBranchName) {
        DistBranchName = distBranchName;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getTelp() {
        return Telp;
    }

    public void setTelp(String telp) {
        Telp = telp;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getDistId() {
        return DistId;
    }

    public void setDistId(int distId) {
        DistId = distId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public mDistributor getDistributor() {
        return distributor;
    }

    public void setDistributor(mDistributor distributor) {
        this.distributor = distributor;
    }

    public ArrayList<mCustomerAndDistBranch> getCustomerAndDistributorBranches() {
        return customerAndDistributorBranches;
    }

    public void setCustomerAndDistributorBranches(ArrayList<mCustomerAndDistBranch> customerAndDistributorBranches) {
        this.customerAndDistributorBranches = customerAndDistributorBranches;
    }
}
