package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjin on 2/5/2016.
 */
public class mCustomer implements Serializable {

    @SerializedName("_Id")
    private int _Id;
    @SerializedName("CustId")
    private int CustId;

    @SerializedName("CustGroupId")
    private String CustGroupId;
    @SerializedName("CustName")
    private String CustName;
    @SerializedName("AliasName")
    private String AliasName;
    @SerializedName("Address")
    private String Address;
    @SerializedName("Lat")
    private double Lat;
    @SerializedName("Lng")
    private double Lng;
    @SerializedName("Radius")
    private double Radius;
    @SerializedName("Pic")
    private String Pic;
    @SerializedName("PicJabatan")
    private String PicJabatan;
    @SerializedName("Telp")
    private String Telp;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Hp")
    private String Hp;
    @SerializedName("Website")
    private String Website;
    @SerializedName("Npwp")
    private String Npwp;
    @SerializedName("CreditLimit")
    private String CreditLimit;
    @SerializedName("Top")
    private String Top;
    @SerializedName("JoinDate")
    private String JoinDate;
    @SerializedName("EcTolerance")
    private int EcTolerance;
    @SerializedName("SalesmanId")
    private int SalesmanId;
    @SerializedName("SalesmanName")
    private String SalesmanName;
    @SerializedName("CustomerDistBranchId")
    private int CustomerDistBranchId;
    @SerializedName("AreaId")
    private int AreaId;
    @SerializedName("AreaCode")
    private String AreaCode;
    @SerializedName("AreaName")
    private String AreaName;
    @SerializedName("StsPkpId")
    private String StsPkpId;
    @SerializedName("StsPkpName")
    private String StsPkpName;
    @SerializedName("CustById")
    private String CustById;
    @SerializedName("CustByName")
    private String CustByName;
    @SerializedName("FreqTypeId")
    private int FreqTypeId;
    @SerializedName("FreqTypeName")
    private String FreqTypeName;
    @SerializedName("ChannelId")
    private int ChannelId;
    @SerializedName("ChannelName")
    private String ChannelName;
    @SerializedName("CustLevelId")
    private int CustLevelId;
    @SerializedName("CustLevelName")
    private String CustLevelName;
    @SerializedName("CustZoneId")
    private int CustZoneId;
    @SerializedName("CustZoneName")
    private String CustZoneName;
    @SerializedName("CustStatusId")
    private int CustStatusId;
    @SerializedName("CustStatusName")
    private String CustStatusName;
    @SerializedName("CustIdAndro")
    private String CustIdAndro;
    private int StatusSend;
    private boolean selected;

    private  int NewOutlet;
    //include other model
    private ArrayList<mCustomerAndDistBranch> customerAndBranch;

    public mCustomer() {
    }

    public mCustomer(int _Id, int custId, String custGroupId, String custName, String aliasName, String address,
                     double lat, double lng, double radius, String pic, String picJabatan, String telp, String email,
                     String hp, String website, String npwp, String creditLimit, String top, String joinDate, int ecTolerance,
                     int salesmanId, String salesmanName, int areaId, String areaCode, String areaName, String stsPkpId, String stsPkpName,
                     String custById, String custByName, int freqTypeId, String freqTypeName, int channelId,
                     String channelName, int custLevelId, String custLevelName, int custZoneId, String custZoneName,
                     int custStatusId, String custStatusName, String custIdAndro, int statusSend,int newOutlet,
                     ArrayList<mCustomerAndDistBranch> customerAndBranch) {
        this._Id = _Id;
        CustId = custId;
        CustGroupId = custGroupId;
        CustName = custName;
        AliasName = aliasName;
        Address = address;
        Lat = lat;
        Lng = lng;
        Radius = radius;
        Pic = pic;
        PicJabatan = picJabatan;
        Telp = telp;
        Email = email;
        Hp = hp;
        Website = website;
        Npwp = npwp;
        CreditLimit = creditLimit;
        Top = top;
        JoinDate = joinDate;
        EcTolerance = ecTolerance;
        SalesmanId = salesmanId;
        SalesmanName = salesmanName;
        AreaId = areaId;
        AreaCode = areaCode;
        AreaName = areaName;
        StsPkpId = stsPkpId;
        StsPkpName = stsPkpName;
        CustById = custById;
        CustByName = custByName;
        FreqTypeId = freqTypeId;
        FreqTypeName = freqTypeName;
        ChannelId = channelId;
        ChannelName = channelName;
        CustLevelId = custLevelId;
        CustLevelName = custLevelName;
        CustZoneId = custZoneId;
        CustZoneName = custZoneName;
        CustStatusId = custStatusId;
        CustStatusName = custStatusName;
        CustIdAndro = custIdAndro;
        StatusSend = statusSend;
        this.customerAndBranch = customerAndBranch;
    }

