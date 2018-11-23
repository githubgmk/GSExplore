package com.gmk.geisa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kenjin on 1/12/2017.
 */

public class PostResult implements Serializable {
    @SerializedName(value="errNot", alternate={"notError"})
    private boolean errNot;
    @SerializedName("errId")
    private int errId;
    @SerializedName("_Id")
    private String _Id;
    @SerializedName(value="errMsg", alternate={"error_msg"})
    private String errMsg;
    @SerializedName("errMsgInt")
    private  double errMsgInt;
    @SerializedName(value="errTag", alternate={"tag"})
    private String errTag;
    @SerializedName(value="msgA", alternate={"a"})
    private String msgA;
    @SerializedName(value="msgB", alternate={"b"})
    private String msgB;
    @SerializedName(value="msgC", alternate={"c"})
    private String msgC;
    @SerializedName(value="msgD", alternate={"d"})
    private String msgD;
    @SerializedName(value="msgE", alternate={"e"})
    private String msgE;
    @SerializedName(value="msgF", alternate={"f"})
    private String msgF;
    @SerializedName(value="msgG", alternate={"g"})
    private String msgG;
    @SerializedName(value="msgBol", alternate={"msg_Bol"})
    private  boolean msgBol;

    public PostResult() {
    }

    public PostResult(boolean errNot, String errMsg) {
        this.errNot = errNot;
        this.errMsg = errMsg;
    }

    public PostResult(boolean errNot, int errId, String errMsg, double errMsgInt, String errTag, String msgA, String msgB, String msgC, String msgD, String msgE, String msgF, String msgG, boolean msgBol) {
        this.errNot = errNot;
        this.errId=errId;
        this.errMsg = errMsg;
        this.errMsgInt = errMsgInt;
        this.errTag = errTag;
        this.msgA = msgA;
        this.msgB = msgB;
        this.msgC = msgC;
        this.msgD = msgD;
        this.msgE = msgE;
        this.msgF = msgF;
        this.msgG=msgG;
        this.msgBol=msgBol;
    }

    public String get_Id() {
        return _Id;
    }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }

    public boolean isMsgBol() {
        return msgBol;
    }

    public boolean isErrNot() {
        return errNot;
    }

    public void setErrNot(boolean errNot) {
        this.errNot = errNot;
    }

    public int getErrId() {
        return errId;
    }

    public void setErrId(int errId) {
        this.errId = errId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public double getErrMsgInt() {
        return errMsgInt;
    }

    public void setErrMsgInt(double errMsgInt) {
        this.errMsgInt = errMsgInt;
    }

    public String getErrTag() {
        return errTag;
    }

    public void setErrTag(String errTag) {
        this.errTag = errTag;
    }

    public String getMsgA() {
        return msgA;
    }

    public void setMsgA(String msgA) {
        this.msgA = msgA;
    }

    public String getMsgB() {
        return msgB;
    }

    public void setMsgB(String msgB) {
        this.msgB = msgB;
    }

    public String getMsgC() {
        return msgC;
    }

    public void setMsgC(String msgC) {
        this.msgC = msgC;
    }

    public String getMsgD() {
        return msgD;
    }

    public void setMsgD(String msgD) {
        this.msgD = msgD;
    }

    public String getMsgE() {
        return msgE;
    }

    public void setMsgE(String msgE) {
        this.msgE = msgE;
    }

    public String getMsgF() {
        return msgF;
    }

    public void setMsgF(String msgF) {
        this.msgF = msgF;
    }

    public String getMsgG() {
        return msgG;
    }

    public void setMsgG(String msgG) {
        this.msgG = msgG;
    }

    public boolean getMsgBol() {
        return msgBol;
    }

    public void setMsgBol(boolean msgBol) {
        this.msgBol = msgBol;
    }
}
