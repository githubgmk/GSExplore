package com.gmk.geisa.activities.Promo1;

public class Promo {

    private String promoName, promoGroup, promoDetail;

    public Promo(){

    }

    public Promo(String promoName,String promoGroup, String promoDetail){
        this.promoName = promoName;
        this.promoGroup = promoGroup;
        this.promoDetail = promoDetail;

    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoGroup() {
        return promoGroup;
    }

    public void setPromoGroup(String promoGroup) {
        this.promoGroup = promoGroup;
    }

    public String getPromoDetail() {
        return promoDetail;
    }

    public void setPromoDetail(String promoDetail) {
        this.promoDetail = promoDetail;
    }
}