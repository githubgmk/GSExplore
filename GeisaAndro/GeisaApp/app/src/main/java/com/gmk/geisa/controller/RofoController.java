package com.gmk.geisa.controller;

import android.content.Context;
import android.util.Log;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mRofoAktualisasi;
import com.gmk.geisa.model.mRofoTarget;
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

public class RofoController {
//belum dibenerin
    private static mDB data;
    private APIService service;
    private static RofoController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;
    private ArrayList<mRofo> list = new ArrayList<>();
    private ArrayList<mRofoTarget> list1 = new ArrayList<>();
    private ArrayList<mRofoAktualisasi> list2 = new ArrayList<>();

    public static RofoController getInstance(Context context) {
        if (instance == null) {
            instance = new RofoController();
        }
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        return instance;
    }

    private CallBackGetDataSync callBackGetDataSync;

    public void setCallBackGetDataSync(CallBackGetDataSync callBackGetDataSync) {
        this.callBackGetDataSync = callBackGetDataSync;
    }

    public  interface CallBackGetDataSync {
        public  void resultReady(boolean hasil);
    }


    //calback get
    private CallBackGetDataRofo callBackGetDataRofo;

    public void setCallBackGetDataRofo(CallBackGetDataRofo callBackGetDataRofo) {
        this.callBackGetDataRofo = callBackGetDataRofo;
    }

    public  interface CallBackGetDataRofo {
        public  void resultReady(ArrayList<mRofo> customers, boolean hasil);
    }

    private CallBackGetDataRofoTarget callBackGetDataRofoTarget;

    public void setCallBackGetDataRofoTarget(CallBackGetDataRofoTarget callBackGetDataRofoTarget) {
        this.callBackGetDataRofoTarget = callBackGetDataRofoTarget;
    }

    public  interface CallBackGetDataRofoTarget {
        public  void resultReady(ArrayList<mRofoTarget> customers, boolean hasil);
    }

    private CallBackGetDataRofoAktualisasi callBackGetDataRofoAktualisasi;

    public void setCallBackGetDataRofoAktualisasi(CallBackGetDataRofoAktualisasi callBackGetDataRofoAktualisasi) {
        this.callBackGetDataRofoAktualisasi = callBackGetDataRofoAktualisasi;
    }

    public  interface CallBackGetDataRofoAktualisasi {
        public  void resultReady(ArrayList<mRofoAktualisasi> customers, boolean hasil);
    }
    /*end callback*/

    public void updateRofoToServer(final ArrayList<mRofo> po,final String salesId) {
        if(po!=null){
            data.InsertUpateAllRofo(po);
        }
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
        //Map<String, String> params = new HashMap<String, String>();
        //params.put("salesId", salesId);
        //params.put("pass", pass);

        final ArrayList<mRofo> rofo=data.getRofoBySalesIdStatus(salesId,"1");
        //Log.e("ada roro","ada rofo"+rofo.size());
        Call<ArrayList<mRofo>> userlist = service.updateRofo(rofo);
        userlist.enqueue(new Callback<ArrayList<mRofo>>() {
            @Override
            public void onResponse(Call<ArrayList<mRofo>> call, Response<ArrayList<mRofo>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    if(response.code()==200){
                       ArrayList<mRofo> result = response.body();
                        if (result != null) {
                            //list.clear();
                            //list.addAll(result);
                            for(mRofo  cu: rofo){
                                data.updateRofoStatus(cu.getRofoId(),String.valueOf(cu.getSalesmanId()),String.valueOf(cu.getYear()),
                                        String.valueOf(cu.getMonth()),0);
                               // Log.e(" isi up",cu.getProductCode());
                            }
                            for(mRofo  cu: result){
                               // Log.e(" isi del",cu.getProductCode());
                                data.deleteRofoCustMonth(cu.getSalesmanId(),cu.getYear(),cu.getMonth(),cu.getCustId(),cu.getDistBranchId());
                            }
                            data.InsertUpateAllRofo(result);

                        }
                    }
                    //Log.e("isi respion", response.body().toString());

                }else{
                    Log.e("error",response.message()+","+response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mRofo>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
               //callBackGetData.resultReady(list,false);
            }


        });
    }

    public void reSendRofoToServer(final ArrayList<mRofo> rofo,final String salesId) {
        if(rofo!=null & rofo.size()>0) {
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

            //Log.e("ada roro","ada rofo"+rofo.size());
            Call<ArrayList<mRofo>> userlist = service.updateRofo(rofo);
            userlist.enqueue(new Callback<ArrayList<mRofo>>() {
                @Override
                public void onResponse(Call<ArrayList<mRofo>> call, Response<ArrayList<mRofo>> response) {
                    //Log.e("isi respion cek update", response.code()+" isi code");
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            ArrayList<mRofo> result = response.body();
                            if (result != null) {
                                //list.clear();
                                //list.addAll(result);
                                for (mRofo cu : rofo) {
                                    data.updateRofoStatus(cu.getRofoId(), String.valueOf(cu.getSalesmanId()), String.valueOf(cu.getYear()),
                                            String.valueOf(cu.getMonth()), 0);
                                    // Log.e(" isi up",cu.getProductCode());
                                }
                                for (mRofo cu : result) {
                                    // Log.e(" isi del",cu.getProductCode());
                                    data.deleteRofoCustMonth(cu.getSalesmanId(), cu.getYear(), cu.getMonth(), cu.getCustId(), cu.getDistBranchId());
                                }
                                data.InsertUpateAllRofo(result);
                                callBackGetDataSync.resultReady(true);
                            } else {
                                callBackGetDataSync.resultReady(false);
                            }
                        }
                        //Log.e("isi respion", response.body().toString());

                    } else {
                        Log.e("error", response.message() + "," + response.code());
                        callBackGetDataSync.resultReady(false);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<mRofo>> call, Throwable t) {
                    Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                    callBackGetDataSync.resultReady(false);
                }


            });
        }else {
            callBackGetDataSync.resultReady(true);
        }
    }

