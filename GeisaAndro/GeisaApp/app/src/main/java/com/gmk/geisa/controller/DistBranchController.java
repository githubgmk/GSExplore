package com.gmk.geisa.controller;

import android.content.Context;
import android.util.Log;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.mDistributorBranch;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjinsan on 5/10/17.
 */

public class DistBranchController {

    private static mDB data;
    private APIService service;
    private static DistBranchController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;
    private ArrayList<mDistributorBranch> list = null;

    public static DistBranchController getInstance(Context context) {
        if (instance == null) {
            instance = new DistBranchController();
        }
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        return instance;
    }

    //calback get update customer
    private CallBackGetData callBackGetData;

    public void setCallBackGetData(CallBackGetData callBackGetData) {
        this.callBackGetData = callBackGetData;
    }

    public  interface CallBackGetData {
        public  void resultReady(ArrayList<mDistributorBranch> customers, boolean hasil);
    }


    /*end callback*/



    public void getAllDistBranchFromServer(final String salesId) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));
            //  Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        //params.put("pass", pass);

        Call<ArrayList<mDistributorBranch>> userlist = service.getDataDistBranch(params);
        userlist.enqueue(new Callback<ArrayList<mDistributorBranch>>() {
            @Override
            public void onResponse(Call<ArrayList<mDistributorBranch>> call, Response<ArrayList<mDistributorBranch>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mDistributorBranch> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        list =result;
                        data.InsertUpateAllDistBranch(result);

                    }
                    callBackGetData.resultReady(list,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetData.resultReady(list,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mDistributorBranch>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetData.resultReady(list,false);
            }


        });
    }

    public  ArrayList<mDistributorBranch> getAllDistBranch(){
        return  data.getDistBranchAll();
    }



}
