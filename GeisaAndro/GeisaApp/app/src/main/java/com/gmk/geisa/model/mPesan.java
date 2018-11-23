package com.gmk.geisa.model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjin on 9/4/2016.
 */
public class mPesan implements Serializable {

    private long id;
    private int idPengirim;
    private String pengirim;
    private int idPenerima;
    private String penerima;
    private String judul;
    private String isiPesan;
    private String fcmid;
    private String typePesan;
    private String dateSend;
    private String dateRead;
    private String statusPesan;
    private boolean statusSend;
    private String refId;
    private boolean newMessage;


    public mPesan() {
    }

    public mPesan(long id, int idPengirim, String pengirim, int idPenerima, String penerima, String judul, String isiPesan, String fcmid,
                  String typePesan, String dateSend, String dateRead, String statusPesan, String refid, boolean statussend) {
        this.id = id;
        this.idPengirim = idPengirim;
        this.pengirim = pengirim;
        this.idPenerima = idPenerima;
        this.penerima = penerima;
        this.judul = judul;
        this.isiPesan = isiPesan;
        this.fcmid = fcmid;
        this.typePesan = typePesan;
        this.dateSend = dateSend;
        this.dateRead = dateRead;
        this.statusPesan = statusPesan;
        this.refId = refid;
        this.statusSend = statussend;
    }

    public mPesan(long id, int idPengirim, String pengirim, int idPenerima, String penerima, String judul, String isiPesan, String fcmid,
                  String typePesan, String dateSend, String dateRead, String statusPesan, String refid, boolean statussend, boolean newMessage) {
        this.id = id;
        this.idPengirim = idPengirim;
        this.pengirim = pengirim;
        this.idPenerima = idPenerima;
        this.penerima = penerima;
        this.judul = judul;
        this.isiPesan = isiPesan;
        this.fcmid = fcmid;
        this.typePesan = typePesan;
        this.dateSend = dateSend;
        this.dateRead = dateRead;
        this.statusPesan = statusPesan;
        this.refId = refid;
        this.statusSend = statussend;
        this.newMessage = newMessage;
    }

    public mPesan(long id, int idPengirim, String pengirim, int idPenerima, String penerima, String judul, String isiPesan, String fcmid, String typePesan, String statusPesan) {
        this.id = id;
        this.idPengirim = idPengirim;
        this.pengirim = pengirim;
        this.idPenerima = idPenerima;
        this.penerima = penerima;
        this.judul = judul;
        this.isiPesan = isiPesan;
        this.fcmid = fcmid;
        this.typePesan = typePesan;
        this.statusPesan = statusPesan;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdPengirim() {
        return idPengirim;
    }

    public void setIdPengirim(int idPengirim) {
        this.idPengirim = idPengirim;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public int getIdPenerima() {
        return idPenerima;
    }

    public void setIdPenerima(int idPenerima) {
        this.idPenerima = idPenerima;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsiPesan() {
        return isiPesan;
    }

    public void setIsiPesan(String isiPesan) {
        this.isiPesan = isiPesan;
    }

    public String getFcmid() {
        return fcmid;
    }

    public void setFcmid(String fcmid) {
        this.fcmid = fcmid;
    }

    public String getTypePesan() {
        return typePesan;
    }

    public void setTypePesan(String typePesan) {
        this.typePesan = typePesan;
    }

    public String getStatusPesan() {
        return statusPesan;
    }

    public void setStatusPesan(String statusPesan) {
        this.statusPesan = statusPesan;
    }

    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }

    public String getDateRead() {
        return dateRead;
    }

    public void setDateRead(String dateRead) {
        this.dateRead = dateRead;
    }

    public boolean getStatusSend() {
        return statusSend;
    }

    public void setStatusSend(boolean statusSend) {
        this.statusSend = statusSend;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public boolean isStatusSend() {
        return statusSend;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }
}
