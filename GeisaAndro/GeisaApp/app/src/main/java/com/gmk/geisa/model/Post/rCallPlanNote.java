package com.gmk.geisa.model.Post;

import com.gmk.geisa.model.mCallPlanNote;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenjinsan on 6/6/17.
 */

public class rCallPlanNote implements Serializable {
    String salesId;
    List<mCallPlanNote> callplannotelist;

    public rCallPlanNote() {
    }

    public rCallPlanNote(String salesId, List<mCallPlanNote> callplannotelist) {
        this.salesId = salesId;
        this.callplannotelist = callplannotelist;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<mCallPlanNote> getCallplannotelist() {
        return callplannotelist;
    }

    public void setCallplannotelist(List<mCallPlanNote> callplannotelist) {
        this.callplannotelist = callplannotelist;
    }
}
