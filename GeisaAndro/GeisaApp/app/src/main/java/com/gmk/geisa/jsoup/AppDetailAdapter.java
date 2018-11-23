package com.gmk.geisa.jsoup;


import org.jsoup.nodes.Element;

import java.io.IOException;

public class AppDetailAdapter extends com.gmk.geisa.jsoup.jsoup.ElementAdapter<AppDetail> {
  @Override
  public String query() {
    return "div.details-section-contents";
  }

  @Override
  public AppDetail fromElement(Element element) throws IOException {
    //String lastUpdate = element.select("a.title").first().attr("title");
    String lastUpdate = element.select("[itemprop=datePublished]").first().text();
    String requiredment = element.select("[itemprop=operatingSystems]").first().text();
    String version = element.select("[itemprop=softwareVersion]").first().text();
    String elemen =element.select("div.content").eq(5).text();
    String developer = element.select("div.content").eq(6).text();
    return new AppDetail(lastUpdate, requiredment,version,elemen,developer);
  }
}
