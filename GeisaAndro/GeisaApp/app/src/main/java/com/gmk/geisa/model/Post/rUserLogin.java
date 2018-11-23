package com.gmk.geisa.model.Post;

import java.io.Serializable;

/**
 * Created by kenjinsan on 7/12/17.
 */

public class rUserLogin implements Serializable {
   private String uname;
    private  String pass;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