    public mCustomer(int custId, String custGroupId, String custName, String aliasName, String address, double lat, double lng,
                     double radius, String pic, String picJabatan, String telp, String email, String hp, String website,
                     String npwp, String creditLimit, String top, String joinDate, int ecTolerance, int salesmanId,
                     int customerDistBranchId, int areaId, String stsPkpId, String custById, int freqTypeId,
                     int channelId, int custLevelId, int custZoneId, int custStatusId, String custIdAndro, int statusSend) {
        CustId = custId;
        CustGroupId = custGroupId;
        CustName = custName;
        AliasName = aliasName;
        Address = address;
        Lat = lat;
        Lng = lng;
        Radius = radius;
        Pic = pic;
        PicJabatan = picJabatan;
        Telp = telp;
        Email = email;
        Hp = hp;
        Website = website;
        Npwp = npwp;
        CreditLimit = creditLimit;
        Top = top;
        JoinDate = joinDate;
        EcTolerance = ecTolerance;
        SalesmanId = salesmanId;
        CustomerDistBranchId = customerDistBranchId;
        AreaId = areaId;
        StsPkpId = stsPkpId;
        CustById = custById;
        FreqTypeId = freqTypeId;
        ChannelId = channelId;
        CustLevelId = custLevelId;
        CustZoneId = custZoneId;
        CustStatusId = custStatusId;
        CustIdAndro = custIdAndro;
        StatusSend = statusSend;
    }

    public String getSalesmanName() {
        return SalesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        SalesmanName = salesmanName;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        StatusSend = statusSend;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public String getCustGroupId() {
        return CustGroupId;
    }

    public void setCustGroupId(String custGroupId) {
        CustGroupId = custGroupId;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getAliasName() {
        return AliasName;
    }

    public void setAliasName(String aliasName) {
        AliasName = aliasName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public double getRadius() {
        return Radius;
    }

    public void setRadius(double radius) {
        Radius = radius;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getPicJabatan() {
        return PicJabatan;
    }

    public void setPicJabatan(String picJabatan) {
        PicJabatan = picJabatan;
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

    public String getHp() {
        return Hp;
    }

    public void setHp(String hp) {
        Hp = hp;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getNpwp() {
        return Npwp;
    }

    public void setNpwp(String npwp) {
        Npwp = npwp;
    }

    public String getCreditLimit() {
        return CreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        CreditLimit = creditLimit;
    }

    public String getTop() {
        return Top;
    }

    public void setTop(String top) {
        Top = top;
    }

    public String getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(String joinDate) {
        JoinDate = joinDate;
    }

    public int getEcTolerance() {
        return EcTolerance;
    }

    public void setEcTolerance(int ecTolerance) {
        EcTolerance = ecTolerance;
    }

    public int getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        SalesmanId = salesmanId;
    }

    public int getCustomerDistBranchId() {
        return CustomerDistBranchId;
    }

    public void setCustomerDistBranchId(int customerDistBranchId) {
        CustomerDistBranchId = customerDistBranchId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
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

    public String getStsPkpId() {
        return StsPkpId;
    }

    public void setStsPkpId(String stsPkpId) {
        StsPkpId = stsPkpId;
    }

    public String getStsPkpName() {
        return StsPkpName;
    }

    public void setStsPkpName(String stsPkpName) {
        StsPkpName = stsPkpName;
    }

    public String getCustById() {
        return CustById;
    }

    public void setCustById(String custById) {
        CustById = custById;
    }

    public String getCustByName() {
        return CustByName;
    }

    public void setCustByName(String custByName) {
        CustByName = custByName;
    }

    public int getFreqTypeId() {
        return FreqTypeId;
    }

    public void setFreqTypeId(int freqTypeId) {
        FreqTypeId = freqTypeId;
    }

    public String getFreqTypeName() {
        return FreqTypeName;
    }

    public void setFreqTypeName(String freqTypeName) {
        FreqTypeName = freqTypeName;
    }

    public int getChannelId() {
        return ChannelId;
    }

    public void setChannelId(int channelId) {
        ChannelId = channelId;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public int getCustStatusId() {
        return CustStatusId;
    }

    public void setCustStatusId(int custStatusId) {
        CustStatusId = custStatusId;
    }

    public String getCustStatusName() {
        return CustStatusName;
    }

    public void setCustStatusName(String custStatusName) {
        CustStatusName = custStatusName;
    }

    public String getCustIdAndro() {
        return CustIdAndro;
    }

    public void setCustIdAndro(String custIdAndro) {
        CustIdAndro = custIdAndro;
    }

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }

    public int getCustLevelId() {
        return CustLevelId;
    }

    public void setCustLevelId(int custLevelId) {
        CustLevelId = custLevelId;
    }

    public String getCustLevelName() {
        return CustLevelName;
    }

    public void setCustLevelName(String custLevelName) {
        CustLevelName = custLevelName;
    }

    public int getCustZoneId() {
        return CustZoneId;
    }

    public void setCustZoneId(int custZoneId) {
        CustZoneId = custZoneId;
    }

    public String getCustZoneName() {
        return CustZoneName;
    }

    public void setCustZoneName(String custZoneName) {
        CustZoneName = custZoneName;
    }

    public ArrayList<mCustomerAndDistBranch> getCustomerAndBranch() {
        return customerAndBranch;
    }

    public void setCustomerAndBranch(ArrayList<mCustomerAndDistBranch> customerAndBranch) {
        this.customerAndBranch = customerAndBranch;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNewOutlet() {
        return NewOutlet;
    }

    public void setNewOutlet(int newOutlet) {
        NewOutlet = newOutlet;
    }
}
