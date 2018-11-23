package com.gmk.geisa.model.Post;

import com.gmk.geisa.model.mCallPlanNote;
import com.gmk.geisa.model.mDemo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenjinsan on 6/6/17.
 */

public class rCallPlanDemo implements Serializable {
    String salesId;
    List<mDemo> callplandemolist;

    public rCallPlanDemo() {
    }

    public rCallPlanDemo(String salesId, List<mDemo> callplandemolist) {
        this.salesId = salesId;
        this.callplandemolist = callplandemolist;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<mDemo> getCallplandemolist() {
        return callplandemolist;
    }

    public void setCallplandemolist(List<mDemo> callplandemolist) {
        this.callplandemolist = callplandemolist;
    }
}
