package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/7/17.
 */

public class mProdukComplain implements Serializable {
    private String ProductComplainId;
    private String ComplainId;
    private String ItemId;
    private String BatchNumber;
    private String QtyComplain;
    private String QtyComplainSatuan;
    private String QtyPembelian;
    private String QtyPembelianSatuan;
    private String CategoryComplain;
    private String CategoryComplainId;
    private int StatusSend;

    public mProdukComplain() {
    }

    public mProdukComplain(String productComplainId, String complainId, String itemId, String batchNumber, String qtyComplain, String qtyComplainSatuan, String qtyPembelian, String qtyPembelianSatuan, String categoryComplain, String categoryComplainId,int statusSend) {
        ProductComplainId = productComplainId;
        ComplainId=complainId;
        ItemId=itemId;
        BatchNumber = batchNumber;
        QtyComplain = qtyComplain;
        QtyComplainSatuan = qtyComplainSatuan;
        QtyPembelian = qtyPembelian;
        QtyPembelianSatuan = qtyPembelianSatuan;
        CategoryComplain=categoryComplain;
        CategoryComplainId=categoryComplainId;
        StatusSend=statusSend;

    }

    public String getProductComplainId() {
        return ProductComplainId;
    }

    public void setProductComplainId(String productComplainId) {
        this.ProductComplainId = productComplainId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getBatchNumber() {
        return BatchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }

    public String getQtyComplain() {
        return QtyComplain;
    }

    public void setQtyComplain(String qtyComplain) {
        QtyComplain = qtyComplain;
    }

    public String getQtyComplainSatuan() {
        return QtyComplainSatuan;
    }

    public void setQtyComplainSatuan(String qtyComplainSatuan) {
        QtyComplainSatuan = qtyComplainSatuan;
    }

    public String getQtyPembelian() {
        return QtyPembelian;
    }

    public void setQtyPembelian(String qtyPembelian) {
        QtyPembelian = qtyPembelian;
    }

    public String getQtyPembelianSatuan() {
        return QtyPembelianSatuan;
    }

    public void setQtyPembelianSatuan(String qtyPembelianSatuan) {
        QtyPembelianSatuan = qtyPembelianSatuan;
    }

    public String getComplainId() {
        return ComplainId;
    }

    public void setComplainId(String complainId) {
        ComplainId = complainId;
    }

    public String getCategoryComplain() {
        return CategoryComplain;
    }

    public void setCategoryComplain(String categoryComplain) {
        CategoryComplain = categoryComplain;
    }

    public String getCategoryComplainId() {
        return CategoryComplainId;
    }

    public void setCategoryComplainId(String categoryComplainId) {
        CategoryComplainId = categoryComplainId;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        StatusSend = statusSend;
    }
}
