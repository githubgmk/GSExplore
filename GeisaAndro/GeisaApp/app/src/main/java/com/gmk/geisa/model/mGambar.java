package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjin on 8/14/2016.
 */
public class mGambar implements Serializable {
    private String  idGambar;
    private String pathGambar;
    private  String namaGambar;
    private  String foreignKey;
    private  String waktuAmbil;
    private int statusSend;
    private String createDate;

    public mGambar() {
    }

    public mGambar(String idGambar, String pathGambar, String namaGambar, String foreignKey,String waktuAmbil, int statusSend,String createDate) {
        this.idGambar = idGambar;
        this.pathGambar = pathGambar;
        this.namaGambar = namaGambar;
        this.foreignKey = foreignKey;
        this.waktuAmbil=waktuAmbil;
        this.statusSend = statusSend;
        this.createDate = createDate;
    }

    public String getIdGambar() {
        return idGambar;
    }

    public void setIdGambar(String idGambar) {
        this.idGambar = idGambar;
    }

    public String getPathGambar() {
        return pathGambar;
    }

    public void setPathGambar(String pathGambar) {
        this.pathGambar = pathGambar;
    }

    public String getNamaGambar() {
        return namaGambar;
    }

    public void setNamaGambar(String namaGambar) {
        this.namaGambar = namaGambar;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public int getStatusSend() {
        return statusSend;
    }

    public void setStatusSend(int statusSend) {
        this.statusSend = statusSend;
    }

    public String getWaktuAmbil() {
        return waktuAmbil;
    }

    public void setWaktuAmbil(String waktuAmbil) {
        this.waktuAmbil = waktuAmbil;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
