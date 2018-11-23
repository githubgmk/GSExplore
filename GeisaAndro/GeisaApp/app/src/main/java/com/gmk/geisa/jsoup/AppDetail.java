package com.gmk.geisa.jsoup;


import com.gmk.geisa.jsoup.jsoup.annotations.SoupAdapter;

@SoupAdapter(AppDetailAdapter.class)
public class AppDetail {
  public final String lastUpdate;
  public final String requiredment;
  public  final String version;
  public  final String elemen;
  public  final String developer;

  public AppDetail(String lastUpdate, String requiredment, String version, String elemen, String developer) {
    this.lastUpdate = lastUpdate;
    this.requiredment = requiredment;
    this.version=version;
    this.elemen=elemen;
    this.developer=developer;
  }

  @Override
  public String toString() {
    return String.format("lastupdate: %s requiredment: %s version: %s elemen: %s developer: %s", lastUpdate, requiredment,version,elemen,developer);
  }
}
