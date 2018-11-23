package com.gmk.geisa.jsoup;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlayStoreApi {
  String BASE_URL = "https://play.google.com/store/";

  @GET("apps/details?id=com.gmk.geisa")
  Call<AppVersion> getApps();
}
