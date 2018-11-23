package com.gmk.geisa.model.Post;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenjinsan on 5/29/17.
 */

public class rPoPost implements Serializable {
    private String salesId;
    private List<rStringList> StringList = null;

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<rStringList> getPolist() {
        return StringList;
    }

    public void setPolist(List<rStringList> polist) {
        this.StringList = polist;
    }
}
