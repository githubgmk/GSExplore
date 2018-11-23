package com.gmk.geisa.activities.Promo1;

public class MyData {

    private int PromoId;
    private String PromoName,EndDate;

    public MyData(int promoId, String promoName, String endDate) {
        PromoId = promoId;
        PromoName = promoName;
        EndDate = endDate;
    }

    public int getPromoId() {
        return PromoId;
    }

    public void setPromoId(int promoId) {
        PromoId = promoId;
    }

    public String getPromoName() {
        return PromoName;
    }

    public void setPromoName(String promoName) {
        PromoName = promoName;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
