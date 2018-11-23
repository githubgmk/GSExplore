package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 5/31/17.
 */

public class mTrackingPicture implements Serializable {
    private String TrackingPictureId;
    private String PictureRef;
    private String Picture;
    private String StatusBattery;
    private String Note;
    private String CreatedDate;
    private String CreatedBy;
    private int StatusSend;

    public mTrackingPicture(String trackingPictureId, String pictureRef, String picture, String statusBattery, String note, String createdDate, String createdBy, int statusSend) {
        TrackingPictureId = trackingPictureId;
        PictureRef = pictureRef;
        Picture = picture;
        StatusBattery = statusBattery;
        Note = note;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        StatusSend = statusSend;
    }

    public String getTrackingPictureId() {
        return TrackingPictureId;
    }

    public void setTrackingPictureId(String trackingPictureId) {
        TrackingPictureId = trackingPictureId;
    }

    public String getPictureRef() {
        return PictureRef;
    }

    public void setPictureRef(String pictureRef) {
        PictureRef = pictureRef;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getStatusBattery() {
        return StatusBattery;
    }

    public void setStatusBattery(String statusBattery) {
        StatusBattery = statusBattery;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public int getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(int statusSend) {
        StatusSend = statusSend;
    }
}
