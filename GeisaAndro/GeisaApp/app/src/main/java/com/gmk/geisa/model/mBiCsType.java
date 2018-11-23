package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/5/17.
 */

public class mBiCsType implements Serializable {
    int BiCsTypeId;
    String BiCsTypeName;
    String BiCsTypeEmail;
    String BiCsTypeJenis;
    int BiCsTypeStatus;

    public mBiCsType() {
    }

    public mBiCsType(int biCsTypeId, String biCsTypeName, String biCsTypeEmail, String biCsTypeJenis, int biCsTypeStatus) {
        BiCsTypeId = biCsTypeId;
        BiCsTypeName = biCsTypeName;
        BiCsTypeEmail = biCsTypeEmail;
        BiCsTypeJenis = biCsTypeJenis;
        BiCsTypeStatus = biCsTypeStatus;
    }

    public int getBiCsTypeId() {
        return BiCsTypeId;
    }

    public void setBiCsTypeId(int biCsTypeId) {
        BiCsTypeId = biCsTypeId;
    }

    public String getBiCsTypeName() {
        return BiCsTypeName;
    }

    public void setBiCsTypeName(String biCsTypeName) {
        BiCsTypeName = biCsTypeName;
    }

    public String getBiCsTypeEmail() {
        return BiCsTypeEmail;
    }

    public void setBiCsTypeEmail(String biCsTypeEmail) {
        BiCsTypeEmail = biCsTypeEmail;
    }

    public String getBiCsTypeJenis() {
        return BiCsTypeJenis;
    }

    public void setBiCsTypeJenis(String biCsTypeJenis) {
        BiCsTypeJenis = biCsTypeJenis;
    }

    public int getBiCsTypeStatus() {
        return BiCsTypeStatus;
    }

    public void setBiCsTypeStatus(int biCsTypeStatus) {
        BiCsTypeStatus = biCsTypeStatus;
    }
}
