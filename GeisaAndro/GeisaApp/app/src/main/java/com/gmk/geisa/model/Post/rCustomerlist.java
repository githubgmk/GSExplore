package com.gmk.geisa.model.Post;

import java.io.Serializable;

/**
 * Created by kenjinsan on 4/28/17.
 */

public class rCustomerlist implements Serializable {
    private long customerId;

    public long getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId (long customerId)
    {
        this.customerId = customerId;
    }
}
