package com.gmk.geisa.service;

import android.util.Log;

import com.gmk.geisa.helper.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Pratik Butani
 */
public class RetroClient {

    /********
     * URLS
     *******/

    public static Retrofit retrofit = null;
    public static Retrofit getClient(String Authorization){
        if(retrofit==null){
           // Log.e("ada",url);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor(Authorization)).build();
            retrofit  = new Retrofit.Builder()
                    .baseUrl(Constants.APP_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getClient(String Authorization,int timeout){
        if(retrofit==null){
            // Log.e("ada",url);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor(Authorization)).build();
            retrofit  = new Retrofit.Builder()
                    .baseUrl(Constants.APP_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit retrofitbackup = null;
    public static Retrofit getClientBackup(String Authorization){
        if(retrofitbackup==null){
          //  Log.e("ada",url);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor(Authorization)).build();
            retrofitbackup  = new Retrofit.Builder()
                    .baseUrl(Constants.BACKUP_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitbackup;
    }
    public static Retrofit getClientBackup(String Authorization,int timeout){
        if(retrofitbackup==null){
            //  Log.e("ada",url);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor(Authorization)).build();
            retrofitbackup  = new Retrofit.Builder()
                    .baseUrl(Constants.BACKUP_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitbackup;
    }
    public static boolean checkUrl(){
        boolean exists = false;
        HostSelectionInterceptor interceptor = new HostSelectionInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Request request = new Request.Builder()
                .url("http://www.coca-cola.com/robots.txt")
                .build();

        okhttp3.Call call1 = okHttpClient.newCall(request);
        try {
            okhttp3.Response response1 = call1.execute();
            Log.e("isi err mal",response1.code()+" xx");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("isi err mal",e.getMessage());
        }

        return  exists;
    }
    public static final class HostSelectionInterceptor implements Interceptor {
        private volatile String host;

        public void setHost(String host) {
            this.host = host;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String host = this.host;
            if (host != null) {
                HttpUrl newUrl = request.url().newBuilder()
                        .host(host)
                        .build();
                request = request.newBuilder()
                        .url(newUrl)
                        .build();
            }
            return chain.proceed(request);
        }
    }
//end test
}
