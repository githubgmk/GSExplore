package com.gmk.geisa.model.Post;

import com.gmk.geisa.model.mCustomer;

import java.io.Serializable;

/**
 * Created by kenjinsan on 5/16/17.
 */

public class rCustomerAdd implements Serializable {
    private String salesId;
    private  int GroupCust;
    private mCustomer customer;


    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public int getGroupCust() {
        return GroupCust;
    }

    public void setGroupCust(int groupCust) {
        GroupCust = groupCust;
    }

    public mCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(mCustomer customer) {
        this.customer = customer;
    }
}
