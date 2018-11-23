package com.gmk.geisa.model.Post;

import com.gmk.geisa.model.mCallPlan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenjinsan on 5/24/17.
 */

public class rCallPlan implements Serializable {
    String salesId;
    List<mCallPlan> callplanlist;

    public rCallPlan() {
    }

    public rCallPlan(String salesId, List<mCallPlan> listCallPlan) {
        this.salesId = salesId;
        this.callplanlist = listCallPlan;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<mCallPlan> getListCallPlan() {
        return callplanlist;
    }

    public void setListCallPlan(List<mCallPlan> listCallPlan) {
        this.callplanlist = listCallPlan;
    }
}
