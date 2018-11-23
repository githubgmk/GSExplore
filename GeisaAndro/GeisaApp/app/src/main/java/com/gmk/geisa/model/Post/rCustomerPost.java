package com.gmk.geisa.model.Post;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenjinsan on 4/28/17.
 */

public class rCustomerPost implements Serializable {

    private String salesId;
    private List<rCustomerlist> customerlist = null;

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public List<rCustomerlist> getCustomerlist() {
        return customerlist;
    }

    public void setCustomerlist(List<rCustomerlist> customerlist) {
        this.customerlist = customerlist;
    }


}
