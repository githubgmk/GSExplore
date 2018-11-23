package com.gmk.geisa.controller;

import android.util.Log;

import com.gmk.geisa.model.mSession;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestController {

    private static RestController instance = null;
    //RetroClient retrofit;
    private ResultReadyCallback callback;

    private static final String BASE_URL = "http://139.0.7.220/servergeisa/geisa/";
    private APIService service;
    List<mSession> users = null;
    boolean success = false;


    public RestController() {
        /*Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
          service = retrofit.create(APIService.class);
                */

    }


    public void setCallbackUser(ResultReadyCallback callback) {
        this.callback = callback;
    }

    /*public boolean createUser(final Context ctx, User user) {
        Call<User> u = service.createUser(user);
        u.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                success = response.isSuccessful();
                if(success) {
                    Toast.makeText(ctx, "User Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("REST", t.getMessage());
                Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();
            }


        });
        return success;
    }*/



    public interface ResultReadyCallback {
        public void resultReady(List<mSession> users);
    }
}