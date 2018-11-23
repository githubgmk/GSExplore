package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjin on 1/17/2017.
 */

public class mTracking implements Serializable {
    private long id;
    private String TrackingId;
    private String SalesmanId;
    private String TrackingType;
    private String TrackingDate;
    private String TrackingTime;
    private String TrackingLat;
    private String TrackingLot;
    private String TrackingRef;
    private String TrackingStatus;
    private int StatusSend;
    private String CreateDate;
    private String InfoDevice;

    public mTracking() {
    }

    public mTracking(long id, String trackingId, String SalesmanId, String trackingType, String trackingDate, String trackingTime, String trackingLat, String trackingLot, String trackingRef, String trackingStatus, int statusSend, String createDate,String infoDevice) {
        this.id = id;
        this.TrackingId = trackingId;
        this.SalesmanId = SalesmanId;
        this.TrackingType = trackingType;
        this.TrackingDate = trackingDate;
        this.TrackingTime = trackingTime;
        this.TrackingLat = trackingLat;
        this.TrackingLot = trackingLot;
        this.TrackingRef = trackingRef;
        this.TrackingStatus = trackingStatus;
        this.StatusSend = statusSend;
        this.CreateDate = createDate;
        this.InfoDevice=infoDevice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return TrackingId;
    }

    public void setTrackingId(String trackingId) {
        this.TrackingId = trackingId;
    }

    public String getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.SalesmanId = salesmanId;
    }

    public String getTrackingType() {
        return TrackingType;
    }

    public void setTrackingType(String trackingType) {
        this.TrackingType = trackingType;
    }

    public String getTrackingDate() {
        return TrackingDate;
    }

    public void setTrackingDate(String trackingDate) {
        this.TrackingDate = trackingDate;
    }

    public String getTrackingTime() {
        return TrackingTime;
    }

    public void setTrackingTime(String trackingTime) {
        this.TrackingTime = trackingTime;
    }

    public String getTrackingLat() {
        return TrackingLat;
    }

    public void setTrackingLat(String trackingLat) {
        this.TrackingLat = trackingLat;
    }

    public String getTrackingLot() {
        return TrackingLot;
    }

    public void setTrackingLot(String trackingLot) {
        this.TrackingLot = trackingLot;
    }

    public String getTrackingRef() {
        return TrackingRef;
    }

    public void setTrackingRef(String trackingRef) {
        this.TrackingRef = trackingRef;
    }

    public String getTrackingStatus() {
        return TrackingStatus;
    }

    public void setTrackingStatus(String trackingStatus) {
        this.TrackingStatus = trackingStatus;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        this.StatusSend = statusSend;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        this.CreateDate = createDate;
    }

    public String getInfoDevice() {
        return InfoDevice;
    }

    public void setInfoDevice(String infoDevice) {
        InfoDevice = infoDevice;
    }
}
