package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by kenjin on 6/29/2016.
 */
public class mSession  implements Serializable {
    @SerializedName("SalesmanId")
    private int id;
    private String nama;
    private  int status;
    private String nilai1;
    private String nilai2;
    private String nilai3;
    private String nilai4;
    private String nilai5;
    private String nilai6;
    private String nilai7;
    private String nilai8;
    private String nilai9;
   // @SerializedName("user_name")
   @SerializedName("UserName")
    private String userName ;
    @SerializedName("UserPass")
    private String userPass ;
    @SerializedName("StatusId")
    private String userStatus ;
    @SerializedName("SpvId")
    private String userSPV;
    @SerializedName("SalesmanLevelId")
    private String userLevel ;
    @SerializedName("AreaId")
    private String areaId ;
    @SerializedName("Email")
    private String userEmail ;
    @SerializedName("mac_address_device")
    private String userMacAddress ;
    @SerializedName("SalesmanName")
    private String userAlias ;
    @SerializedName("picture")
    private byte[] picture;
    private int statusChange;
    @SerializedName("FcmId")
    private  String fcmid;
    @SerializedName("Imei")
    private  String imei;
    @SerializedName("RecIdTab")
    private  String recId;
    private String oldPass ;


    /*@JsonCreator
    public mSession(@JsonProperty("user_id")int id, @JsonProperty("user_name")String cust,
                    @JsonProperty("user_pass")String user_pass, @JsonProperty("user_status")String user_status,
                    @JsonProperty("spv_sales_id")String spv_sales_id, @JsonProperty("level")String level,
                    @JsonProperty("area_id")String area_id, @JsonProperty("area_type")String area_type,
                    @JsonProperty("teritory_id")String teritory_id, @JsonProperty("user_alias")String user_alias,
                    @JsonProperty("user_email")String user_email, @JsonProperty("aktif")String aktif) {
        this.id = id;
        this.userName = cust;
        this.userStatus = user_status;
        this.userPass = user_pass;

    }*/
    public mSession(int id, String nama,int status, String nilai1, String nilai2, String nilai3,
                    String nilai4, String nilai5, String nilai6, String nilai7, String nilai8, String nilai9) {
        this.id = id;
        this.nama = nama;
        this.status=status;
        this.nilai1 = nilai1;
        this.nilai2 = nilai2;
        this.nilai3 = nilai3;
        this.nilai4 = nilai4;
        this.nilai5 = nilai5;
        this.nilai6 = nilai6;
        this.nilai7 = nilai7;
        this.nilai8 = nilai8;
        this.nilai9 = nilai9;
    }
    public mSession(int id, String userName, String userPass, String userStatus, String userSPV, String userLevel,
                    String areaId,  String userEmail, String userMacAddress,String userAlias,byte[] picture,String fcmid,String imei,String recId, int statusChange) {
        this.id = id;
        this.userName = userName;
        this.userPass = userPass;
        this.userStatus = userStatus;
        this.userSPV = userSPV;
        this.userLevel = userLevel;
        this.areaId = areaId;
        this.userEmail = userEmail;
        this.userMacAddress = userMacAddress;
        this.userAlias = userAlias;
        this.picture=picture;
        this.fcmid=fcmid;
        this.imei=imei;
        this.recId=recId;
        this.statusChange=statusChange;
    }

    public mSession(int id, String userName, String userPass, String userStatus, String userSPV, String userLevel,
                    String areaId,  String userEmail, String userAlias,String fcmid,String imei,String recId) {
        this.id = id;
        this.userName = userName;
        this.userPass = userPass;
        this.userStatus = userStatus;
        this.userSPV = userSPV;
        this.userLevel = userLevel;
        this.areaId = areaId;
        this.userEmail = userEmail;
        this.userAlias = userAlias;
        this.fcmid=fcmid;
        this.imei=imei;
        this.recId=recId;
    }
    public mSession(int id, String userName, String userPass, String userStatus, String userSPV, String userLevel,
                    String areaId,  String userEmail, String userMacAddress,String userAlias,byte[] picture,String fcmid,
                    String imei,String recId,int statusChange,
                    String nama,int status, String nilai1, String nilai2, String nilai3, String nilai4,
                    String nilai5, String nilai6, String nilai7, String nilai8, String nilai9) {
        this.id = id;
        this.userName = userName;
        this.userPass = userPass;
        this.userStatus = userStatus;
        this.userSPV = userSPV;
        this.userLevel = userLevel;
        this.areaId = areaId;
        this.userEmail = userEmail;
        this.userMacAddress = userMacAddress;
        this.userAlias = userAlias;
        this.picture=picture;
        this.fcmid=fcmid;
        this.imei=imei;
        this.recId=recId;
        this.statusChange=statusChange;
        this.nama = nama;
        this.status=status;
        this.nilai1 = nilai1;
        this.nilai2 = nilai2;
        this.nilai3 = nilai3;
        this.nilai4 = nilai4;
        this.nilai5 = nilai5;
        this.nilai6 = nilai6;
        this.nilai7 = nilai7;
        this.nilai8 = nilai8;
        this.nilai9 = nilai9;
    }
    public mSession(int id, String nama,int status, String nilai1, String nilai2, String nilai3, String nilai4) {
        this.id = id;
        this.nama = nama;
        this.status=status;
        this.nilai1 = nilai1;
        this.nilai2 = nilai2;
        this.nilai3 = nilai3;
        this.nilai4 = nilai4;
    }

    public mSession() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNilai1() {
        return nilai1;
    }

    public void setNilai1(String nilai1) {
        this.nilai1 = nilai1;
    }

    public String getNilai2() {
        return nilai2;
    }

    public void setNilai2(String nilai2) {
        this.nilai2 = nilai2;
    }

    public String getNilai3() {
        return nilai3;
    }

    public void setNilai3(String nilai3) {
        this.nilai3 = nilai3;
    }

    public String getNilai4() {
        return nilai4;
    }

    public void setNilai4(String nilai4) {
        this.nilai4 = nilai4;
    }

    public String getNilai5() {
        return nilai5;
    }

    public void setNilai5(String nilai5) {
        this.nilai5 = nilai5;
    }

    public String getNilai6() {
        return nilai6;
    }

    public void setNilai6(String nilai6) {
        this.nilai6 = nilai6;
    }

    public String getNilai7() {
        return nilai7;
    }

    public void setNilai7(String nilai7) {
        this.nilai7 = nilai7;
    }

    public String getNilai8() {
        return nilai8;
    }

    public void setNilai8(String nilai8) {
        this.nilai8 = nilai8;
    }

    public String getNilai9() {
        return nilai9;
    }

    public void setNilai9(String nilai9) {
        this.nilai9 = nilai9;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserSPV() {
        return userSPV;
    }

    public void setUserSPV(String userSPV) {
        this.userSPV = userSPV;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMacAddress() {
        return userMacAddress;
    }

    public void setUserMacAddress(String userMacAddress) {
        this.userMacAddress = userMacAddress;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getStatusChange() {
        return statusChange;
    }

    public void setStatusChange(int statusChange) {
        this.statusChange = statusChange;
    }

    public String getFcmid() {
        return fcmid;
    }

    public void setFcmid(String fcmid) {
        this.fcmid = fcmid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }
}
