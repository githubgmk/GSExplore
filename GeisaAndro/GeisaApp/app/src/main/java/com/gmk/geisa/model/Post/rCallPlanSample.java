package com.gmk.geisa.model.Post;

import com.gmk.geisa.model.mSample;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenjinsan on 6/6/17.
 */

public class rCallPlanSample implements Serializable {
    String salesId;
    List<mSample> callplansamplelist;

    public rCallPlanSample() {
    }

    public rCallPlanSample(String salesId, List<mSample> callplansamplelist) {
        this.salesId = salesId;
        this.callplansamplelist = callplansamplelist;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<mSample> getCallplansamplelist() {
        return callplansamplelist;
    }

    public void setCallplansamplelist(List<mSample> callplansamplelist) {
        this.callplansamplelist = callplansamplelist;
    }
}