    public void getAllRofoAktualisasiFromServer(final String salesId,final  String year) {
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
        params.put("year",year);

        Call<ArrayList<mRofoAktualisasi>> userlist = service.getDataRofoAktualisasi(params);
        userlist.enqueue(new Callback<ArrayList<mRofoAktualisasi>>() {
            @Override
            public void onResponse(Call<ArrayList<mRofoAktualisasi>> call, Response<ArrayList<mRofoAktualisasi>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body());
                    ArrayList<mRofoAktualisasi> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        list2.clear();
                        list2 =result;
                        data.InsertUpateAllRofoAktualisasi(result);

                    }
                    callBackGetDataRofoAktualisasi.resultReady(list2,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetDataRofoAktualisasi.resultReady(list2,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mRofoAktualisasi>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetDataRofoAktualisasi.resultReady(list2,false);
            }


        });
    }

    public void getAllRofoFromServer(final String salesId,final String year, final String month) {
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
        params.put("month", month);
        params.put("year",year);

        Call<ArrayList<mRofo>> userlist = service.getDataRofo(params);
        userlist.enqueue(new Callback<ArrayList<mRofo>>() {
            @Override
            public void onResponse(Call<ArrayList<mRofo>> call, Response<ArrayList<mRofo>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mRofo> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        list.clear();
                        list =result;
                        data.InsertUpateAllRofo(result);

                    }
                    callBackGetDataRofo.resultReady(list,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetDataRofo.resultReady(list,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mRofo>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetDataRofo.resultReady(list,false);
            }


        });
    }

    public void getAllTargetFromServer(final String salesId,final String year) {
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
        params.put("year", year);

        Call<ArrayList<mRofoTarget>> userlist = service.getDataRofoTarget(params);
        userlist.enqueue(new Callback<ArrayList<mRofoTarget>>() {
            @Override
            public void onResponse(Call<ArrayList<mRofoTarget>> call, Response<ArrayList<mRofoTarget>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mRofoTarget> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        list1.clear();
                        list1 =result;
                        data.InsertUpateAllTargetRofo(result);

                    }
                    callBackGetDataRofoTarget.resultReady(list1,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetDataRofoTarget.resultReady(list1,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mRofoTarget>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetDataRofoTarget.resultReady(list1,false);
            }


        });
    }

    public ArrayList<mRofoAktualisasi> getRofoAktualBySalesIdYear(String salesId, int tahun) {
        return  data.getRofoAktualBySalesIdYear(salesId,tahun);
    }

    public  ArrayList<mRofo> getAllRofoPending(String salesId){
        return  data.getRofoBySalesIdStatus(salesId,"1");
    }

    public  ArrayList<mRofo> getRofoBySalesId(String salesId){
        return  data.getRofoBySalesId(salesId);
    }
    public  ArrayList<mCustomer> getCustomerBySalesIdYearMonth(String salesId, int year, int month){
        return  data.getCustomerBySalesIdYearMonth(salesId,String.valueOf(year),String.valueOf(month));
    }

    public  ArrayList<mRofo> getRofoBySalesIdYearMonth(String salesId,int year,int month){
        return  data.getRofoBySalesIdYearMonth(salesId,String.valueOf(year),String.valueOf(month));
    }
    public  ArrayList<mRofo> getRofoBySalesIdYearMonthApprove(String salesId,int year,int month){
        return  data.getRofoBySalesIdYearMonthApprove(salesId,String.valueOf(year),String.valueOf(month));
    }
    public  ArrayList<mRofo> getRofoBySalesIdYearMonthNotApprove(String salesId,int year,int month){
        return  data.getRofoBySalesIdYearMonthNotApprove(salesId,String.valueOf(year),String.valueOf(month));
    }
    public  ArrayList<mRofo> getRofoBySalesIdYearMonthCust(String salesId, int year, int month, String custid, String custbranchid){
        return  data.getRofoBySalesIdYearMonthCust(salesId,String.valueOf(year),String.valueOf(month),custid,custbranchid);
    }

    public ArrayList<mRofoTarget> getTargetRofoBySalesId(String salesId){
        return  data.getTargetRofoBySalesId(salesId);
    }

    public ArrayList<mRofoTarget> getTargetRofoBySalesId(String salesId,int year,int month){
        return  data.getTargetRofoBySalesIdYearMonth(salesId,String.valueOf(year),String.valueOf(month));
    }





}
