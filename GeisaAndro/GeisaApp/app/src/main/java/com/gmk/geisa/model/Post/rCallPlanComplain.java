package com.gmk.geisa.model.Post;

import com.gmk.geisa.model.mComplain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenjinsan on 6/6/17.
 */

public class rCallPlanComplain implements Serializable {
    String salesId;
    List<mComplain> callplancomplainlist;

    public rCallPlanComplain() {
    }

    public rCallPlanComplain(String salesId, List<mComplain> callplancomplainlist) {
        this.salesId = salesId;
        this.callplancomplainlist = callplancomplainlist;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<mComplain> getCallplancomplainlist() {
        return callplancomplainlist;
    }

    public void setCallplancomplainlist(List<mComplain> callplancomplainlist) {
        this.callplancomplainlist = callplancomplainlist;
    }
}
