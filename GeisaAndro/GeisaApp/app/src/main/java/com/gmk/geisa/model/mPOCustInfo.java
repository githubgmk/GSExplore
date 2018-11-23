package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 6/20/17.
 */

public class mPOCustInfo implements Serializable {
    private String customerId;
    private String salesId;
    private int typepo;
    private String povia;
    private String postatus;
    private String shipmentdate;
    private String endperiode;
    private String picdist;
    private String pengirim;
    private String shipmentaddress;
    private String notes;
    private String mechanisme;
    private boolean isError;

    public mPOCustInfo() {
    }

    public mPOCustInfo(String customerId, String salesId, int typepo, String povia, String postatus, String shipmentdate, String endperiode, String mechanisme, String picdist, String pengirim, String shipmentaddress, String notes) {
        this.customerId = customerId;
        this.salesId = salesId;
        this.typepo=typepo;
        this.povia = povia;
        this.postatus = postatus;
        this.shipmentdate = shipmentdate;
        this.endperiode = endperiode;
        this.mechanisme=mechanisme;
        this.picdist = picdist;
        this.pengirim = pengirim;
        this.shipmentaddress = shipmentaddress;
        this.notes = notes;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getTypepo() {
        return typepo;
    }

    public void setTypepo(int typepo) {
        this.typepo = typepo;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getPovia() {
        return povia;
    }

    public void setPovia(String povia) {
        this.povia = povia;
    }

    public String getPostatus() {
        return postatus;
    }

    public void setPostatus(String postatus) {
        this.postatus = postatus;
    }

    public String getShipmentdate() {
        return shipmentdate;
    }

    public void setShipmentdate(String shipmentdate) {
        this.shipmentdate = shipmentdate;
    }

    public String getEndperiode() {
        return endperiode;
    }

    public void setEndperiode(String endperiode) {
        this.endperiode = endperiode;
    }

    public String getMechanisme() {
        return mechanisme;
    }

    public void setMechanisme(String mechanisme) {
        this.mechanisme = mechanisme;
    }

    public String getPicdist() {
        return picdist;
    }

    public void setPicdist(String picdist) {
        this.picdist = picdist;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getShipmentaddress() {
        return shipmentaddress;
    }

    public void setShipmentaddress(String shipmentaddress) {
        this.shipmentaddress = shipmentaddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
