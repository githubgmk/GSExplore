package com.gmk.geisa.jsoup;


import com.gmk.geisa.jsoup.jsoup.annotations.SoupQuery;

/**
 * Created by kenjinsan on 9/18/17.
 */

@SoupQuery("div.details-section.metadata")
public class AppVersion {
    public final AppDetail detail;

    public AppVersion(AppDetail detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return String.format("%s", detail);
    }
}
