package com.gmk.geisa.model;

public class PromoList {

    private int promoid;
    private String promoname;
    private String promogroup;


    public PromoList(String promoname, int promoid, String promogroup) {
        this.promoid = promoid;
        this.promoname = promoname;
        this.promogroup = promogroup;
    }

    public int getPromoid() {
        return promoid;
    }

    public void setPromoid(int promoid) {
        this.promoid = promoid;
    }

    public String getPromoname() {
        return promoname;
    }

    public void setPromoname(String promoname) {
        this.promoname = promoname;
    }

    public String getPromogroup() {
        return promogroup;
    }

    public void setPromogroup(String promogroup) {
        this.promogroup = promogroup;
    }
}
